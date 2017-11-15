import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.CardLayout;

public class Sprite extends RenderableObject implements Runnable, KeyListener{

	/*ATTRIBUTES*/
	public static final int SIZE = 30;
	protected ArrayList<Missile> shoots= new ArrayList<Missile>(); // arraylist for the missiles
	private int speed;
	private boolean side;

	private int lastMove;
	private int life;
	private int damage;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	public static final boolean PLAYER1 = true;
	public static final boolean PLAYER2 = false;

	public static final int ORIGINAL_DAMAGE = 10;
	public static final int ORIGINAL_SPEED = 15;
	private int loadingTime;
	private final int delay = 500;

	private int speedUp;				// checkers for the power up
	private int damageUp;
	private int healthUp;
	public static final int ACTIVATED = 1;
	public static final int WEARING_OFF = 2;
	public static final int STOPPED = 3;
	private static AudioLibrary audio = new AudioLibrary();

	public static boolean paused;
	public Sprite(boolean side, TileMap tm, Block[][] blocks){
		super(100, 100, false,SIZE);
		setPosition(blocks,tm);

		System.out.println("Coordinates of Sprite: (" +this.xPos+","+this.yPos + ")");
		this.dx = 0;
		this.dy = 0;
		this.size = SIZE;
		this.side = side;
		this.image = (side == PLAYER1?(art.returnImage("GreenMushroomF")):(art.returnImage("RedMushroomF")));
		this.lastMove = DOWN;
		this.life = 100;
		this.loadingTime = 0;
		this.speed = ORIGINAL_SPEED;
        this.damage = ORIGINAL_DAMAGE;
        this.speedUp = 3;
        this.damageUp = 3;
        this.healthUp = 3;
        paused = false;
	}

