package com.sanduhr.discord.commands.console;

import com.sanduhr.discord.utils.Logutils;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.http.util.Args;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sanduhr on 05.04.2017
 */
public class Input extends ListenerAdapter {
    private static HashMap<String, ArrayList<String>> aliMap = new HashMap<>();
    private static final ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService exe2 = Executors.newScheduledThreadPool(1);
    @Override
    public void onReady(ReadyEvent e) {
        System.err.println("---==[INFO]==---\nconsole commands are available in 20 seconds!\nAfter startup the input will be checked each 5 seconds!");
        exe2.schedule(()->System.err.println("[Log] Ab jetzt.."),20, TimeUnit.SECONDS);
        init("shutdown","quit");
        addAlias("shutdown","exit");
        init("log", "console");
        init("help","commands");
        Runnable r = () -> {
            String IN = new Scanner(System.in).nextLine();
            aliMap.forEach((s, strings) -> {
                if (strings.contains(IN)) {
                    if (s.equalsIgnoreCase("shutdown")) {
                        System.exit(666);
                    } else if (s.equalsIgnoreCase("log")) {
                        Logutils.log.debug("This command is broken..");
                        System.err.println("This command is broken..");
                    } else if (s.equalsIgnoreCase("help")) {
                        System.out.println("Commands:\n-help [command (soon)]\n-log <msg>\n-shutdown");
                    }
                } else {
                    System.err.println("Unknown command dude. Type help..");
                }
            });
        };
        exe.scheduleAtFixedRate(r, 0, 5,TimeUnit.SECONDS);
    }


    private void addAlias(String command, String alias) {
        ArrayList<String> temp = aliMap.get(command);
        if (temp.equals(null)) {
            return;
        } else if (temp.contains(alias)) {
            return;
        }
        temp.add(alias);
        aliMap.put(command, temp);
        return;
    }
    public boolean addAlias(String command, ArrayList<String> alias) {
        ArrayList<String> temp = aliMap.get(command);
        if (temp.equals(null)) {
            return false;
        } else if (temp.containsAll(alias)) {
            return false;
        }
        temp.addAll(alias);
        aliMap.put(command, temp);
        return true;
    }
    private void init(String command, String alias) {
        Args.notEmpty(command,"The command");
        ArrayList<String> temp = new ArrayList<>();
        temp.add(command);
        aliMap.put(command, temp);
    }
    public void init(String command, ArrayList<String> alias) {
        if (!alias.contains(command)) {
            alias.add(command);
        }
        aliMap.put(command, alias);
    }
}
