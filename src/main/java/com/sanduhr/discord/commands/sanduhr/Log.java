package com.sanduhr.discord.commands.sanduhr;

import static com.sanduhr.discord.utils.Logutils.*;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.Useless;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;

import static com.sanduhr.discord.Lib.*;

public class Log extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        enableTextChannelLog();
        syncronize(Useless.shards.get(0).getTextChannelById(Lib.LOG_CHANNEL));
    }
}
