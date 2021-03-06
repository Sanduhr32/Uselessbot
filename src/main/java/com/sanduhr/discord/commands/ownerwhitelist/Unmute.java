package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;


public class Unmute extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

        //Not the `unmute` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "unmute")) {
            return;
        }

        //If `unmute` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Tierutils.getWhiteListForGuild(e.getGuild()).contains(e.getAuthor().getIdLong()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        List<User> u = e.getMessage().getMentionedUsers();
        List<Role> r = e.getMessage().getMentionedRoles();
        List<TextChannel> c = e.getMessage().getMentionedChannels();

        if ((!u.isEmpty() || !r.isEmpty()) && c.isEmpty()) {

            for ( User user : u ) {

                if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                    e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).setAllow(Permission.MESSAGE_WRITE).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                }
            }
            for ( Role role : r) {

                if (e.getTextChannel().getPermissionOverride(role) == null) {
                    e.getTextChannel().createPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(role).getManager().grant(Permission.MESSAGE_WRITE).queue();
                }
            }
        }

        if ((!u.isEmpty() || !r.isEmpty()) && !c.isEmpty()) {

            for ( Channel channel : c ) {

                for ( User user : u) {

                    if (channel.getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        channel.createPermissionOverride(e.getGuild().getMember(user)).setAllow(Permission.MESSAGE_WRITE).queue();
                    } else {
                        channel.getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                    }
                }
                for ( Role role : r) {

                    if (channel.getPermissionOverride(role) == null) {
                        channel.createPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue();
                    } else {
                        channel.getPermissionOverride(role).getManager().grant(Permission.MESSAGE_WRITE).queue();
                    }
                }
            }
        }
        if (u.isEmpty() && r.isEmpty()) {
            e.getChannel().sendMessage("You mentioned an unknown object or didn't used the right syntax!").queue();
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
        return Unmute.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Unmutes all mentioned users";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " @USER|@ROLE [#CHANNEL]`";
    }
}