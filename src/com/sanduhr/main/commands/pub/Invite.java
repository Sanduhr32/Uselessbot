package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Invite` command
        if (!content.equalsIgnoreCase(Lib.PREFIX + "Invite")) {
            return;
        }

        //If `Invite` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        eb.setAuthor(e.getAuthor().getName(),null,e.getAuthor().getEffectiveAvatarUrl());
        eb.addField("Add Uselessbot to your server and type `??Syntax` for Help", "[Auth-Link](https://discordapp.com/oauth2/authorize?client_id=" + e.getJDA().getSelfUser().getId() + "&scope=bot&permissions=-1)" +
                "\nIf you need Help or like to talk to the creator of useless, join: [Invite](https://discord.gg/Vz2uaVN)", false);
        e.getAuthor().openPrivateChannel().complete().sendMessage(mb.setEmbed(eb.build()).build()).queue();


        Lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return Invite.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Sends you a dm with an auth link for your server and an Invite for my server";
    }
    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}