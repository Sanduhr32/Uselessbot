package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Github extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContentDisplay();

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Github` command
        if (!content.equalsIgnoreCase(Lib.PREFIX + "Github")) {
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        Lib.receivedcmd++;
        eb.setAuthor("GitHub","https://github.com",Lib.GITHUB_PNG);
        eb.addField("Repositorys:","Sanduhrs: [**[Sanduhr32-Uselessbot]**](https://github.com/Sanduhr32/Uselessbot)" +
                "\nHelper: [[gerd2002-Uselessbot]](https://github.com/gerd2002/Uselessbot)",false);
        e.getAuthor().openPrivateChannel().queue(chan -> chan.sendMessage(mb.setEmbed(eb.build()).build()).queue());

        System.out.println(e.getAuthor().getName() + " looked into the github repositories");

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
        return Github.class.getSimpleName().toLowerCase();
    }
    private String getDescription() {
        return "Sends you a dm with the Github of useless";
    }
    private String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}