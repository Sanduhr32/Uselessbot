package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Mentioneveryone extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Mentioneveryone` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Mentioneveryone")) {
            return;
        }

        /*If the member that sent the command isn't in the Whitelist
          or the Owner of the Guild, they don't have permission to run this command!*/
        if (!e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        e.getChannel().sendMessage(e.getGuild().getPublicRole().getName()).queue();
        Lib.executedcmd++;
    }

    public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
        onGuildMessageReceived(new GuildMessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Mentioneveryone.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "mssingno";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}