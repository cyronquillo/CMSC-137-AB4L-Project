package instantiation;
import java.util.ArrayList;

public class Missile extends RenderableObject implements Constants, Runnable{
	private static final int SIZE = 10;
	private static CollisionChecker cc;
	//private int x;
	//private int y;
	private int x_inc;
	private int y_inc;
	private int speed;
	private String src;
	private String direction;
	private ArrayList<Missile> storage;
	private Boolean is_collided;

	GhostWarsServer broadcaster;
	GameState game;
	public Missile(int x, int y, String src, String direction, ArrayList<Missile> storage, GhostWarsServer broadcaster, GameState game){
		super(x,y,false,SIZE);
		this.game = game;
		this.broadcaster = broadcaster;
		this.cc = new CollisionChecker(this.game);
		this.speed = 1;
		this.is_collided = false;
		//this.x = x + 15;
		//this.y = y + 15;
		this.xPos = x + 15;
		this.yPos = y + 15;
		this.src = src;
		this.direction = direction;
		this.storage = storage;
		switch(direction){
			case "Upwards":
				this.y_inc = -speed;
				this.x_inc = 0;
				break;
			case "Downwards":
				this.y_inc = speed;
				this.x_inc = 0;
				break;
			case "Leftwards":
				this.y_inc = 0;
				this.x_inc = -speed;
				break;
			case "Rightwards":
				this.y_inc = 0;
				this.x_inc = speed;
				break;
			default:
				System.out.println("edi wow");
		}
		Thread m = new Thread(this);
		m.start();
	}

	public void update(){
		this.xPos += this.x_inc;
		this.yPos += this.y_inc;
	}
	public String toString(){
		String return_string = "MISSILE " + src + " "
							 + this.xPos + " "
							 + this.yPos;
		return return_string;
	}

	public String getSrc(){
		return src;
	}


	public void run(){
		while(this.xPos > -30 && this.xPos < FRAME_WIDTH+30 && this.yPos < FRAME_HEIGHT+30 && this.yPos > -30 && this.is_collided == false){
			
			cc.checkCollision(this);
			try { 
				Thread.sleep(3);
				this.update();
				broadcaster.broadcast(game.toString());
			} catch(Exception e){}

		}
		this.is_collided = true;
		System.out.println("hit");
		storage.remove(this);
	}
}