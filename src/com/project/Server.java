package com.project;

import com.chat_module.*;
import com.game_module.*;

public class Server {
  public static void main (String[] args) {
    Server_Chat chat_instance = new Server_Chat();
    Server_Game game_instance = new Server_Game(Integer.parseInt(args[0]));

    System.out.println("[DONE] Server started");
  }
}