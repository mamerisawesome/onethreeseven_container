import java.net.*;
import java.io.*;
import java.util.*;

public class Client{
    public static void main (String [] args) { 	
        final Socket client;
        
        try {
        	Chat chatClient = new Chat();
        	chatClient.setVisible(true);
        	
            String serverName = args[0]; //get IP address of server from first param
            int port = Integer.parseInt(args[1]); //get port from second param
                        
            /* Open a ClientSocket and connect to ServerSocket */
            
            chatClient.getChatArea().append("Connecting to " + serverName + " on port " + port + "\n");
            
            client = new Socket(serverName, port);
            chatClient.getChatArea().append("Enter name:");
            
            chatClient.getChatArea().append("Just connected to " + client.getRemoteSocketAddress());
        
            /* Send data to the ServerSocket */
            Thread send = new Thread(){
                public void run(){
                    String message;
                    try{
                        OutputStream outToServer = client.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        while(true){
                            message = chatClient.getInputField().getText();		
                            out.writeUTF(getName() + ": " +message);
                        }
                    }
                    catch(Exception e){ e.printStackTrace(); }
                }
            };

            /* Receive data from the ServerSocket */
            Thread receive = new Thread(){
                public void run(){
                    try{
                        InputStream inFromServer = client.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);	
                        while(true){
                            System.out.println(in.readUTF());
                        }
                    } catch(Exception e){ e.printStackTrace(); }
                }
            };
            send.start();
            receive.start();
        }
        catch(IOException e) { e.printStackTrace(); }
        catch(ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
    }

	
}

