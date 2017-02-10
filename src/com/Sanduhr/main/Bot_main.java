package com.Sanduhr.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;

import java.util.List;

public class Bot_main {
    private static JDA JDA;
    private static JDABuilder j = new JDABuilder(AccountType.BOT);
    public static void main(String[] args)throws Exception {
        start();
    }
    static void start() throws Exception {
        Lib.init();
        JDA jda = j.buildBlocking();
        JDA = jda;
        System.out.println(jda.getAccountType());
        //Could generate a huge output in the console
        List<Guild> g = JDA.getGuilds();
        if (g.isEmpty()) {
            System.out.println("No Guilds");
        }
    }
    public static JDA getJDA() {
        return JDA;
    }
    static JDABuilder getJ() {
        return j;
    }
}
