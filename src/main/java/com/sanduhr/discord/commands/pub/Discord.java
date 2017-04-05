package com.sanduhr.discord.commands.pub;

import com.sanduhr.discord.Lib;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Random;

import static com.sanduhr.discord.Lib.PREFIX;
import static com.sanduhr.discord.Lib.getCmdMap;
import static com.sanduhr.discord.Lib.getSynMap;

/**
 * Created by Sanduhr on 21.03.2017
 */
public class Discord extends ListenerAdapter {
    private ArrayList<String> zitate = new ArrayList<>();
    private String TWITTER = "https://cdn.discordapp.com/attachments/293410445031768064/298070195149209601/Twitter_bird_logo_2012.png";
    private String SANDUHR = "https://twitter.com/Sanduhr32";
    private String MONDANZ = "";
    private String DISCORD = "https://twitter.com/discordapp";
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        if (e.getAuthor().isBot()) {
            return;
        }

        String[] syntax = e.getMessage().getContent().split("\\s+");

        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "discord")) {
            return;
        }

        if (syntax[1].equalsIgnoreCase("tanya")) {
            e.getChannel().sendMessage("Zitat Tanya:" +
                    "\n`Ich weiß schon, was du meinst, aber nachdem das Problem bei Google lag und unsere Login-Server von Millionen von kleinen Wumpusen betrieben werden, hat es dann doch ganz gut geklappt. : )`")
                    .queue();
        }
        if (syntax[1].equalsIgnoreCase("twitter")) {
            String zitat = zitate.get(new Random().nextInt(zitate.size()));
            e.getChannel().sendMessage(new EmbedBuilder().addField("Zitat von Twitter:",zitat,false).setAuthor("Twitter","https://twitter.com",TWITTER).build()).queue();
        }
    }

    public void onReady(ReadyEvent e) {
        initter();
    }

    public void initter() {
        getCmdMap().put(getName(), getDescription());
        getSynMap().put(getName(), getSyntax());
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Neustarten ist sowieso immer meine Lieblingsempfehlung! Das solltest du gleich nochmal durchführen.. :-D](https://twitter.com/discordapp/status/848148308447121408)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Drei Clients auf einmal? Na wer's braucht :P](https://twitter.com/discordapp/status/847896383222894593)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Not macht erfinderisch. Hat es denn dann auch irgendwann geklappt?](https://twitter.com/discordapp/status/847896383222894593)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [WOOOAAAAAHHHHHHHHHHHH. Kann der Bot auch Pfannkuchen machen?](https://twitter.com/discordapp/status/842161681602084866)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Hey! Hattest du ein Ticket geschrieben und ne Antwort bekommen? Du Zerstörer! :-)](https://twitter.com/discordapp/status/839465935891226624)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Cool! Kann man den dann irgendwann auch einmal ausprobieren? ;)](https://twitter.com/discordapp/status/837904203750191104)");
        zitate.add("Discord: [@Mondanzo]("+MONDANZ+") [@Sanduhr32]("+SANDUHR+") [Wie bedient man denn das Alles mit 10 Fingern? :-P](https://twitter.com/discordapp/status/848151502376095744)\nSanduhr: [@discordapp]("+ DISCORD +") [@Mondanzo]("+MONDANZ+") [Einfach ein paar finger_int.dll laden \uD83D\uDE02](https://twitter.com/Sanduhr32/status/848161744061116416)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Lifegoal von Discord: User glücklich machen: ✔ <3](https://twitter.com/discordapp/status/835596186971144192)");
        zitate.add("\u2764 [@discordapp]("+DISCORD+") gefällt dein Tweet\n[Lifegoal: Von [@discordapp]("+DISCORD+") gelobt werden: ✔\n" +
                "(Es war in einer langen Email \uD83D\uDE0F)](https://twitter.com/Sanduhr32/status/835513741315878913)");
        zitate.add("[@Sanduhr32]("+SANDUHR+") [Hey! Was ein Copycat Bot! Aber das gefällt uns trotzdem gut! Ganz so useless ist der Bot wohl gar nicht mehr, mh? :)](https://twitter.com/discordapp/status/848557178046877696)");
    }
    public String getName() {
        return Discord.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Zitiert ein paar Discord Mitarbeiter";
    }
    public String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}
