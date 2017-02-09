package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

/**
 * Created by Sanduhr on 22.01.2017.
 */
public class Command_whitelisted extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        Lib.whitelist.forEach(string -> {
            if (!e.isFromType(ChannelType.TEXT)) {
                if (e.getAuthor().isBot())
                    return;
                e.getChannel().sendMessage(Lib.Error_guild).queue();
            }
            if (e.getAuthor().getId().equals(string)) {
                //Rolemanager for X User but only 1 Role each time
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                        e.getMessage().delete().queue();
                        u.forEach(user -> {
                            e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                        });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                    });
                    Lib.executedcmd++;
                }
                //Part of Permissionmanager
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                            e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                        }
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                            e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                        }
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                            e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                        }
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                            e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                        }
                    });
                    Lib.executedcmd++;
                }
                //Write a msg as your Bot
                if (syntaxx[0].equalsIgnoreCase(Lib.prefix + "message")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().delete().queue();
                    }
                    if (syntaxx[1] == null) {
                        e.getChannel().sendMessage("Syntax error").queue();
                    }
                    if (syntaxx[1] != null) {
                        e.getChannel().sendMessage(syntaxx[1]).queue();
                    }
                    Lib.sent++;
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                    int i = Integer.parseInt(syntax[1]);
                    List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                    msg.forEach(message -> {
                        message.delete().queue();
                        Lib.cleared++;
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                        e.getGuild().getController().kick(user.getId()).queue();
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                        e.getGuild().getController().ban(user, 1).complete();
                    });
                    Lib.executedcmd++;
                }
                if (content.equalsIgnoreCase("shutdown")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown();
                }
                if (content.equalsIgnoreCase("relog")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown(false);
                    try {
                        Bot_main.start();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                    String GAME = e.getJDA().getPresence().getGame().getName();
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().delete().queue();
                    }
                    if (syntax[1].equalsIgnoreCase("set")) {
                        if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
                            e.getJDA().getPresence().setGame(Game.of(syntaxx[1]));
                            eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                            eb.setColor(Lib.Green);
                            eb.addField("Old Game:", GAME, true);
                            eb.addField("New Game:", syntaxx[1], true);
                            mb.setEmbed(eb.build());
                            Message m = mb.build();
                            e.getChannel().sendMessage(m).queue();
                        } else {
                            e.getChannel().sendMessage("Your are not allowed to set the Game").queue();
                        }
                    }
                    Lib.sent++;
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "whitelist")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (syntax[1].equalsIgnoreCase("print")) {
                        eb.setColor(Lib.Blue);
                        Lib.whitelist.forEach(id -> {
                            eb.addField("UserID:","**Name:** "+e.getJDA().getUserById(id).getName()+"\n**ID:** "+ id, false);
                        });
                        mb.setEmbed(eb.build());
                        e.getAuthor().openPrivateChannel().queue(privateChannel -> {
                            privateChannel.sendMessage(mb.build()).queue();
                        });
                    }
                }
            }
        });
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        Lib.whitelist.forEach(string -> {
            if (!e.isFromType(ChannelType.TEXT)) {
                if (e.getAuthor().isBot())
                    return;
                e.getChannel().sendMessage(Lib.Error_guild).queue();
            }
            if (e.getAuthor().getId().equals(string)) {
                //Rolemanager for X User but only 1 Role each time
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                    });
                    Lib.executedcmd++;
                }
                //Part of Permissionmanager
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                            e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                        }
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                            e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                        }
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                            e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                        }
                        if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                            e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                        }
                    });
                    Lib.executedcmd++;
                }
                //Write a msg as your Bot
                if (syntaxx[0].equalsIgnoreCase(Lib.prefix + "message")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().delete().queue();
                    }
                    if (syntaxx[1] == null) {
                        e.getChannel().sendMessage("Syntax error").queue();
                    }
                    if (syntaxx[1] != null) {
                        e.getChannel().sendMessage(syntaxx[1]).queue();
                    }
                    Lib.sent++;
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                    int i = Integer.parseInt(syntax[1]);
                    List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                    msg.forEach(message -> {
                        message.delete().queue();
                        Lib.cleared++;
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                        e.getGuild().getController().kick(user.getId()).queue();
                    });
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    e.getMessage().delete().queue();
                    u.forEach(user -> {
                        e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                        e.getGuild().getController().ban(user, 1).complete();
                    });
                    Lib.executedcmd++;
                }
                if (content.equalsIgnoreCase("shutdown")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown();
                }
                if (content.equalsIgnoreCase("relog")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown(false);
                    try {
                        Bot_main.start();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                    String GAME = e.getJDA().getPresence().getGame().getName();
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().delete().queue();
                    }
                    if (syntax[1].equalsIgnoreCase("set")) {
                        if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
                            e.getJDA().getPresence().setGame(Game.of(syntaxx[1]));
                            eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                            eb.setColor(Lib.Green);
                            eb.addField("Old Game:", GAME, true);
                            eb.addField("New Game:", syntaxx[1], true);
                            mb.setEmbed(eb.build());
                            Message m = mb.build();
                            e.getChannel().sendMessage(m).queue();
                        } else {
                            e.getChannel().sendMessage("Your are not allowed to set the Game").queue();
                        }
                    }
                    Lib.sent++;
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "whitelist")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (syntax[1].equalsIgnoreCase("print")) {
                        eb.setColor(Lib.Blue);
                        Lib.whitelist.forEach(id -> {
                            eb.addField("UserID:","**Name:** "+e.getJDA().getUserById(id).getName()+"\n**ID:** "+ id, false);
                        });
                        mb.setEmbed(eb.build());
                        e.getAuthor().openPrivateChannel().queue(privateChannel -> {
                            privateChannel.sendMessage(mb.build()).queue();
                        });
                    }
                }
            }
        });
    }
}