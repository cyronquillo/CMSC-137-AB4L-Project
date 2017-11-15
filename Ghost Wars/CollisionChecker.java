import java.util.ArrayList;													//import statements

public class CollisionChecker{
	private static AudioLibrary audio = new AudioLibrary();		//has an AudioLibrary static attribute for playing audio

	public void checkCollision(Sprite s){
		Block[][] blocks = GamePanel.map.getBlocks();
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < (blocks[i]).length; j++){			//this nested for-loop goes through all the blocks to see if it collides with a Sprite
                if(s.isTouched(blocks[i][j]) && !blocks[i][j].getPassable()){
                	// audio.returnAudio("WallResponse").play(false);		//plays the corresponding sound effect
                    blocks[i][j].collisionResponse(s);				//goes to the block's response to the collision
                }
        	}
		}
		Sprite enemy = (s.getSide()== Sprite.PLAYER1?  GamePanel.redPlayer: GamePanel.greenPlayer);
		if(s.isTouched(enemy)){																		//sprite-to-sprite collision
			s.collisionResponse(enemy);
		}
	}

	public void checkCollision(Missile mi){
		Block[][] blocks = GamePanel.map.getBlocks();
		ArrayList<Missile> miss = mi.getShooter().getMissiles();
        for(int i = 0; i < blocks.length; i++){								//same as the previous nested-for loop but for missiles instead of sprites
            for(int j = 0; j < (blocks[i]).length; j++){
	        	if(mi.isTouched(blocks[i][j])&& !blocks[i][j].getPassable()){
	            	blocks[i][j].collisionResponse(mi);
	            	miss.remove(mi);
		        }
		    }
		}
		Sprite enemy = (mi.getShooter().getSide()== Sprite.PLAYER1?  GamePanel.redPlayer: GamePanel.greenPlayer);
			if(mi.isTouched(enemy)){
				audio.returnAudio("MissileResponse").play(false);			//a sprite-to-missile collision response
				enemy.collisionResponse(mi);
				miss.remove(mi);
			}
	}
	public void checkCollision(PowerUps p){											//checks if a specific player collides with a powerup.
		if(p.isTouched(GamePanel.redPlayer)){
			if(p.getType() == PowerUps.BOLT) audio.returnAudio("SpeedUp").play(false);
			if(p.getType() == PowerUps.LIFE) audio.returnAudio("HealthUp").play(false);		//plays a sound effect corresponding to the powerup
			if(p.getType() == PowerUps.BUFF) audio.returnAudio("DamageUp").play(false);
			p.collisionResponse(GamePanel.redPlayer);
			p.setTaken();
		}
		if(p.isTouched(GamePanel.greenPlayer)){
			if(p.getType() == PowerUps.BOLT) audio.returnAudio("SpeedUp").play(false);
			if(p.getType() == PowerUps.LIFE) audio.returnAudio("HealthUp").play(false);
			if(p.getType() == PowerUps.BUFF) audio.returnAudio("DamageUp").play(false);
			p.collisionResponse(GamePanel.greenPlayer);
			p.setTaken();
		}
	}
}
