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
	private int speed;
	public Sprite(String name, InetAddress ip, int port, int ith, GhostWarsServer broadcaster, GameState game, int x, int y){
		this.rand = new Random();
		this.speed = 5;
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

	public int getBulletSize(){
		return this.bullet_size;
	}
	public String toString(){
		String return_string = "PLAYER " + name + " "
							 + x + " "
							 + y + " "
							 + state + " "
							 + is_dead + " "
							 + bullet_size + " "
							 + life + " "
							 + health + " "
							 + speed;

		return return_string;
	}

	public boolean isDead(){
		return this.is_dead;
	}

	public void resurrect(){
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
		broadcaster.broadcast("AUDIO MissileHit");
		if(damage == BIG_BULLET_SIZE){
			damage = 40;
		} else{
			damage = 20;
		}
		this.health = this.health - damage;
		System.out.println(this.health);
		if(this.health <= 0){
			this.health = INIT_HEALTH;
			this.life = this.life -1;
			if(this.life != 0){
				broadcaster.broadcast("AUDIO Resurrect " + this.name );
				this.resurrect();
				this.bullet_size = BULLET_SIZE;
				this.speed = NORMAL_SPEED;
			} else{
				this.state = "SpriteRIP";
				this.is_dead = IS_DEAD;
			}
		}
	}

	public void collisionResponse(int werpa){
		switch(werpa){
			case DAMAGE_UP:
				this.bullet_size = BIG_BULLET_SIZE;
				broadcaster.broadcast("AUDIO DamageUp");
				break;
			case HEALTH_UP:
				this.health = this.health + 40;
				if(this.health > 100){
					this.life++;
					broadcaster.broadcast("AUDIO LifeUp");
					this.health = this.health % 100;
				} else{
					broadcaster.broadcast("AUDIO HealthUp");
				}
				break;
			case SPEED_UP:
				if (this.speed == NORMAL_SPEED){
					this.speed = FAST_SPEED;
				} else if (this.speed == FAST_SPEED){
					this.speed = FASTER_SPEED;
				} else{
					this.speed = FASTEST_SPEED;
				}
				broadcaster.broadcast("AUDIO SpeedUp");

				break;
		}
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			} catch(Exception e){}
			if(prev_x != x || prev_y != y){
				prev_x = x;
				prev_y = y;
				broadcaster.broadcast(game.toString());
			}
		}
	}
}