package com.sanduhr.main.commands.ownerwhitelist;

import com.sanduhr.main.Lib;
import com.sanduhr.main.utils.Whitelist;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Whitelister extends ListenerAdapter {

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
        if (!Lib.getWhitelist_().get(e.getGuild()).contains(e.getAuthor().getId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        Lib.receivedcmd++;
        List<User> u = e.getMessage().getMentionedUsers();

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        //If argument is `print`
        if (syntax[1].equalsIgnoreCase("print")) {
            eb.setColor(Lib.BLUE);
            Lib.getWhitelist_().get(e.getGuild()).forEach(string -> eb.addField(e.getJDA().getUserById(string.toString()).getName(),string.toString(),false));
            e.getAuthor().openPrivateChannel().complete().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            return;
        }

        //If author isn't owner or the list is empty
        if (u.isEmpty()&&!e.getMember().isOwner()) {
            eb.setColor(Color.red);
            eb.setAuthor("Possible error:", null, Lib.ERROR_PNG);
            eb.setDescription("-" + Lib.ERROR_TARGET + "\n-" + Lib.ERROR_PERMS + "\n-" + Lib.ERROR_TARGET);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            return;
        }

        //If argument is `add`
        if (syntax[1].equalsIgnoreCase("add")) {
            for (User user : u) {
                Lib.getWhitelist_().get(e.getGuild()).add(user.getId());
            }
        }

        //If argument is `remove`
        if (syntax[1].equalsIgnoreCase("remove")) {
            for (User user : u) {
                Lib.getWhitelist_().get(e.getGuild()).remove(user.getId());
            }
        }

        Lib.executedcmd++;
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }
    public void onReady(ReadyEvent e) {
        initter();
        List<Guild> g = e.getJDA().getGuilds();
        ArrayList<String> ids = new ArrayList<>();
        ids.add(Lib.YOUR_ID);
        ids.add(Lib.GERD_ID);
        ids.add(Lib.NOBODY);
        g.forEach(guild -> {
            //Whitelist wl = new Whitelist(guild,ids);
            //Lib.getWhitelist().put(guild,wl);
            Lib.getWhitelist_().put(guild,ids);
        });
    }
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return "whitelist";
    }
    private String getDescription() {
        return "Adds|Removes mentioned users to the whitelist or prints the whitelist";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " <args> @USER`\n\nArguments:`add`, `print`, `remove`";
    }
}