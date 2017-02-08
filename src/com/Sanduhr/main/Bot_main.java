package com.Sanduhr.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

import java.util.List;

import static com.Sanduhr.main.config.BOT_TOKEN;
import static com.Sanduhr.main.config.BOT_GAME;

public class Bot_main {
    private static JDA JDA;
    public static void main(String[] args)throws Exception {

        JDABuilder j = new JDABuilder(AccountType.BOT);
        j.setToken(BOT_TOKEN);
        j.setGame(Game.of(BOT_GAME));
        j.setStatus(OnlineStatus.ONLINE);
        j.addListener(new Command_pub());
        j.addListener(new Command_guildowner());
        j.addListener(new Command_whitelisted());
        j.addListener(new Guildevent());
        JDA jda = j.buildBlocking();
        JDA = jda;
        System.out.println(jda.getAccountType());
        //Could generate a huge output in the console
        List<Guild> g = JDA.getGuilds();
        if (g.isEmpty()) {
            System.out.println("No Guilds");
        }
        Lib.main();
    }
    public static JDA getJDA() {
        return JDA;
    }
}
