package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Clear extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

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
//        if (!Tierutils.isTier(e.getAuthor(), Tierutils.Tier.GUILD_WHITELIST, e.getGuild())||
//                !Tierutils.isTier(e.getAuthor(), Tierutils.Tier.GUILD_OWNER, e.getGuild())) {
//            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
//            return;
//        }

        if (!e.getMember().isOwner()||!Tierutils.getWhiteListForGuild(e.getGuild()).contains(e.getAuthor().getIdLong())) {
            return;
        }

        if (syntax.length < 3) {
            return;
        }
        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        } else {
            e.getChannel().sendMessage("Uhm.. i cant delete messages.. clear wont work. pls fix kthx").queue();
            return;
        }

        int i = Integer.parseInt(syntax[1]);

        if (i < 2) {
            e.getChannel().sendMessage(i + " is no valid number above `2`").queue();
            return;
        }

        if (i > 100) {
            return;
        }

        String filter = "`important`";

        e.getChannel().getHistory().retrievePast(i).queue(msg -> {
                msg = msg.stream().filter(message -> !message.getContentRaw().toLowerCase().contains(filter)).collect(Collectors.toList());

                if (syntax[2].equalsIgnoreCase("fast")) {
                    msg = msg.stream().filter(message -> message.getCreationTime().isAfter(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList());
                    Lib.cleared = Lib.cleared + msg.size();
                    e.getTextChannel().deleteMessages(msg).queue();
                }

                if (syntax[2].equalsIgnoreCase("slow")) {
                    Lib.cleared = Lib.cleared + msg.size();
                    msg.forEach(message -> message.delete().queue());
                }
            });

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