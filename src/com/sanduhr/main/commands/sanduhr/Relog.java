package com.sanduhr.main.commands.sanduhr;

import com.sanduhr.main.Lib;
import com.sanduhr.main.Useless;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Relog extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

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

        if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
            e.getJDA().shutdown(false);
            try {
                Useless.start();
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

    public void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }

    public String getName() {
        return Relog.class.getSimpleName().toLowerCase();
    }

    public String getDescription() {
        return "";
    }

    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}