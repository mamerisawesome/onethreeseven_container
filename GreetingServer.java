   // File Name GreetingServer.java

import java.net.*;
import java.io.*;
import java.util.*;

public class GreetingServer{
   private static ServerSocket serverSocket;
   public GreetingServer(int port) throws IOException
   {
      //insert missing line here for binding a port to a socket
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
   }

   public static void main(String [] args)
   {
   
      
      try
      {
         int port = Integer.parseInt(args[0]);
         GreetingServer gs = new GreetingServer(port);
         Thread accept = new Thread(){
		       	public void run(){
		       		try{
		       			LinkedList<Socket> clients = new LinkedList<Socket>();
		       			while(true){
				     			Socket server = serverSocket.accept();
		          		clients.add(server);
		          		Thread send = new Thread(){
								 		public void run(){
								 			try{
								 				DataInputStream in = new DataInputStream(server.getInputStream());
								    		while(true){
										  		String message = in.readUTF();
										  		for(Socket s : clients){
										  			DataOutputStream out = new DataOutputStream(s.getOutputStream());
										  			out.writeUTF(message);
									 				}
								 				}
								 			}catch(Exception e){e.printStackTrace();}
								 		}
								 };
								 send.start();
						   }
            	}catch(Exception e){e.printStackTrace();}
		       	}
         };
          
      	accept.start();    
         
      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Usage: java GreetingServer <port no.>");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingServer <port no.> ");
      }
   }
}
