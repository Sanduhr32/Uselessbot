package com.sanduhr.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Useless extends ListenerAdapter implements EventListener {
    public static final String VERSION = "2.5.2.BETA";
    public static List<JDA> shards = new ArrayList<>();
    public static boolean EXPERIMENTAL;
    public static int shardCount = 1;
    private static JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT);
    private static JDA JDA;
    private static String token;

    public static void main(String[] args) throws Exception {
        JSONObject config = new JSONObject(new File("config.json"));
        token = config.getString("token");
        EXPERIMENTAL = config.getBoolean("experimental");
        jdaBuilder.setToken(token);
        shardCount = config.getInt("shardCount");
        start();
    }

    private static void start() throws Exception {
        Lib.initOnce();
        if (EXPERIMENTAL) {
            Lib.init_expOnce();
        }
        if (shardCount < 2) {
            shards.add(getJdaBuilder().buildAsync());
        } else {
            for (int i = 0; i < shardCount; i++) {
                shards.add(getJdaBuilder().useSharding(i, shardCount).buildAsync());
            }
        }
    }


    public static void restart() throws Exception {
        shards.forEach(net.dv8tion.jda.core.JDA::shutdown);
        shards.clear();
        start();
    }


    static JDABuilder getJdaBuilder() {
        return jdaBuilder;
    }
}