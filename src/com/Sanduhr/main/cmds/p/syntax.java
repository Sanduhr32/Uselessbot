package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class syntax extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `syntax` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "syntax")) {
            return;
        }

        //If `syntax` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax.length < 2) {
            eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getEffectiveAvatarUrl());
            eb.setColor(lib.Orange);
            lib.getSynMap().forEach((s, s2) ->
                    eb.addField(s, s2, false));
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            return;
        }
        if (syntax.length == 2) {
            if (syntax[1].isEmpty()) {
                eb.setColor(Color.red);
                eb.setAuthor("Possible error:", null, lib.Error_png);
                eb.setDescription("-" + lib.Error_perms + "\n-" + lib.Error_wrong + "\n-" + lib.Error_empty + "type `"+ lib.prefix + getName() + "`");
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
                return;
            }
            String val = lib.getSynMap().get(syntax[1].toLowerCase());
            if (val != null ) {
                eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getEffectiveAvatarUrl());
                eb.setColor(lib.Orange);
                eb.addField(syntax[1], val, false);
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            }
            else {
                eb.setColor(Color.red);
                eb.setAuthor("Possible error:", null, lib.Error_png);
                eb.setDescription("-" + lib.Error_perms + "\n-" + lib.Error_wrong + "\n-" + lib.Error_empty + "\ntype `"+ lib.prefix + getName() + "`");
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            }
        }
        if (syntax.length > 2) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, lib.Error_png);
            eb.setDescription("-" + lib.Error_perms + "\n-" + lib.Error_wrong + "\n-" + lib.Error_empty + "\n-" + lib.Error_many +"\ntype `"+ lib.prefix + getName() + "`");
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
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
        return syntax.class.getSimpleName();
    }
    public String getDescription() {
        return "Sends you the syntax for commands";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + " [CMD]´";
    }
}