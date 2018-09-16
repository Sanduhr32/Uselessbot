package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class Mute extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

        //Not the `mute` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "mute")) {
            return;
        }

        //If `mute` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getIdLong()) && !e.getMember().isOwner()) {
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
                    e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).setDeny(Permission.MESSAGE_WRITE).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
            }
            for ( Role role : r) {

                if (e.getTextChannel().getPermissionOverride(role) == null) {
                    e.getTextChannel().createPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(role).getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
            }
        }
        if ((!u.isEmpty() || !r.isEmpty()) && !c.isEmpty()) {
            for ( Channel channel : c ) {

                for ( User user : u) {

                    if (channel.getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        channel.createPermissionOverride(e.getGuild().getMember(user)).setDeny(Permission.MESSAGE_WRITE).queue();
                    } else {
                        channel.getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                    }
                }
                for ( Role role : r) {

                    if (channel.getPermissionOverride(role) == null) {
                        channel.createPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue();
                    } else {
                        channel.getPermissionOverride(role).getManager().deny(Permission.MESSAGE_WRITE).queue();
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
        return Mute.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Mutes all mentioned users at the current channel if no channels are mentioned";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " @USER|@ROLE [#CHANNEL]`";
    }
}