import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main (String [] args) {
    int i = 0;	
    Scanner sc = new Scanner(System.in);

    try {
      String name = args[2];
      String serverName = args[0]; //get IP address of server from first param
      int port = Integer.parseInt(args[1]); //get port from second param
      String message="";//get message from the third param

      while(!message.equals("exit")){
        message = sc.nextLine();
        Socket client  = new Socket(serverName, port);
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF(name+":\t"+message);
        /* Receive data from the ServerSocket */

        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println(in.readUTF());
        //insert missing line for closing the socket from the client side
        client.close();	
      }
    }
    catch(IOException e) { e.printStackTrace(); }
    catch(ArrayIndexOutOfBoundsException e) { System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'"); }
  }
}
