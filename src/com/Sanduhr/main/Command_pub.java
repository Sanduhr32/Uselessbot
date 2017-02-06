package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
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
        Lib.received++;
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "request")) {
            e.getMessage().deleteMessage().complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requestting a new command").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested:").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(syntax[1] + ", " + syntaxx[1]).complete();
            Lib.sent++;
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (content.equalsIgnoreCase(Lib.prefix + "status")) {
            Lib.receivedcmd++;
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
            eb.addField("Received messages:",String.valueOf(Lib.received), false);
            eb.addField("Received commands:", String.valueOf(Lib.receivedcmd),false);
            eb.addField("Sent messages:", String.valueOf(Lib.sent + 1), false);
            eb.addField("Successful executed commands:", String.valueOf(Lib.executedcmd + 1), false);
            eb.addField("Cleared messages:", String.valueOf(Lib.cleared), false);
            mb.setEmbed(eb.build());
            Message m = mb.build();
            e.getChannel().sendMessage(m).queue();
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "invite")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("Add Uselessbot to your server and get useless Commands like kick,ban,mute,clear,game,unmute,add,remove and more (soon)").complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://discordapp.com/oauth2/authorize?client_id=" + Bot_main.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8").complete();
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (content.equalsIgnoreCase(Lib.prefix + "github")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://github.com/Sanduhr32/Uselessbot").complete();
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
            String GAME = String.valueOf(e.getJDA().getPresence().getGame().getName());
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (e.getChannelType().equals(ChannelType.TEXT)) {
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
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "syntax")) {
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (syntax[1].equals("")) {
                eb.setAuthor(e.getAuthor().getName(),null,e.getAuthor().getAvatarUrl());
                eb.addField("`"+Lib.prefix+"github`", "Sends you the github link via dm",false);
                eb.addField("`"+Lib.prefix+"request <name> :<Action>`", "Requests Sanduhr to fix it",false);
                eb.addField("`"+Lib.prefix+"status`", "Returns some infos",false);
                eb.addField("`"+Lib.prefix+"invite`", "Sends you a auth link via dm",false);
                eb.addField("`"+Lib.prefix+"game <get|set :NAME>`", "Sends you the current game or sets the current game if you have permissions",false);
                eb.addField("`"+Lib.prefix+"syntax`", "Sends you this message again",false);
                Lib.whitelist.forEach(id -> {
                    if (e.getAuthor().getId().equals(id)||e.getAuthor().equals(e.getGuild().getOwner())) {
                        eb.addField("`"+Lib.prefix+"add @USER @ROLE`", "Adds unlimited roles to unlimited user",false);
                        eb.addField("`"+Lib.prefix+"remove @USER @ROLE`", "Removes unlimited roles to unlimited user",false);
                        eb.addField("`"+Lib.prefix+"kick @USER :REASON`", "Kicks unlimited user and sends them a dm with the reason",false);
                        eb.addField("`"+Lib.prefix+"ban @USER :REASON`", "Swings the banhammer for unlimited user and sends them a dm with the reason",false);
                        eb.addField("`"+Lib.prefix+"mute @USER`", "Mutes unlimited user",false);
                        eb.addField("`"+Lib.prefix+"ummute @USER`", "Unmutes unlimited user",false);
                        eb.addField("`"+Lib.prefix+"clear <1-100>`", "Deletes the last <1-100> messages but takes a while | RestActiom",false);
                        eb.addField("`"+Lib.prefix+"whitelist <add|remove @USER>|<print>`", "Adds|Removes mentioned user at the whitelist or prints the whitelist",false);
                    }
                });
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            } else {
                e.getChannel().sendMessage("´"+Lib.prefix+"syntax` has no syntax like that\nSo write ??syntax or let it be").queue();
            }
        }
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "request")) {
            e.getMessage().deleteMessage().complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requestting a new command").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested:").complete();
            Bot_main.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(syntax[1] + ", " + syntaxx[1]).complete();
            Lib.sent++;
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (content.equalsIgnoreCase(Lib.prefix + "status")) {
            Lib.receivedcmd++;
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
            eb.addField("Received messages:",String.valueOf(Lib.received), false);
            eb.addField("Received commands:", String.valueOf(Lib.receivedcmd),false);
            eb.addField("Sent messages:", String.valueOf(Lib.sent + 1), false);
            eb.addField("Successful executed commands:", String.valueOf(Lib.executedcmd + 1), false);
            eb.addField("Cleared messages:", String.valueOf(Lib.cleared), false);
            mb.setEmbed(eb.build());
            Message m = mb.build();
            e.getChannel().sendMessage(m).queue();
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "invite")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("Add Uselessbot to your server and get useless Commands like kick,ban,mute,clear,game,unmute,add,remove and more (soon)").complete();
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://discordapp.com/oauth2/authorize?client_id=" + Bot_main.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8").complete();
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (content.equalsIgnoreCase(Lib.prefix + "github")) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("https://github.com/Sanduhr32/Uselessbot").complete();
            Lib.sent++;
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
            String GAME = String.valueOf(e.getJDA().getPresence().getGame().getName());
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (e.getChannelType().equals(ChannelType.TEXT)) {
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
            Lib.sent++;
            Lib.executedcmd++;
        }
        if (syntax[0].equalsIgnoreCase(Lib.prefix + "syntax")) {
            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();
            if (syntax[1].equals("")) {
                eb.setAuthor(e.getAuthor().getName(),null,e.getAuthor().getAvatarUrl());
                eb.addField("`"+Lib.prefix+"github`", "Sends you the github link via dm",false);
                eb.addField("`"+Lib.prefix+"request <name> :<Action>`", "Requests Sanduhr to fix it",false);
                eb.addField("`"+Lib.prefix+"status`", "Returns some infos",false);
                eb.addField("`"+Lib.prefix+"invite`", "Sends you a auth link via dm",false);
                eb.addField("`"+Lib.prefix+"game <get|set :NAME>`", "Sends you the current game or sets the current game if you have permissions",false);
                eb.addField("`"+Lib.prefix+"syntax`", "Sends you this message again",false);
                Lib.whitelist.forEach(id -> {
                    if (e.getAuthor().getId().equals(id)||e.getAuthor().equals(e.getGuild().getOwner())) {
                        eb.addField("`"+Lib.prefix+"add @USER @ROLE`", "Adds unlimited roles to unlimited user",false);
                        eb.addField("`"+Lib.prefix+"remove @USER @ROLE`", "Removes unlimited roles to unlimited user",false);
                        eb.addField("`"+Lib.prefix+"kick @USER :REASON`", "Kicks unlimited user and sends them a dm with the reason",false);
                        eb.addField("`"+Lib.prefix+"ban @USER :REASON`", "Swings the banhammer for unlimited user and sends them a dm with the reason",false);
                        eb.addField("`"+Lib.prefix+"mute @USER`", "Mutes unlimited user",false);
                        eb.addField("`"+Lib.prefix+"ummute @USER`", "Unmutes unlimited user",false);
                        eb.addField("`"+Lib.prefix+"clear <1-100>`", "Deletes the last <1-100> messages but takes a while | RestActiom",false);
                        eb.addField("`"+Lib.prefix+"whitelist <add|remove @USER>|<print>`", "Adds|Removes mentioned user at the whitelist or prints the whitelist",false);
                    }
                });
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            } else {
                e.getChannel().sendMessage("´"+Lib.prefix+"syntax` has no syntax like that\nSo write ??syntax or let it be").queue();
            }
        }
    }
}