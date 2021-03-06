package com.sanduhr.discord.commands.sanduhr;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Shutdown extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `shutdown` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "shutdown")) {
            return;
        }

        //If `shutdown` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;
        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        if (e.getAuthor().getIdLong() == Lib.YOUR_ID) {
            e.getJDA().shutdown();
        }
        else {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
        }

        Lib.executedcmd++;
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }

    private String getName() {
        return Shutdown.class.getSimpleName().toLowerCase();
    }

    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "Shuts the bot down";
    }

    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}