package com.sanduhr.main.commands.ownerwhitelist;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class Clear extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `clear` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "clear")) {
            return;
        }

        //If `clear` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        if (syntax.length < 3) {
            return;
        }
        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        int i = Integer.parseInt(syntax[1]);

        if (i > 100 || i < 2) {
            e.getChannel().sendMessage(i + " is no valid number between `2 und 100`").queue();
            return;
        }

        List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();

        String filter = "`important`";

        msg = msg.stream().filter(message -> !message.getRawContent().toLowerCase().contains(filter)).collect(Collectors.toList());

        if (syntax[2].equalsIgnoreCase("fast")) {
            msg = msg.stream().filter(message -> !message.getCreationTime().isBefore(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList());
            Lib.cleared = Lib.cleared + msg.size();
            e.getTextChannel().deleteMessages(msg).queue();
        }

        if (syntax[2].equalsIgnoreCase("slow")) {
            Lib.cleared = Lib.cleared + msg.size();
            msg.forEach(message -> message.delete().queue());
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
        return Clear.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Clears the last x messages";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " 2-100 <mode>`\n\nModes:`fast`, `slow`";
    }
}