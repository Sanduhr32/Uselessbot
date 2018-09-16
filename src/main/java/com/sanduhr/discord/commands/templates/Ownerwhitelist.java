package com.sanduhr.discord.commands.templates;

import static com.sanduhr.discord.Lib.*;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


class Ownerwhitelist extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContentDisplay().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `CMD` command
        if (!syntax[0].equalsIgnoreCase(PREFIX + "CMD")) {
            return;
        }

        //If `CMD` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the Whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getIdLong()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(ERROR_PERMS).queue();
            return;
        }

        receivedcmd++;
        e.getMessage().delete().queue();

        executedcmd++;
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    private void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Ownerwhitelist.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "";
    }
    private String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}