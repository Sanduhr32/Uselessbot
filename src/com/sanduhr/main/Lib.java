package com.sanduhr.main;

import com.sanduhr.main.commands.ownerwhitelist.*;
import com.sanduhr.main.commands.pub.*;
import com.sanduhr.main.commands.pub.Game;
import com.sanduhr.main.commands.pub.Invite;
import com.sanduhr.main.commands.sanduhr.*;
import com.sanduhr.main.commands.sanduhr.Message;
import net.dv8tion.jda.core.*;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.sanduhr.main.Config.BOT_GAME;
import static com.sanduhr.main.Config.TOKEN_BOT;
import static com.sanduhr.main.Useless.getJ;

public class Lib {
    public static final String PREFIX ="??";
    public static final String YOUR_ID = "198137282018934784";
    public static final String GERD_ID = "247410291732774913";
    public static final String GITHUB_PNG = "https://cdn.discordapp.com/avatars/277970452327038977/74b8b6de441bce1a59f9c4ac74f666e6.png";

    public static final String LOG_CHANNEL = "286210279463845888";

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

    public static final ScheduledExecutorService EXECUTE = Executors.newScheduledThreadPool(1);

    private static final HashMap<String, ArrayList> whitelist = new HashMap<>();

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
        System.out.println("[Log]");
        getJ().setToken(TOKEN_BOT);
        getJ().setGame(net.dv8tion.jda.core.entities.Game.of(BOT_GAME));
        getJ().setStatus(OnlineStatus.ONLINE);
        /* Owner and whitelisted */
            getJ().addListener(new Allow());
            getJ().addListener(new Deny());
            getJ().addListener(new Add());
            getJ().addListener(new Remove());
            getJ().addListener(new Ban());
            getJ().addListener(new Kick());
            getJ().addListener(new Mentioneveryone());
            getJ().addListener(new Clear());
            getJ().addListener(new Mute());
            getJ().addListener(new Unmute());
            getJ().addListener(new Whitelist());
        /* Public */
            getJ().addListener(new Game());
            getJ().addListener(new Github());
            getJ().addListener(new Help());
            getJ().addListener(new Info());
            getJ().addListener(new Invite());
            getJ().addListener(new Request());
            getJ().addListener(new Protection());
            getJ().addListener(new Status());
            getJ().addListener(new Syntax());
            getJ().addListener(new Time());
        /* Other */
            getJ().addListener(new Eventlist());
            getJ().addListener(new Message());
            getJ().addListener(new Relog());
            getJ().addListener(new Shutdown());
            getJ().addListener(new Fix());
            getJ().addListener(new Log());
        /* Initting */
        initperms();
        initreq();
        System.out.println("DONE");
    }

    public static HashMap<String, Permission> getPermMap() {
        return permMap;
    }
    public static HashMap<String, String> getCmdMap() {
        return cmdMap;
    }
    public static HashMap<String, String> getSynMap() {
        return synMap;
    }
    public static HashMap<String, String> getReqMap() {
        return reqMap;
    }
    public static HashMap<String, ArrayList> getWhitelist() {
        return whitelist;
    }
}