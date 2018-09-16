package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static com.sanduhr.discord.Lib.PREFIX;
import static com.sanduhr.discord.Lib.getCmdMap;
import static com.sanduhr.discord.Lib.getSynMap;

/**
 * Created by Sanduhr on 08.04.2017
 */
public class Calculate extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }

        String[] syntax = e.getMessage().getContentRaw().split("\\s+",2);

        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "calculate")) {
            return;
        }

        if (syntax.length < 2) {
            e.getChannel().sendMessage("Error :eyes:").queue();
            return;
        }

        if (syntax[1].contains("while")) {
            e.getChannel().sendMessage("Sorry! I don't support while!").queue();
            return;
        }

        if (syntax[1].contains("\"")) {
            e.getChannel().sendMessage("Sorry! I don't support Strings!").queue();
            return;
        }

        ScriptEngine se = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            se.eval("var imports = new JavaImporter(" +
                    "java.lang," +
                    "java.math" +
                    ");");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }

        syntax[1] = syntax[1].replace("com.","").replace("net.","")
                .replace("java.","").replace("Runtime","Error").replace("ProcessBuilder","Error")
                .replace("javax.","").replace("jdk.","").replace("System","Error")
                .replace("Terminator","Error").replace("Shutdown","Error").replace("Thread","Error");



        try  {

            Object out = se.eval(
                    "{" +
                            "with (imports) {" +
                            syntax[1] +
                            "}" +
                            "};");
            e.getChannel().sendMessage(out.toString()).queue();
        } catch (ScriptException ex) {
            new MessageBuilder().append(ex.toString()).buildAll(MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> e.getChannel().sendMessage(message).queue());
        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().getReactionEmote().getName().equalsIgnoreCase("\uD83D\uDD02")) {
            if (e.getJDA().getTextChannelById(e.getChannel().getId()).getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                e.getChannel().getMessageById(e.getMessageId()).queue(msg -> msg.clearReactions().queue());
            }
            onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getChannel().getMessageById(e.getMessageId()).complete()));
        }
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    private void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Calculate.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Lemme do some math..";
    }
    private String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}
