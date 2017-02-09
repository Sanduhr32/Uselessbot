package com.Sanduhr.main;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Sanduhr on 21.01.2017.
 */
public class Lib {
    public static String prefix ="??";
    public static String YOUR_ID = "198137282018934784";
    public static String Error_guild = "Only works at guilds";
    public static Color Green = new Color(40,255,40);
    public static Color Blue = new Color(50, 100,190);
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");
    public static ArrayList<String> whitelist = new ArrayList<>();

    public static int member = 0;
    public static int received = 0;
    public static int receivedcmd = 0;
    public static int sent = 0;
    public static int executedcmd = 0;
    public static int cleared = 0;
    public static void main() {
        whitelist.add(YOUR_ID);
    }
}