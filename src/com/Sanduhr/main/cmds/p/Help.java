package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `help` command
        if (!content.equalsIgnoreCase(Lib.prefix + "help")) {
            return;
        }

        //If `help` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        eb.setAuthor(e.getAuthor().getName(),null,e.getAuthor().getEffectiveAvatarUrl());
        eb.setColor(Lib.Orange);
        Lib.getCmdMap().forEach((s, s2) ->
        eb.addField(s, s2, false));
        e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();

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
    }
    public String getName() {
        return Github.class.getName();
    }
    public String getDescription() {
        return "Sends you a of all commands";
    }
}