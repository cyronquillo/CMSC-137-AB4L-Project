package instantiation;
import java.util.HashMap;
import java.util.ArrayList;
public class CollisionDetection{
	private GameState gs;

	public CollisionDetection(GameState gs){
		this.gs = gs;
	}



	public void checkCollision(Sprite sp){
		HashMap<String, Sprite> player_list = gs.getPlayers();
		for(String key: player_list.keySet()){
			if(player_list.get(key).getName()
				.equals(sp.getName())){
				continue;
			}

		}

	}

	public void checkCollision(Missile mi){
		
	}
}