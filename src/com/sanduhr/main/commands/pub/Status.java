package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class Status extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Status` command
        if (!content.equalsIgnoreCase(Lib.PREFIX + "Status")) {
            return;
        }

        //If `Status` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        List<Guild> g = e.getJDA().getGuilds();
        Lib.member = 0;
        g.forEach(guild -> {
            guild.getMembers();
            Lib.member = Lib.member + guild.getMembers().size() - 1;
        });

        //Builder
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        //Configuraing Builder
        eb.setAuthor(e.getJDA().getSelfUser().getName(), null, e.getJDA().getSelfUser().getAvatarUrl());
        eb.addField("Guilds:", String.valueOf(e.getJDA().getGuilds().size()), false);
        eb.addField("Member:", String.valueOf(Lib.member), false);
        eb.addField("Received messages:",String.valueOf(Lib.received), false);
        eb.addField("Received commands:", String.valueOf(Lib.receivedcmd),false);
        eb.addField("Sent messages:", String.valueOf(Lib.sent + 1), false);
        eb.addField("Successful executed commands:", String.valueOf(Lib.executedcmd + 1), false);
        eb.addField("Cleared messages:", String.valueOf(Lib.cleared), false);

        e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();

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
        return Status.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Sends some info's like received|send stuff etc";
    }
    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}