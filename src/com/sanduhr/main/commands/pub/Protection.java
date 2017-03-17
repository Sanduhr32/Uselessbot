package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import com.sanduhr.main.utils.ScheduleUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

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
        if (!e.getMessage().isMentioned(e.getGuild().getOwner().getUser())
                ||e.getMember().isOwner()
                ||Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getId())
                ||e.getMessage().getRawContent().startsWith("`Important`")) {
            return;
        }

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        e.getChannel().sendMessage("Please don't mention " + e.getGuild().getOwner().getUser().getName() + "!\nIf its really `Important` insert a ```\n`Important`\n``` at the begin of your message!").queue(
                msg -> ScheduleUtil.scheduledAction(()->msg.delete().queue(),20, TimeUnit.MINUTES)
        );
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
}