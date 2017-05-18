package com.sanduhr.discord.commands.Experimental;

import static com.sanduhr.discord.Lib.*;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Logutils;

import com.sun.management.GarbageCollectorMXBean;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sun.management.GarbageCollectionNotifInfoCompositeData;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Sanduhr on 12.03.2017
 */

public class Eval_exp {

    public static void run(MessageReceivedEvent event, String arguments, boolean respondToBots) {

        if (!Lib.WL.contains(event.getAuthor().getIdLong())) {
            return;
        }

        String[] args = arguments.split("\\s+", 2);

        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        try {
            se.eval("var imports = new JavaImporter(" +
                    "java.nio.file," +
                    "Packages.net.dv8tion.jda.core.Permission," +
                    "Packages.net.dv8tion.jda.core," +
                    "java.lang," +
                    "java.lang.management," +
                    "java.text," +
                    "java.sql," +
                    "java.util," +
                    "java.time," +
                    "Packages.com.sun.management," +
                    "Packages.com.sanduhr.discord.utils," +
                    "Packages.com.sanduhr.discord.utils.Channel," +
                    "Packages.com.sanduhr.discord.utils.Guild" +
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
.replace("getToken", "getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue");
        //        .replace("ProcessBuilder","throw new UnsupportedOperationException(\"Locked\")");

        //    modified_msg = modified_msg.replaceAll("#", "().");

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
            new MessageBuilder().appendCodeBlock(out.toString(), "Java").buildAll(MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> event.getChannel().sendMessage(message).queue());
        } catch (ScriptException e) {
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(modified_msg)
                    .append("```An exception was thrown:").toString()).queue();
            new MessageBuilder().appendCodeBlock(e.toString(), "Java").buildAll(MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> event.getChannel().sendMessage(message).queue());
        }

        Logutils.log.info(event.getAuthor().getName() + " evaluated");
    }
    private void initter() {
        getCmdMap().put("eval", getDescription());
        getSynMap().put("eval", getSyntax());
    }
    private String getName() {
        return Eval_exp.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "";
    }
    private String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
    //public static Commandutils.Command EVAL =
    //        new Commandutils.Command(Lib.PREFIX, getName(), getDescription(), getSyntax(), Tierutils.DEVS, new Eval_exp());
}