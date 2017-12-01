package instantiation;
import java.net.InetAddress;
public class Sprite implements Runnable{
	private InetAddress ip;
	private int port;
	private String name;
	private int x,y;
	private int ith;
	private String color;
	private String state;
	private String position;
	private int prev_x;
	private int prev_y;
	private GhostWarsServer broadcaster;
	private GameState game;
	private Thread t;
	private boolean is_dead;
	public Sprite(String name, InetAddress ip, int port, int ith, GhostWarsServer broadcaster, GameState game, int x, int y){
		this.is_dead = false;
		this.broadcaster = broadcaster;
		this.game = game;
		this.ip = ip;
		this.name = name;
		this.port = port;
		this.ith = ith;
		this.x = x;
		this.y = y;
		switch(ith%4){
			case 0:
				this.color = "red";
				break;
			case 1:
				this.color = "blue";
				break;
			case 2:
				this.color = "orange";
				break;
			case 3:
				this.color = "pink";
				break;
		}
		this.position = "Up"; // default
		this.state = this.color + "." + this.position;
		t = new Thread(this);
		
		t.start();

	}

	public InetAddress getIP(){
		return ip;
	}

	public int getPort(){
		return port;
	}

	public String getName(){
		return name;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public String getState(){
		return state;
	}

	public String getColor(){
		return color;
	}
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public void setState(String state){
		this.state = state;
		String[] temp = state.split("\\.");
		this.position = temp[1];
	}
	public String toString(){
		String return_string = "PLAYER " + name + " "
							 + x + " "
							 + y + " "
							 + state + " "
							 + is_dead;

		return return_string;
	}

	public boolean isDead(){
		return this.is_dead;
	}
	public void collisionResponse(){
		this.state = "SpriteRIP";
		this.is_dead = true;
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(3);
			} catch(Exception e){}
			if(prev_x != x || prev_y != y){
				prev_x = x;
				prev_y = y;
				broadcaster.broadcast(game.toString());
			}
		}
	}
}