package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class Kick extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");
        String[] syntaxx = e.getMessage().getContentDisplay().split(":");

        //Not the `kick` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "kick")) {
            return;
        }

        //If `kick` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getIdLong()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        List<User> u = e.getMessage().getMentionedUsers();
        Member self = e.getGuild().getSelfMember();

        if (!self.hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getChannel().sendMessage("I don't have permissions").queue();
            return;
        }

        if (self.hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        if (!u.isEmpty() && syntaxx.length == 2) {

            if (!self.hasPermission(Permission.KICK_MEMBERS)) {
                e.getChannel().sendMessage("I don't have permissions").queue();
                return;
            }

            for ( User user : u ) {

                if (!self.canInteract(e.getGuild().getMember(user))) {
                    e.getChannel().sendMessage("I cant kick..").queue();
                    continue;
                }

                e.getGuild().getController().kick(user.getId()).queue();
                e.getJDA().getUserById(user.getId()).openPrivateChannel()
                        .queue(chan -> chan.sendMessage("You were kicked for " + syntaxx[1] + "!").queue());
            }
        }
        else {
            e.getChannel().sendMessage(Lib.ERROR_TARGET).queue();
        }

        Lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Kick.class.getSimpleName().toLowerCase();
    }
    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "Kicks all mentioned user";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " @USER :REASON`";
    }
}