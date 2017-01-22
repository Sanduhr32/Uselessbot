package com.Sanduhr.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.util.List;

public class Bot_main {
    private static JDA JDA;
    public static final String BOT_TOKEN = ""; //Insert your Application Token
    public static final String BOT_GAME = "powered by Sanduhr.exe"; //DONT CHANGE THE DEFAULT GAME ORE GIVE ME CREDIT SOMEWHERE ELSE
    public static void main(String[] args)throws Exception {

        JDABuilder j = new JDABuilder(AccountType.BOT);
        j.setToken(BOT_TOKEN);
        j.setGame(Game.of(BOT_GAME));
        j.setStatus(OnlineStatus.ONLINE);
        j.addListener(new Command_pub());
        j.addListener(new Command_guildowner());
        j.addListener(new Command_appowner());
        j.addListener(new Guildevent());
        JDA jda = j.buildBlocking();
        JDA = jda;
        System.out.println(jda.getAccountType());
        //Could generate a huge output in the console
        List<Guild> g = JDA.getGuilds();
        if (g.isEmpty()) {
            System.out.println("No Guilds");
        }
        if (g.size()>=1) {
            g.forEach(guild -> {
                List<Member> m = guild.getMembers();
                System.out.println(guild.getName());
                System.out.println(guild.getId());
                System.out.println("Name: " + guild.getOwner().getUser().getName() + " ID: " + guild.getOwner().getUser().getId());
                m.forEach(member -> { //Remove to reduce output
                    System.out.println("Name: " + member.getEffectiveName()+ " ID: " + member.getUser().getId()); //Remove for reduce output
                }); //Remove for reduce output
            });
        }
    }
    public static JDA getJDA() {
        return JDA;
    }
}
