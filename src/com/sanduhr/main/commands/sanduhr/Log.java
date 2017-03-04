package com.sanduhr.main.commands.sanduhr;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.PrintStream;

public class Log extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        //Runnable r = () -> {

            //if (LINE.contains("[Fatal]")||LINE.contains("[Log]")) {
            //    e.getJDA().getTextChannelById("286210279463845888").sendMessage(LINE).queue();
            //}
        //};
        //Lib.EXECUTE.scheduleWithFixedDelay(r,1,1, TimeUnit.SECONDS);
        System.setOut(new PrintStream(System.out){
            @Override
            public void println(String s) {
                if (s.contains("[Fatal]")||s.contains("[Log]")) {
                    System.out.println(s);
                    e.getJDA().getTextChannelById("286210279463845888").sendMessage(s).queue();
                }
            }
        });
    }
}
