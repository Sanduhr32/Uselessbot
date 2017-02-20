package com.sanduhr.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Useless {
    private static JDA JDA;
    private static JDABuilder j = new JDABuilder(AccountType.BOT);
    public static void main(String[] args)throws Exception {
        start();
    }
    private static void start() throws Exception {
        Lib.init();
        JDA = j.useSharding(1, 2).buildBlocking();
        JDA = j.useSharding(0, 2).buildBlocking();
        System.out.println(JDA.getAccountType());
    }
    public static void restart() throws Exception {
        JDA = j.useSharding(1, 2).buildBlocking();
        JDA = j.useSharding(0, 2).buildBlocking();
    }
    public static JDA getJDA() {
        return JDA;
    }
    static JDABuilder getJ() {
        return j;
    }
}