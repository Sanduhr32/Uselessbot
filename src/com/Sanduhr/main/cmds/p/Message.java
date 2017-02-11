package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class message extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `message` command
        if (!syntaxx[0].equalsIgnoreCase(lib.prefix + "message")) {
            return;
        }

        //If `message` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();

        if (e.getAuthor().getId().equals(lib.YOUR_ID)) {
            e.getChannel().sendMessage(syntaxx[1]).queue();
        }
        else {
            e.getChannel().sendMessage(lib.Error_perms).queue();
        }

        lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        lib.getCmdMap().put(getName(), getDescription());
        lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return message.class.getSimpleName();
    }
    public String getDescription() {
        return "Send a message as your Bot";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + ":TEXT`";
    }
}