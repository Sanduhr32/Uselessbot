package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Game extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Not the `game` command
        if (!syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
            return;
        }

        //If `game` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

        String GAME = e.getJDA().getPresence().getGame().getName();
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax[1].equalsIgnoreCase("get")) {
            eb.setAuthor(e.getAuthor().getName(), e.getAuthor().getEffectiveAvatarUrl(), e.getAuthor().getEffectiveAvatarUrl());
            eb.setColor(Lib.Green);
            eb.addField("Current Game:", GAME, false);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }
        else if (syntax[1].equalsIgnoreCase("set")) {
            if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
                e.getJDA().getPresence().setGame(net.dv8tion.jda.core.entities.Game.of(syntaxx[1]));
                eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                eb.setColor(Lib.Green);
                eb.addField("Old Game:", GAME, false);
                eb.addField("New Game:", syntaxx[1], false);
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            }
            else {
                e.getChannel().sendMessage(Lib.Error_perms).queue();
            }
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
    }
    public String getName() {
        return Game.class.getName();
    }
    public String getDescription() {
        return "Returns the current game or sets it";
    }
}