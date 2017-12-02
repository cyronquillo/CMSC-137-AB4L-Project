package instantiation;
import java.net.InetAddress;
import java.util.Random;
public class Sprite implements Runnable, Constants{
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
	private int bullet_size;
	private int life;
	private int health;
	private Random rand;
	public Sprite(String name, InetAddress ip, int port, int ith, GhostWarsServer broadcaster, GameState game, int x, int y){
		this.rand = new Random();
		
		this.is_dead = false;
		this.broadcaster = broadcaster;
		this.game = game;
		this.ip = ip;
		this.name = name;
		this.port = port;
		this.ith = ith;
		this.x = x;
		this.y = y;
		this.health = INIT_HEALTH;
		this.life = INIT_LIFE;
		this.bullet_size = BULLET_SIZE;
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
							 + is_dead + " "
							 + bullet_size + " "
							 + life + " "
							 + health;

		return return_string;
	}

	public boolean isDead(){
		return this.is_dead;
	}

	public void resurrect(){
		System.out.println("testing");
		int new_x,new_y;
		do{
			new_x = rand.nextInt(MAP_WIDTH);
			new_y = rand.nextInt(MAP_HEIGHT);
			System.out.println(new_x + " " + new_y);
			if(broadcaster.game.mapArr[new_y][new_x] != TILE_FLOOR){
				continue;
			} else if(!broadcaster.spriteCollision(this,new_x * 40, new_y * 40)){
				continue;
			} 
			break;
		}while(true);
		this.prev_x = this.x;

		this.prev_y = this.y;
		this.x = new_x*40;
		this.y = new_y*40;

	}

	public void collisionResponse(Missile mi){
		int damage = mi.getBulletSize();
		if(damage == 25){
			damage = 40;
		} else{
			damage = 20;
		}
		health = health - damage;
		if(health <= 0){
			health = INIT_HEALTH;
			life = life -1;
			if(life != 0){
				this.resurrect();
			} else{
				this.state = "SpriteRIP";
				this.is_dead = IS_DEAD;
			}
		}
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