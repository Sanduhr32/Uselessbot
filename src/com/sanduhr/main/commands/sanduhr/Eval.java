package com.sanduhr.main.commands.sanduhr;

/**
 * Created by Sanduhr on 12.03.2017
 */

import static com.sanduhr.main.Lib.*;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Eval extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (!Lib.WL.contains(event.getAuthor().getId())) {
            return;
        }

        if (!event.getMessage().getRawContent().startsWith(Lib.PREFIX + "eval")) {
            return;
        }
        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        se.put("event",   event);
        se.put("jda",     event.getJDA());
        se.put("guild",   event.getGuild());
        se.put("channel", event.getChannel());
        se.put("message", event.getMessage());
        se.put("author",  event.getAuthor());

        String modified_msg = event.getMessage().getRawContent()
                .replaceAll("#","().")
                .replace("getToken","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(`Nice try m8!`)\").queue()")
                .replace("Runtime.getRuntime().exec","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(`Nice try m8!`)\").queue()")
                .replace("ProcessBuilder","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(`Nice try m8!`)\").queue()");

        List<String> splitContent = new LinkedList<>();
        Collections.addAll(splitContent, modified_msg.split("\\s+"));
        splitContent.remove(0);
        String statement = String.join(" ", splitContent);

        try {
            event.getMessage().getTextChannel().sendMessage(new StringBuilder().append("```Java\n").append(statement)
                    .append("\n```\nEvaluated successfully:\n```Java\n").append(se.eval(statement)).append("\n```").toString()).queue();
        } catch (Exception e) {
            event.getMessage().getTextChannel().sendMessage(new StringBuilder().append("```Java\n").append(statement)
                    .append("\n```\nAn exception was thrown:\n```Java\n").append(e).append("\n```").toString()).queue();
        }
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
        return Eval.class.getSimpleName().toLowerCase();
    }

    public String getDescription() {
        return "";
    }

    public String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}