// @ref Joseph Anthony C. Hermocilla
package bin.com.game_module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game_State{
  private Map players=new HashMap();

  public Game_State(){
    // do nothing
  }

  public void update(String name, Game_Player player){
    players.put(name,player);
  }

  public String toString(){
    String retval="";
    for(Iterator ite=players.keySet().iterator();ite.hasNext();){
      String name=(String)ite.next();
      Game_Player player=(Game_Player)players.get(name);
      retval+=player.toString()+":";
    }
    return retval;
  }

  public Map getPlayers(){
    return players;
  }
}
