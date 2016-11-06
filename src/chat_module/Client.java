import java.net.*;
import java.io.*;
import java.util.*;
public class Client {
    public static void main (String [] args) { 	
        final Socket client;
        try {
            String serverName = args[0]; //get IP address of server from first param
            int port = Integer.parseInt(args[1]); //get port from second param

            /* Open a ClientSocket and connect to ServerSocket */
            System.out.println("Connecting to " + serverName + " on port " + port);
            client = new Socket(serverName, port);
            
            System.out.print(">> Enter name: ");
            Scanner name_sc = new Scanner(System.in);
            final String name = name_sc.nextLine();

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            /* Send data to the ServerSocket */
            Thread send = new Thread(){
                public void run(){
                    Scanner sc = new Scanner(System.in);
                    String message;
                    try{
                        OutputStream outToServer = client.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        while(true){
                            message = sc.nextLine();		
                            out.writeUTF(name + ": " +message);
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

