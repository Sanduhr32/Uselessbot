package com.sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.ResumedEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Eventlist extends ListenerAdapter {
    private int countdown;
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        System.out.println("Joined " + e.getGuild().getName());
        e.getGuild().getPublicChannel().sendMessage("Hello, " + e.getGuild().getOwner().getUser().getName()).queue();
        e.getJDA().getPresence().setGame(Game.of(e.getGuild().getName()));
    }
    public void onGuildLeave(GuildLeaveEvent e) {
        e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage("Bye").complete();
        System.out.println("Left " + e.getGuild().getName());
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Welcome " + e.getMember().getUser().getName(), null);
        eb.setDescription("Introduce some infos about you :wink:");
        eb.setColor(Lib.BLUE);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue();
    }
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Bye " + e.getMember().getUser().getName(), null);
        eb.setDescription("See you soon :wink:");
        eb.setColor(Lib.BLUE);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue();
    }
    public void onReady(ReadyEvent e) {
        countdown = 14;
        shutdown();
    }
    public void onResume(ResumedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        int id = e.getJDA().getShardInfo().getShardId() + 1;
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Resumed " + now.format(Lib.DTF)).queue();
    }
    public void onReconnect(ReconnectedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        int id = e.getJDA().getShardInfo().getShardId() + 1;
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Reconnected " + now.format(Lib.DTF)).queue();
    }
    public void onShutdown(ShutdownEvent e) {
        System.out.println(e.getShutdownTime().format(Lib.DTF));
    }
    private void shutdown() {
        ScheduledExecutorService EXEC_ = Executors.newScheduledThreadPool(1);
        Runnable r = () -> {
            if (countdown != 0) {
                countdown--;
                System.out.println(countdown);
            } else {
                Useless.getJDA().shutdown(false);
                try {
                    Useless.restart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        EXEC_.scheduleWithFixedDelay(r, 1, 1, TimeUnit.DAYS);
    }
}