package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Sanduhr extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `sanduhr` command
        if (!e.getMessage().isMentioned(e.getJDA().getUserById(Lib.YOUR_ID))) {
            return;
        }

        //If `Sanduhr` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        e.getMessage().delete().queue();
        e.getChannel().sendMessage("Please dont mention " + e.getJDA().getUserById(Lib.YOUR_ID).getName() + "!").queue();
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
}