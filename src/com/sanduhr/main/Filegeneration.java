package com.sanduhr.main;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sanduhr.main.Lib.EXECUTE;
import static com.sanduhr.main.Lib.getWhitelist;

public class Filegeneration extends ListenerAdapter {

    private List<Guild> guilds = new ArrayList<>();
    private FileWriter whitelistJSON;
    private File wl = new File(".\\whitelist.json");
    private File jars = new File(".\\jars.json");
    private FileWriter jarlogJSON;
    private String whitelist;


    private static String server = "[`[2.0.Beta_104]`](https://cdn.discordapp.com/attachments/288695554773614592/289157997215612928/useless.jar)";
    private static String panel = "[`[1.0]`](https://cdn.discordapp.com/attachments/288695554773614592/289050751534235648/Panel.jar)";
    private static String util = "[`[0.5]`](https://cdn.discordapp.com/attachments/288695554773614592/289447575202037760/Util.jar)";

    private BufferedReader reader;

    private Jar SERVER = new Jar("Server-jar",server);
    private Jar PANEL = new Jar("Panel-jar",panel);
    private Jar UTIL = new Jar("Util-jar",util);
    private List<Jar> JARS = new ArrayList<>();

    @Override
    public void onReady(ReadyEvent e) {
        JARS.add(SERVER);
        JARS.add(PANEL);
        JARS.add(UTIL);
        guilds = e.getJDA().getGuilds();
        whitelist = getWhitelist().toString();
        replacer();
        writter();
        Runnable jarbackup = () -> {
            writeJARS();
        };
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
        whitelistJSON = null;
        replacer();
        try {
            whitelistJSON = new FileWriter(wl);
            whitelistJSON.write(whitelist);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (whitelistJSON != null) {
                try {
                    whitelistJSON.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
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
        getJARS();
        readJARS();
        jarlogJSON = null;
        try {
            jarlogJSON = new FileWriter(jars);
            jarlogJSON.write(j());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (jarlogJSON != null) {
                try {
                    jarlogJSON.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void readJARS() {
        try {
            reader = new BufferedReader(new FileReader(jars));
            List<String> LINES = reader.lines().collect(Collectors.toList());
            LINES.forEach(s -> {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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
