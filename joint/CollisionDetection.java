package instantiation;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Rectangle;

public class CollisionDetection implements Constants{
	private GameState gs;

	public CollisionDetection(GameState gs){
		this.gs = gs;
	}



	public boolean checkCollision(Sprite sp1, int prop_x, int prop_y, Sprite sp2){
		Rectangle sp1Rect = getBounds(prop_x, prop_y);
		Rectangle sp2Rect = getBounds(sp2.getX(), sp2.getY());
		if(sp1Rect.intersects(sp2Rect)){
			System.out.println("collided");
			return HAS_COLLIDED;
		}
		// System.out.println("NOT COLLIDED");
		return NOT_COLLIDED;

	}

	public void checkCollision(Missile mi){
		
	}


	public Rectangle getBounds(int x, int y){
		return new Rectangle(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
}