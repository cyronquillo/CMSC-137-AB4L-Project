package instantiation;
import java.util.HashMap;
import java.util.ArrayList;

public class CollisionChecker{
	private GameState game;

	//Constructor for collision checker 
	//Gamestate is needed for traversing the Sprites
	public CollisionChecker(GameState game){
		this.game = game;
	}

	public void checkCollision(Sprite s){
		//System.out.println("Sprite to Sprite");
		for(String name: this.game.getPlayers().keySet()){
			Sprite sprite = game.getPlayers().get(name);
			//If the shooter and the target is the same, bypass
			if(sprite.getName().equals(s.getName()) ){
				
			}
			//If the shooter and thetarget are not the same
			else{
				if(s.isTouched(sprite)){
					s.collisionAction();
				}
			}
		}
	}	

	//Checks the collision of the missile
	public void checkCollision(Missile mi){
		for(String name: this.game.getPlayers().keySet()){
			Sprite sprite = game.getPlayers().get(name);
			//If the shooter and the target is the same, bypass
			if(sprite.getName().equals(mi.getSrc()) ){
				
			}
			//If the shooter and thetarget are not the same
			else{
				if(mi.isTouched(sprite)){
					//System.out.println("Sprite Name: " + sprite.getName() + " Src: " + mi.getSrc());
					game.getMissiles().remove(mi);
					//System.out.println("hitMi");
				}
			}
		}
		
	}

}