// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Game_State{
  private Map players=new HashMap();
  int type=1, x=450, y=250, x1=50, y1=50;
  Shape[] s;
  int counter = 0;
  public Game_State(){
    // do nothing
  }

  public void update(String name, Game_Player player, int count){
    players.put(name,player);
    player.color = count;
  }

  public String toString(){
  if(counter==0){
    counter+=1;
    s = new Shape[10];
    Random rand = new Random();
    for(int i = 0; i < s.length; i++){
      s[i] = new Shape(1,rand.nextInt(1000),rand.nextInt(500),rand.nextInt(4), rand.nextInt(this.getPlayers().size()));
    }
  }
    String retval="";
    for(Iterator ite=players.keySet().iterator();ite.hasNext();){
      String name=(String)ite.next();
      Game_Player player=(Game_Player)players.get(name);
      retval+=player.toString();
      Random randirection = new Random();
      int signX, signY;
          for(int y = 0; y < s.length; y++){
          int temp = randirection.nextInt(200);
          if(s[y].getDirection() == 0){
            if(temp<100){signX=1;signY=1;}
              else if(temp>=100 && temp<150){signX=1;signY=-1;}
              else if(temp>=150 && temp<160){signX=-1;signY=1;}
              else{signX=-1;signY=-1;}
          } else if(s[y].getDirection() == 1){
            if(temp<50){signX=1;signY=1;}
              else if(temp>=50 && temp<150){signX=1;signY=-1;}
              else if(temp>=150 && temp<160){signX=-1;signY=1;}
              else{signX=-1;signY=-1;}
          } else if(s[y].getDirection() == 2){
            if(temp<30){signX=1;signY=1;}
              else if(temp>=30 && temp<60){signX=1;signY=-1;}
              else if(temp>=60 && temp<160){signX=-1;signY=1;}
              else{signX=-1;signY=-1;}
          } else {
            if(temp<30){signX=1;signY=1;}
              else if(temp>=30 && temp<60){signX=1;signY=-1;}
              else if(temp>=60 && temp<90){signX=-1;signY=1;}
              else{signX=-1;signY=-1;}
          }

          if(s[y].getX()>=1000 || s[y].getX()<=0 || s[y].getY()>=500 || s[y].getY()<=0){
            s[y].setX(randirection.nextInt(1000));
            s[y].setY(randirection.nextInt(500));
            s[y].setColor(randirection.nextInt(players.size()));
          }
          if(y==0) retval+=" ";
          retval+=s[y].getColor();
          s[y].setX(s[y].getX()+1*signX);
          retval+="/";
          retval+=Integer.toString(s[y].getX());
          s[y].setY(s[y].getY()+1*signY);
          retval+="/"+Integer.toString(s[y].getY());
            if(y!=s.length-1) retval+="_";
          }
      retval+=":";
    }
    return retval;
  }

  public Map getPlayers(){
    return players;
  }
}
