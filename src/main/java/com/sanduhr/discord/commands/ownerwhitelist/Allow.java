package com.sanduhr.discord.commands.ownerwhitelist;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Logutils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

import static com.sanduhr.discord.utils.Channel.TextChannelUtils.*;
import static com.sanduhr.discord.utils.Guild.MemberUtil.*;
import static com.sanduhr.discord.utils.Guild.RoleUtil.*;

public class Allow extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `allow` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "allow")) {
            return;
        }

        //If `allow` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        //If attempting to disallow write permissions, suggest using `unmute` command.
        if (syntax[1].equalsIgnoreCase("write")) {
            e.getChannel().sendMessage("Don't use `"+ Lib.PREFIX+"allow " + syntax[1] + " @USER`, try `"+ Lib.PREFIX+"unmute @USER`").queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        List<User> u = e.getMessage().getMentionedUsers();
        List<Role> r = e.getMessage().getMentionedRoles();
        List<TextChannel> c = e.getMessage().getMentionedChannels();

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax.length < 2) {
            return;
        }

        Permission perm = Lib.getPermMap().get(syntax[1].toLowerCase());
        if (perm != null && (!u.isEmpty()||!r.isEmpty()) && c.isEmpty()) {
            for ( User user : u ) {
                if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                    e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(perm).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(perm).queue();
                }
            }

            for ( Role role : r ) {
                if (e.getTextChannel().getPermissionOverride(role) == null) {
                    e.getTextChannel().createPermissionOverride(role).complete().getManager().grant(perm).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(role).getManager().grant(perm).queue();
                }
            }

            Logutils.log.info("Allowed " + perm + " for " + UserToNameList(u) + " in " + e.getTextChannel().getName());
            Logutils.log.info("Allowed " + perm + " for " + RoleListAsName(r) + " in " + e.getTextChannel().getName());
            return;
        }

        if (perm != null && (!u.isEmpty()||!r.isEmpty()) && !c.isEmpty()) {

            for ( Channel channel : c ) {

                for (User user : u) {

                    if (channel.getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        channel.createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(perm).queue();
                    } else {
                        channel.getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(perm).queue();
                    }
                }
                for (Role role : r) {

                    if (channel.getPermissionOverride(role) == null){
                        channel.createPermissionOverride(role).complete().getManager().grant(perm).queue();
                    } else{
                        channel.getPermissionOverride(role).getManager().grant(perm).queue();
                    }
                }
            }
            Logutils.log.info("Allowed " + perm + " for " + UserToNameList(u) + " in " + TextChannelAsName(c));
            Logutils.log.info("Allowed " + perm + " for " + RoleListAsName(r) + " in " + TextChannelAsName(c));
            return;
        }
        if ((u.isEmpty()&&r.isEmpty())||perm == null) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
            eb.setDescription("-**Unknown permission type provided! Your input:** " + syntax[1] + "\n-**Unknown object mentioned!**");
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
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
        return Allow.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Allows the mentioned users the permission";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " <perm> @USER|@ROLE [#CHANNEL]`\n\n" +
                "Permissions:\n" +
                "`write`, `write_tts`, `attach_files`, `embed_links`, `ext_emojis`, `mention_@e`, `reactions`, `read`, `read_history`, `create_invites`, `manage_channel`, `manage_perms`, `manage_webhooks`, `manage_msgs`";
    }
}