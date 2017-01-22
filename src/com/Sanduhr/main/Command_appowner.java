package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

/**
 * Created by Sanduhr on 22.01.2017.
 */
public class Command_appowner extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
            //Rolemanager for X User but only 1 Role each time
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), e.getGuild().getRoleById(r.get(0).getId())).queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), e.getGuild().getRoleById(r.get(0).getId())).queue();
                });
            }
            //Part of Permissionmanager
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                    }
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                        e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                    }
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                    }
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                        e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                    }
                });
            }
            //Write a msg as your Bot
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "message")) {
                if (syntaxx[1] == null) {
                    e.getMessage().deleteMessage().queue();
                }
                if (syntaxx[1] != null) {
                    e.getMessage().deleteMessage().queue();
                    e.getChannel().sendMessage(syntaxx[1]).queue();
                }
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                int i = Integer.parseInt(syntax[1]);
                List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                msg.forEach(message -> {
                    message.deleteMessage().queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                    e.getGuild().getController().kick(user.getId()).queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                    e.getGuild().getController().ban(user, 1).complete();
                });
            }
            if (content.equalsIgnoreCase("shutdown")) {
                Bot_main.getJDA().shutdown();
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                String GAME = String.valueOf(e.getJDA().getPresence().getGame().getName());
                EmbedBuilder eb = new EmbedBuilder();
                MessageBuilder mb = new MessageBuilder();
                e.getMessage().deleteMessage().queue();
                if (syntax[1].equalsIgnoreCase("set")) {
                    e.getJDA().getPresence().setGame(Game.of(syntaxx[1]));
                    eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                    eb.setColor(Lib.Green);
                    eb.addField("Old Game:", GAME, true);
                    eb.addField("New Game:", syntaxx[1], true);
                    mb.setEmbed(eb.build());
                    Message m = mb.build();
                    e.getChannel().sendMessage(m).queue();
                }
                if (syntax[1].equalsIgnoreCase("get")) {
                    eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                    eb.setColor(Lib.Green);
                    eb.addField("Current Game:", GAME, true);
                    mb.setEmbed(eb.build());
                    Message m = mb.build();
                    e.getChannel().sendMessage(m).queue();
                }
            }
        }
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (e.getAuthor().getId().equals(Lib.YOUR_ID)) {
            //Rolemanager for X User but only 1 Role each time
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), e.getGuild().getRoleById(r.get(0).getId())).queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), e.getGuild().getRoleById(r.get(0).getId())).queue();
                });
            }
            //Part of Permissionmanager
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().deny(Permission.MESSAGE_WRITE).complete();
                    }
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                        e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().deny(Permission.MESSAGE_WRITE).queue();
                    }
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) == null) {
                        e.getTextChannel().createPermissionOverride(e.getGuild().getMember(user)).complete().getManager().grant(Permission.MESSAGE_WRITE).complete();
                    }
                    if (e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)) != null) {
                        e.getTextChannel().getPermissionOverride(e.getGuild().getMember(user)).getManager().grant(Permission.MESSAGE_WRITE).queue();
                    }
                });
            }
            //Write a msg as your Bot
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "message")) {
                if (syntaxx[1] == null) {
                    e.getMessage().deleteMessage().queue();
                }
                if (syntaxx[1] != null) {
                    e.getMessage().deleteMessage().queue();
                    e.getChannel().sendMessage(syntaxx[1]).queue();
                }
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                int i = Integer.parseInt(syntax[1]);
                List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                msg.forEach(message -> {
                    message.deleteMessage().queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                    e.getGuild().getController().kick(user.getId()).queue();
                });
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " + syntaxx[1]).complete();
                    e.getGuild().getController().ban(user, 1).complete();
                });
            }
            if (content.equalsIgnoreCase("shutdown")) {
                Bot_main.getJDA().shutdown();
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "game")) {
                String GAME = String.valueOf(e.getJDA().getPresence().getGame().getName());
                EmbedBuilder eb = new EmbedBuilder();
                MessageBuilder mb = new MessageBuilder();
                e.getMessage().deleteMessage().queue();
                if (syntax[1].equalsIgnoreCase("set")) {
                    e.getJDA().getPresence().setGame(Game.of(syntaxx[1]));
                    eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                    eb.setColor(Lib.Green);
                    eb.addField("Old Game:", GAME, true);
                    eb.addField("New Game:", syntaxx[1], true);
                    mb.setEmbed(eb.build());
                    Message m = mb.build();
                    e.getChannel().sendMessage(m).queue();
                }
                if (syntax[1].equalsIgnoreCase("get")) {
                    eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
                    eb.setColor(Lib.Green);
                    eb.addField("Current Game:", GAME, true);
                    mb.setEmbed(eb.build());
                    Message m = mb.build();
                    e.getChannel().sendMessage(m).queue();
                }
            }
        }
    }
}