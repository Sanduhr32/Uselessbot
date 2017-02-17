package com.sanduhr.main.commands.ownerwhitelist;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Mentioneveryone extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Mentioneveryone` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Mentioneveryone")) {
            return;
        }

        //If `Mentioneveryone` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the Whitelist
          or the Owner of the Guild, they don't have permission to run this command!*/
        if (!e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        e.getChannel().sendMessage(e.getGuild().getPublicRole().getName()).queue();
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
        return Mentioneveryone.class.getSimpleName().toLowerCase();
    }

    public String getDescription() {
        return "mssingno";
    }

    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}