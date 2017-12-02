package instantiation;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Rectangle;

public class CollisionDetection implements Constants{
	private GameState gs;
	private GhostWarsServer broadcaster;
	public CollisionDetection(GameState gs, GhostWarsServer broadcaster){
		this.gs = gs;
		this.broadcaster = broadcaster;
	}



	public boolean checkCollision(Sprite sp1, int prop_x, int prop_y, Sprite sp2){
		Rectangle sp1Rect = getBounds(prop_x, prop_y, BLOCK_SIZE);
		Rectangle sp2Rect = getBounds(sp2.getX(), sp2.getY(), BLOCK_SIZE);
		if(sp1Rect.intersects(sp2Rect)){
			return HAS_COLLIDED;
		}
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Sprite sp1, int prop_x, int prop_y, int[][] map){
		Rectangle sp1Rect = getBounds(prop_x, prop_y, BLOCK_SIZE);
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				if(map[i][j] == TILE_CORNER || 
					map[i][j] == HORIZONTAL_BORDER ||
						map[i][j] == HORIZONTAL_LEFT_BORDER ||
							map[i][j] == HORIZONTAL_RIGHT_BORDER || 
								map[i][j] == VERTICAL_BORDER ||
									map[i][j] == VERTICAL_UP_BORDER ||	
										map[i][j] == VERTICAL_DOWN_BORDER ||
											map[i][j] == STEELEST_BLOCK ||
												map[i][j] == STEELER_BLOCK ||
													map[i][j] == STEEL_BLOCK){
					Rectangle blockRect = getBounds(j * 40, i * 40, BLOCK_SIZE);
					if(sp1Rect.intersects(blockRect)){
						return HAS_COLLIDED;
					}
				}
			}
		}
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Missile mi, Sprite sp){
		Rectangle miRect = getBounds(mi.getX(), mi.getY(), mi.getBulletSize());
		Rectangle spRect = getBounds(sp.getX(), sp.getY(), BLOCK_SIZE);
		if(miRect.intersects(spRect)){
			mi.setCollided(true);
			sp.collisionResponse(mi);
			return HAS_COLLIDED;
		}
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Missile mi1, Missile mi2){
		Rectangle mi1Rect = getBounds(mi1.getX(), mi1.getY(), mi1.getBulletSize());
		Rectangle mi2Rect = getBounds(mi2.getX(), mi2.getY(), mi2.getBulletSize());
		if(mi1Rect.intersects(mi2Rect)){
			if(mi2.getBulletSize() > mi1.getBulletSize()){
				return HAS_COLLIDED;	
			} else if( mi2.getBulletSize() < mi1.getBulletSize()){
				mi2.setCollided(HAS_COLLIDED);
			} else{
				mi2.setCollided(HAS_COLLIDED);
				return HAS_COLLIDED;
			}
		}
		// System.out.println("NOT COLLIDED");
		return NOT_COLLIDED;
	}

	public boolean checkCollision(Missile mi, int[][] map){
		Rectangle miRect = getBounds(mi.getX(), mi.getY(), mi.getBulletSize());
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				Rectangle blockRect = getBounds(j * 40, i * 40, BLOCK_SIZE);
				if(map[i][j] == TILE_CORNER || 
					map[i][j] == HORIZONTAL_BORDER ||
						map[i][j] == HORIZONTAL_LEFT_BORDER ||
							map[i][j] == HORIZONTAL_RIGHT_BORDER || 
								map[i][j] == VERTICAL_BORDER ||
									map[i][j] == VERTICAL_UP_BORDER ||	
										map[i][j] == VERTICAL_DOWN_BORDER){
					if(miRect.intersects(blockRect)){
						mi.setCollided(HAS_COLLIDED);
					}
				} else if(map[i][j] == STEELEST_BLOCK ||
					map[i][j] == STEELER_BLOCK){
					if(miRect.intersects(blockRect)){
						mi.setCollided(HAS_COLLIDED);
						map[i][j]--;
					}
				} else if(map[i][j] == STEEL_BLOCK){
					if(miRect.intersects(blockRect)){
						mi.setCollided(HAS_COLLIDED);
						map[i][j] = TILE_FLOOR;
					}
				}
			}
		}
		broadcaster.broadcast("MAP\n" + gs.mapString());
		return NOT_COLLIDED;
	}

	public Rectangle getBounds(int x, int y, int size){
		return new Rectangle(x, y, size, size);
	}

}