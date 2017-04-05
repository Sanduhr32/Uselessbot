package com.sanduhr.discord.utils.Channel;

import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 */
public class PrivateChannelUtils {

    private static int temp = 1;

    /**
     * Returns the private channels of type
     * @param members as List or Collection
     */

    public static List<PrivateChannel> getPrivateChannelsByMember(Collection<Member> members) {

        List<PrivateChannel> out = new ArrayList<>();

        for (Member m : members) {
            out.add(m.getUser().getPrivateChannel());
        }

        return out;
    }
    public static List<PrivateChannel> getPrivateChannelsByMember(List<Member> members) {

        List<PrivateChannel> out = new ArrayList<>();

        for (Member m : members) {
            out.add(m.getUser().getPrivateChannel());
        }

        return out;
    }

    /**
     * Returns the private channels of type
     * @param users as List or Collection
     */

    public static List<PrivateChannel> getPrivateChannelsByUser(Collection<User> users) {
        List<PrivateChannel> out = new ArrayList<>();

        for (User u : users) {
            out.add(u.getPrivateChannel());
        }

        return out;
    }
    public static List<PrivateChannel> getPrivateChannelsByUser(List<User> users) {

        List<PrivateChannel> out = new ArrayList<>();

        for (User u : users) {
            out.add(u.getPrivateChannel());
        }

        return out;
    }

    /**
     * Returns a String of the Names from the private channels
     * @param privateChannels as List or Collection
     */

    public static String PrivateChannelAsName(Collection<PrivateChannel> privateChannels) {

        StringBuilder sb = new StringBuilder();
        for (PrivateChannel p : privateChannels) {
            if (temp != privateChannels.size()) {
                temp++;
                sb.append(p.getName()).append(", ");
            } else {
                sb.append(p.getName());
            }
        }

        return sb.toString();
    }
    public static String PrivateChannelAsName(List<PrivateChannel> privateChannels) {

        return privateChannels.stream().map(PrivateChannel::getName).collect(Collectors.joining(", "));
    }
}
