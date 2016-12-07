package com.project;

import com.chat_module.*;
import com.game_module.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Screen {
  public static void main (String[] args) throws Exception {
    JFrame frame = new JFrame("Shot!");
    JPanel main_cont = new JPanel(new BorderLayout());

    Client_Chat cc = new Client_Chat(args[0], args[1]);
    System.out.println("[DONE] Added chat module");

    Client_Game cg = new Client_Game(args[0], args[1]);
    System.out.println("[DONE] Added game module");

    main_cont.add(cc, BorderLayout.WEST);
    main_cont.add(cg, BorderLayout.CENTER);

    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");
    cg.setCursor(blankCursor);

    frame.getContentPane().add(main_cont);
    frame.setLocation(30,40);
    frame.pack();
    frame.setVisible(true);
  }
}