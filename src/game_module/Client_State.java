// @author Joseph Anthony C. Hermocilla
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Client_State{
  private Map players=new HashMap();

  public Client_State(){
    // do nothing
  }

  public void update(String name, Player player){
    players.put(name,player);
  }

  public String toString(){
    String retval="";
    for(Iterator ite=players.keySet().iterator();ite.hasNext();){
      String name=(String)ite.next();
      Player player=(Player)players.get(name);
      retval+=player.toString()+":";
    }
    return retval;
  }

  public Map getPlayers(){
    return players;
  }
}
