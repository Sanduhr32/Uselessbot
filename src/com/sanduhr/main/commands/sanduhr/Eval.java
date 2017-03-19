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
        try {
            se.eval("var imports = new JavaImporter(" +
                    "Packages.com.sanduhr.main.utils," +
                    "Packages.com.sanduhr.main.utils.Channel," +
                    "Packages.com.sanduhr.main.utils.Guild," +
                    "Packages.java.nio.file," +
                    "Packages.net.dv8tion.jda.core.Permission" +
                    ");");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        String modified_msg = event.getMessage().getRawContent()
                .replaceAll("#","().")
                .replace("getToken","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue()")
                .replace("Runtime.getRuntime().exec","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue()")
                .replace("ProcessBuilder","getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue()");

        String[] splitContent = modified_msg.split("\\s+",2);

        try {
            Object out = se.eval(
                    "{" +
                            "with (imports) {" +
                            splitContent[1] +
                                "}" +
                            "};");

            if (out == null) {
                out = "Your action..";
            }
            event.getMessage().getTextChannel().sendMessage(new StringBuilder().append("```Java\n").append(splitContent[1])
                    .append("```Evaluated successfully:```Java\n").append(out).append("```").toString()).queue();
        } catch (ScriptException e) {
            event.getMessage().getTextChannel().sendMessage(new StringBuilder().append("```Java\n").append(splitContent[1])
                    .append("```An exception was thrown:```Java\n").append(e).append("```").toString()).queue();
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
