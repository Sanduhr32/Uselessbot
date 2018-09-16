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

    public static Collection<Long> MemberToIdList(Collection<Member> members) {
        return members.stream().filter(Objects::nonNull).map(member -> member.getUser().getIdLong()).collect(Collectors.toList());
    }

    public static Collection<Long> UserToIdList(Collection<User> users) {
        return users.stream().filter(Objects::nonNull).map(User::getIdLong).collect(Collectors.toList());
    }

    public static String MemberToNameList(Collection<Member> members) {
        return members.stream().filter(Objects::nonNull).map(Member::getEffectiveName).collect(Collectors.joining(", "));
    }

    public static String UserToNameList(Collection<User> users) {
        return users.stream().filter(Objects::nonNull).map(User::getName).collect(Collectors.joining(", "));
    }
}