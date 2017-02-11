package com.Sanduhr.main.cmds.o_w;

import com.Sanduhr.main.lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class whitelist extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split(" ");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `whitelist` command
        if (!syntax[0].equalsIgnoreCase(lib.prefix + "whitelist")) {
            return;
        }

        //If `whitelist` command was received from a non-TextChannel, inform command is Guild-only
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
        e.getMessage().delete().queue();

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        //If there is no argument
        if (syntax[1].equalsIgnoreCase("print")) {
            eb.setColor(lib.Blue);
            lib.getWhitelist().forEach(string -> eb.addField("User:","**Name:** "+e.getJDA().getUserById(string).getName()+"\n**ID:** "+ string, false));
            e.getAuthor().openPrivateChannel().complete().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }
        if (syntax[1].equalsIgnoreCase("add") && e.getMember().isOwner() && !u.isEmpty()) {
            for ( User user : u ) {
                lib.getWhitelist().add(user.getId());
            }
        }
        if (syntax[1].equalsIgnoreCase("remove") && e.getMember().isOwner() && !u.isEmpty()) {
            for ( User user : u ) {
                lib.getWhitelist().remove(user.getId());
            }
        }
        if (u.isEmpty()||!e.getMember().isOwner()) {
            eb.setColor(Color.red);
            eb.addField("Possible error:","-" + lib.Error_target + "\n-" + lib.Error_perms + "\n-" + "Wrong arguments",false);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }

        lib.executedcmd++;
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
        return "Whitelist";
    }
    public String getDescription() {
        return "Adds|Removes users to the whitelist or prints the whitelist";
    }
}