package com.Sanduhr.main;

import com.Sanduhr.main.cmds.o_w.*;
import com.Sanduhr.main.cmds.p.*;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static com.Sanduhr.main.Bot_main.getJ;
import static com.Sanduhr.main.config.BOT_GAME;
import static com.Sanduhr.main.config.BOT_TOKEN;

public class Lib {
    public static String prefix ="??";
    public static String YOUR_ID = "198137282018934784";
    public static String Error_guild = "Only works at guilds";
    public static String Error_perms = "You don't have permissions to run this command! :no_entry_sign:";
    public static String Error_target = "You haven't mentioned a user";
    public static Color Green = new Color(40,255,40);
    public static Color Blue = new Color(50, 100,190);
    public static Color Orange = new Color(255, 100, 0);
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");
    private static ArrayList<String> whitelist = new ArrayList<>();
    private static HashMap<String, Permission> permMap = new HashMap<>();
    private static HashMap<String, String> cmdMap = new HashMap<>();

    public static int member = 0;
    public static int received = 0;
    public static int receivedcmd = 0;
    public static int sent = 0;
    public static int executedcmd = 0;
    public static int cleared = 0;
    static void initwhitelist() {
        whitelist.add(YOUR_ID);
    }
    static void initperms() {
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
    static void init() {
        getJ().setToken(BOT_TOKEN);
        getJ().setGame(Game.of(BOT_GAME));
        getJ().setStatus(OnlineStatus.ONLINE);
        /* Owner and whitelisted */
        getJ().addListener(new Add());
        getJ().addListener(new Allow());
        getJ().addListener(new Ban());
        getJ().addListener(new Clear());
        getJ().addListener(new Deny());
        getJ().addListener(new Kick());
        getJ().addListener(new Mute());
        getJ().addListener(new Remove());
        getJ().addListener(new Unmute());
        getJ().addListener(new Whitelist());
        /* Public */
        getJ().addListener(new com.Sanduhr.main.cmds.p.Game());
        getJ().addListener(new Github());
        getJ().addListener(new Help());
        getJ().addListener(new Invite());
        getJ().addListener(new Message());
        getJ().addListener(new Request());
        getJ().addListener(new Sanduhr());
        getJ().addListener(new Status());
        getJ().addListener(new Task());
        /* Other */
        getJ().addListener(new Eventlist());
        initwhitelist();
        initperms();
    }
    public static HashMap<String, Permission> getPermMap() {
        return permMap;
    }
    public static HashMap<String ,String> getCmdMap() {
        return cmdMap;
    }
    public static ArrayList<String> getWhitelist() {
        return whitelist;
    }
}