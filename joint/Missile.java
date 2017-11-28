package instantiation;
import java.util.ArrayList;

public class Missile extends Thread implements Constants{
	private int x;
	private int y;
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
		this.game = game;
		this.broadcaster = broadcaster;
		this.speed = 1;
		this.is_collided = false;
		this.x = x + 15;
		this.y = y + 15;
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
		this.start();
	}

	public void update(){
		this.x += this.x_inc;
		this.y += this.y_inc;
	}
	public String toString(){
		String return_string = "MISSILE " + src + " "
							 + x + " "
							 + y;
		return return_string;
	}

	public void run(){
		while(this.x > -30 && this.x < FRAME_WIDTH+30 && this.y < FRAME_HEIGHT+30 && this.y > -30 && this.is_collided == false){
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