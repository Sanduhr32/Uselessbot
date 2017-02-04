package com.Sanduhr.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.nio.charset.Charset;
import java.util.List;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
import java.io.*;

public class Bot_main {
    private static JDA JDA;
    public static String t; //Insert your Application Token
    public static final String BOT_GAME = "created by Sanduhr.exe"; //DONT CHANGE THE DEFAULT GAME ORE GIVE ME CREDIT SOMEWHERE ELSE
    public static void main(String[] args)throws Exception {

        t = "INVALIDTOKEN";
        Path p = Paths.get("useless-config.txt");
        String s = "INSERT BOT-TOKEN HERE";
        try{
            List<String> lines = Files.readAllLines(p, Charset.defaultCharset());
            t = lines.get(0);
        }
        catch (Exception e1) {
            byte data[] = s.getBytes();
            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(p, CREATE, APPEND))) {
                out.write(data, 0, data.length);
                System.out.println("Please insert your Token if you haven't already done so. The file is named useless-config.txt and is in the execution path");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        JDABuilder j = new JDABuilder(AccountType.BOT);
        j.setToken(t);
        j.setGame(Game.of(BOT_GAME));
        j.setStatus(OnlineStatus.ONLINE);
        j.addListener(new Command_pub());
        j.addListener(new Command_guildowner());
        j.addListener(new Command_appowner());
        j.addListener(new Guildevent());
        j.addListener(new Command_priv());
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
