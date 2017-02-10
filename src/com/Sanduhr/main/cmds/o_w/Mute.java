package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.Lib;
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

        String[] syntax = e.getMessage().getContent().split(" ");

        //Not the `mute` command
        if (!syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
            return;
        }

        //If `mute` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.Error_guild).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist().contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.Error_perms).queue();
            return;
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();
        List<User> u = e.getMessage().getMentionedUsers();
        List<Role> r = e.getMessage().getMentionedRoles();
        List<TextChannel> c = e.getMessage().getMentionedChannels();
        System.out.println(u);
        System.out.println(r);
        System.out.println(c);

        if ((!u.isEmpty() || !r.isEmpty()) && c.isEmpty()) {
            for ( User user : u ) {
                if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                    e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
                else {
                    e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
            }
            for ( Role role : r) {
                if (e.getTextChannel().getPermissionOverride(role) == null) {
                    e.getTextChannel().createPermissionOverride(role).complete().getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
                else {
                    e.getTextChannel().getPermissionOverride(role).getManager().deny(Permission.MESSAGE_WRITE).queue();
                }
            }
        }
        if ((!u.isEmpty() || !r.isEmpty()) && !c.isEmpty()) {
            for ( Channel channel : c ) {
                for ( User user : u) {
                    if (channel.getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        channel.createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).queue();
                    } else {
                        channel.getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                    }
                }
                for ( Role role : r) {
                    if (channel.getPermissionOverride(role) == null) {
                        channel.createPermissionOverride(role).complete().getManager().deny(Permission.MESSAGE_WRITE).queue();
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
    public void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
    }
    public String getName() {
        return Mute.class.getName();
    }
    public String getDescription() {
        return "Mutes all mentioned user at the current channel if no channels are mentioned";
    }
}