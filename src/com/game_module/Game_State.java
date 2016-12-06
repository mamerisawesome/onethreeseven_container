// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game_State{
  private Map players=new HashMap();
  int type=1, x=22, y=150;
  public Game_State(){
    // do nothing
  }

  public void update(String name, Game_Player player){
    players.put(name,player);
  }

  public String toString(int type){
    String retval="";
    for(Iterator ite=players.keySet().iterator();ite.hasNext();){
      String name=(String)ite.next();
      Game_Player player=(Game_Player)players.get(name);
      retval+=player.toString();
      if(type==1){
    	  retval+=" "+Integer.toString(x+=1);
    	  retval+=" "+Integer.toString(y+=1);
      }
      if(type==2){
    	  retval+=" "+Integer.toString(x+=4);
    	  retval+=" "+Integer.toString(y+=2);
      }
      retval+=":";
    }
    return retval;
  }

  public Map getPlayers(){
    return players;
  }
}
