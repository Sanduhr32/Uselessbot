package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Protection extends ListenerAdapter {

    private HashMap<Guild, Boolean> protMap = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //If `Protection` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            return;
        }

        if (e.getMessage().getRawContent().equalsIgnoreCase(Lib.PREFIX+"protection toggle") && e.getMember().isOwner()) {
            boolean b = protMap.get(e.getGuild());
            b = !b;
            protMap.put(e.getGuild(), b);
            e.getChannel().sendMessage("Set OWNER_MENTION_PROTECTION to " + b).queue();
        }

        if (!protMap.get(e.getGuild()))  {
            return;
        }

        //Not the `Protection` command
        if (!e.getMessage().isMentioned(e.getGuild().getOwner().getUser())
                ||e.getMember().isOwner()
                ||Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getIdLong())
                ||e.getMessage().getRawContent().startsWith("`Important`")) {
            return;
        }

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        e.getChannel().sendMessage("Please don't mention " + e.getGuild().getOwner().getUser().getName() + "!\nIf its really `Important` insert a \\`Important\\` at the begin of your message!\nIf you want to disable the protection ask " + e.getGuild().getOwner().getAsMention() + ". The command is \\??protection toggle\\`").queue(
                msg -> msg.delete().queueAfter(30,TimeUnit.SECONDS)
        );
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onReady(ReadyEvent e) {
        e.getJDA().getGuilds().forEach(guild->{
            protMap.put(guild, false);
        });
    }
}