package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

@SuppressWarnings("ALL")
public class Request extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContent().split("\\s+", 4);

        //Not the `Request` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Request")) {
            return;
        }

        //If `Request` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax.length < 2) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:",null, Lib.ERROR_PNG);
            eb.setDescription("\n-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG + "\n-" + Lib.ERROR_EMPTY);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            return;
        }

        String val = Lib.getReqMap().get(syntax[1].toLowerCase());
        if (val != null && !syntax[2].isEmpty() && !syntax[3].isEmpty()) {
            e.getAuthor().openPrivateChannel().complete().sendMessage("Thanks for requesting " + val + " " + syntax[2] + ", " + syntax[3]).queue();
            e.getJDA().getUserById(Lib.YOUR_ID).openPrivateChannel().complete().sendMessage(e.getAuthor().getName() + " requested " + val + " command " + syntax[2] + "\n" + syntax[3]).queue();
        }
        else {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:",null, Lib.ERROR_PNG);
            eb.setDescription("\n-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG + "\n-" + Lib.ERROR_EMPTY);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }

        Lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Request.class.getSimpleName().toLowerCase();
    }
    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "Requests Sanduhr to fix|Add|Remove it!";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " <args> <CMD> <TEXT>`\n\nArguments:\n`fix`, `add`, `remove`";
    }
}