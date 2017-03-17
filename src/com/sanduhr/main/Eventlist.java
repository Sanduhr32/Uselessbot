package com.sanduhr.main;

import com.sanduhr.main.utils.Logutils;
import com.sanduhr.main.utils.ScheduleUtil;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.guild.*;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Eventlist extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        Guild guild = e.getGuild();
        e.getGuild().getPublicChannel().sendMessage("Hello, " + e.getGuild().getOwner().getUser().getName()).queue(
                msg -> ScheduleUtil.scheduledAction(()->msg.delete().queue(),36,TimeUnit.HOURS)
        );
        e.getJDA().getPresence().setGame(Game.of(e.getGuild().getName()));
        Lib.getWhitelist_().put(guild,Lib.WL);
        Logutils.log.info("[Joined] " + e.getGuild().getName());

    }
    public void onGuildLeave(GuildLeaveEvent e) {
        Guild guild = e.getGuild();
        e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage("Bye").queue(
                msg -> ScheduleUtil.scheduledAction(()->msg.delete().queue(),36,TimeUnit.HOURS)
        );
        Lib.getWhitelist().remove(guild);
        Logutils.log.info("[Left] " + e.getGuild().getName());
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {

        if (!Lib.getConMap().get(e.getGuild())) {
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Welcome " + e.getMember().getUser().getName(), null);
        eb.setDescription("Introduce some infos about you :wink:");
        eb.setColor(Lib.BLUE);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue(
                msg -> ScheduleUtil.scheduledAction(()->msg.delete().queue(),36,TimeUnit.HOURS));
        if (e.getGuild().getId().equals(Lib.LOG_GUILD)) {
            if (e.getMember().getUser().getName().toLowerCase().contains("testuser")) {
                e.getGuild().getController().addRolesToMember(e.getMember(),e.getGuild().getRolesByName("testuser",true)).queue();
            }
        }
    }
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {

        if (e.getMember().equals(e.getGuild().getSelfMember())) {
            return;
        }

        if (!Lib.getConMap().get(e.getGuild())) {
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Bye " + e.getMember().getUser().getName(), null);
        eb.setDescription("See you soon :wink:");
        eb.setColor(Lib.BLUE);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue(
                msg -> ScheduleUtil.scheduledAction(()->msg.delete().queue(),36,TimeUnit.HOURS));
    }
    public void onReady(ReadyEvent e) {
        List<Guild> g = e.getJDA().getGuilds();
        g.forEach(guild -> Lib.getConMap().put(guild, false));
        Logutils.log.info("Login Successful!, Connected to WebSocket!, Finished Loading!");
    }
    public void onResume(ResumedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Resumed " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Resumed " + now.format(Lib.DTF)).queue();
    }
    public void onReconnect(ReconnectedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Reconnected " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Reconnected " + now.format(Lib.DTF)).queue();
    }
    public void onShutdown(ShutdownEvent e) {
        System.out.println(e.getShutdownTime().format(Lib.DTF));
    }
}