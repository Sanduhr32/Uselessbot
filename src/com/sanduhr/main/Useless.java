package com.sanduhr.main;

import com.sanduhr.main.utils.Logutils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Useless {
    private static JDA JDA;
    private static final JDABuilder j = new JDABuilder(AccountType.BOT);

    public static void main(String[] args) throws Exception {
        start();
    }
    private static void start() throws Exception {
        Lib.init();
        JDA = j.buildBlocking();
    }
    public static void restart() throws Exception {
        JDA.shutdown(false);
        JDA = j.buildBlocking();
        Logutils.log.info("Login Successful!,Connected to WebSocket!,Finished Loading!");
        Logutils.log.info("Relogged Successful!");
    }

    public static JDA getJDA() {
        return JDA;
    }
    static JDABuilder getJ() {
        return j;
    }
}