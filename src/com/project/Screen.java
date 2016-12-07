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
	public Screen(){
		final JFrame oFrame = new JFrame("Shot!");
		JPanel panel = new JPanel();
		final JTextField t1 = new JTextField("IP Address");
		final JTextField t2 = new JTextField("Name");
		JButton submit = new JButton("Play");
		panel.add(t1);
		panel.add(t2);
		panel.add(submit);
		oFrame.add(panel);
		oFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    oFrame.setVisible(true);
	    oFrame.setSize(300,300);
		submit.addActionListener(new ActionListener() { 
		  public void actionPerformed(ActionEvent e) { 
			  oFrame.setVisible(false);
			  try {
				gameUI(t1.getText(), t2.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  } 
		});
	}
  public static void gameUI(String ip, String name) throws Exception{
	  JFrame frame = new JFrame("Shot!");
	    JPanel main_cont = new JPanel(new BorderLayout());

	    Client_Chat cc = new Client_Chat(ip, name);
	    System.out.println("[DONE] Added chat module");

	    Client_Game cg = new Client_Game(ip, name, main_cont);
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
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
  }
  public static void main (String[] args) throws Exception {
	Screen s = new Screen();
  }
}