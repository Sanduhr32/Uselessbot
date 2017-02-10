package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
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

        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Not the `kick` command
        if (!syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
            return;
        }

        //If `kick` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist().contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.Error_perms).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        List<User> u = e.getMessage().getMentionedUsers();
        if (!u.isEmpty() && !syntaxx[1].isEmpty()) {
            for ( User user : u ) {
                e.getGuild().getController().kick(user.getId()).queue();
                e.getJDA().getUserById(user.getId()).openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage("You were kicked for " + syntaxx[1] + "!"));
            }
        }
        else {
            e.getChannel().sendMessage(Lib.Error_target).queue();
        }

        Lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
    }
    public String getName() {
        return Kick.class.getName();
    }
    public String getDescription() {
        return "Kicks all mentioned user";
    }
}