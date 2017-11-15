import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;

public class PowerUps extends RenderableObject implements Runnable{
	public static final int POWERUP_SIZE = 30;
	protected boolean taken;													// taken is equal to true if a player has picked up the powerup
	protected final int EXPIRE = 10;
	public static final int BUFF = 0;
	public static final int LIFE = 1;
	public static final int BOLT = 2;
	protected int type;

	public PowerUps(TileMap tm, Block[][] blocks){								// instantiates a powerup
		super(0,0,true,POWERUP_SIZE);
		setPosition(tm, blocks);
		this.taken = false;
	}

	public void setPosition(TileMap tm, Block[][] blocks){						// sets the position of the powerups on the map
		int repeat = 1;															// using a random number generator
		Random rand = new Random();
		do{
			int counter = 0;
			int done = 0;
			int randomNum = rand.nextInt(tm.getNumBricks())+1;

			for(int i = 0; i < blocks.length; i++){								// the loop checks a random (x,y) coordinate 
				for(int j = 0; j < blocks[0].length; j++){						// for an existing powerup. If the space is available, 
					if( blocks[i][j].getType() == Block.BRICK){					// the loop will place a powerup and set the value of repeat to 0
						counter++;		
						if(counter == randomNum){
							Brick brick = (Brick)blocks[i][j];
							done = 1;
							this.xPos = brick.getXPos()+5;
							this.yPos = brick.getYPos()+5;
							if(!brick.getOccupied()){
								brick.setOccupied();
								repeat = 0;
							}	
							break;
						}
					}
				}
				if(done == 1) break;
			}
			
		} while(repeat == 1);
	}

	public void run(){									// checks the collision and whether or not a player has picked up the powerup
		while(!this.taken){
			try{
			if(Sprite.paused == true){
				Thread.sleep(1000);
			}
			else{
				Thread.sleep(5);
				GamePanel.cc.checkCollision(this);
			}

			}catch(Exception e){}
		}
	}
	public int getType(){
		return this.type;
	}
	public void setTaken(){
		this.taken = true;
	}
	public boolean getTaken(){
		return this.taken;
	}

	public void paint(Graphics g, JPanel panel){		// draws the powerup image
		if(this.getTaken()) g.drawImage(null,this.xPos, this.yPos, POWERUP_SIZE, POWERUP_SIZE, panel); 
		else g.drawImage(image,this.xPos, this.yPos, POWERUP_SIZE, POWERUP_SIZE, panel);
	}

}