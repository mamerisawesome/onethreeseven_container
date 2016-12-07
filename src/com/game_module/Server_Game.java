// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Server_Game implements Runnable, Game_Constants {
  String playerData;
  int playerCount = 0;
  DatagramSocket serverSocket = null;
  Game_State game;
  int gameStage = WAITING_FOR_PLAYERS;
  int numPlayers;
  Thread t = new Thread(this);

  public Server_Game (int numPlayers) {
    this.numPlayers = numPlayers;
    try {
      serverSocket = new DatagramSocket(PORT);
      serverSocket.setSoTimeout(100);
    } catch (IOException e) {
      System.err.println("Could not listen on port: "+PORT);
      System.exit(-1);
    } catch(Exception e){
      // do nothing
    }
    game = new Game_State();

    System.out.println("[DONE] Game created");

    t.start();
  }

  public void broadcast (String msg) {
    for(Iterator ite = game.getPlayers().keySet().iterator();ite.hasNext();){
      String name = (String)ite.next();
      Game_Player player = (Game_Player)game.getPlayers().get(name);
      send(player,msg);
    }
  }

  public void send (Game_Player player, String msg) {
    DatagramPacket packet;
    byte buf[] = msg.getBytes();
    packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
    try {
      serverSocket.send(packet);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void run () {
    while(true){
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        serverSocket.receive(packet);
      } catch (Exception ioe) {
        // do nothing
      }
      playerData = new String(buf);
      playerData = playerData.trim();

      switch (gameStage) {

        case WAITING_FOR_PLAYERS:
          if (playerData.startsWith("CONNECT")){
            String tokens[] = playerData.split(" ");
            Game_Player player=new Game_Player(tokens[1],packet.getAddress(),packet.getPort());
            System.out.println("Player connected: "+tokens[1]);
            game.update(tokens[1].trim(),player, playerCount);
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
            // [FORM] PLAYER <player name> <x> <y>
            String[] playerInfo = playerData.split(" ");
            String pname =playerInfo[1];
            int x = Integer.parseInt(playerInfo[2].trim());
            int y = Integer.parseInt(playerInfo[3].trim());

            Game_Player player=(Game_Player)game.getPlayers().get(pname);
            player.setX(x);
            player.setY(y);

            game.update(pname, player, player.color);
            broadcast(game.toString());
          }
          broadcast(game.toString());

          break;

      }
    }
  }
}
