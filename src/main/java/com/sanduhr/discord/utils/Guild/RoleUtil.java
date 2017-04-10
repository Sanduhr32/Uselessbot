package com.sanduhr.discord.utils.Guild;

import net.dv8tion.jda.core.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 05.03.2017
 * This project contains some Utils for JDA Bots
 */

public class RoleUtil {

    private static int temp = 1;

    public static String RoleListAsMention(Collection<Role> roles) {

        return roles.stream().map(Role::getAsMention).collect(Collectors.joining(", "));
    }

    public static String RoleListAsName(Collection<Role> roles) {

        return roles.stream().map(Role::getName).collect(Collectors.joining(", "));
    }

}
