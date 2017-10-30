package com.sanduhr.discord.utils;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.Useless;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sanduhr on 18.04.2017
 */
public class Voteutils extends ListenerAdapter {
    private static HashMap<Integer, Long> voteMap = new HashMap<>();

    public static void createVote(String text) {
        createVote(new MessageBuilder().append(text));
    }

    public static void createVote(String text, MessageEmbed messageEmbed) {
        createVote(new MessageBuilder().append(text).setEmbed(messageEmbed));
    }

    public static void createVote(MessageEmbed messageEmbed) {
        if (!messageEmbed.isSendable(AccountType.BOT)) {
            Logutils.log.warn("The embed isnt sendable!");
            return;
        }
        createVote(new MessageBuilder().append("Vote#" + voteMap.size()).setEmbed(messageEmbed).build());
    }

    public static void createVote(MessageBuilder messageBuilder) {
        createVote(messageBuilder.append(" | Vote#" + voteMap.size()).build());
    }

    private static void createVote(Message message) {
        Useless.shards.get(0).getGuildById(Lib.LOG_GUILD).getTextChannelsByName("nsfw-votes", true).get(0).sendMessage(message)
                .queue(msg -> {
                    msg.addReaction("\u2705").queue();
                    msg.addReaction("\u274C").queue();
                    voteMap.put(voteMap.size(), msg.getIdLong());
                });
    }

    public static void deleteVote(Integer id, Event event, boolean shouldDelete) {
        JDA jda = Useless.shards.get(0);
        TextChannel textChannel = jda.getGuildById(Lib.LOG_GUILD).getTextChannelsByName("nsfw-votes", true).get(0);
        Long msgId = voteMap.get(id);
        final String[] msg_ = {""};
        textChannel.getMessageById(msgId).queue(message -> {
            msg_[0] = message.getContent();
        });
        textChannel.sendMessage(getResult(id, event)).queue(msg -> {
            textChannel.editMessageById(msgId + "", "**CLOSED**\n~~" + msg_[0] + "~~").queue();
        });
        if (shouldDelete) {
            Useless.shards.get(0).getGuildById(Lib.LOG_GUILD).getTextChannelsByName("nsfw-votes", true).get(0).deleteMessageById(voteMap.get(id)).queue();
        }
    }

    public static String getResult(Integer id, Event event) {
        StringBuilder sb = new StringBuilder();
        final int[] yes = {0};
        final int[] no = {0};

        MessageReceivedEvent MRE = new MessageReceivedEvent(event.getJDA(), event.getResponseNumber(), event.getJDA().getGuildById(Lib.LOG_GUILD).getTextChannelsByName("nsfw-votes", true).get(0).getMessageById(voteMap.get(id)).complete());

        MRE.getMessage().getReactions().forEach(reaction -> {
            if (reaction.getEmote().getName().equalsIgnoreCase("\u2705")) {
                yes[0] = reaction.getCount() - 1;
            } else if (reaction.getEmote().getName().equalsIgnoreCase("\u274C")) {
                no[0] = reaction.getCount() - 1;
            }
        });

        if (yes[0] == 0) {
            sb.append(yes[0]).append(" ").append("people voted for `yes`\n");
        }
        if (yes[0] == 1) {
            sb.append("(" + yes[0] + ") ").append(" \uD83D\uDC64 people voted for `yes`\n");
        }
        if (yes[0] > 2) {
            sb.append(yes[0]).append(" \uD83D\uDC65 people voted for `yes`\n");
        }

        if (no[0] == 0) {
            sb.append(no[0]).append(" ").append("people voted for `no`");
        }
        if (no[0] == 1) {
            sb.append("(" + no[0] + ") ").append(" \uD83D\uDC64 people voted for `no`");
        }
        if (no[0] > 2) {
            sb.append(no[0]).append(" \uD83D\uDC65 people voted for `no`");
        }

        return sb.toString();
    }

    public static List<Message> getVotes(Event event) {
        List<Message> votes = new ArrayList<>();

        voteMap.forEach((integer, Long) -> {
            votes.add(event.getJDA().getGuildById(Lib.LOG_GUILD)
                    .getTextChannelsByName("votes", true).get(0)
                    .getMessageById(voteMap.get(Long)).complete());
        });

        return votes;
    }

    public static HashMap<Integer, Long> getVotes() {
        return voteMap;
    }
}
