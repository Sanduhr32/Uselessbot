package com.sanduhr.discord.utils.Guild;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.util.*;
/**
 * Created by Sanduhr on 08.03.2017
 * This project contains some Utils for JDA Bots
 */
public class MemberUtil {

    private static int temp = 1;

    public static boolean MemberHasOneOfRole(Member m, Collection<Role> roles) {
        for(Role r : roles){
            if(m.getRoles().contains(r)){
                return true;
            }
        }
        return false;
    }
    public static boolean MemberHasOneOfRole(Member m, List<Role> roles) {
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

    public static ArrayList<String> MemberToIdList(Collection<Member> members) {
        ArrayList<String> out = new ArrayList<>();
        for (Member m : members) {
            out.add(m.getUser().getId());
        }
        return out;
    }
    public static ArrayList<String> MemberToIdList(List<Member> members) {
        ArrayList<String> out = new ArrayList<>();
        for (Member m : members) {
            out.add(m.getUser().getId());
        }
        return out;
    }

    public static ArrayList<String> UserToIdList(Collection<User> users) {
        ArrayList<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.getId());
        }
        return out;
    }
    public static ArrayList<String> UserToIdList(List<User> users) {
        ArrayList<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.getId());
        }
        return out;
    }

    public static String MemberToNameList(Collection<Member> members) {
        StringBuilder sb = new StringBuilder();
        for(Member m : members) {
            if (temp != members.size()) {
                temp++;
                sb.append(m.getUser().getName()).append(", ");
            }
            else {
                sb.append(m.getUser().getName());
            }
        }
        temp = 1;
        return sb.toString();
    }
    public static String MemberToNameList(List<Member> members) {
        StringBuilder sb = new StringBuilder();
        for(Member m : members) {
            if (temp != members.size()) {
                temp++;
                sb.append(m.getUser().getName()).append(", ");
            }
            else {
                sb.append(m.getUser().getName());
            }
        }
        temp = 1;
        return sb.toString();
    }

    public static String UserToNameList(Collection<User> users) {
        StringBuilder sb = new StringBuilder();
        for(User u : users) {
            if (temp != users.size()) {
                temp++;
                sb.append(u.getName()).append(", ");
            }
            else {
                sb.append(u.getName());
            }
        }
        temp = 1;
        return sb.toString();
    }
    public static String UserToNameList(List<User> users) {
        StringBuilder sb = new StringBuilder();
        for(User u : users) {
            if (temp != users.size()) {
                temp++;
                sb.append(u.getName()).append(", ");
            }
            else {
                sb.append(u.getName());
            }
        }
        temp = 1;
        return sb.toString();
    }
}