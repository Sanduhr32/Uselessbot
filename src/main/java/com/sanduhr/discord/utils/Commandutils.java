package com.sanduhr.discord.utils;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

/**
 * Created by Sanduhr on 01.04.2017
 */
public class Commandutils {

    public static HashMap<String, Command> commands = new HashMap<>();

    public static class Command {

        private static String PREFIX;
        private static String NAME;
        private static String HELP;
        private static String SYNTAX;
        private static Tierutils.Level reqLEVEL;
        private static Object CLASS;

        public Command(String prefix, String name, String help, String syntax, Tierutils.Level requiredLevel, Object Class) {
            PREFIX = prefix;
            NAME = name;
            HELP = help;
            SYNTAX = syntax;
            reqLEVEL = requiredLevel;
            CLASS = Class;
        }

        public String getNAME() {
            return NAME;
        }
        public String getPREFIX() {
            return PREFIX;
        }
        public String getHELP() {
            return HELP;
        }
        public String getSYNTAX() {
            return SYNTAX;
        }
        public Tierutils.Level getReqLEVEL() {
            return reqLEVEL;
        }
        public Object getCLASS() {
            return CLASS;
        }
        public static void setNAME(String name) {
            NAME = name;
        }
    }
    @Deprecated
    public static void registerCommand(JDA api, Command command) {
        api.addEventListener(command.getCLASS());
        commands.put(command.getNAME(), command);
    }
    @Deprecated
    public static void registerCommand(JDABuilder api, Command command) {
        api.addListener(command.getCLASS());
        commands.put(command.getNAME(), command);
    }

    /**
     * @author Sanduhr32
     * @param event Can't be null
     * @param prefix The String prefix can be null but a command must be set
     * @param command The Command can be null but a prefix must be set
     * @param type Can't be null
     * @param respondBots Can't be null
     * @return TRUE if the message is a command
     * @throws NullPointerException if an important param is null
     */
    @Deprecated
    public static boolean isCommand(MessageReceivedEvent event, String prefix, Command command, ChannelType type, Boolean respondBots) {
        Command c = commands.get(command.getNAME());
        Message Msg = event.getMessage();
        String msg = Msg.getContent();

        if (event.equals(null)&&type.equals(null)&&respondBots.equals(null)) {
            try {
                throw new NullPointerException("Event, type and respondToBots can't be null!");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (!respondBots) {
            if (Msg.getAuthor().isBot()) {
                return false;
            }
        }

        if (!Msg.isFromType(type)) {
            return false;
        }

        if (c.equals(null)) {
            if (!prefix.isEmpty()||!prefix.equals(null)||!prefix.equalsIgnoreCase("")||prefix!=null||prefix!="") {
                return msg.startsWith(prefix);
            }
        }

        return msg.startsWith(c.getPREFIX()+c.getNAME());
    }
    @Deprecated
    public static boolean isCommand(MessageReceivedEvent event, String prefix, ChannelType type, Boolean respondBots) {
        return isCommand(event, prefix,null, type, respondBots);
    }
}
