package com.sanduhr.main.commands.sanduhr;

import static com.sanduhr.main.utils.Logutils.*;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;

import static com.sanduhr.main.Lib.*;

public class Log extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        List<TextChannel> tc = new ArrayList<>();
        tc.add(e.getJDA().getTextChannelById(LOG_CHANNEL));
        enableTextChannelLog();
        setTextChannel(tc);
        logToTextChannels(e);
    }
}
