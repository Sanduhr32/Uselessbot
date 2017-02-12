package com.Sanduhr.main.cmds.o_w;


import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class deny extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `deny` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "deny")) {
            return;
        }

        //If `allow` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(lib.Error_guild).queue();
            return;
        }

        //If attempting to disallow write permissions, suggest using `mute` command.
        if (syntax[1].equalsIgnoreCase("write")) {
            e.getChannel().sendMessage("Dont use `"+ lib.prefix+"deny " + syntax[1] + " @USER`, try `"+ lib.prefix+"mute @USER`").queue();
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
        e.getMessage().delete().queue();
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        Permission perm = lib.getPermMap().get(syntax[1]);
        if (perm != null && u != null) {
            u.forEach(user -> {
                if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                    e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(perm).queue();
                } else {
                    e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(perm).queue();
                }
            });
        }
        else {
            eb.setColor(Color.red);
            eb.addField("Possible error:","**Unknown permission type provided! Your input:** " + syntax[1] +
                    "\n\n**Unknown user mentioned!**",false);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
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
        lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return deny.class.getSimpleName();
    }
    public String getDescription() {
        return "Denies all mentioned users the permission";
    }
    public String getSyntax() {
        return "`" + lib.prefix + getName() + " <perm> @USER`\n\nPermissions:\nsoon:tm:";
    }
}