package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Protection extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //If `Protection` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            return;
        }

        //Not the `Protection` command
        if (!e.getMessage().isMentioned(e.getGuild().getOwner().getUser())||e.getMember().isOwner()||Lib.getWhitelist().contains(e.getAuthor().getId())) {
            return;
        }

        if (e.getGuild().getId().equals("280264062753964032")) {
            return;
        }

        e.getMessage().delete().queue();
        e.getChannel().sendMessage("Please dont mention " + e.getGuild().getOwner().getUser().getName() + "!").queue();
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
}