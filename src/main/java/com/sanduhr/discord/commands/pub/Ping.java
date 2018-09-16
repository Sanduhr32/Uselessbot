package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Sanduhr on 11.04.2017 at com.sanduhr.discord.commands.pub.
 */
public class Ping extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getAuthor().isBot()) {
            return;
        }

        if (!e.getMessage().getContentRaw().equalsIgnoreCase(Lib.PREFIX + "ping")) {
            return;
        }

        e.getChannel().sendMessage("Getting ping..").queue(
            msg->e.getChannel().sendMessage(new EmbedBuilder()
            .addField("Ping:","**Discord API:** " + e.getJDA().getPing() + "ms\n" +
            "**You:** " + String.valueOf((msg.getCreationTime().getNano() / 1000000) - (e.getMessage().getCreationTime().getNano() / 1000000)) +"ms",false)
            .build()).queue());
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onReady(ReadyEvent e) {
        initter();
    }
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Ping.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Returns the ping between discord and me and the ping between you and me";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}
