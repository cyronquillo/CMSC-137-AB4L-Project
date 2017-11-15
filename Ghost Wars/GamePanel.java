import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;											//import statements
import java.awt.Color;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable{

	public static Map map = new Map();
	public static Block[][] set = map.getBlocks();
	public static CollisionChecker cc = new CollisionChecker();
	public static Sprite redPlayer = new Sprite(Sprite.PLAYER2,map.getTileMap(), set);
	public static Sprite greenPlayer = new Sprite(Sprite.PLAYER1,map.getTileMap(), set);
	public static ArtLibrary art = new ArtLibrary();				//has attributes for all the to-be-used images. also allows for manipulation of data
	public static AudioLibrary audio = new AudioLibrary();
	public Thread player1 = new Thread(redPlayer);
	public Thread player2 = new Thread(greenPlayer);
	public static PowerUpsArray pUpsArray = new PowerUpsArray(map.getTileMap(), set);


	public void paintComponent(Graphics g){
		try{
			super.paintComponent(g);


			Brick brick = null;
			Steel steel = null;
			Border border = null;
			Grass grass = null;
			Floor floor = null;

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.STEEL){
	                    ((Steel)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.FLOOR){
						((Floor)set[i][j]).paint(g,this);
					}																											//paints all blocks by checking its type, one of its attributes
					if(set[i][j].getType() == Block.BRICK){
						((Brick)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.BORDER){
	                    border = (Border)set[i][j];
	                    g.drawImage(art.returnImage("Floor"), border.getXPos(), border.getYPos(), Block.BLOCK_SIZE, Block.BLOCK_SIZE, this);
						((Border)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.GRASS){ // for the floor under the grass
						grass = (Grass)set[i][j];
						g.drawImage(art.returnImage("Floor"), grass.getXPos(), grass.getYPos(), Block.BLOCK_SIZE, Block.BLOCK_SIZE, this);
					}

				}
			}

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.BRICK){
						brick = (Brick)set[i][j];
						for(PowerUps p : PowerUpsArray.pUps){								//paints a powerup block under a brick
							if(p.getXPos() - 5 == brick.getXPos() && p.getYPos()-5 == brick.getYPos()){
								if(brick.getPassable()){
									p.paint(g,this);
								}
							}
						}

					}
				}
			}


			redPlayer.paint(g,this);
			Iterator iter = redPlayer.getMissiles().iterator();				//paints the red player and its missiles
			while(iter.hasNext()){
				((Missile)iter.next()).paint(g,this);
			}


			greenPlayer.paint(g,this);
			iter = greenPlayer.getMissiles().iterator();							//paints the green player and its missiles
			while(iter.hasNext()){
				((Missile)iter.next()).paint(g,this);
			}

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.GRASS){								//paints the grass blocks
						((Grass)set[i][j]).paint(g,this);
					}
				}
			}
			if(Sprite.paused == true){
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
				g2d.fillRect(0,0,800,600);
				g.drawImage(art.returnImage("Pause"), 140,175,500,250, this);			//paints the pause image
			}
		} catch(Exception e){}

	}

	public Sprite getP1(){																				//gets the gamepanel's red player
		return this.redPlayer;
	}

	public Sprite getP2(){																				//gets the gamepanel's green player
		return this.greenPlayer;
	}

	public void run(){
		while(true){
			try{
				Thread.sleep(5);																				//sleeps for 5 milliseconds then repaints
        this.repaint();
			} catch(Exception e){}
		}
	}

}
