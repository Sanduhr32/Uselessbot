package com.sanduhr.discord;

import com.sanduhr.discord.utils.Logutils;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.guild.*;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Eventlist extends ListenerAdapter {
    public static List<Object> CORE = new ArrayList<>();
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        Guild guild = e.getGuild();
        e.getGuild().getPublicChannel().sendMessage("Hello, " + e.getGuild().getOwner().getUser().getName()).queue(
                msg -> msg.delete().queueAfter(10,TimeUnit.HOURS)
        );
        e.getJDA().getPresence().setGame(Game.of(e.getGuild().getName()));
        Lib.getWhitelist_().put(guild,Lib.WL);

        Logutils.log.info("Joined " + guild.getName());
    }
    @Override
    public void onGuildLeave(GuildLeaveEvent e) {
        Guild guild = e.getGuild();
        e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage("Bye").queue(
                msg -> msg.delete().queueAfter(100,TimeUnit.HOURS)
        );
        Lib.getWhitelist_().remove(guild);

        Logutils.log.info("Left " + guild.getName());
    }
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {

        Logutils.log.info("[" + e.getGuild().getName() + "] [JoinEvent] " + e.getMember().getUser().getName());

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
                msg -> msg.delete().queueAfter(10,TimeUnit.HOURS));
        if (e.getGuild().getIdLong() == Lib.LOG_GUILD) {
            if (e.getMember().getUser().getName().toLowerCase().contains("testuser")) {
                e.getGuild().getController().addRolesToMember(e.getMember(),e.getGuild().getRolesByName("testuser",true)).queue();
            }
        }
    }
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {

        if (e.getMember().equals(e.getGuild().getSelfMember())) {
            return;
        }

        Logutils.log.info("[" + e.getGuild().getName() + "] [LeaveEvent] " + e.getMember().getUser().getName());

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
                msg -> msg.delete().queueAfter(10,TimeUnit.HOURS));
    }
    public void onReady(ReadyEvent e) {
        Tierutils.init();
        HashMap<Guild, ArrayList<String>> n = (HashMap<Guild, ArrayList<String>>) Tierutils.tierMap.get(Tierutils.Tier.GUILD_WHITELIST.getName()).getOBJECT();
        Lib.start = false;
        CORE = e.getJDA().getRegisteredListeners();
        List<Guild> g = e.getJDA().getGuilds();
        g.forEach(guild -> {
            Lib.getConMap().put(guild, false);
            Lib.getWhitelist_().put(guild, Lib.WL);
        });
        Tierutils.tierMap.get(Tierutils.Tier.GUILD_WHITELIST.getName()).setOBJECT(n);
    }
    @Override
    public void onResume(ResumedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Resumed " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Resumed " + now.format(Lib.DTF)).queue();
    }
    @Override
    public void onReconnect(ReconnectedEvent e) {
        OffsetDateTime now = OffsetDateTime.now();
        e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage("Reconnected " + now.format(Lib.DTF)).queue();
        e.getJDA().getUserById(Lib.GERD_ID).openPrivateChannel().complete().sendMessage("Reconnected " + now.format(Lib.DTF)).queue();
    }
    @Override
    public void onShutdown(ShutdownEvent e) {
        System.out.println(e.getShutdownTime().format(Lib.DTF));
    }
    @Override
    public void onException(ExceptionEvent e) {
        if (e.isLogged()) {
            return;
        }
        System.err.println("\n===== Received Silent Exception/Error =====\n");
        e.getCause().printStackTrace();
    }
}