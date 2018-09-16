package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.Useless;
import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.sql.Time;
import java.text.DecimalFormat;

public class Info {

    public static void run(MessageReceivedEvent e, String arguments, boolean respondToBots) {

        //Never respond to a bot!
        if (e.getAuthor().isBot() != respondToBots) {
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        String OS = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getName();
        OS = OS + " " + ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getArch() + " " + ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getVersion();
        String cpu0 = new DecimalFormat("###.###%").format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuLoad());
        String cpu2 = new DecimalFormat("###.###%").format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getSystemCpuLoad());
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
        eb.addField("Operating System:",OS,false);
        eb.addField("Library:","[JDA (Java Discord API) " + JDAInfo.VERSION + "](http://home.dv8tion.net:8080/job/JDA/" + JDAInfo.VERSION_BUILD + "/)", false);
        eb.addField("Times:", "**Started:** " + startt.toString() + "\n**Uptime:** " + upp.toString(), false);
        eb.addField("Bot version:", Useless.VERSION, false);
        eb.addField("CPU:","**Cores:** " + cpu1 + "\n**Usage:** " + cpu0 + " / " + cpu2,false);
        eb.addField("RAM:","**Usage:** " + ram0 +"MB\n**Max:** " + ram1 + "MB", false);

        e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();

        Lib.executedcmd++;
    }
    
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Info.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Sends you current infos of the JVM of this bot";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}