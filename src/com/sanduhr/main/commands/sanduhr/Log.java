package com.sanduhr.main.commands.sanduhr;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.PrintStream;

import static com.sanduhr.main.Lib.*;

public class Log extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        System.setOut(new PrintStream(System.out){
            @Override
            public void println(String s) {
                if (s.contains("[Fatal]")
                    ||s.contains("[Log]")
                    ||s.contains("[RestAction]")
                    ||s.contains("[Info]")
                    ||s.contains("[Warning]")
                    ||s.contains("[Trace]")
                    ||s.contains("[Debug]")
                    ||s.contains("[Finest]")) {
                    e.getJDA().getTextChannelById(LOG_CHANNEL).sendMessage(s).queue();
                }
            }
        });
    }
}
