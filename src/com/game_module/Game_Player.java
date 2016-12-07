// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.net.InetAddress;

public class Game_Player {
  private InetAddress address;
  private int port;
  private String name;
  private int x,y;
  int color;
  int score = 0;

  public Game_Player(String name,InetAddress address, int port){
    this.address = address;
    this.port = port;
    this.name = name;
  }

  public InetAddress getAddress(){
    return address;
  }

  public void setScore(int score){
    this.score = score;
  }

  public int getPort(){
    return port;
  }

  public String getName(){
    return name;
  }

  public void setX(int x){
    this.x=x;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public void setY(int y){
    this.y=y;
  }

  public String toString(){
    String retval="";
    retval+="PLAYER ";
    retval+=name+" ";
    retval+=x+" ";
    retval+=y+" ";
    retval+=color+" ";
    retval+=score;
    return retval;
  }
}
