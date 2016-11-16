// @author Joseph Anthony C. Hermocilla
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server implements Runnable, Constants{
  String playerData; // data recvd by player
  int playerCount=0;
  DatagramSocket serverSocket = null;
  GameState game; // game instance
  int gameStage=WAITING_FOR_PLAYERS;
  int numPlayers;
  Thread t = new Thread(this); // game thread

  public Server(int numPlayers){
    this.numPlayers = numPlayers;
    try {
      serverSocket = new DatagramSocket(PORT);
    } catch (IOException e) {
      System.out.print("[OOPS] ");
      System.err.println("Could not listen on port: "+PORT);
      System.exit(-1);
    } catch(Exception e){
      System.out.print("[OOPS] ");
      e.printStackTrace();
    }
    game = new GameState();

    System.out.print("[DONE] ");
    System.out.println("Game created...");

    t.start();
  }

  public void broadcast(String msg){
    for(Iterator ite=game.getPlayers().keySet().iterator();ite.hasNext();){
      String name=(String)ite.next();
      Player player=(Player)game.getPlayers().get(name);
      send(player,msg);
    }
  }

  public void send(Player player, String msg){
    DatagramPacket packet;
    byte buf[] = msg.getBytes();
    packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
    try{
      serverSocket.send(packet);
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }

  public void run(){
    while(true){
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try{
           serverSocket.receive(packet);
      }catch(Exception ioe){}

      playerData=new String(buf);
      playerData = playerData.trim();

      switch(gameStage){
          case WAITING_FOR_PLAYERS:
            if (playerData.startsWith("CONNECT")){
              String tokens[] = playerData.split(" ");
              Player player=new Player(tokens[1],packet.getAddress(),packet.getPort());
              System.out.println("Player connected: "+tokens[1]);
              game.update(tokens[1].trim(),player);
              broadcast("CONNECTED "+tokens[1]);
              playerCount++;
              if (playerCount==numPlayers){
                gameStage=GAME_START;
              }
            }
            break;
          case GAME_START:
            System.out.println("Game State: START");
            broadcast("START");
            gameStage=IN_PROGRESS;
            break;
          case IN_PROGRESS:
            if (playerData.startsWith("PLAYER")){
              String[] playerInfo = playerData.split(" ");
              String pname =playerInfo[1];
              int x = Integer.parseInt(playerInfo[2].trim());
              int y = Integer.parseInt(playerInfo[3].trim());

              Player player=(Player)game.getPlayers().get(pname);
              player.setX(x);
              player.setY(y);

              game.update(pname, player);
              broadcast(game.toString());
            }
            break;
      }
    }
  }

  public static void main(String args[]){
    if (args.length != 1){
      System.exit(1);
    }

    new Server(Integer.parseInt(args[0]));
  }
}

