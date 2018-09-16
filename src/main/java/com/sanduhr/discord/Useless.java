package com.sanduhr.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Useless {
    public static List<JDA> shards= new ArrayList<>();
    private static JDA JDA;
    private static final JDABuilder j = new JDABuilder(AccountType.BOT);
    public static final String VERSION = "2.5.2.BETA";
    public static boolean EXPERIMENTAL = false;

    public static void main(String[] args) throws Exception {
        System.err.println("Useless im EXPERIMENTAL Modus starten? yes/no");
        String in = new Scanner(System.in).nextLine();

        switch (in) {
            case "yes":
                EXPERIMENTAL = true;
                exp();
                break;
            case "no":
                EXPERIMENTAL = false;
                start();
                break;
            default:
                int i = 0;
                System.err.println("Invalid");
                for (Character c : in.toCharArray()) {
                    i += c.hashCode();
                }
                System.exit(i);
        }
        /**
         * {@link net.dv8tion.jda.core.Permission.MESSAGE_MANAGE}
         */
    }
    private static void start() throws Exception {
        Lib.init();
        JDA = j.useSharding(0,2).build().awaitReady();
        shards.add(JDA);
        Thread.sleep(7000);
        JDA = j.useSharding(1,2).build().awaitReady();
        shards.add(JDA);
        Thread.sleep(7000);
    }
    private static void exp() throws Exception {
        Lib.init();
        Lib.init_exp();
        JDA = j.useSharding(0,2).build().awaitReady();
        shards.add(JDA);
        Thread.sleep(7000);
        JDA = j.useSharding(1,2).build().awaitReady();
        shards.add(JDA);
        Thread.sleep(7000);
    }
    public static void restart() throws Exception {
        JDA.shutdown();
        JDA = j.build().awaitReady();
    }
    public static void reload() throws Exception {
        restart();
    }

    public static JDA getJDA() {
        return JDA;
    }
    static JDABuilder getJ() {
        return j;
    }
}