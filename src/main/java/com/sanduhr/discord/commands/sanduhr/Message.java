package com.sanduhr.discord.commands.sanduhr;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@SuppressWarnings("ALL")
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

        if (Lib.YOUR_ID == e.getAuthor().getIdLong()||e.getMember().isOwner()) {
            e.getChannel().sendMessage(syntaxx[1]).queue();
            e.getMessage().delete().queue();
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
        return Message.class.getSimpleName().toLowerCase();
    }
    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "Send a Message as your Bot";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " TEXT`";
    }
}