package com.sanduhr.main.commands.pub;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@SuppressWarnings("ALL")
public class Game extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        String[] syntax = e.getMessage().getContent().split("\\s+",3);

        //Not the `Game` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Game")) {
            return;
        }

        //If `Game` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        Lib.receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        String GAME = e.getJDA().getPresence().getGame().getName();
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        if (syntax[1].equalsIgnoreCase("get")) {
            eb.setAuthor(e.getAuthor().getName(), e.getAuthor().getEffectiveAvatarUrl(), e.getAuthor().getEffectiveAvatarUrl());
            eb.setColor(Lib.GREEN);
            eb.addField("Current Game:", GAME, false);
            e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
        }
        if (syntax[1].equalsIgnoreCase("set")) {
            if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
                e.getJDA().getPresence().setGame(net.dv8tion.jda.core.entities.Game.of(syntax[2]));
                eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                eb.setColor(Lib.GREEN);
                eb.addField("Old Game:", GAME, false);
                eb.addField("New Game:", syntax[2], false);
                e.getChannel().sendMessage(mb.setEmbed(eb.build()).build()).queue();
            }
            else {
                e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            }
        }
        if (syntax[1].equalsIgnoreCase("clear")) {
            if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
                e.getJDA().getPresence().setGame(null);
            } else {
                e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
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
    private void initter() {
        Lib.getCmdMap().put(getName(), getDescription());
        Lib.getSynMap().put(getName(), getSyntax());
    }
    private String getName() {
        return Game.class.getSimpleName().toLowerCase();
    }
    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "Returns the current Game or sets it";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + " <args> [String]` \n\nArguments:\n`get`, `set`, `clear`";
    }
}