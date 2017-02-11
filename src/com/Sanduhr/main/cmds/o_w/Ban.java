package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class ban extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Not the `ban` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "ban")) {
            return;
        }

        //If `ban` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!lib.getWhitelist().contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(lib.Error_perms).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();
        List<User> u = e.getMessage().getMentionedUsers();
        int i = Integer.parseInt(syntaxx[2]);
        if (!u.isEmpty() && !syntaxx[1].isEmpty()) {
            for ( User user : u ) {
                e.getGuild().getController().ban(user.getId(), i).queue();
                e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You are banned for " + syntaxx[1] + "!").queue();
            }
        }
        else {
            e.getChannel().sendMessage(lib.Error_target).queue();
        }

        lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        lib.getCmdMap().put(getName(), getDescription());
    }
    public String getName() {
        return "Ban";
    }
    public String getDescription() {
        return "Swings the ban hammer for each mentioned user";
    }
}