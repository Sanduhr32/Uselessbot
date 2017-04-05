package com.sanduhr.discord.commands.console;

import com.sanduhr.discord.Lib;
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
    private static ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void onReady(ReadyEvent e) {
        init("shutdown","quit");
        addAlias("shutdown","exit");
        init("log", (String) null);
        String IN = new Scanner(System.in).nextLine();
        aliMap.forEach((s, strings) -> {
            if (!strings.contains(IN)) {
            } else {
                if (s.equalsIgnoreCase("shutdown")) {
                    String[] split = IN.split("\\-+");
                    if (split.length < 2) {
                        System.exit(666);
                    }
                    if (split.length >= 2)  {
                        String[] parts = split[1].split("\\s+");
                        if (parts.length < 2) {
                            System.err.println("-Inserted invalid arguments\n-Inserted not enough arguments");
                        } else if (parts.length > 2) {
                            System.err.println("-Inserted invalid arguments\n-Inserted too many arguments");
                        } else {
                            if (parts[0].equalsIgnoreCase("t")) {
                                exe.schedule(()->System.exit(0074),Long.valueOf(parts[1]), TimeUnit.SECONDS);
                            }
                            if (parts[0].equalsIgnoreCase("j")) {
                                e.getJDA().shutdown(false);
                                System.out.println("JDA exit with code 006A.");
                            }
                            if (parts[0].equalsIgnoreCase("f")) {
                                e.getJDA().shutdown(false);
                                System.out.println("JDA exit with code 0066.");
                            }
                            if (split.length == 3) {
                                if (parts[0].equalsIgnoreCase("t")&&split[2].split("\\s+")[0].equalsIgnoreCase("j")) {
                                    exe.schedule(()->e.getJDA().shutdown(false),Long.valueOf(parts[1]), TimeUnit.SECONDS);
                                }
                            }
                            if (split.length == 4) {
                                if (parts[0].equalsIgnoreCase("t")&&split[2].split("\\s+")[0].equalsIgnoreCase("j")&&split[3].split("\\s+")[0].equalsIgnoreCase("f")) {
                                    exe.schedule(()->{e.getJDA().shutdown(false);System.out.println("JDA exit with code 0066.");},Long.valueOf(parts[1]), TimeUnit.SECONDS);
                                }
                            }
                        }
                    }
                } else if (s.equalsIgnoreCase("log")) {
                    e.getJDA().getTextChannelById(Lib.LOG_CHANNEL).sendMessage(IN.split("\\s+",2)[1]).queue();
                } else if (s.equalsIgnoreCase("help")) {
                    System.out.println("Commands:\n-help [command (soon)]\n-log <msg>\n-shutdown [param]\n\nParameters are:\n-t <val> (in seconds)  --> System.exit\n-j  --> JDA#shutdown()\n-f  --> same as -j but too lazy to remove it again after noticed misstake..");
                }
            }
        });
    }


    public boolean addAlias(String command, String alias) {
        ArrayList<String> temp = aliMap.get(command);
        if (temp.equals(null)) {
            return false;
        } else if (temp.contains(alias)) {
            return false;
        }
        temp.add(alias);
        aliMap.put(command, temp);
        return true;
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
    public void init(String command, String alias) {
        Args.notEmpty(command,"The command");
        ArrayList<String> temp = new ArrayList<>();
        temp.add(command);
        if (!alias.isEmpty()||alias != null) {
            temp.add(alias);
        }
        aliMap.put(command, temp);
    }
    public void init(String command, ArrayList<String> alias) {
        if (!alias.contains(command)) {
            alias.add(command);
        }
        aliMap.put(command, alias);
    }
}
