package com.sanduhr.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.util.Scanner;

public class Useless {
    private static JDA JDA;
    private static final JDABuilder j = new JDABuilder(AccountType.BOT);
    public static final String VERSION = "2.5";

    public static void main(String[] args) throws Exception {
        System.err.println("Useless im EXPERIMENTAL Modus starten? yes/no");
        String in = new Scanner(System.in).nextLine();

        switch (in) {
            case "yes":
                exp();
                break;
            case "no":
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
		
    }
    private static void start() throws Exception {
        Lib.init();
        JDA = j.buildBlocking();
    }
    private static void exp() throws Exception {
        Lib.init();
        Lib.init_exp();
        JDA = j.buildBlocking();
    }
    public static void restart() throws Exception {
        JDA.shutdown(false);
        JDA = j.buildBlocking();
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