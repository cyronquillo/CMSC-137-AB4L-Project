package instantiation;
import java.util.HashMap;

public class GameState{


	private HashMap<String, Sprite> players;


	public GameState(){
		players = new HashMap<String,Sprite>();
	}


	public void update(String name, Sprite sprite){
		players.put(name, sprite);
	}


	public String toString(){
		String return_value = "";
		for(String key: players.keySet()){
			return_value += 
				players.get(key).toString() + ":";
		}
		return return_value;
	}



	public HashMap<String, Sprite> getPlayers(){
		return players;
	}

}