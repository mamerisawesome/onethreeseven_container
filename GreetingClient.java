import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GreetingClient
{
   public static void main(String [] args)
   {  	
   	JFrame frame = new JFrame("Chat");
   	JTextArea messageBox = new JTextArea();
   	JTextField chatBox = new JTextField();
   	messageBox.setEnabled(false);
   	JPanel panel = new JPanel(new BorderLayout());
   	panel.add(messageBox, BorderLayout.CENTER);
   	panel.add(chatBox, BorderLayout.SOUTH);
   	frame.add(panel);
   	frame.setSize(400,400);
   	frame.setVisible(true);
	
      try
      {
   	  String serverName = args[0]; //get IP address of server from first param
	      int port = Integer.parseInt(args[1]); //get port from second param

         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //insert missing line here for creating a new socket for client and binding it to a port
	 			 Socket client = new Socket(serverName, port);			
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
	      	/* Send data to the ServerSocket */
	     OutputStream outToServer = client.getOutputStream();
       	 DataOutputStream out = new DataOutputStream(outToServer);	
         
			chatBox.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	try{
			       out.writeUTF("Client " + client.getLocalSocketAddress()+" says: " +chatBox.getText());
			       chatBox.setText("");
			   		}catch(Exception er){}	
			    }
			});      	
         
         
	      				
         
	      /* Receive data from the ServerSocket */
	      Thread receive = new Thread(){
	      	public void run(){
	      		try{
	      		InputStream inFromServer = client.getInputStream();
         		DataInputStream in = new DataInputStream(inFromServer);	
		       		while(true){
			       		messageBox.append(in.readUTF() + "\n");
		       		}
         		}catch(Exception e){}
	      	}
	      };
	      
	      receive.start();
        //insert missing line for closing the socket from the client side
				

      }catch(IOException e)
      {
         //e.printStackTrace();
      	System.out.println("Cannot find Server");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingClient <server ip> <port no.>");
      }
   }
}

