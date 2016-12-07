package com.project;

import com.chat_module.*;
import com.game_module.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Screen {
	public Screen(){
		final JFrame oFrame = new JFrame("Shot!");
		JPanel panel = new JPanel();
		
		String headlab = "<html><h1 style='font-size:50px;'>SH<span style='color:red;'>O</span>T!</h1><br></html>";
		JLabel shotlab = new JLabel(headlab);
		panel.add(shotlab);
		
		final JTextField t1 = new JTextField("IP Address");
		panel.add(t1);
		
		final JTextField t2 = new JTextField("Name");
		panel.add(t2);
		
		JButton submit = new JButton("Play");
		submit.setBackground(Color.orange);
		panel.add(submit);
		
		JButton instr = new JButton("Instructions");
		instr.setBackground(Color.orange);
		panel.add(instr);
		
		panel.setBackground(Color.LIGHT_GRAY);
		oFrame.add(panel);
		oFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    oFrame.setVisible(true);
	    oFrame.setSize(300,270);
		
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
		
	    instr.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
//				  BufferedImage img = null;
//				  try {
//					  img = ImageIO.read(new File("bg.jpg"));
//					  img.getGraphics().drawImage(img, 0, 0, null);
//				  } catch (IOException ioe) { }
				  String instr = "<html><div><h1>INSTRUCTIONS</h1><h2>MAIN GOAL</h2><p>Goal of the game is simple. Move to distract your opponents. Shoot shapes of the same color with you to gain points.</p><h2>NOTE</h2><p>Be wary of the timer.</p><p>You can use the chatbox to say whatever you want to feel or think about them.</p></div></html>";
				  JOptionPane.showMessageDialog(oFrame, new JLabel(instr));
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