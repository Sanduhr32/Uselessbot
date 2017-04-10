package com.sanduhr.discord.commands.sanduhr;

/**
 * Created by Sanduhr on 12.03.2017
 */

import static com.sanduhr.discord.Lib.*;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Commandutils;
import com.sanduhr.discord.utils.Logutils;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!Lib.WL.contains(event.getAuthor().getIdLong())) {
            return;
        }

        if (!event.getMessage().getRawContent().startsWith("??eval")) {
            return;
        }

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

        String modified_msg = event.getMessage().getRawContent()
                .replace("getToken", "getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue")
                .replace("ProcessBuilder","throw new UnsupportedOperationException(\"Locked\")");

        modified_msg = modified_msg.replaceAll("#", "().");

        String[] splitContent = modified_msg.split("\\s+",2);

        if (!splitContent[0].equalsIgnoreCase("??eval")) {
            return;
        }

        if (splitContent[1].startsWith("Runtime")) {
            splitContent[1] = splitContent[1].replaceFirst("Runtime","throw new NullPointerException(\"UnsupportedOperationException(null))\")");
        }

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

            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(splitContent[1])
                    .append("```Evaluated successfully:").toString()).queue();
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(out).append("```").toString()).queue();
        } catch (ScriptException e) {
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(splitContent[1])
                    .append("```An exception was thrown:").toString()).queue();
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(e).append("```").toString()).queue();
        }

        Logutils.log.info(event.getAuthor().getName() + " evaluated");
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().getEmote().getName().equalsIgnoreCase("\uD83D\uDD02") && Lib.WL.contains(e.getUser().getIdLong())) {
            e.getChannel().getMessageById(e.getMessageId()).complete().clearReactions().queue();
            onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getChannel().getMessageById(e.getMessageId()).complete()));
        }
    }

    public void onGenericMessageReaction(GenericMessageReactionEvent event) {

    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    private void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
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