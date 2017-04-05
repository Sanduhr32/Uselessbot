package com.sanduhr.discord;

import com.sanduhr.discord.commands.Experimental.Eval_exp;
import com.sanduhr.discord.commands.console.Input;
import com.sanduhr.discord.commands.ownerwhitelist.*;
import com.sanduhr.discord.commands.pub.*;
import com.sanduhr.discord.commands.pub.Game;
import com.sanduhr.discord.commands.pub.Invite;
import com.sanduhr.discord.commands.sanduhr.*;
import com.sanduhr.discord.commands.sanduhr.Message;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.sanduhr.discord.Config.*;
import static com.sanduhr.discord.Useless.*;

public class Lib {

    public static ArrayList<String> WL = new ArrayList<>();

    public static final String PREFIX ="??";
    public static final String YOUR_ID = "198137282018934784";
    public static final String GERD_ID = "247410291732774913";
    public static final String NOBODY = "189702310429982720";
    public static final String GITHUB_PNG = "https://cdn.discordapp.com/avatars/277970452327038977/74b8b6de441bce1a59f9c4ac74f666e6.png";

    static final        String LOG_GUILD = "283353013530132500";
    public static final String LOG_CHANNEL = "286210279463845888";

    public static final String ERROR_GUILDS = "Only works at guilds";
    public static final String ERROR_PERMS = "You don't have permissions to run this command! :no_entry_sign:";
    public static final String ERROR_TARGET = "You haven't mentioned a user";
    public static final String ERROR_WRONG = "Wrong arguments";
    public static final String ERROR_EMPTY = "Empty arguments";
    public static final String ERROR_MANY = "Too many arguments";
    public static final String ERROR_PNG = "https://cdn.discordapp.com/attachments/279257860121165834/280098209945092097/error.png";

    public static final  Color GREEN = new Color(40,255,40);
    public static final  Color BLUE = new Color(50, 100,190);
    public static final  Color ORANGE = new Color(255, 100, 0);

    public static net.dv8tion.jda.core.entities.Message perms = new MessageBuilder().append(ERROR_PERMS).build();

    static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");


    private static final HashMap<Guild, ArrayList> whitelist_ = new HashMap<>();

    private static final HashMap<String,   Permission> permMap = new HashMap<>();
    private static final HashMap<String,   String> cmdMap = new HashMap<>();
    private static final HashMap<String,   String> synMap = new HashMap<>();
    private static final HashMap<String,   String> reqMap = new HashMap<>();
    private static final HashMap<Guild,    Boolean> conMap = new HashMap<>();
    private static final HashMap<Object[], Boolean> listenerMap = new HashMap<>();

    private static final Object Add = new Add();
    private static final Object Remove = new Remove();
    private static final Object Feed = new Feed();
    private static final Object Allow = new Allow();
    private static final Object Deny = new Deny();
    private static final Object Mention = new Mentioneveryone();
    private static final Object Clear = new Clear();
    private static final Object Mute = new Mute();
    private static final Object Unmute = new Unmute();
    private static final Object Ban = new Ban();
    private static final Object Kick = new Kick();
    private static final Object Eval = new Eval();
    private static final Object EXP = new Eval_exp();
    //private static final Object File = new Filegeneration();
    //private static final Object jarupdate = new Jarupdate();
    private static final Object Unknown = new Unknown();
    private static final Object Module = new Module();
    private static final Object Wl = new Whitelist();
    private static final Object DISOCRD = new Discord();
    public static final Object[] Permissions = {Allow,Deny};
    public static final Object[] Roles = {Add,Remove};
    public static final Object[] Channels = {Clear, Mention, Mute, Unmute};
    public static final Object[] Guilds = {Ban, Kick};

    public static int member = 0;
    public static int received = 0;
    public static int receivedcmd = 0;
    public static int sent = 0;
    public static int executedcmd = 0;
    public static int cleared = 0;

    static boolean start = true;

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
        WL();
        //modules();
        System.out.println("[Log]");
        getJ().setToken(TOKEN_BOT);
        getJ().setGame(net.dv8tion.jda.core.entities.Game.of(BOT_GAME));
        getJ().setStatus(OnlineStatus.ONLINE);
        ownerwhitelist();
        pub();
        other();
        initting();
        System.out.println("DONE");
    }
    private static void ownerwhitelist() {
        /* Owner and whitelisted */
        getJ().addListener(Wl);
        getJ().addListener(Feed);
    }
    private static void pub() {
        /* Public */
        if (start) {
            getJ().addListener(new Game());

            getJ().addListener(new Github());
            getJ().addListener(new Invite());

            getJ().addListener(new Help());
            getJ().addListener(new Syntax());

            getJ().addListener(new Request());
            getJ().addListener(new Info());

            getJ().addListener(new Status());
            getJ().addListener(new Time());
        }
    }
    private static void other() {
        /* Other */
        if (start) {
            getJ().addListener(new Eventlist());
            getJ().addListener(new Message());
            getJ().addListener(new Protection());
            getJ().addListener(new Relog());
            getJ().addListener(new Shutdown());
            getJ().addListener(new Fix());
            getJ().addListener(new Log());
            //getJ().addListener(jarupdate);
            getJ().addListener(Unknown);
            //getJ().addListener(File);
            getJ().addListener(Eval);
            //getJ().addListener(Module);
            getJ().addListener(DISOCRD);
            getJ().addListener(new Test());
        }
    }
    private static void initting() {
        /* Initting */
        initperms();
        initreq();
    }
    private static void WL() {
        if (!WL.isEmpty()) {
            WL.clear();
        }
        WL.add(YOUR_ID);
        WL.add(GERD_ID);
        WL.add(NOBODY);
    }
    private static void modules() {
        if (listenerMap.isEmpty()) {
            listenerMap.put(Permissions, true);
            listenerMap.put(Roles, true);
            listenerMap.put(Channels, true);
            listenerMap.put(Guilds, true);
        }

        if (listenerMap.get(Permissions)) {
            for (Object part : Permissions) {
                getJ().addListener(part);
            }
        }

        if (listenerMap.get(Roles)) {

            for (Object part : Roles) {
                getJ().addListener(part);
            }
        }

        if (listenerMap.get(Guilds)) {

            for (Object part : Guilds) {
                getJ().addListener(part);
            }
        }

        if (listenerMap.get(Channels)) {

            for (Object part : Channels) {
                getJ().addListener(part);
            }
        }
    }
    static void init_exp() {
        modules();
        getJ().addListener(Module);
        getJ().removeListener(Eval);
        getJ().addListener(EXP);
        getJ().addListener(new Input());
        getJ().setGame(net.dv8tion.jda.core.entities.Game.of("EXPERIMENTAL","twitch.tv"));
    }

    public static HashMap<String,   Permission> getPermMap() {
        return permMap;
    }
    public static HashMap<String,   String> getCmdMap() {
        return cmdMap;
    }
    public static HashMap<String,   String> getSynMap() {
        return synMap;
    }
    public static HashMap<String,   String> getReqMap() {
        return reqMap;
    }
    public static HashMap<Guild,    Boolean> getConMap() {
        return conMap;
    }
    public static HashMap<Object[], Boolean> getListenerMap() {
        return listenerMap;
    }
    public static HashMap<Guild,    ArrayList> getWhitelist_() {
        return whitelist_;
    }
}

