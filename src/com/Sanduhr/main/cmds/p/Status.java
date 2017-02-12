package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class status extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `status` command
        if (!content.equalsIgnoreCase(lib.prefix + "status")) {
            return;
        }

        //If `status` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();
        List<Guild> g = e.getJDA().getGuilds();
        lib.member = 0;
        g.forEach(guild -> {
            guild.getMembers();
            lib.member = lib.member + guild.getMembers().size() - 1;
        });

        //Builder
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        //Configuraing Builder
        eb.setAuthor(e.getJDA().getSelfUser().getName(), null, e.getJDA().getSelfUser().getAvatarUrl());
        eb.addField("Guilds:", String.valueOf(e.getJDA().getGuilds().size()), false);
        eb.addField("Member:", String.valueOf(lib.member), false);
        eb.addField("Received messages:",String.valueOf(lib.received), false);
        eb.addField("Received commands:", String.valueOf(lib.receivedcmd),false);
        eb.addField("Sent messages:", String.valueOf(lib.sent + 1), false);
        eb.addField("Successful executed commands:", String.valueOf(lib.executedcmd + 1), false);
        eb.addField("Cleared messages:", String.valueOf(lib.cleared), false);

        e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();

        lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        lib.getCmdMap().put(getName(), getDescription());
        lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return status.class.getSimpleName();
    }
    public String getDescription() {
        return "Sends some info's like received|send stuff etc";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + "`";
    }
}