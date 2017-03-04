package com.sanduhr.main.commands.sanduhr;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class Message extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntaxx = e.getMessage().getRawContent().split("\\s+",2);

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Message` command
        if (!syntaxx[0].equalsIgnoreCase(Lib.PREFIX + "Message")) {
            return;
        }

        //If `Message` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        if (e.getAuthor().getId().equals(Lib.YOUR_ID)||e.getMember().isOwner()) {
            e.getChannel().sendMessage(syntaxx[1]).queue();
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
        return Message.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Send a Message as your Bot";
    }
    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " TEXT`";
    }
}