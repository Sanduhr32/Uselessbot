package com.sanduhr.discord.utils.Guild;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 08.03.2017
 * This project contains some Utils for JDA Bots
 */
public class MemberUtil extends ListenerAdapter {

    private static int temp = 1;

    public static boolean MemberHasOneOfRole(Member m, Collection<Role> roles) {
        for(Role r : roles){
            if(m.getRoles().contains(r)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasMutualGuilds(User user) {
        return user.getMutualGuilds().size() > 1;
    }
    public static boolean hasMutualGuilds(Member member) {
        return hasMutualGuilds(member.getUser());
    }

    public static ArrayList<Long> MemberToIdList(Collection<Member> members) {
        ArrayList<Long> out = new ArrayList<>();
        for (Member m : members) {
            out.add(m.getUser().getIdLong());
        }
        return out;
    }

    public static ArrayList<Long> UserToIdList(Collection<User> users) {
        ArrayList<Long> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.getIdLong());
        }
        return out;
    }

    public static String MemberToNameList(Collection<Member> members) {

        return members.stream().map(member -> member.getUser().getName()).collect(Collectors.joining(", "));
    }

    public static String UserToNameList(Collection<User> users) {

        return users.stream().map(User::getName).collect(Collectors.joining(", "));
    }
}