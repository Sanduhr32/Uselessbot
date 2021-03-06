package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class Ban extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContentRaw().split("\\s+",2);
        String[] syntaxx = e.getMessage().getContentRaw().split(":",3);

        //Not the `ban` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "ban")) {
            return;
        }

        //If `ban` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Tierutils.getWhiteListForGuild(e.getGuild()).contains(e.getAuthor().getIdLong()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        if (syntax.length < 2 && syntaxx.length < 3) {
            e.getChannel().sendMessage("Error :eyes:").queue();
            return;
        }

        Lib.receivedcmd++;
        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }
        List<User> u = e.getMessage().getMentionedUsers();
        int i = Integer.parseInt(syntaxx[2]);
        if (!u.isEmpty() && !syntaxx[1].isEmpty()) {
            for ( User user : u ) {
                if (!e.getMember().canInteract(e.getGuild().getMember(user))) {
                    e.getChannel().sendMessage("I cant ban..").queue();
                    continue;
                }
                e.getGuild().getController().ban(user.getId(), i).queue();
                //e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You are banned for " + syntaxx[1] + "!").queue();
            }
        }
        else {
            e.getChannel().sendMessage(Lib.ERROR_TARGET).queue();
        }

        Lib.executedcmd++;
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onReady(ReadyEvent e) {
        init();
    }

    private void init() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Ban.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Swings the ban hammer for each mentioned user";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " @USER :REASON:DAYS`";
    }
}