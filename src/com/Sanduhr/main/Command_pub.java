package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;


/**
 * Created by Sanduhr on 21.01.2017.
 */
public class Command_pub extends ListenerAdapter{
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "request")) {
            e.getMessage().deleteMessage().complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requestting a new command").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested:").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(syntax[1] + ", " + syntaxx[1]).complete();
        }
        if (content.equalsIgnoreCase(Lib.prefix + "status")) {
            List<Guild> g = e.getJDA().getGuilds();
            Lib.member = 0;
            g.forEach(guild -> {
                guild.getMembers();
                Lib.member = Lib.member + guild.getMembers().size() - 1;
            });
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            e.getMessage().deleteMessage().queue();
            eb.setAuthor(e.getJDA().getSelfUser().getName(), null, e.getJDA().getSelfUser().getAvatarUrl());
            eb.addField("Guilds:", String.valueOf(e.getJDA().getGuilds().size()), false);
            eb.addField("Member:", String.valueOf(Lib.member), false);
            mb.setEmbed(eb.build());
            Message m = mb.build();
            e.getChannel().sendMessage(m).queue();
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "invite")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("Add Uselessbot to your server and get useless Commands like kick,ban,mute,clear,game,unmute,add,remove and more (soon)").complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://discordapp.com/oauth2/authorize?client_id=" + Bot_main.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8").complete();
        }
        if (content.equalsIgnoreCase(Lib.prefix + "github")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://github.com/gerd2002/Uselessbot").complete();
        }
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "request")) {
            if (e.getChannelType().equals(0)) {
                e.getMessage().deleteMessage().queue();
            }
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requestting a new command").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested:").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(syntax[1] + ", " + syntaxx[1]).complete();
        }
        if (content.equalsIgnoreCase(Lib.prefix + "status")) {
            List<Guild> g = e.getJDA().getGuilds();
            Lib.member = 0;
            g.forEach(guild -> {
                guild.getMembers();
                Lib.member = Lib.member + guild.getMembers().size() - 1;
            });
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (e.getChannelType().equals(0)) {
                e.getMessage().deleteMessage().queue();
            }
            eb.setAuthor(e.getJDA().getSelfUser().getName(), null, e.getJDA().getSelfUser().getAvatarUrl());
            eb.addField("Guilds:", String.valueOf(e.getJDA().getGuilds().size()), false);
            eb.addField("Member:", String.valueOf(Lib.member), false);
            mb.setEmbed(eb.build());
            Message m = mb.build();
            e.getChannel().sendMessage(m).queue();
        }
        if (content.equalsIgnoreCase(Lib.prefix + "invite")) {
            if (e.getChannelType().equals(0)) {
                e.getMessage().deleteMessage().queue();
            }
            e.getAuthor().openPrivateChannel().complete().sendMessage("Add Uselessbot to your server and get useless Commands like kick,ban,mute,clear,game,unmute,add,remove and more (soon)").complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://discordapp.com/oauth2/authorize?client_id=" + Bot_main.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8").complete();
        }
        if (content.equalsIgnoreCase(Lib.prefix + "github")) {
            if (e.getChannelType().equals(0)) {
                e.getMessage().deleteMessage().queue();
            }
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://github.com/gerd2002/Uselessbot").complete();
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
            String GAME = String.valueOf(e.getJDA().getPresence().getGame().getName());
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (e.getChannelType().equals(0)) {
                e.getMessage().deleteMessage().queue();
            }
            if (syntax[1].equalsIgnoreCase("get")) {
                eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                eb.setColor(Lib.Green);
                eb.addField("Current Game:", GAME, true);
                mb.setEmbed(eb.build());
                Message m = mb.build();
                e.getChannel().sendMessage(m).queue();
            }
            else {
                e.getChannel().sendMessage("Syntax Error or no permissions").queue();
            }
        }
    }
}