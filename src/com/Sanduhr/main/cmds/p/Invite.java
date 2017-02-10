package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.Lib;
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

        //Not the `invite` command
        if (!content.equalsIgnoreCase(Lib.prefix + "invite")) {
            return;
        }

        //If `invite` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

        e.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Add Uselessbot to your server and type `??syntax` for help" +
                "\nhttps://discordapp.com/oauth2/authorize?client_id=" + e.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8"+
                "\nIf you need help or like to talk to the creator of useless, join: discord.gg/Vz2uaVN").queue());

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
    }
    public String getName() {
        return Invite.class.getName();
    }
    public String getDescription() {
        return "Sends you a dm with an auth link for your server and an invite for my server";
    }
}