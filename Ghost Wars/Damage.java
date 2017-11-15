import java.awt.*;
import javax.swing.*;			// import statements
import java.awt.event.*;

public class Damage extends PowerUps{
	private static Sprite lol;
	private static int seconds;		// attributes
	private static final int SPAN = 20000;
	private static ActionListener aL = new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	if(Sprite.paused == false){
                if (getSeconds() > 15000 && getSeconds() <SPAN) {
                    if(getSeconds() % 1000 == 0) lol.setDamagePower(Sprite.WEARING_OFF);
                    else lol.setDamagePower(Sprite.ACTIVATED);
                    System.out.println("WEARING OFF");
                }
                else if(getSeconds() >= SPAN){
                	lol.setDamagePower(Sprite.STOPPED);
                	timer.stop();
                	System.out.println("STOPPED");
                	lol.setDamage(Sprite.ORIGINAL_DAMAGE);
                }
               	else{
               		lol.setDamagePower(Sprite.ACTIVATED);
               		System.out.println("ACTIVATED");
               	}
               	setSeconds(timer.getDelay());
               	System.out.println(getSeconds());
        	}

            
        }
    };
	private static Timer timer = new Timer(500,aL);;

	public Damage(TileMap tm, Block[][] blocks){
		super(tm,blocks);
		this.image = art.returnImage("StarPower");
		System.out.println("Damage Up at: " + (xPos-5)/Block.BLOCK_SIZE + "," + (yPos-5)/Block.BLOCK_SIZE);
		int seconds = 0;
	}

	public static int getSeconds(){
		return seconds;
	}
	public static void setSeconds( int num){
		seconds += num;
	}
	public void collisionResponse(Sprite tank){
		if(timer.isRunning()) timer.stop();
		seconds = 0;
		tank.setDamage(tank.getDamage()*3);
		if(tank.getSide() == Sprite.PLAYER1)System.out.println("Damage Up for greenshroom");
		else System.out.println("Damage Up for redshroom");
		lol = tank;
		lol.setDamagePower(Sprite.ACTIVATED);
		timer.start();
	}
}
