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
import com.Sanduhr.main.methods.*;

import java.awt.*;
import java.util.List;

/**
 * Created by gs on 04/02/2017.
 */
public class Command_priv extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
            switchCommand(e);
        }

    }
    public void switchCommand(MessageReceivedEvent e){
        String[] syntax1 = e.getMessage().getContent().split(" ");
        if(e.getMessage().getContent().startsWith(Lib.prefix + "embed")) {
            int col = Integer.parseInt(syntax1[1].substring(0,1));
            int prefixlenght = Lib.prefix.length() + "embed".length() + syntax1[1].length() + 1;
            embed.embed(col, e.getMessage(), e.getMessage().getContent().substring(prefixlenght));
        }
    }
}
