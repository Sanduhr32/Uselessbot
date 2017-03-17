package com.sanduhr.main;

import com.sanduhr.main.utils.ScheduleUtil;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

import static com.sanduhr.main.Lib.*;

/**
 * Created by Sanduhr on 07.03.2017
 * This class tests if the provided command exists
 */
class Unknown extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getAuthor().isBot()) {
            return;
        }

        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(ERROR_GUILDS).queue();
        }

        if (!e.getMessage().getContent().startsWith("??")) {
            return;
        }

        String[] syntax = e.getMessage().getContent().split("\\s+");
        String cmd = syntax[0].replace("??","").toLowerCase();

        String desc = getCmdMap().get(cmd);
        if (desc == null) {
            e.getChannel().sendMessage("Unknown command! Type `??help`!").queue(msg->
                    ScheduleUtil.scheduledAction(()->msg.delete().queue(),10, TimeUnit.SECONDS));
        }
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(),e.getResponseNumber(),e.getMessage()));
    }
}
