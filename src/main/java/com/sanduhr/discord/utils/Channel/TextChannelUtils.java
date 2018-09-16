package com.sanduhr.discord.utils.Channel;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 */
public class TextChannelUtils extends ListenerAdapter {

    public static String TextChannelAsName(Collection<TextChannel> textChannels) {

        return textChannels.stream().map(TextChannel::getName).collect(Collectors.joining(", "));
    }

    public static List<TextChannel> getTextChannelsByIds(Event event, ArrayList<Long> ids) {

        return ids.stream().map(aLong -> event.getJDA().getTextChannelById(aLong)).collect(Collectors.toList());
    }
}
