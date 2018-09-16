package com.sanduhr.discord;

import com.sanduhr.discord.commands.Experimental.Eval_exp;
import com.sanduhr.discord.commands.pub.Info;
import com.sanduhr.discord.commands.sanduhr.Eval;
import com.sanduhr.discord.commands.sanduhr.Vote;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Sanduhr on 20.04.2017
 */
public class MessageManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (!e.getMessage().getContentDisplay().startsWith("??")) {
            return;
        }

        String[] parts = e.getMessage().getContentDisplay().split("\\s+", 2);
        parts[0] = parts[0].replaceFirst("\\?{2}","");

        if (!Lib.commands.contains(parts[0])) {
//            if (!parts[0].contains("?") || !parts[0].isEmpty()) {
//                if (e.getTextChannel().canTalk()) {
//                    e.getChannel().sendMessage("Unknown command! Type `??help`!").queue(msg -> msg.delete().queueAfter(20, TimeUnit.SECONDS));
//                } else {
//                    e.getAuthor().openPrivateChannel().complete().sendMessage("Unknown command! Type `??help`!\n\n" +
//                    "*Btw i'm sorry i can't write in " + e.getChannel().getName() + " at " + e.getGuild().getName() + "*").queue(msg->msg.delete().queueAfter(20, TimeUnit.SECONDS));
//                }
//            }
            return;
        }
        switch (parts[0]) {
            case "eval": {
                if (Useless.EXPERIMENTAL) {
                    Eval_exp.run(e, e.getMessage().getContentRaw(), false);
                } else {
                    Eval.run(e, e.getMessage().getContentRaw(), false);
                }
                break;
            }
            case "vote": {
                Vote.run(e, e.getMessage().getContentRaw(), false);
                break;
            }
            case "info": {
                Info.run(e, e.getMessage().getContentRaw(), false);
                break;
            }
            case "say": {
                System.err.println(e.getMessage().getContentRaw());
                break;
            }
        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().getReactionEmote().getName().equalsIgnoreCase("\uD83D\uDD02")) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                e.getChannel().getMessageById(e.getMessageId()).queue(msg -> msg.clearReactions().queue());
            }
            onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getChannel().getMessageById(e.getMessageId()).complete()));
        }
    }

    @Override
    public void onReady(ReadyEvent e) {

    }
}
