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

public class request extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Not the `request` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "request")) {
            return;
        }

        //If `request` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        lib.receivedcmd++;
        e.getMessage().delete().queue();

        String val = lib.getReqMap().get(syntax[1]);
        if (val != null && syntaxx[1].length() > 1 || syntaxx[2].length() > 1) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requesting " + val + " " + syntaxx[1] + "," + syntaxx[2]).queue();
            e.getJDA().getUserById(lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested " + val + " command " + syntaxx[1] + "\n" + syntaxx[2]).queue();
        }
        else {
            eb.setColor(Color.red);
            eb.addField("Possible error:","-" + "\n-" + lib.Error_perms + "\n-" + lib.Error_wrong + "\n-" + lib.Error_empty ,false);
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
    }
    public String getName() {
        return request.class.getSimpleName();
    }
    public String getDescription() {
        return "Requests sanduhr to fix|implement it!";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + " <args> :CMD:TEXT`\n\nArguments:\n`fix`, `add`, `remove`";
    }
}