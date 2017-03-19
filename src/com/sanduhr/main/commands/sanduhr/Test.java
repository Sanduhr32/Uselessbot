package com.sanduhr.main.commands.sanduhr;

import com.sanduhr.main.Lib;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;


@SuppressWarnings("ALL")
public class Test extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Test` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "Test")) {
            return;
        }

        //If `Test` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(Lib.ERROR_GUILDS).queue();
            return;
        }

        /*If the member that sent the command isn't in the Whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!e.getMember().isOwner()) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        List<User> u = e.getMessage().getMentionedUsers();

        if (!u.isEmpty()) {
            e.getChannel().sendMessage("Result: " + Lib.getWhitelist().get(e.getGuild()).toString() + ".").queue();
        }

        Lib.receivedcmd++;
        e.getMessage().delete().queue();

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
        return Test.class.getSimpleName().toLowerCase();
    }

    @SuppressWarnings("SameReturnValue")
    private String getDescription() {
        return "";
    }

    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}