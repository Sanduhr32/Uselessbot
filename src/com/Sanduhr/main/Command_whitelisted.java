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
            if (e.getAuthor().getId().equals(string)) {
                //Rolemanager for X User but only 1 Role each time
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                //Part of Permissionmanager
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                                e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                            }
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                                e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                            }
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                                e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                            }
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                                e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                            }
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                //Write a msg as your Bot
                if (syntaxx[0].equalsIgnoreCase(Lib.prefix + "message")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
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
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                        msg.forEach(message -> {
                            message.deleteMessage().queue();
                            Lib.cleared++;
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                            e.getGuild().getController().kick(user.getId()).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                            e.getGuild().getController().ban(user, 1).complete();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (content.equalsIgnoreCase("shutdown")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown();
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                    String GAME = e.getJDA().getPresence().getGame().getName();
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
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
                    EmbedBuilder eb1 = new EmbedBuilder();
                    MessageBuilder mb1 = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        if (syntax[1].equalsIgnoreCase("print")) {
                            eb.setColor(Lib.Blue);
                            eb.setTitle("ID:");
                            Lib.whitelist.forEach(id -> {
                                eb.addField("User:", id, false);
                            });
                            eb1.setColor(Lib.Blue);
                            eb1.setTitle("Name:");
                            Lib.whitelistt.forEach(name -> {
                                eb1.addField("User:", name, false);
                            });
                            mb.setEmbed(eb.build());
                            mb1.setEmbed(eb1.build());
                            e.getGuild().getOwner().getUser().openPrivateChannel().queue(privateChannel -> {
                                privateChannel.sendMessage("Namen und ID stimmen in der Reihenfolge überein.").queue();
                                privateChannel.sendMessage(mb1.build()).queue();
                                privateChannel.sendMessage(mb.build()).queue();
                            });
                            Lib.sent++;
                        }
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
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
            if (e.getAuthor().getId().equals(string)) {
                //Rolemanager for X User but only 1 Role each time
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    List<Role> r = e.getMessage().getMentionedRoles();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                //Part of Permissionmanager
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                                e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                            }
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                                e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                            }
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                                e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                            }
                            if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                                e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                            }
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                //Write a msg as your Bot
                if (syntaxx[0].equalsIgnoreCase(Lib.prefix + "message")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
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
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                        msg.forEach(message -> {
                            message.deleteMessage().queue();
                            Lib.cleared++;
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                            e.getGuild().getController().kick(user.getId()).queue();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                    List<User> u = e.getMessage().getMentionedUsers();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
                        u.forEach(user -> {
                            e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                            e.getGuild().getController().ban(user, 1).complete();
                        });
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                    Lib.executedcmd++;
                }
                if (content.equalsIgnoreCase("shutdown")) {
                    if (!e.getAuthor().getId().equals(Lib.YOUR_ID))
                        return;
                    Bot_main.getJDA().shutdown();
                }
                if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                    String GAME = e.getJDA().getPresence().getGame().getName();
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageBuilder mb = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        e.getMessage().deleteMessage().queue();
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
                    EmbedBuilder eb1 = new EmbedBuilder();
                    MessageBuilder mb1 = new MessageBuilder();
                    if (e.getChannelType().equals(ChannelType.TEXT)) {
                        if (syntax[1].equalsIgnoreCase("print")) {
                            eb.setColor(Lib.Blue);
                            eb.setTitle("ID:");
                            Lib.whitelist.forEach(id -> {
                                eb.addField("User:", id, false);
                            });
                            eb1.setColor(Lib.Blue);
                            eb1.setTitle("Name:");
                            Lib.whitelistt.forEach(name -> {
                                eb1.addField("User:", name, false);
                            });
                            mb.setEmbed(eb.build());
                            mb1.setEmbed(eb1.build());
                            e.getGuild().getOwner().getUser().openPrivateChannel().queue(privateChannel -> {
                                privateChannel.sendMessage("Namen und ID stimmen in der Reihenfolge überein.").queue();
                                privateChannel.sendMessage(mb1.build()).queue();
                                privateChannel.sendMessage(mb.build()).queue();
                            });
                            Lib.sent++;
                        }
                    } else {
                        e.getChannel().sendMessage(Lib.Error_guild).queue();
                    }
                }
            }
        });
    }
}
