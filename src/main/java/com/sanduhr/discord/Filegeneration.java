/*package com.sanduhr.discord;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sanduhr.discord.Lib.*;

class Filegeneration extends ListenerAdapter {

    private List<Guild> guilds = new ArrayList<>();
    private FileWriter whitelistJSON;
    private final File wl = new File(".\\whitelist.json");
    private final File jars = new File(".\\jars.json");
    private String whitelist;


    private static String server = "[`[2.0.Beta_104]`](https://cdn.discordapp.com/attachments/288695554773614592/289157997215612928/useless.jar)";
    private static String panel = "[`[1.0]`](https://cdn.discordapp.com/attachments/288695554773614592/289050751534235648/Panel.jar)";
    private static String util = "[`[0.5]`](https://cdn.discordapp.com/attachments/288695554773614592/289447575202037760/Util.jar)";

    private final Jar SERVER = new Jar("Server-jar",server);
    private final Jar PANEL = new Jar("Panel-jar",panel);
    private final Jar UTIL = new Jar("Util-jar",util);
    private final List<Jar> JARS = new ArrayList<>();

    @Override
    public void onReady(ReadyEvent e) {
        JARS.add(SERVER);
        JARS.add(PANEL);
        JARS.add(UTIL);
        guilds = e.getJDA().getGuilds();
        whitelist = getWhitelist_().toString();
        replacer();
        writter();
        Runnable jarbackup = this::writeJARS;
        EXECUTE.scheduleWithFixedDelay(jarbackup, 20, 20, TimeUnit.SECONDS);
    }

    public void onShutdown(ShutdownEvent e) {
        writter();
    }

    private void replacer() {
        whitelist = whitelist.replace("G:", "\n")
                             .replace("}", "\n}");
    }
    private void writter() {
        replacer();
        Fileutils.writeFile(wl,whitelist);
    }

    private void getJARS() {
        JARS.removeAll(JARS);
        SERVER.Version = Jarupdate.getServerjar();
        PANEL.Version = Jarupdate.getPaneljar();
        UTIL.Version = Jarupdate.getUtiljar();

        JARS.add(SERVER);
        JARS.add(PANEL);
        JARS.add(UTIL);

        server = Jarupdate.getServerjar();
        panel = Jarupdate.getPaneljar();
        util = Jarupdate.getUtiljar();
    }

    private void writeJARS() {
        readJARS();
        Fileutils.writeFile(jars,j());
    }

    private void readJARS() {
        if (!jars.exists()) {
            return;
        }
        Fileutils.getFileLines(jars).forEach(s -> {
            String[] in = s.split(":",4);
            if (in[1].equalsIgnoreCase("server-jar")) {
                SERVER.Version = in[3];
                return;
            }
            if (in[1].equalsIgnoreCase("panel-jar")) {
                PANEL.Version = in[3];
                return;
            }
            if (in[1].equalsIgnoreCase("util-jar")) {
                UTIL.Version = in[3].replace(")]",")");
            }
        });
    }

    private String j() {
        return JARS.toString().replace(", ","\n");
    }

    static String getServer() {
        return server;
    }
    static String getPanel() {
        return panel;
    }

    private class Jar {
        private String Type;
        private String Version;

        private Jar() {

        }

        Jar(String type,String version) {
            this.Type = type;
            this.Version = version;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Type:").append(Type).append(":").append("Version:").append(Version);
            return sb.toString();
        }
    }
}
*/