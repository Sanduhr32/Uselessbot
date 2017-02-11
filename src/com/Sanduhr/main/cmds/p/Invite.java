package com.Sanduhr.main.cmds.p;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class invite extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `invite` command
        if (!content.equalsIgnoreCase(lib.prefix + "invite")) {
            return;
        }

        //If `invite` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();

        e.getAuthor().openPrivateChannel().complete().sendMessage("Add Uselessbot to your server and type `??syntax` for help" +
                "\nhttps://discordapp.com/oauth2/authorize?client_id=" + e.getJDA().getSelfUser().getId() + "&scope=bot&permissions=8"+
                "\nIf you need help or like to talk to the creator of useless, join: discord.gg/Vz2uaVN").queue();

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
        return invite.class.getSimpleName();
    }
    public String getDescription() {
        return "Sends you a dm with an auth link for your server and an invite for my server";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + "`";
    }
}