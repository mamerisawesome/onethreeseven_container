package bin.com.chat_module;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Chat_Constants {
  private static ServerSocket serverSocket;
  public Server () throws IOException {
    serverSocket = new ServerSocket(PORT);
  }

  public static void main (String [] args) {
    try {
    Server gs = new Server();
    Thread accept = new Thread () {
      public void run () {
        try {
          final LinkedList<Socket> clients = new LinkedList<Socket>();
          while (true) {
            final Socket server = serverSocket.accept();
            clients.add(server);
            Thread send = new Thread () {
              public void run () {
                try {
                  DataInputStream in = new DataInputStream(server.getInputStream());
                  while(true){
                    String message = in.readUTF();
                    for (Socket s : clients) {
                      DataOutputStream out = new DataOutputStream(s.getOutputStream());
                      out.writeUTF(message);
                    }
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            };
            send.start();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    accept.start();

    } catch (IOException e) {
      System.out.println("Usage: java Server");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Usage: java Server");
    }
  }
}
