package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class clear extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `clear` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "clear")) {
            return;
        }

        //If `clear` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!lib.getWhitelist().contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(lib.Error_perms).queue();
            return;
        }

        lib.receivedcmd++;
        e.getMessage().delete().queue();
        int i = Integer.parseInt(syntax[1]);
        if (i > 100 || i < 1) {
            e.getChannel().sendMessage(i + " is no valid number between `1 to 100`").queue();
            return;
        }

        List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
        msg.forEach(message -> message.delete().queue());
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
        return clear.class.getSimpleName();
    }
    public String getDescription() {
        return "Clears the last x messages";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + " 1-100`";
    }
}