	public void setPosition(Block[][] blocks, TileMap tm){		// sets the position of the sprite at the start of the game
		int repeat = 1;
		Random rand = new Random();
		do{
			int counter = 0;
			int done = 0;
			int randomNum = rand.nextInt(tm.getNumFloors())+1;

			for(int i = 0; i < blocks.length; i++){
				for(int j = 0; j < blocks[0].length; j++){
					if( blocks[i][j].getType() == Block.FLOOR){
						counter++;
						if(counter == randomNum){
							Floor floor = (Floor)blocks[i][j];
							done = 1;
							this.xPos = floor.getXPos()+5;
							this.yPos = floor.getYPos()+5;
							if(!floor.getOccupied()){
								floor.setOccupied();
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

	public void run(){			
		while(true){
			try{
				if(paused == true){
					Thread.sleep(1000);
				}
				else{
					Thread.sleep(this.speed);
					if(this.checkDead()){		// checks if the shroom is dead
						audio.returnAudio("Victory").play(false);
						String winner = ((side == PLAYER1? "RedShroom": "GreenShroom"));
						System.out.println(winner + " wins!");
						CardLayout victoryCard = (CardLayout)Menu.panel.getLayout();	// sets the victory card for the winning shroom
						if(winner == "RedShroom"){
							victoryCard.show(Menu.panel, Menu.RED_MUSHROOM_WIN);

							paused = true;
							return;	
						} 
						else{
							victoryCard.show(Menu.panel, Menu.GREEN_MUSHROOM_WIN);
							paused = true;
							return;	
						} 
					}
					GamePanel.cc.checkCollision(this);
					Sprite enemy = (side == PLAYER1? GamePanel.redPlayer: GamePanel.greenPlayer);   //getP1 && getP2();
				 	this.moveSprite();
					
					this.setLoadingTime();	
				}
			} catch(Exception e){}
		}
	}

	public ArrayList<Missile> getMissiles(){				// getters for the missiles of the shroom
		return shoots;
	}
    
	public void fireMissile(){		// when firing, it creates a new missile instance
		Missile m = new Missile(this.damage,this.getLastMove(), this.xPos+ (SIZE / 4), this.yPos + (SIZE/4), this.side, this);
		shoots.add(m);
		Thread t1 = new Thread(m);
		t1.start();
	}
    
	public void removeMissile(Missile m){		// removes the missile once collided
		shoots.remove(m);
	}
    
	public void setLoadingTime(){		// used for the delay of the missile
		if(loadingTime!= 0) loadingTime--;
	}
		// getters, setters and checkers
	public int isSpedUp(){
		return this.speedUp;
	}
	public int isDamageUp(){
		return this.damageUp;
	}
	public int isHealthUp(){
		return this.healthUp;
	}
	public void setSpeedPower(int stat){
		this.speedUp = stat;
	}
	public void setDamagePower(int stat){
		this.damageUp = stat;
	}
	public void setHealthPower(int stat){
		this.healthUp = stat;
	}


	public void setLife(int life){
		this.life+= life;
		if(this.life > 100){
			this.life = 100;
		}
	}
	public int getLife(){
		return this.life;
	}

	public void setSpeed(int speed){
		this.speed = speed;
		if(this.speed < 7) this.speed = 7;
	}
	public int getSpeed(){
		return this.speed;
	}
	public void setDamage(int damage){
		this.damage = damage;
		if(this.damage >= 90) this.damage = 30;
	}
	public int getDamage(){
		return this.damage;
	}
    
	public int getLastMove(){		// to know the current state of the shroom
		return this.lastMove;
	}
    
	public void moveSprite(){
		this.xPos += this.dx;
		this.yPos += this.dy;
	}
	public boolean checkDead(){
		if (this.life <= 0) return true;
		return false;
	}

	public void keyPressed(KeyEvent ke){		// movement of the shroom
		int key = ke.getKeyCode();
		if(paused == false){

			if((key == KeyEvent.VK_UP  && this.side == PLAYER1) || (key == KeyEvent.VK_W && this.side == PLAYER2)){
				this.dy = -1;
				this.dx = 0;
				this.lastMove = UP;
				image = this.side == PLAYER2? art.returnImage("RedMushroomB"): art.returnImage("GreenMushroomB");
			}
			if((key == KeyEvent.VK_LEFT && this.side == PLAYER1) || (key == KeyEvent.VK_A && this.side == PLAYER2)){
				this.dx = -1;
				this.dy = 0;
				this.lastMove = LEFT;
				image = this.side == PLAYER2? art.returnImage("RedMushroomL"): art.returnImage("GreenMushroomL");
			}
			if((key == KeyEvent.VK_RIGHT && this.side == PLAYER1) || (key == KeyEvent.VK_D && this.side == PLAYER2)){
				this.dx = 1;
				this.dy = 0;
				this.lastMove = RIGHT;
				image = this.side == PLAYER2? art.returnImage("RedMushroomR"): art.returnImage("GreenMushroomR");
			}
			if((key == KeyEvent.VK_DOWN && this.side == PLAYER1) || (key == KeyEvent.VK_S && this.side == PLAYER2)){
				this.dy = 1;
				this.dx = 0;
				this.lastMove = DOWN;
				image = this.side == PLAYER2? art.returnImage("RedMushroomF"): art.returnImage("GreenMushroomF");
			}
		}

	}
    public boolean getSide(){
    	return this.side;
    }
	public void keyTyped(KeyEvent ke){}
	
    public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		if(paused == false){
			if((key == KeyEvent.VK_UP  && this.side == PLAYER1) || (key == KeyEvent.VK_W && this.side == PLAYER2)){
				this.dy = 0;
			}
			if((key == KeyEvent.VK_LEFT && this.side == PLAYER1) || (key == KeyEvent.VK_A && this.side == PLAYER2)){
				this.dx = 0;
			}
			if((key == KeyEvent.VK_RIGHT && this.side == PLAYER1) || (key == KeyEvent.VK_D && this.side == PLAYER2)){
				this.dx = 0;
			}
			if((key == KeyEvent.VK_DOWN && this.side == PLAYER1) || (key == KeyEvent.VK_S && this.side == PLAYER2)){
				this.dy = 0;
	        }
	        if(((key == KeyEvent.VK_SPACE && this.side == PLAYER1 ) || (key == KeyEvent.VK_E && this.side == PLAYER2)) && this.loadingTime == 0){
	            this.fireMissile();
	            this.loadingTime = 50;
	            if(this.damage == 10) audio.returnAudio("NormalShoot").play(false);
	            else audio.returnAudio("BuffedShoot").play(false);

	        }
		}
        if(key == KeyEvent.VK_P && this.side == PLAYER1){				// can pause the game
			this.paused = !this.paused;
			System.out.println("lol");
			if(paused == true) GamePanel.audio.returnAudio("Paused").play(true);
			else GamePanel.audio.returnAudio("Paused").stop();
			
		}
	}



	public void collisionResponse(Missile m){		// collision response for the shroom when a missile hits it
		setLife(m.getDamage() * -1);
		String str = side == PLAYER1? "player1":"player2";
		System.out.println(str + " was hit.");
		System.out.println("Life left: " +this.life);
		m.setVisible(false);
	}
	public void collisionResponse(Sprite enemy){ // collision response for the shroom when a shroom moves to it
		this.dx = 0;
		this.dy = 0;
	}

	public void paint(Graphics g, JPanel panel){
		g.drawImage(image,xPos,yPos,SIZE,SIZE,panel);
	}
}