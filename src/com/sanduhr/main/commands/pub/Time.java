package com.sanduhr.main.commands.pub;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.sanduhr.main.Lib.*;

public class Time extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Time` command
        if (!content.equalsIgnoreCase(PREFIX + "Time")) {
            return;
        }

        receivedcmd++;

        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("EE dd.MM.YYYY, HH:mm:ss | KK:mm:ss a");
        OffsetDateTime now = OffsetDateTime.now();
        e.getChannel().sendMessage("It's " + now.format(DTF)).queue();
        executedcmd++;
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    public void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
    }

    public String getName() {
        return Time.class.getSimpleName().toLowerCase();
    }

    public String getDescription() {
        return "Returns the current time (german time)";
    }

    public String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}
