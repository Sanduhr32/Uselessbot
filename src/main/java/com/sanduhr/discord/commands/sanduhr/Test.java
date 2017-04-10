package com.sanduhr.discord.commands.sanduhr;

import com.sanduhr.discord.Lib;
import com.sanduhr.discord.utils.Commandutils;
import com.sanduhr.discord.utils.Tierutils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Sanduhr on 01.04.2017
 */
public class Test extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

//        if (!Tierutils.isTier(e.getAuthor(), Tierutils.Tier.BOT_OWNER, null)) {
//            return;
//        }

        //e.getGuild().getController().createWebhook(e.getTextChannel(),e.getAuthor().getName()).complete().getManager().setAvatar(Icon.from(e.getAuthor().getAvatarUrl().getBytes())).queue();

        //Tierutils.add(Tierutils.Tier.BOT_DEVELOPER, "137232539008892928", null);
    }
    private static String getName() {
        return Test.class.getSimpleName().toLowerCase();
    }
    private static String getDescription() {
        return "NullPointerExcption";
    }
    private static String getSyntax() {
        return "`" + Lib.PREFIX + getName() + "`";
    }
}
