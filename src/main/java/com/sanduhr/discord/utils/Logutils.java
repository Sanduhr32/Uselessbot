package com.sanduhr.discord.utils;

        import net.dv8tion.jda.core.MessageBuilder;
        import net.dv8tion.jda.core.entities.TextChannel;
        import net.dv8tion.jda.core.hooks.ListenerAdapter;
        import net.dv8tion.jda.core.utils.SimpleLog;

        import java.time.OffsetDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.concurrent.TimeUnit;

/**
 * Created by Sanduhr on 11.03.2017
 * This project contains some Utils for JDA Bots
 * @author Sanduhr
 */

public class Logutils extends ListenerAdapter {

    public static SimpleLog log;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");

    private static TextChannel textChannel;

    private static boolean log_private = false;
    private static boolean log_text = false;

    /**
     * Enables logging into type channels
     */
    public static void enableTextChannelLog() {
        log_text = true;
    }

    /**
     * Disables logging into type channels
     */
    public static void disableTextChannelLog() {
        log_text = false;
    }

    /**
     * This booleans return if logging into type channel is enabled
     */
    public static boolean getTextChannelLogStatus() {
        return log_text;
    }

    public static void setTextChannel(TextChannel TextChannel) {
        if (TextChannel == null) {
            try {
                throw new NullPointerException("Hey! You spoon! NULL isn't a net.dv8tion.jda.core.entities.TextChannel!");
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        textChannel = TextChannel;
    }

    /**
     *
     * Logs into all text channels the JDA Log
     */
    public static void syncronize(TextChannel TextChannel) {
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
                if (!level.equals(SimpleLog.Level.TRACE)) {
                    String s = o.toString();
                    if (s.contains("Unrecognized event") || s.contains("Received response with following cf-rays:")) {
                        return;
                    }
                    String msg = "[%time%] [%level%]: " + o;
                    msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                    msg = msg.replace("%level%",level.getTag());
                    new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE)
                            .forEach(message -> TextChannel.sendMessage(message).queue(
                                    success -> {
                                    }, fail -> {
                                        System.err.println("WHUPS! An error appeared! Resending the message \""+ message.getRawContent() +"\"");
                                        TextChannel.sendMessage(message).queueAfter(3, TimeUnit.SECONDS, sentmsg -> sentmsg.pin().queue());
                                    }));
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
                String msg = "[%time%]: " + throwable;
                msg = msg.replace("%time%", OffsetDateTime.now().format(DTF));
                new MessageBuilder().append(msg).buildAll(MessageBuilder.SplitPolicy.SPACE, MessageBuilder.SplitPolicy.NEWLINE, MessageBuilder.SplitPolicy.ANYWHERE)
                        .forEach(message -> TextChannel.sendMessage(message).queue(
                                success -> {
                                }, fail -> {
                                    System.err.println("WHUPS! An error appeared! Resending the message \""+ message.getRawContent() +"\"");
                                    TextChannel.sendMessage(message).queueAfter(3, TimeUnit.SECONDS, sentmsg -> sentmsg.pin().queue());
                                }));
                log = simpleLog;
            }
        });
    }

    public static TextChannel getTextChannel() {
        return textChannel;
    }
}