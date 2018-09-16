package com.sanduhr.discord.commands.sanduhr;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.Useless;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Relog extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContentDisplay().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Relog` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Relog")) {
            return;
        }

        //If `Relog` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

        if (e.getAuthor().getIdLong() == Lib.YOUR_ID) {
            try {
                Useless.restart();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
        return Relog.class.getSimpleName().toLowerCase();
    }

    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "";
    }

    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}