package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.Bot_main;
import com.Sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Request extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntaxx = e.getMessage().getContent().split(":");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `request` command
        if (!syntaxx[0].equalsIgnoreCase(Lib.prefix + "request")) {
            return;
        }

        //If `request` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

        if (!syntaxx[1].equalsIgnoreCase("")&&!syntaxx[2].equalsIgnoreCase("")) {
            e.getAuthor().openPrivateChannel().queue(privateChannel ->
            privateChannel.sendMessage("Thanks for requesting " + syntaxx[1] + "\nMy developer will check if its possible"));
            e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().queue(privateChannel ->
            privateChannel.sendMessage(e.getAuthor().getName() + " requested:\n" + syntaxx[1] + ":" + syntaxx[2]));
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
        return Request.class.getName();
    }
    public String getDescription() {
        return "Requests " + Bot_main.getJDA().getUserById(Lib.YOUR_ID).getName() + " to fix|implement it!";
    }
}