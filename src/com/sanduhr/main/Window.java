package com.sanduhr.main;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
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
 *This class creates a frame interface for the Bot
 */

class Window extends ListenerAdapter {

    private static JFrame WINDOW = new JFrame();

    private static JButton BUTTON_0 = new JButton("1");
    private static JButton BUTTON_1 = new JButton("2");
    private static JButton BUTTON_2 = new JButton("3");
    private static JButton BUTTON_3 = new JButton("4");
    private static JButton BUTTON_4 = new JButton("5");
    private static JButton BUTTON_5 = new JButton("6");

    private static JTextField MESSAGE = new JTextField("Nachricht");

    static String id;
    static String cid;

    private static final String TITLE = "Useless - control panel";

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
    private static List<TextChannel> textChannelList = new List<TextChannel>() {
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
        public Iterator<TextChannel> iterator() {
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
        public boolean add(TextChannel textChannel) {
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
        public boolean addAll(Collection<? extends TextChannel> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends TextChannel> c) {
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
        public TextChannel get(int index) {
            return null;
        }

        @Override
        public TextChannel set(int index, TextChannel element) {
            return null;
        }

        @Override
        public void add(int index, TextChannel element) {

        }

        @Override
        public TextChannel remove(int index) {
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
        public ListIterator<TextChannel> listIterator() {
            return null;
        }

        @Override
        public ListIterator<TextChannel> listIterator(int index) {
            return null;
        }

        @Override
        public List<TextChannel> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> cname = new ArrayList<>();
    private static HashMap<String, String> liste = new HashMap<>();
    private static HashMap<String, String> channels = new HashMap<>();

    private static void buttons() {
        BUTTON_0.addActionListener(new Button_0());
        BUTTON_1.addActionListener(new Button_1());
        BUTTON_2.addActionListener(new Button_2());
        BUTTON_3.addActionListener(new Button_3());
        BUTTON_4.addActionListener(new Button_4());
        BUTTON_5.addActionListener(new Button_5());
    }

    private static void guilds() {
        if (!name.isEmpty()) {
            name.clear();
        }
        if (!liste.isEmpty()) {
            liste.clear();
        }
        guildList.forEach(guild -> {
            name.add(guild.getName());
            liste.put(guild.getName(),guild.getId());
        });
    }

    private static void channels() {

        if (!cname.isEmpty()) {
            cname.clear();
        }

        if (!channels.isEmpty()) {
            channels.clear();
        }

        textChannelList = Useless.getJDA().getGuildById(id).getTextChannels();

        textChannelList.forEach(ch -> {
            channels.put(ch.getName(),ch.getId());
            cname.add(ch.getName());
        });
    }

    static void open() {

        guilds();
        guild();

        WINDOW.setTitle(TITLE);
        WINDOW.pack();
        WINDOW.setSize(320,1080);

        WINDOW.add(BUTTON_0);
        WINDOW.add(BUTTON_1);
        WINDOW.add(BUTTON_2);
        WINDOW.add(BUTTON_3);

        WINDOW.setVisible(true);
        WINDOW.setLayout(new GridLayout(4,1));

        BUTTON_0.setText("SEND MESSAGE");

        BUTTON_1.setText("RESELECT");
        BUTTON_1.setBackground(new Color(105,10,100));

        BUTTON_2.setText("LEAVE");

        BUTTON_3.setText("CREATE INVITE");
    }

    static void message() {
        WINDOW.setTitle(TITLE);

        WINDOW.add(MESSAGE);
        WINDOW.add(BUTTON_4);
        WINDOW.add(BUTTON_5);

        WINDOW.remove(BUTTON_0);
        WINDOW.remove(BUTTON_1);
        WINDOW.remove(BUTTON_2);
        WINDOW.remove(BUTTON_3);

        WINDOW.pack();
        WINDOW.setSize(300,250);
        WINDOW.setVisible(true);
        WINDOW.setLayout(new GridLayout(3,1));

        BUTTON_4.setText("Senden");
        BUTTON_5.setText("Schließen");
    }

    static void close() {
        WINDOW.dispose();
    }

    private static void guild() {
        String guild = (String) JOptionPane.showInputDialog(WINDOW,"Guild:",TITLE,JOptionPane.INFORMATION_MESSAGE,null,name.toArray(),name.toArray()[2]);
        id = liste.get(guild);
    }

    private static void channel() {
        channels();
        String channel = (String) JOptionPane.showInputDialog(WINDOW,"Channel:",TITLE,JOptionPane.QUESTION_MESSAGE,null,cname.toArray(),cname.toArray()[2]);
        cid = channels.get(channel);
    }

    private static class Button_0 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            channel();
            message();
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
            Useless.getJDA().getUserById(YOUR_ID).openPrivateChannel().complete().sendMessage("https://discord.gg/" + Useless.getJDA().getGuildById(id).getPublicChannel().createInvite().complete().getCode()).queue();
            close();
        }
    }

    private static class Button_4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = MESSAGE.getText();
            Useless.getJDA().getTextChannelById(cid).sendMessage(msg).queue();
            MESSAGE.setText("");
        }
    }

    private static class Button_5 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WINDOW.remove(MESSAGE);
            WINDOW.remove(BUTTON_4);
            WINDOW.remove(BUTTON_5);
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
        buttons();
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
