package com.Sanduhr.main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
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
public class Command_guildowner extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (e.getAuthor().equals(e.getGuild().getOwner().getUser())) {
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                Lib.receivedcmd++;
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
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                Lib.receivedcmd++;
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
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                Lib.receivedcmd++;
                int i = Integer.parseInt(syntax[1]);
                List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                msg.forEach(message -> {
                    message.deleteMessage().queue();
                    Lib.cleared++;
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                    e.getGuild().getController().kick(user.getId()).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " +syntaxx[1]).complete();
                    e.getGuild().getController().ban(user, 1).complete();
                    Lib.sent++;
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "whitelist")) {
                EmbedBuilder eb = new EmbedBuilder();
                MessageBuilder mb = new MessageBuilder();
                List<User> u = e.getMessage().getMentionedUsers();
                if (syntax[1].equalsIgnoreCase("add")) {
                    u.forEach(user -> {
                        Lib.whitelist.add(user.getId().toString());
                    });
                }
                if (syntax[1].equalsIgnoreCase("remove")) {
                    u.forEach(user -> {
                        Lib.whitelist.remove(user.getId().toString());
                    });
                }
                if (syntax[1].equalsIgnoreCase("print")) {
                    eb.setColor(Lib.Blue);
                    Lib.whitelist.forEach(string -> {
                        eb.addField("UserID:", string, false);
                    });
                    mb.setEmbed(eb.build());
                    e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage(mb.build()).complete();
                }
            }
        }
    }
    public void onMessageUpdate(MessageUpdateEvent e) {
        String content = e.getMessage().getContent();
        String[] syntax = e.getMessage().getContent().split(" ");
        String[] syntaxx = e.getMessage().getContent().split(":");
        if (e.getAuthor().equals(e.getGuild().getOwner().getUser())) {
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "add")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().addRolesToMember(e.getGuild().getMember(user), r).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "remove")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                List<Role> r = e.getMessage().getMentionedRoles();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getGuild().getController().removeRolesFromMember(e.getGuild().getMember(user), r).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "mute")) {
                Lib.receivedcmd++;
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
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "unmute")) {
                Lib.receivedcmd++;
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
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "clear")) {
                Lib.receivedcmd++;
                int i = Integer.parseInt(syntax[1]);
                List<Message> msg = e.getTextChannel().getHistory().retrievePast(i).complete();
                msg.forEach(message -> {
                    message.deleteMessage().queue();
                    Lib.cleared++;
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "kick")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was kicked for " + syntaxx[1]).complete();
                    e.getGuild().getController().kick(user.getId()).queue();
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "ban")) {
                Lib.receivedcmd++;
                List<User> u = e.getMessage().getMentionedUsers();
                e.getMessage().deleteMessage().queue();
                u.forEach(user -> {
                    e.getJDA().getUserById(user.getId()).openPrivateChannel().complete().sendMessage("You was banned for " +syntaxx[1]).complete();
                    e.getGuild().getController().ban(user, 1).complete();
                    Lib.sent++;
                });
                Lib.executedcmd++;
            }
            if (syntax[0].equalsIgnoreCase(Lib.prefix + "whitelist")) {
                EmbedBuilder eb = new EmbedBuilder();
                MessageBuilder mb = new MessageBuilder();
                List<User> u = e.getMessage().getMentionedUsers();
                if (syntax[1].equalsIgnoreCase("add")) {
                    u.forEach(user -> {
                        Lib.whitelist.add(user.getId().toString());
                    });
                }
                if (syntax[1].equalsIgnoreCase("remove")) {
                    u.forEach(user -> {
                        Lib.whitelist.remove(user.getId().toString());
                    });
                }
                if (syntax[1].equalsIgnoreCase("print")) {
                    eb.setColor(Lib.Blue);
                    Lib.whitelist.forEach(string -> {
                        eb.addField("UserID:", string, false);
                    });
                    mb.setEmbed(eb.build());
                    e.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage(mb.build()).complete();
                }
            }
        }
    }
}
