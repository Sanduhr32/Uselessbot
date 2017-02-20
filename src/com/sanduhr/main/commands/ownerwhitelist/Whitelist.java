package com.sanduhr.main.commands.ownerwhitelist;

import com.sanduhr.main.Lib;
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

public class Whitelist extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `whitelist` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "whitelist")) {
            return;
        }

        //If `whitelist` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!Lib.getWhitelist().contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        List<User> u = e.getMessage().getMentionedUsers();
        e.getMessage().delete().queue();

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        //If there is no argument
        if (syntax[1].equalsIgnoreCase("print")) {
            eb.setColor(Lib.BLUE);
            Lib.getWhitelist().forEach(string -> eb.addField("User:","**Name:** "+e.getJDA().getUserById(string).getName()+"\n**ID:** "+ string, false));
            e.getAuthor().openPrivateChannel().complete().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }
        if (u.isEmpty()&&!e.getMember().isOwner()) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
            eb.setDescription("-" + Lib.ERROR_TARGET + "\n-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_TARGET);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            return;
        }
        if (syntax[1].equalsIgnoreCase("add")) {
            for ( User user : u ) {
                Lib.getWhitelist().add(user.getId());
            }
        }
        if (syntax[1].equalsIgnoreCase("remove")) {
            for ( User user : u ) {
                Lib.getWhitelist().remove(user.getId());
            }
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
        Lib.getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return Whitelist.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Adds|Removes mentioned users to the whitelist or prints the whitelist";
    }
    public String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " <args> @USER`\n\nArguments:`add`, `print`, `remove`";
    }
}