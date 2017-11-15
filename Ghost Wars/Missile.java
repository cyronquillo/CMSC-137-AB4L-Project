import javax.swing.*;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Graphics;

public class Missile extends RenderableObject implements Runnable{
	private int speed;
	private int damage;
	private int key;
	public static final int SIZE = 15;		// attributes
	private Image image;

	private boolean visible;
	private Sprite shooter;


	public Missile(int damage,int lastKey, int xPos, int yPos, boolean side, Sprite shooter){ // constructor
		super(xPos, yPos, false,SIZE);
		this.key = lastKey;
		if(damage == 10){
			this.image = (side == Sprite.PLAYER1?(art.returnImage("GreenBullet")):(art.returnImage("RedBullet"))); // to check where the missile came from
		}
		else{
			this.image = (side == Sprite.PLAYER1?(art.returnImage("GreenBulletP")):(art.returnImage("RedBulletP")));
		}

		if(this.key == Sprite.UP){	// movement of the missile / direction
			this.yPos -= (SIZE+20);
			this.dy = -1;	
		} 
		if(this.key == Sprite.DOWN){
			this.yPos += SIZE;
			this.dy = 1;
		}	
		if(this.key == Sprite.LEFT){
			this.xPos -= (SIZE+20);
			this.dx = -1;
		}	
		if(this.key == Sprite.RIGHT){
			this.xPos += SIZE;	
			this.dx = 1;
		} 

		this.damage = damage;
		this.shooter = shooter;
		this.visible = true;
        this.speed = 5;
	}
	public void moveMissile(){
		this.yPos += this.dy;
		this.xPos += this.dx;
	}
	public int getDamage(){		// getter for damage
		return this.damage;
	}
	public void run(){
		try{
			while(this.visible){
				GamePanel.cc.checkCollision(this);
				if(Sprite.paused == true){
					Thread.sleep(1000);
				}
				else{
					Thread.sleep(speed);
					this.moveMissile();		
				}
			}
		} catch(Exception e){}
		
	}
    
    public void collisionResponse(Sprite s){}
    public void collisionResponse(Missile m){}
    
	public boolean getVisible(){
		return this.visible;
	}
    public void setVisible(boolean bool){
    	this.visible = bool;
    }
    public Sprite getShooter(){
    	return this.shooter;
    }
   
	public void paint(Graphics g, JPanel panel){
		if(!this.visible) g.drawImage(null,xPos,yPos,SIZE,SIZE,panel);
		else g.drawImage(image,xPos,yPos,SIZE,SIZE,panel);

	}


}