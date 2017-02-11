package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class remove extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `remove` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "remove")) {
            return;
        }

        //If `remove` command was received from a non-TextChannel, inform command is Guild-only
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
        List<User> u = e.getMessage().getMentionedUsers();
        List<Role> r = e.getMessage().getMentionedRoles();
        e.getMessage().delete().queue();

        if (u != null && r != null) {
            u.forEach(user -> e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r));
        }
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
    }
    public void initter() {
        lib.getCmdMap().put(getName(), getDescription());
    }
    public String getName() {
        return "Remove";
    }
    public String getDescription() {
        return "Removes all mentioned roles from all mentioned users";
    }
}
