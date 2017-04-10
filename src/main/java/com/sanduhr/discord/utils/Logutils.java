package com.sanduhr.discord.utils;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 * @author Sanduhr
 */

public class Logutils extends ListenerAdapter {

    public static SimpleLog log;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");

    private static ArrayList<Long> tid = new ArrayList<>();
    private static ArrayList<Long> pid = new ArrayList<>();

    private static boolean log_private = false;
    private static boolean log_text = false;

    /**
     * Adds the type channels
     */

    public static void addTextChannel(Collection<TextChannel> textChannels) {
        for (TextChannel t : textChannels) {
            tid.add(t.getIdLong());
        }
    }

    public static void addPrivateChannel(Collection<PrivateChannel> privateChannels) {
        for (PrivateChannel p : privateChannels) {
            pid.add(p.getIdLong());
        }
    }

    /**
     * Removes the type channels
     */

    public static void removeTextChannel(Collection<TextChannel> textChannels) {
        for (TextChannel t : textChannels) {
            tid.remove(t.getIdLong());
        }
    }

    public static void removePrivateChannel(Collection<PrivateChannel> privateChannels) {
        for (PrivateChannel p : privateChannels) {
            pid.remove(p.getIdLong());
        }
    }

    /**
     * Sets the type channels
     */

    public static void setTextChannel(Collection<TextChannel> textChannels) {
        tid.clear();
        for (TextChannel t : textChannels) {
            tid.add(t.getIdLong());
        }
    }

    public static void setPrivateChannel(Collection<PrivateChannel> privateChannels) {
        pid.clear();
        for (PrivateChannel p : privateChannels) {
            pid.add(p.getIdLong());
        }
    }

    /**
     * Returns an ArrayList of the ids from the logging channels
     */

    public static ArrayList<Long> getPrivateChannels() {
        return pid;
    }
    public static ArrayList<Long> getTextChannels() {
        return tid;
    }

    /**
     * Enables logging into type channels
     */

    public static void enablePrivateChannelLog() {
        log_private = true;
    }
    public static void enableTextChannelLog() {
        log_text = true;
    }

    /**
     * Disables logging into type channels
     */

    public static void disablePrivateChannelLog() {
        log_private = false;
    }
    public static void disableTextChannelLog() {
        log_text = false;
    }

    /**
     * This booleans return if logging into type channel is enabled
     */

    public static boolean getPrivateChannelLogStatus() {
        return log_private;
    }
    public static boolean getTextChannelLogStatus() {
        return log_text;
    }

    /**
     *
     * Logs into all text channels the JDA Log
     * @param e Event
     */
    public static void logToTextChannels(Event e) {
        SimpleLog.addListener(new SimpleLog.LogListener() {
            /**
             *
             * @param simpleLog {@link SimpleLog.LogListener}
             * @param level {@link net.dv8tion.jda.core.utils.SimpleLog.Level}
             * @param o Output
             * @see SimpleLog.LogListener
             */
            @Override
            public void onLog(SimpleLog simpleLog, SimpleLog.Level level, Object o) {
                if (!log_text) {
                    return;
                }
                for (long ID : tid) {
                    if (!level.equals(SimpleLog.Level.TRACE)) {
                        if (o.toString().contains("Unrecognized event")) {
                            return;
                        }
                        String msg = "[%time%] [%level%]: " + o;
                        msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                        msg = msg.replace("%level%",level.getTag());
                        new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> e.getJDA().getTextChannelById(ID).sendMessage(message).queue());
                    }
                }
                log = simpleLog;
            }

            /**
             *
             * @param simpleLog {@link SimpleLog.LogListener}
             * @param throwable {@link Throwable}
             * @see SimpleLog.LogListener
             */
            @Override
            public void onError(SimpleLog simpleLog, Throwable throwable) {
                if (!log_text) {
                    return;
                }
                for (long ID : tid) {
                    String msg = "[%time%]: " + throwable;
                    msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                    new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> e.getJDA().getTextChannelById(ID).sendMessage(message).queue());
                }
                log = simpleLog;
            }
        });
    }

    /**
     *
     * Logs into all private channels the JDA Log
     * @param e Event
     */
    public static void logToPrivateChannels(Event e) {
        SimpleLog.addListener(new SimpleLog.LogListener() {
            /**
             *
             * @param simpleLog {@link SimpleLog.LogListener}
             * @param level {@link net.dv8tion.jda.core.utils.SimpleLog.Level}
             * @param o Output
             */
            @Override
            public void onLog(SimpleLog simpleLog, SimpleLog.Level level, Object o) {
                if (!log_private) {
                    return;
                }
                for (long ID : pid) {
                    if (!level.equals(SimpleLog.Level.TRACE)) {
                        String msg = "[%time%] [%level%]: " + o;
                        msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                        msg = msg.replace("%level%",level.getTag());
                        new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> e.getJDA().getPrivateChannelById(ID).sendMessage(message).queue());
                    }
                }
                log = simpleLog;
            }

            /**
             *
             * @param simpleLog {@link SimpleLog.LogListener}
             * @param throwable {@link Throwable}
             */
            @Override
            public void onError(SimpleLog simpleLog, Throwable throwable) {
                if (!log_private) {
                    return;
                }
                for (long ID : pid) {
                    String msg = "[%time%]: " + throwable;
                    msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                    new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE).forEach(message -> e.getJDA().getPrivateChannelById(ID).sendMessage(message).queue());
                }
                log = simpleLog;
            }
        });
    }

}
