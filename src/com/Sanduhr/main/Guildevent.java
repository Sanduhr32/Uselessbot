package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Sanduhr on 22.01.2017.
 */
public class Guildevent extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        System.out.println("Joined " + e.getGuild().getName());
        e.getGuild().getPublicChannel().sendMessage("Hello, " + e.getGuild().getOwner().getAsMention()).queue();
    }
    public void onGuildLeave(GuildLeaveEvent e) {
        e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage("Bye").complete();
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Welcome " + e.getMember().getUser().getName());
        eb.setDescription("Introduce some infos about you :wink:");
        eb.setColor(Lib.Blue);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue();
    }
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setTitle("Bye " + e.getMember().getUser().getName());
        eb.setDescription("See you soon :wink:");
        eb.setColor(Lib.Blue);
        mb.setEmbed(eb.build());
        Message m  = mb.build();
        e.getGuild().getPublicChannel().sendMessage(m).queue();
    }
}