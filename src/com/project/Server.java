package com.project;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chat_module.*;
import com.game_module.*;

public class Server {
  public static void main (String[] args) {
	final JFrame frame = new JFrame("Server capacity");
	JPanel panel = new JPanel();
	final JTextField t1 = new JTextField("Number of Players");
	JButton submit = new JButton("Submit");
	String headlab = "<html><h1 style='font-size:30px;'>SHOT!</h1><br></html>";
	JLabel shotlab = new JLabel(headlab);
	panel.add(shotlab);
	panel.add(t1);
	panel.add(submit);
	frame.getContentPane().add(panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setSize(200,200);
	
	submit.addActionListener(new ActionListener() { 
		  public void actionPerformed(ActionEvent e) { 
			Server_Chat chat_instance = new Server_Chat();
			Server_Game game_instance = new Server_Game(Integer.parseInt(t1.getText()));
			System.out.println("[DONE] Server started");
			frame.setVisible(false);
			frame.dispose();
		  }
		 });
	}
}