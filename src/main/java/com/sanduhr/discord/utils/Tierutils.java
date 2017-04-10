package com.sanduhr.discord.utils;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sanduhr on 31.03.2017
 */
public class Tierutils {

    public static HashMap<String, Level> tierMap = new HashMap<>();
    public static Level SANDUHR;
    public static Level DEVS;
    private static Level GUILD_OWNER;
    private static Level GUILD_WHITE;
    private static Level PUBLIC;

    public enum Tier {
        BOT_OWNER(1,"sanduhr"),
        BOT_DEVELOPER(2,"devs"),
        GUILD_OWNER(3,"guild_owner"),
        GUILD_WHITELIST(4,"guild_white"),
        PUBLIC(5,"public"),
        UNKNOWN(-1,"unknown");

        private int tier_value;
        private String tier_name;

        Tier(Integer tier, String name) {
            this.tier_name = name;
            this.tier_value = tier;
        }

        public String getName() {
            return tier_name;
        }

        public int getTier() {
            return tier_value;
        }
    }

    public static class Level {
        private String NAME;
        private Tier TIER;
        private static Object OBJECT;

        Level(String name, Tier tier, Object object) {
            NAME = name;
            TIER = tier;
            OBJECT = object;
        }

        public Object getOBJECT() {
            return OBJECT;
        }

        public Tier getTIER() {
            return TIER;
        }

        public String getNAME() {
            return NAME;
        }

        public static void setOBJECT(Object object) {
            OBJECT = object;
        }
    }

    public static void init() {
        SANDUHR     = new Level("",Tier.BOT_OWNER,       Lib.YOUR_ID);
        DEVS        = new Level("",Tier.BOT_DEVELOPER,   Lib.WL);
        GUILD_OWNER = new Level("",Tier.GUILD_OWNER,     new HashMap<Guild, Member>());
        GUILD_WHITE = new Level("",Tier.GUILD_WHITELIST, Lib.getWhitelist_());
        PUBLIC      = new Level("",Tier.PUBLIC,    null);

        tierMap.put("sanduhr",     SANDUHR);
        tierMap.put("devs",        DEVS);
        tierMap.put("guild_owner", GUILD_OWNER);
        tierMap.put("guild_white", GUILD_WHITE);
        tierMap.put("public",      PUBLIC);
    }

    @Deprecated
    public static boolean isTier(String ID, Tier tier, Guild guild) {
        Level level = tierMap.get(tier.getName());

        if (level.getTIER().equals(Tier.PUBLIC)) {
            return true;
        }

        if (level.getTIER().equals(Tier.GUILD_OWNER)) {
            HashMap<Guild, Member> GUILD_MAP = (HashMap<Guild, Member>) level.getOBJECT();
            return GUILD_MAP.get(guild).getUser().getId().equals(ID);
        }

        if (level.getTIER().equals(Tier.GUILD_WHITELIST)) {
            HashMap<Guild, ArrayList<String>> GUILD_MAP = (HashMap<Guild, ArrayList<String>>) level.getOBJECT();
            return GUILD_MAP.get(guild).contains(ID);
        }

        return level.getOBJECT().toString().contains(ID);
    }
    @Deprecated
    public static boolean isTier(User user, Tier tier, Guild guild) {
        return isTier(user.getId(), tier, guild);
    }
    @Deprecated
    public static boolean isTier(Member member, Tier tier, Guild guild) {
        return isTier(member.getUser(), tier, guild);
    }

    public static boolean remove(Tier tier, String id, Guild guild) {
        Level level = tierMap.get(tier.getName());

        if (level.getTIER().equals(Tier.BOT_DEVELOPER)) {
            ArrayList<String> IDS = (ArrayList<String>) level.getOBJECT();
            IDS.remove(id);
            level.setOBJECT(IDS);
            return true;
        }

        if (level.getTIER().equals(Tier.GUILD_WHITELIST)) {
            HashMap<Guild, ArrayList<String>> GUILD_MAP = (HashMap<Guild, ArrayList<String>>) level.getOBJECT();
            GUILD_MAP.get(guild).remove(id);
            level.setOBJECT(GUILD_MAP);
            return true;
        }

        try {
            throw new Exception("You entered an invalid tier!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean remove(Tier tier, User user, Guild guild) {
        return remove(tier, user.getId(), guild);
    }
    public static boolean remove(Tier tier, Member member, Guild guild) {
        return remove(tier, member.getUser(), guild);
    }

    public static boolean add(Tier tier, String id, Guild guild) {
        Level level = tierMap.get(tier.getName());

        if (level.getTIER().equals(Tier.BOT_DEVELOPER)) {
            ArrayList<String> IDS = (ArrayList<String>) level.getOBJECT();
            IDS.add(id);
            level.setOBJECT(IDS);
            return true;
        }

        if (level.getTIER().equals(Tier.GUILD_WHITELIST)) {
            HashMap<Guild, ArrayList<String>> GUILD_MAP = (HashMap<Guild, ArrayList<String>>) level.getOBJECT();
            GUILD_MAP.get(guild).add(id);
            level.setOBJECT(GUILD_MAP);
            return true;
        }

        try {
            throw new Exception("You entered an invalid tier!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean add(Tier tier, User user, Guild guild) {
        return add(tier, user.getId(), guild);
    }
    public static boolean add(Tier tier, Member member, Guild guild) {
        Level level = tierMap.get(tier.getName());

        if (level.getTIER().equals(Tier.GUILD_OWNER)) {
            HashMap<Guild, Member> GUILD_MAP = (HashMap<Guild, Member>) level.getOBJECT();
            GUILD_MAP.put(guild, member);
            level.setOBJECT(GUILD_MAP);
            return true;
        }

        return add(tier, member.getUser(), guild);
    }
}
