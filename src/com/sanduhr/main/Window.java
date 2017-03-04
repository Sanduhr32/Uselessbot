package com.sanduhr.main;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static com.sanduhr.main.Lib.*;

/**
 *Created by Sanduhr on 04.03.2017
 *
 *This class creates a frame for the Bot
 */

class Window extends ListenerAdapter {

    private static JFrame WINDOW = new JFrame();
    private static JButton BUTTON_0 = new JButton("1");
    private static JButton BUTTON_1 = new JButton("2");
    private static JButton BUTTON_2 = new JButton("3");
    private static JButton BUTTON_3 = new JButton("4");

    static String id;

    private static List<Guild> guildList = new List<Guild>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Guild> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Guild guild) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Guild> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Guild> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Guild get(int index) {
            return null;
        }

        @Override
        public Guild set(int index, Guild element) {
            return null;
        }

        @Override
        public void add(int index, Guild element) {

        }

        @Override
        public Guild remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Guild> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Guild> listIterator(int index) {
            return null;
        }

        @Override
        public List<Guild> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    private static ArrayList<String> name = new ArrayList<>();
    private static HashMap<String, String> liste = new HashMap<>();

    private static void init() {
        guildList.forEach(guild -> {
            name.add(guild.getName());
            liste.put(guild.getName(),guild.getId());
        });
    }

    static void open() {

        select();

        WINDOW.setTitle("Useless - Controlpanel");
        WINDOW.pack();
        WINDOW.setSize(320,1080);

        WINDOW.add(BUTTON_0);
        WINDOW.add(BUTTON_1);
        WINDOW.add(BUTTON_2);
        WINDOW.add(BUTTON_3);

        WINDOW.setVisible(true);
        WINDOW.setLayout(new GridLayout(4,1));

        BUTTON_0.setText("SEND MESSAGE");
        BUTTON_0.addActionListener(new Button_0());

        BUTTON_1.setText("RESELECT");
        BUTTON_1.addActionListener(new Button_1());

        BUTTON_2.setText("LEAVE");
        BUTTON_2.addActionListener(new Button_2());

        BUTTON_3.setText("CREATE INVITE");
        BUTTON_3.addActionListener(new Button_3());
    }

    static void close() {
        WINDOW.dispose();
    }

    private static void select() {
        String guild = (String) JOptionPane.showInputDialog(WINDOW,"Guild:",WINDOW.getTitle(),JOptionPane.INFORMATION_MESSAGE,null,name.toArray(),name.toArray()[2]);
        id = liste.get(guild);
    }

    private static class Button_0 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Useless.getJDA().getGuildById(id).getPublicChannel().sendMessage("Hi").queue();
            close();
        }
    }

    private static class Button_1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            open();
        }
    }

    private static class Button_2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Useless.getJDA().getGuildById(id).leave().queue();
            close();
        }
    }

    private static class Button_3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Useless.getJDA().getUserById(YOUR_ID).openPrivateChannel().complete().sendMessage("discord.gg/" + Useless.getJDA().getGuildById(id).getPublicChannel().createInvite().complete().getCode()).queue();
            close();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String[] syntax = e.getMessage().getContent().split("\\s+");

        //Never respond to a bot!
        if (e.getAuthor().isBot())
            return;

        //Not the `remove` command
        if (!syntax[0].equalsIgnoreCase(Lib.PREFIX + "window")) {
            return;
        }

        if (!e.getAuthor().getId().equals(YOUR_ID)) {
            e.getChannel().sendMessage(Lib.ERROR_PERMS).queue();
            return;
        }

        open();
    }

    public void onMessageUpdate(MessageUpdateEvent e) {
        onMessageReceived(new MessageReceivedEvent(e.getJDA(), e.getResponseNumber(), e.getMessage()));
    }

    public void onReady(ReadyEvent e) {
        guildList = e.getJDA().getGuilds();
        init();
        initter();
    }
    public void initter() {
        getCmdMap().put(getName(),getDescription());
        getSynMap().put(getName(),getSyntax());
    }
    public String getName() {
        return Window.class.getSimpleName().toLowerCase();
    }
    public String getDescription() {
        return "Reopen the controlpanel";
    }
    public String getSyntax() {
        return "`" + PREFIX + getName() + "`";
    }
}
