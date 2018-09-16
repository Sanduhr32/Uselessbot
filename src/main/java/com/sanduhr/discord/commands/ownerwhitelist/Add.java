package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

import static com.sanduhr.discord.utils.Guild.MemberUtil.*;
import static com.sanduhr.discord.utils.Guild.RoleUtil.*;

public class Add extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContentDisplay().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `add` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "add")) {
            return;
        }

        //If `add` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getIdLong())&& !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        List<User> u = e.getMessage().getMentionedUsers();
        List<Role> r = e.getMessage().getMentionedRoles();

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        if (!u.isEmpty() && !r.isEmpty()) {
            u.forEach(user -> e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue());
            System.out.println("Added " + RoleListAsName(r) + " to " + UserToNameList(u));
        }
        else {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
            eb.setDescription("-" + Lib.ERROR_TARGET + "\n-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_WRONG);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    @Override
    public void onReady(ReadyEvent e) {
        initter();
    }
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Add.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Adds all mentioned roles to all mentioned users";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " @USER @ROLE`";
    }
}
