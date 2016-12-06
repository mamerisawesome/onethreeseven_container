package com.chat_module;

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client_Chat extends JPanel implements Chat_Constants {
  public Client_Chat (String sn, String n) {
    final JTextArea messageBox = new JTextArea();
    final JTextField chatBox = new JTextField();
    messageBox.setEnabled(false);
    messageBox.setBackground(new Color(50, 50, 50));
    messageBox.setForeground(Color.GREEN);
    messageBox.setPreferredSize(new Dimension(200, 500));
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(messageBox, BorderLayout.CENTER);
    panel.add(chatBox, BorderLayout.SOUTH);
    this.add(panel);
    // this.setSize(400,400);
    // this.setVisible(true);

    try {
      String serverName = sn; //get IP address of server from first param
      final String name = n; //get name from second param

      /* Open a Client_ChatSocket and connect to ServerSocket */
      System.out.println("Connecting to " + serverName + " on port " + PORT);
      //insert missing line here for creating a new socket for client and binding it to a port
      final Socket client = new Socket(serverName, PORT);			
      System.out.println("Just connected to " + client.getRemoteSocketAddress());
      /* Send data to the ServerSocket */
      OutputStream outToServer = client.getOutputStream();
      final DataOutputStream out = new DataOutputStream(outToServer);	

      chatBox.setForeground(Color.BLACK);
      chatBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try{
            out.writeUTF(name + ": " +chatBox.getText());
            chatBox.setText("");
          } catch(Exception er){
            er.printStackTrace();
          }
        }
      });      	

      Thread receive = new Thread(){
        public void run(){
          try{
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);	
            while(true){
              messageBox.append(in.readUTF() + "\n");
            }
          }catch(Exception e){
            e.printStackTrace();
          }
        }
      };

      receive.start();
    } catch(IOException e) {
      System.out.println("Cannot find Server");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Usage: java Client_Chat <server ip> <name>");
    }
  }
}
