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
		Rectangle sp1Rect = getBounds(prop_x, prop_y, BLOCK_SIZE);
		Rectangle sp2Rect = getBounds(sp2.getX(), sp2.getY(), BLOCK_SIZE);
		if(sp1Rect.intersects(sp2Rect)){
			System.out.println("collided");
			return HAS_COLLIDED;
		}
		// System.out.println("NOT COLLIDED");
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Sprite sp1, int prop_x, int prop_y, int[][] map){
		Rectangle sp1Rect = getBounds(prop_x, prop_y, BLOCK_SIZE);
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				if(map[i][j] == TILE_CORNER || 
					map[i][j] == HORIZONTAL_BORDER || 
						map[i][j] == VERTICAL_BORDER){
					Rectangle blockRect = getBounds(j * 40, i * 40, BLOCK_SIZE);
					if(sp1Rect.intersects(blockRect)){
						System.out.println("collided");
						return HAS_COLLIDED;
					}
				}
			}
		}
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Missile mi, Sprite sp){
		Rectangle miRect = getBounds(mi.getX(), mi.getY(), BULLET_SIZE);
		Rectangle spRect = getBounds(sp.getX(), sp.getY(), BLOCK_SIZE);
		if(miRect.intersects(spRect)){
			sp.collisionResponse();
			System.out.println("collided");
			return HAS_COLLIDED;
		}
		// System.out.println("NOT COLLIDED");
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Missile mi, int[][] map){
		Rectangle miRect = getBounds(mi.getX(), mi.getY(), BULLET_SIZE);
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				if(map[i][j] == TILE_CORNER || 
					map[i][j] == HORIZONTAL_BORDER || 
						map[i][j] == VERTICAL_BORDER){
					Rectangle blockRect = getBounds(j * 40, i * 40, BLOCK_SIZE);
					if(miRect.intersects(blockRect)){
						System.out.println("collided");
						return HAS_COLLIDED;
					}
				}
			}
		}
		return NOT_COLLIDED;
	}

	public Rectangle getBounds(int x, int y, int size){
		return new Rectangle(x, y, size, size);
	}

}