package com.sanduhr.discord.commands.sanduhr;

/**
 * Created by Sanduhr on 12.03.2017
 */

import static com.sanduhr.discord.Lib.*;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Logutils;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval {

    public static void run(MessageReceivedEvent event, String arguments, boolean respondToBots) {

        if (event.getAuthor().isBot() != respondToBots) {
            return;
        }

        if (!Lib.WL.contains(event.getAuthor().getIdLong())) {
            return;
        }

        String[] args = arguments.split("\\s+",2);

        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        try {
            se.eval("var imports = new JavaImporter(" +
                    "java.nio.file," +
                    "Packages.net.dv8tion.jda.core.Permission," +
                    "java.lang," +
                    "java.lang.management," +
                    "java.text," +
                    "java.sql," +
                    "java.util," +
                    "java.time," +
                    "Packages.com.sun.management" +
                    ");");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        se.put("event", event);
        se.put("jda", event.getJDA());
        se.put("guild", event.getGuild());
        se.put("channel", event.getChannel());
        se.put("message", event.getMessage());
        se.put("author", event.getAuthor());

        String modified_msg = args[1]
                .replace("getToken", "getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue")
                .replace("ProcessBuilder","throw new UnsupportedOperationException(\"Locked\")");

        modified_msg = modified_msg.replaceAll("#", "().");

        if (modified_msg.startsWith("Runtime")) {
            modified_msg = modified_msg.replaceFirst("Runtime","throw new NullPointerException(\"UnsupportedOperationException(null))\")");
        }

        try {
            Object out = se.eval(
                    "{" +
                            "with (imports) {" +
                            modified_msg +
                            "}" +
                            "};");

            if (out == null) {
                out = "Your action..";
            }

            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(modified_msg)
                    .append("```Evaluated successfully:").toString()).queue();
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(out).append("```").toString()).queue();
        } catch (ScriptException e) {
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(modified_msg)
                    .append("```An exception was thrown:").toString()).queue();
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(e).append("```").toString()).queue();
        }

        Logutils.log.info(event.getAuthor().getName() + " evaluated");
    }

    private static String getName() {
        return Eval.class.getSimpleName().toLowerCase();
    }
    private static String getDescription() {
        return "";
    }
    private static String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}