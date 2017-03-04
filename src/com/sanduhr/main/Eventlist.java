package com.sanduhr.main;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.guild.*;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Eventlist extends ListenerAdapter {
    private int countdown;
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        Guild guild = e.getGuild();
        System.out.println("[Log] Joined " + guild.getName());
        e.getGuild().getPublicChannel().sendMessage("Hello, " + e.getGuild().getOwner().getUser().getName()).queue();
        e.getJDA().getPresence().setGame(Game.of(e.getGuild().getName()));
        ArrayList<String> WL = new ArrayList<>();
        WL.add(Lib.YOUR_ID);
        Lib.getWhitelist().put(guild.getId(),WL);
    }
    public void onGuildLeave(GuildLeaveEvent e) {
        Guild guild = e.getGuild();
        e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage("Bye").queue();
        System.out.println("[Log] Left " + guild.getName());
        Lib.getWhitelist().remove(guild.getId());
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
        countdown = 48;
        shutdown();
    }
    public void onResume(ResumedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        int id = e.getJDA().getShardInfo().getShardId() + 1;
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Resumed " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Resumed " + now.format(Lib.DTF)).queue();
    }
    public void onReconnect(ReconnectedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        int id = e.getJDA().getShardInfo().getShardId() + 1;
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Reconnected " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Shard " + id + ": Reconnected " + now.format(Lib.DTF)).queue();
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
                    Useless.getJ().useSharding(Useless.getJDA().getShardInfo().getShardId(),Useless.getJDA().getShardInfo().getShardTotal()).buildBlocking();
                } catch (LoginException | InterruptedException | RateLimitedException e) {
                    e.printStackTrace();
                }
                System.out.println(OffsetDateTime.now().format(Lib.DTF) + "[Log] relogged");
            }
        };
        EXEC_.scheduleWithFixedDelay(r, 1, 1, TimeUnit.HOURS);
    }
}