package instantiation;
import java.util.HashMap;
import java.util.ArrayList;

public class GameState implements Constants{


	private HashMap<String, Sprite> players;
	private ArrayList<Missile> missiles;
	public Map map; 
	public int[][] mapArr;
	private boolean is_paused;
	public String pauser;
	private int remainingRank;

	public GameState(){
		remainingRank = 0;
		players = new HashMap<String,Sprite>();
		missiles = new ArrayList<Missile>();
		map = new Map();
		mapArr = map.getTileMap().getMap();
		this.is_paused = false;
		// System.out.println(map.getTileMap().toString());
		// System.out.println(this.mapString());
	}

	public void addPlayerCount(){
		remainingRank += 1;
	}

	public int getRemainingRank(){
		remainingRank -=1;
		return remainingRank+1;
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

	public void setPaused(boolean p){
		this.is_paused = p;
	}

	public boolean isPaused(){
		return this.is_paused;
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

	public boolean winnerExists(){
		int counter = 0;
		for(String key: players.keySet()){
			if(players.get(key).getLife() != 0){
				counter++;
			}
			if(counter == 2){
				return false;
			}
		}
		return true;
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