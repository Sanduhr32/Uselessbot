package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Logutils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Help` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Help")) {
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax.length < 2) {
            eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getEffectiveAvatarUrl());
            eb.setColor(Lib.ORANGE);
            Lib.getCmdMap().forEach((s, s2) ->
                    eb.addField(s, s2, false));
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue(msg ->
                msg.delete().queueAfter(30,TimeUnit.SECONDS));
            Logutils.log.info(e.getAuthor().getName() + " got help");
            return;
        }
        if (syntax.length == 2) {
            if (syntax[1].isEmpty()) {
                eb.setColor(Color.red);
                eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
                eb.setDescription("-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG + "\n-" + Lib.ERROR_EMPTY + "type `"+ Lib.PREFIX + getName() + "`");
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue(msg ->
                    msg.delete().queueAfter(30,TimeUnit.SECONDS));
                return;
            }
            String val = Lib.getCmdMap().get(syntax[1].toLowerCase());
            if (val != null ) {
                eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getEffectiveAvatarUrl());
                eb.setColor(Lib.ORANGE);
                eb.addField(syntax[1], val, false);
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue(msg ->
                    msg.delete().queueAfter(30,TimeUnit.SECONDS));
                Logutils.log.info(e.getAuthor().getName() + " got help");
            }
            else {
                eb.setColor(Color.red);
                eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
                eb.setDescription("-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG + "\n-" + Lib.ERROR_EMPTY + "\ntype `"+ Lib.PREFIX + getName() + "`");
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue(msg ->
                    msg.delete().queueAfter(30,TimeUnit.SECONDS));
            }
        }
        if (syntax.length > 2) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
            eb.setDescription("-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG + "\n-" + Lib.ERROR_EMPTY + "\n-" + Lib.ERROR_MANY +"\ntype `"+ Lib.PREFIX + getName() + "`");
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue(msg ->
                msg.delete().queueAfter(30,TimeUnit.SECONDS));
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
        return Help.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Sends you a of all commands";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " [CMD]`";
    }
}