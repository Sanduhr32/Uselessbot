package com.sanduhr.discord.commands.sanduhr;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.Useless;
import com.sanduhr.discord.utils.Voteutils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 20.04.2017
 */
public class Vote extends ListenerAdapter {

    public static void run(MessageReceivedEvent event, String arguments, boolean respondToBots) {

        if (event.getAuthor().isBot() != respondToBots) {
            return;
        }

        if (!Lib.WL.contains(event.getAuthor().getIdLong())) {
            return;
        }

        String[] args = arguments.split("\\s", 4);
        String[] a = arguments.split("\\s", 3);

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("print")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(47, 166, 222));
                List<Message> votes = Voteutils.getVotes(event);
                eb.setDescription(
                    votes.stream().map(Message::getContentDisplay).collect(Collectors.joining("\n"))
                );
                event.getChannel().sendMessage(eb.build()).queue();
            } else {
                event.getChannel().sendMessage(new MessageBuilder().appendCodeBlock(
                        "public void returnError(String text) {" +
                        "   try {" +
                        "       throw new IllegalArgumentException(text);" +
                        "   } catch(IllegalArgumentException ex) {" +
                        "       ex.printStackTrace();" +
                        "   }","Java").build()).queue();
            }
        } else if (args.length >= 3) {
            switch (args[1]) {
                case "close" :
                    Voteutils.deleteVote(Integer.parseInt(args[2]), event, Boolean.valueOf(args[3]));
                    break;
                case "state" :
                    Useless.shards.get(0).getGuildById(Lib.LOG_GUILD).getTextChannelsByName("votes", true).get(0).sendMessage(Voteutils.getResult(Integer.parseInt(args[2]), event)).queue();
                    break;
                case "create" :
                    Voteutils.createVote(a[2]);
            }
        }
        else {
            event.getChannel().sendMessage(new MessageBuilder().appendCodeBlock(
                "public void returnError(String text) {" +
                    "   try {" +
                    "       throw new IllegalArgumentException(text);" +
                    "   } catch(IllegalArgumentException ex) {" +
                    "       ex.printStackTrace();" +
                    "   }","Java").build()).queue();
        }
    }
}
