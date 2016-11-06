import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
   private static ServerSocket serverSocket;
   public Server (int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
   }

   public static void main (String [] args) {
      Server gs;
      try {
        int port = Integer.parseInt(args[0]);
        gs = new Server(port);
        Thread accept = new Thread(){
            public void run(){
                try{
                    final LinkedList<Socket> clients = new LinkedList<Socket>();
                    while(true){
                        final Socket server = serverSocket.accept();
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
                                } catch(Exception e){e.printStackTrace();}
                            }
                        };
                        send.start();
                    }
                } catch(Exception e){e.printStackTrace();}
            }
        };
        accept.start();    
      } 
      catch(IOException e) { e.printStackTrace(); } 
      catch(ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
   }
}
