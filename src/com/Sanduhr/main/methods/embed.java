package com.Sanduhr.main.methods;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;

/**
 * Created by gs on 04/02/2017.
 */
public class embed {
    public static void embed(int color, Message message, String content) {
        Color colorascolor = Color.cyan;
        String colorasstring = "Cyan!";
        switch (color){
            case 1: {
                colorascolor = Color.cyan;
                colorasstring = "Cyan!";
                break;
            }
            case 2: {
                colorascolor = Color.red;
                colorasstring = "Red!!";
                break;
            }
            case 3: {
                colorascolor = Color.green;
                colorasstring = "Green!";
                break;
            }
            case 4: {
                colorascolor = Color.blue;
                colorasstring = "Blue!";
                break;
            }
            case 5: {
                colorascolor = Color.yellow;
                colorasstring = "Yellow!";
                break;
            }
            case 6: {
                colorascolor = Color.getHSBColor(0.85f, 0.76f,1f);
                colorasstring = "Pinkish?";
                break;
            }
            case 7: {
                colorascolor = Color.black;
                colorasstring = "Black!";
                break;
            }
            default: {
                colorascolor = Color.black;
                colorasstring = "Incorrect Color Code!";

            }
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(colorascolor);
        embed.addField("Embed by useless", content, false);
        embed.setAuthor(message.getAuthor().getName(), "https://github.com/gerd2002/Uselessbot", message.getAuthor().getAvatarUrl());
        MessageBuilder mb = new MessageBuilder().setEmbed(embed.build());
        message.getChannel().sendMessage(mb.build()).queue();
        message.deleteMessage().queue();
    }
}
