package com.sanduhr.discord.commands.sanduhr;

/**
 * Created by Sanduhr on 18.03.2017
 */

import static com.sanduhr.discord.Lib.*;

import com.sanduhr.discord.Eventlist;
import com.sanduhr.discord.utils.Logutils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Module extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `Module` command
        if (!syntax[0].equalsIgnoreCase(PREFIX + "Module")) {
            return;
        }

        //If `Module` command was received from a non-TextChannel, inform command is Guild-only
        if (!e.isFromType(ChannelType.TEXT)) {
            e.getChannel().sendMessage(ERROR_GUILDS).queue(msg->msg.delete().queueAfter(10,TimeUnit.SECONDS));
            return;
        }

        /*If the member that sent the command isn't in the Whitelist
         or the Owner of the Guild, they don't have permission to run this command!*/
        if (!YOUR_ID.equals(e.getAuthor().getId())) {
            e.getChannel().sendMessage(ERROR_PERMS).queue(msg->msg.delete().queueAfter(10,TimeUnit.SECONDS));
            return;
        }

        receivedcmd++;

        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            e.getMessage().delete().queue();
        }

        if (syntax.length == 2) {

            List<Object> LSNERS = e.getJDA().getRegisteredListeners();
            StringBuilder LISTENERS = new StringBuilder().append("```diff\n").append("-Size: ").append(LSNERS.size()).append("\nListeners/Commands:\n");
            Eventlist.CORE.forEach(o-> {
                if (LSNERS.contains(o)) {
                    LISTENERS.append("+").append(o.getClass().getSimpleName()).append("\n");
                } else {
                    LISTENERS.append("-").append(o.getClass().getSimpleName()).append("\n");
                }
            });

            if (syntax[1].equalsIgnoreCase("list")) {
                e.getChannel().sendMessage(LISTENERS.append("```").toString()).queue(
                        msg -> msg.delete().queueAfter(10,TimeUnit.SECONDS)
                );
            }
        }

        if (syntax.length == 3) {

            if (syntax[1].equalsIgnoreCase("load")) {
                load(syntax, e);
            }

            if (syntax[1].equalsIgnoreCase("unload")) {
                unload(syntax, e);
            }
        }

        if (syntax.length < 2 || syntax.length > 3) {
            e.getChannel().sendMessage("```Java\nvoid Input_Error() {\n    System.err.println(\"Nice try m8!\")\n}```").queue();
        }

        executedcmd++;
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    public void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
    }
    public String getName() {
        return Module.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "";
    }
    public String getSyntax() {
        return "`" + PREFIX + getName() + "<args> [int]" + "`\nArguments:\n`list`, `load`, `unload`\nInteger:\n`1`, `2`, `3`, `4`";
    }

    private void load(String[] syntax, MessageReceivedEvent e) {
        if (syntax[2].equalsIgnoreCase("1")) {
            boolean value = getListenerMap().get(Permissions);
            if (value) {
                e.getChannel().sendMessage("You cant enable it.. its already..").queue();
            } else {
                for (Object part : Permissions) {
                    e.getJDA().addEventListener(part);
                }
                getListenerMap().put(Permissions, true);
                Logutils.log.warn("Successfully enabled module 1");
            }
        }

        if (syntax[2].equalsIgnoreCase("2")) {
            boolean value = getListenerMap().get(Roles);
            if (value) {
                e.getChannel().sendMessage("You cant enable it.. its already..").queue();
            } else {
                for (Object part : Roles) {
                    e.getJDA().addEventListener(part);
                }
                getListenerMap().put(Roles, true);
                Logutils.log.warn("Successfully enabled module 2");
            }
        }

        if (syntax[2].equalsIgnoreCase("3")) {
            boolean value = getListenerMap().get(Channels);
            if (value) {
                e.getChannel().sendMessage("You cant enable it.. its already..").queue();
            } else {
                for (Object part : Channels) {
                e.getJDA().addEventListener(part);
                }
                getListenerMap().put(Channels, true);
                Logutils.log.warn("Successfully enabled module 3");
            }
        }

        if (syntax[2].equalsIgnoreCase("4")) {
            boolean value = getListenerMap().get(Guilds);
            if (value) {
                e.getChannel().sendMessage("You cant enable it.. its already..").queue();
            } else {
                for (Object part : Guilds) {
                    e.getJDA().addEventListener(part);
                }
                getListenerMap().put(Guilds, true);
                Logutils.log.warn("Successfully enabled module 4");
            }
        }
    }
    private void unload(String[] syntax, MessageReceivedEvent e) {
        if (syntax[2].equalsIgnoreCase("1")) {
            boolean value = getListenerMap().get(Permissions);
            if (!value) {
                e.getChannel().sendMessage("You cant disable it.. its already..").queue();
            } else {
                for (Object part : Permissions) {
                    e.getJDA().removeEventListener(part);
                }
                getListenerMap().put(Permissions, false);
                Logutils.log.warn("Successfully disabled module 1");
            }
        }

        if (syntax[2].equalsIgnoreCase("2")) {
            boolean value = getListenerMap().get(Roles);
            if (!value) {
                e.getChannel().sendMessage("You cant disable it.. its already..").queue();
            } else {
                for (Object part : Roles) {
                    e.getJDA().removeEventListener(part);
                }
                getListenerMap().put(Roles, false);
                Logutils.log.warn("Successfully disabled module 2");
            }
        }

        if (syntax[2].equalsIgnoreCase("3")) {
            boolean value = getListenerMap().get(Channels);
            if (!value) {
                e.getChannel().sendMessage("You cant disable it.. its already..").queue();
            } else {
                for (Object part : Channels) {
                    e.getJDA().removeEventListener(part);
                }
                getListenerMap().put(Channels, false);
                Logutils.log.warn("Successfully disabled module 3");
            }
        }

        if (syntax[2].equalsIgnoreCase("4")) {
            boolean value = getListenerMap().get(Guilds);
            if (!value) {
                e.getChannel().sendMessage("You cant disable it.. its already..").queue();
            } else {
                for (Object part : Guilds) {
                    e.getJDA().removeEventListener(part);
                }
                getListenerMap().put(Guilds, false);
                Logutils.log.warn("Successfully disabled module 4");
            }
        }
    }
}