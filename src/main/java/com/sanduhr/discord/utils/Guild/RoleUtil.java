package com.sanduhr.discord.utils.Guild;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Sanduhr on 05.03.2017
 * This project contains some Utils for JDA Bots
 */

public class RoleUtil extends ListenerAdapter {

    public static String RoleListAsMention(Collection<Role> roles) {

        return roles.stream().filter(Objects::nonNull).map(Role::getAsMention).collect(Collectors.joining(", "));
    }

    public static String RoleListAsName(Collection<Role> roles) {

        return roles.stream().filter(Objects::nonNull).map(Role::getName).collect(Collectors.joining(", "));
    }

}
