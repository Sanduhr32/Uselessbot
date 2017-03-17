package com.sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sanduhr.main.Lib.EXECUTE;
import static com.sanduhr.main.Lib.LOG_GUILD;

/**
 * Created by Sanduhr on 08.03.2017
 */
class Jarupdate extends ListenerAdapter {

    private static String server = "[`[2.0.Beta_104]`](https://cdn.discordapp.com/attachments/288695554773614592/289157997215612928/useless.jar)";
    private static String panel = "[`[1.0]`](https://cdn.discordapp.com/attachments/288695554773614592/289050751534235648/Panel.jar)";
    private static String util = "[`[0.5]`](https://cdn.discordapp.com/attachments/288695554773614592/289447575202037760/Util.jar)";
    private final Color update = new Color(158,255,92);

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getAuthor().isBot()||e.getAuthor().isFake()) {
            return;
        }

        if (!e.isFromType(ChannelType.TEXT)) {
            return;
        }

        if (!e.getTextChannel().equals(e.getJDA().getTextChannelById("288695554773614592"))) {
            return;
        }

        if (e.getMessage().getAttachments().isEmpty()) {
            return;
        }

        Message m = e.getMessage();
        List<Message.Attachment> a = e.getMessage().getAttachments();
        if (a.get(0).getFileName().contains("useless")) {
            server = "[`[" + m.getContent() + "]`](" + a.get(0).getUrl() + ")";
        }
        if (a.get(0).getFileName().contains("Panel")) {
            panel = "[`[" + m.getContent() + "]`](" + a.get(0).getUrl() + ")";
        }
        if (a.get(0).getFileName().contains("Util")) {
            util = "[`[" + m.getContent() + "]`](" + a.get(0).getUrl() + ")";
        }
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.addField("NEW JAR","Latest server-jar: " + server + "\nLatest panel-jar: " + panel + "\nLatest util-jar: " + util,false);
        eb.setColor(update);
        mb.setEmbed(eb.build());
        mb.append(e.getJDA().getGuildById(LOG_GUILD).getRolesByName("JAR Update",false).get(0).getAsMention());
        e.getGuild().getTextChannelById("288695454127095808").sendMessage(mb.build()).queue();
    }
    static String getServerjar() {
        return server;
    }
    static String getPaneljar() {
        return panel;
    }
    static String getUtiljar() {
        return util;
    }
    public void onReady(ReadyEvent e) {
        EXECUTE.schedule(()->getJARS(),10, TimeUnit.SECONDS);
    }
    private void getJARS() {
        server = Filegeneration.getServer();
        panel = Filegeneration.getPanel();
    }
}
