package instantiation;
import java.util.HashMap;
import java.util.ArrayList;

public class GameState implements Constants{


	private HashMap<String, Sprite> players;
	private ArrayList<Missile> missiles;
	public Map map; 
	public int[][] mapArr;

	public GameState(){
		players = new HashMap<String,Sprite>();
		missiles = new ArrayList<Missile>();
		map = new Map();
		mapArr = map.getTileMap().getMap();
		// System.out.println(map.getTileMap().toString());
		// System.out.println(this.mapString());
	}


	public void update(String name, Sprite sprite){
		players.put(name, sprite);
	}

	public String mapString(){
		String return_val = "";
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				return_val += this.mapArr[i][j] + " ";
			}
			return_val = return_val.trim();
			if(i != MAP_HEIGHT-1){
				return_val += "\n";
			}
		}
		return return_val;
	}
	public String toString(){
		String return_value = "";
		for(String key: players.keySet()){
			return_value += 
				players.get(key).toString() + ":";
		}
		for(int i = 0; i < missiles.size(); i++){
			return_value +=
				missiles.get(i).toString() + ":";
		}

		return return_value;
	}

	public int missileCount(){
		return this.missiles.size();
	}

	public Missile getMissile(int index){
		return this.missiles.get(index);
	}
	public void updateMissiles(){
		// for(int i = 0; i< this.missiles.size(); i++){
		// 	this.missiles.get(i).update();
		// }
	}

	public void addMissile(Missile mi){
		this.missiles.add(mi);
	}

	public void removeMissile(Missile mi){
		this.missiles.remove(mi);
	}

	public ArrayList<Missile> getMissiles(){
		return this.missiles;
	}


	public HashMap<String, Sprite> getPlayers(){
		return players;
	}

}