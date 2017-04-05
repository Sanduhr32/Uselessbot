package com.sanduhr.discord.commands.Experimental;

import static com.sanduhr.discord.Lib.*;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Logutils;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Sanduhr on 12.03.2017
 */

public class Eval_exp extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //if (!Tierutils.isTier(event.getAuthor(), Tierutils.Tier.BOT_DEVELOPER, null)) {
        //    return;
        //}

        if (!Lib.WL.contains(event.getAuthor().getId())) {
            return;
        }

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

        String modified_msg = event.getMessage().getRawContent()
.replace("getToken", "getTextChannelById(channel.getId()).sendMessage(\"UnsupportedOperationException(\\\"Nice try m8!\\\")\").queue");
        //        .replace("ProcessBuilder","throw new UnsupportedOperationException(\"Locked\")");

        //    modified_msg = modified_msg.replaceAll("#", "().");

        String[] splitContent = modified_msg.split("\\s+",2);

        if (!splitContent[0].equalsIgnoreCase("??eval")) {
            return;
        }

        //if (splitContent[1].startsWith("Runtime")) {
        //        splitContent[1] = splitContent[1].replaceFirst("Runtime","throw new NullPointerException(\"UnsupportedOperationException(null))\")");
        //}

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
            new MessageBuilder().appendCodeBlock(out.toString(), "Java").buildAll(MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> event.getChannel().sendMessage(message).queue());
        } catch (ScriptException e) {
            event.getChannel().sendMessage(new StringBuilder().append("```Java\n").append(splitContent[1])
                    .append("```An exception was thrown:").toString()).queue();
            new MessageBuilder().appendCodeBlock(e.toString(), "Java").buildAll(MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> event.getChannel().sendMessage(message).queue());
        }

        Logutils.log.info(event.getAuthor().getName() + " evaluated");
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().getEmote().getName().equalsIgnoreCase("\uD83D\uDD02") && Lib.WL.contains(e.getUser().getId())) {
            if (e.getJDA().getTextChannelById(e.getChannel().getId()).getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                e.getChannel().getMessageById(e.getMessageId()).complete().clearReactions().queue();
            }
            onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getChannel().getMessageById(e.getMessageId()).complete()));
        }
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    public void initter() {
        getCmdMap().put("eval", getDescription());
        getSynMap().put("eval", getSyntax());
    }
    public String getName() {
        return Eval_exp.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "";
    }
    public String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
    //public static Commandutils.Command EVAL =
    //        new Commandutils.Command(Lib.PREFIX, getName(), getDescription(), getSyntax(), Tierutils.DEVS, new Eval_exp());
}