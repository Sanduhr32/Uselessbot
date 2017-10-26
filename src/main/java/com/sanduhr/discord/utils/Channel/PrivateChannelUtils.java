package com.sanduhr.discord.utils.Channel;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 */
public class PrivateChannelUtils extends ListenerAdapter {

    private static int temp = 1;

    /**
     * Returns the private channels of type
     *
     * @param members as List or Collection
     */

    public static List<PrivateChannel> getPrivateChannelsByMember(Collection<Member> members) {


        return members.stream().map(member -> member.getUser().openPrivateChannel().complete()).collect(Collectors.toList());
    }

    /**
     * Returns the private channels of type
     *
     * @param users as List or Collection
     */

    public static List<PrivateChannel> getPrivateChannelsByUser(Collection<User> users) {

        return users.stream().map(user -> user.openPrivateChannel().complete()).collect(Collectors.toList());
    }

    /**
     * Returns a String of the Names from the private channels
     *
     * @param privateChannels as List or Collection
     */

    public static String PrivateChannelAsName(Collection<PrivateChannel> privateChannels) {

        return privateChannels.stream().map(PrivateChannel::getName).collect(Collectors.joining(", "));
    }
}
