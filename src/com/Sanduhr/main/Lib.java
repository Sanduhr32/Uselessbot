package com.Sanduhr.main;

import com.Sanduhr.main.cmds.o_w.*;
import com.Sanduhr.main.cmds.p.*;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static com.Sanduhr.main.bot_main.getJ;
import static com.Sanduhr.main.config.BOT_GAME;
import static com.Sanduhr.main.config.BOT_TOKEN;

public class lib {
    public static String prefix ="??";
    public static String YOUR_ID = "198137282018934784";

    public static String Error_guild = "Only works at guilds";
    public static String Error_perms = "You don't have permissions to run this command! :no_entry_sign:";
    public static String Error_target = "You haven't mentioned a user";
    public static String Error_wrong = "Wrong arguments";
    public static String Error_empty = "Empty arguments";
    public static String Error_many = "Too many arguments";
    public static String Error_png = "https://cdn.discordapp.com/attachments/279257860121165834/280098209945092097/error.png";

    public static Color Green = new Color(40,255,40);
    public static Color Blue = new Color(50, 100,190);
    public static Color Orange = new Color(255, 100, 0);

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");

    private static ArrayList<String> whitelist = new ArrayList<>();

    private static HashMap<String, Permission> permMap = new HashMap<>();
    private static HashMap<String, String> cmdMap = new HashMap<>();
    private static HashMap<String, String> synMap = new HashMap<>();
    private static HashMap<String, String> reqMap = new HashMap<>();

    public static int member = 0;
    public static int received = 0;
    public static int receivedcmd = 0;
    public static int sent = 0;
    public static int executedcmd = 0;
    public static int cleared = 0;

    private static void initwhitelist() {
        whitelist.add(YOUR_ID);
    }
    private static void initperms() {
        permMap.put("write_tts",       Permission.MESSAGE_TTS);
        permMap.put("attach_files",    Permission.MESSAGE_ATTACH_FILES);
        permMap.put("embed_links",     Permission.MESSAGE_EMBED_LINKS);
        permMap.put("ext_emojis",      Permission.MESSAGE_EXT_EMOJI);
        permMap.put("mention_@e",      Permission.MESSAGE_MENTION_EVERYONE);
        permMap.put("reactions",       Permission.MESSAGE_ADD_REACTION);
        permMap.put("read",            Permission.MESSAGE_READ);
        permMap.put("read_history",    Permission.MESSAGE_HISTORY);
        permMap.put("create_invites",  Permission.CREATE_INSTANT_INVITE);
        permMap.put("manage_channel",  Permission.MANAGE_CHANNEL);
        permMap.put("manage_perms",    Permission.MANAGE_PERMISSIONS);
        permMap.put("manage_webhooks", Permission.MANAGE_WEBHOOKS);
        permMap.put("manage_msgs",     Permission.MESSAGE_MANAGE);
    }
    private static void initreq() {
        reqMap.put("fix",    "to fix");
        reqMap.put("add",    "to add");
        reqMap.put("remove", "to remove");
    }
    static void init() {
        getJ().setToken(BOT_TOKEN);
        getJ().setGame(Game.of(BOT_GAME));
        getJ().setStatus(OnlineStatus.ONLINE);
        /* Owner and whitelisted */
        getJ().addListener(new add());
        getJ().addListener(new allow());
        getJ().addListener(new ban());
        getJ().addListener(new clear());
        getJ().addListener(new deny());
        getJ().addListener(new kick());
        getJ().addListener(new mute());
        getJ().addListener(new remove());
        getJ().addListener(new unmute());
        getJ().addListener(new whitelist());
        /* Public */
        getJ().addListener(new game());
        getJ().addListener(new github());
        getJ().addListener(new help());
        getJ().addListener(new invite());
        getJ().addListener(new message());
        getJ().addListener(new request());
        getJ().addListener(new sanduhr());
        getJ().addListener(new status());
        getJ().addListener(new syntax());
        getJ().addListener(new task());
        /* Other */
        getJ().addListener(new eventlist());
        /* Initting */
        initwhitelist();
        initperms();
        initreq();
    }

    public static HashMap<String, Permission> getPermMap() {
        return permMap;
    }
    public static HashMap<String ,String> getCmdMap() {
        return cmdMap;
    }
    public static HashMap<String, String> getSynMap() {
        return synMap;
    }
    public static HashMap<String, String> getReqMap() {
        return reqMap;
    }
    public static ArrayList<String> getWhitelist() {
        return whitelist;
    }
}