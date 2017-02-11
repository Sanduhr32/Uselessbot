package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.lib;
import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.sql.Time;
import java.text.DecimalFormat;

public class task extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `task` command
        if (!content.equalsIgnoreCase(lib.prefix + "task")) {
            return;
        }

        //If `task` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();

        String cpu0 = new DecimalFormat("###.###%").format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuLoad());
        int cpu1 = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        long ram0 = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1000000;
        long ram1 = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / 1000000;
        long start = ManagementFactory.getRuntimeMXBean().getStartTime();
        long up = ManagementFactory.getRuntimeMXBean().getUptime();

        Time startt = new Time(start);
        Time upp = new Time(up - 3600000);

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        eb.setColor(Color.BLUE);
        eb.addField("Zeiten:", "**Gestartet:** " + startt.toString() + "\n**Läuft seit:** " + upp.toString(), false);
        eb.addField("CPU:","**Kerne:** " + String.valueOf(cpu1) + "\n**Auslastung:** " + cpu0,false);
        eb.addField("RAM:","**Genutzt:** " + ram0 +"MB\n**Max:** " + ram1 + "MB", false);

        e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();

        lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        lib.getCmdMap().put(getName(), getDescription());
        lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return "Task";
    }
    public String getDescription() {
        return "Sends you current infos of the JVM of this bot";
    }
    public String getSyntax() {
        return lib.prefix + getName();
    }
}