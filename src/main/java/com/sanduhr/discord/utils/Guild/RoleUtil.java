package com.sanduhr.discord.utils.Guild;

import net.dv8tion.jda.core.entities.Role;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sanduhr on 05.03.2017
 * This project contains some Utils for JDA Bots
 */

public class RoleUtil {

    private static int temp = 1;

    public static String RoleListAsMention(Collection<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role r : roles) {
            if (temp != roles.size()) {
                temp++;
                sb.append(r.getAsMention()).append(", ");
            }
            else {
                sb.append(r.getAsMention());
            }
        }
        temp = 1;
        return sb.toString();
    }
    public static String RoleListAsMention(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role r : roles) {
            if (temp != roles.size()) {
                temp++;
                sb.append(r.getAsMention()).append(", ");
            }
            else {
                sb.append(r.getAsMention());
            }
        }
        temp = 1;
        return sb.toString();
    }

    public static String RoleListAsName(Collection<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role r : roles) {
            if (temp != roles.size()) {
                temp++;
                sb.append(r.getName()).append(", ");
            }
            else {
                sb.append(r.getName());
            }
        }
        temp = 1;
        return sb.toString();
    }
    public static String RoleListAsName(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role r : roles) {
            if (temp != roles.size()) {
                temp++;
                sb.append(r.getName()).append(", ");
            }
            else {
                sb.append(r.getName());
            }
        }
        temp = 1;
        return sb.toString();
    }

}
