package com.sanduhr.discord.utils.Channel;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 */
public class TextChannelUtils {

    private static int temp = 1;

    public static String TextChannelAsName(Collection<TextChannel> textChannels) {
        StringBuilder sb = new StringBuilder();
        for (TextChannel t : textChannels) {
            if (temp != textChannels.size()) {
                temp++;
                sb.append(t.getName()).append(", ");
            } else {
                sb.append(t.getName());
            }
        }
        return sb.toString();
    }
    public static String TextChannelAsName(List<TextChannel> textChannels) {
        StringBuilder sb = new StringBuilder();
        for (TextChannel t : textChannels) {
            if (temp != textChannels.size()) {
                temp++;
                sb.append(t.getName()).append(", ");
            } else {
                sb.append(t.getName());
            }
        }
        return sb.toString();
    }

    public static List<TextChannel> getTextChannelsByIds(Event event, ArrayList<String> ids) {

        List<TextChannel> out = new ArrayList<>();

        for (String ID : ids) {
            out.add(event.getJDA().getTextChannelById(ID));
        }

        return out;
    }
}
