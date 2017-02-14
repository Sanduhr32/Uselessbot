package com.sanduhr.main;

import com.sanduhr.main.commands.ownerwhitelist.*;
import com.sanduhr.main.commands.pub.*;
import com.sanduhr.main.commands.sanduhr.*;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static com.sanduhr.main.Config.*;
import static com.sanduhr.main.Useless.getJ;

public class Lib {
    public static final String PREFIX ="??";
    public static final String YOUR_ID = "198137282018934784";

    public static final String ERROR_GUILDS = "Only works at guilds";
    public static final String ERROR_PERMS = "You don't have permissions to run this command! :no_entry_sign:";
    public static final String ERROR_TARGET = "You haven't mentioned a user";
    public static final String ERROR_WRONG = "Wrong arguments";
    public static final String ERROR_EMPTY = "Empty arguments";
    public static final String ERROR_MANY = "Too many arguments";
    public static final String ERROR_PNG = "https://cdn.discordapp.com/attachments/279257860121165834/280098209945092097/error.png";

    public static final Color GREEN = new Color(40,255,40);
    public static final Color BLUE = new Color(50, 100,190);
    public static final Color ORANGE = new Color(255, 100, 0);

    static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");

    private static final ArrayList<String> whitelist = new ArrayList<>();

    private static final HashMap<String, Permission> permMap = new HashMap<>();
    private static final HashMap<String, String> cmdMap = new HashMap<>();
    private static final HashMap<String, String> synMap = new HashMap<>();
    private static final HashMap<String, String> reqMap = new HashMap<>();

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
        reqMap.put("Add",    "to Add");
        reqMap.put("Remove", "to Remove");
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
        getJ().addListener(new com.sanduhr.main.commands.pub.Game());
        getJ().addListener(new Github());
        getJ().addListener(new Help());
        getJ().addListener(new Invite());
        getJ().addListener(new Request());
        getJ().addListener(new Sanduhr());
        getJ().addListener(new Status());
        getJ().addListener(new Syntax());
        getJ().addListener(new Task());
        getJ().addListener(new Time());
        /* Other */
        getJ().addListener(new Eventlist());
        getJ().addListener(new Message());
        getJ().addListener(new Relog());
        getJ().addListener(new Shutdown());
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
