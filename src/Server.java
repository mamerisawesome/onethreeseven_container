import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {
    private LinkedList <Socket> ll;
    private LinkedList <String> st;
    private ServerSocket serverSocket;

    public GreetingServer (int port) throws IOException {
        ll = new LinkedList <Socket> ();
        st = new LinkedList <String> ();
        serverSocket = new ServerSocket(port);
    }

    public void run () {
        boolean connected = true;
        while(connected) {
            try {
                Socket server = serverSocket.accept();
                // ip_format: /192.168.0.17:34104
                st.add(server.getRemoteSocketAddress() + "");

                DataInputStream in = new DataInputStream(server.getInputStream());
                String message = in.readUTF() + "";
                System.out.println(message);
                
                for (String cred: st) {
                    String [] ac 	= cred.split(":");
                    String ip 		= ac[0].split("/")[1];
                    int port 		= Integer.parseInt(ac[1]);

                    System.out.println(ip + " " + port);

                    Socket sock = new Socket(ip, port);
                
                    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
                    out.writeUTF(message);
                    sock.close();
                }
                
                // serverSocket.sendToAll(message);
                server.close();

            }
            catch(SocketTimeoutException e) { e.printStackTrace(); }
            catch(IOException e) { e.printStackTrace(); }
        } 
    }
    public static void main(String [] args) {
        try {
            int port = Integer.parseInt(args[0]);
            Thread t = new GreetingServer(port);
            t.start();
        }
        catch(IOException e) { e.printStackTrace(); }
        catch(ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
    }
}