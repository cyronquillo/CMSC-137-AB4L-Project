import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Speed extends PowerUps{
	private static Sprite lol;
	private static int seconds;
	private static final int SPAN = 15000;
	private static ActionListener aL = new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	if(Sprite.paused == false){
                if (getSeconds() > 10000 && getSeconds() < SPAN) {
                    if(getSeconds() % 1000 == 0) lol.setSpeedPower(Sprite.WEARING_OFF);
                    else lol.setSpeedPower(Sprite.ACTIVATED);
                    System.out.println("WEARING OFF");
                }
                else if(getSeconds() >= SPAN){
                	lol.setSpeedPower(Sprite.STOPPED);
                	timer.stop();
                	System.out.println("STOPPED");
                	lol.setSpeed(Sprite.ORIGINAL_DAMAGE);
                }
               	else{
               		lol.setSpeedPower(Sprite.ACTIVATED);
               		System.out.println("ACTIVATED");
               	}
               	setSeconds(timer.getDelay());
               	System.out.println(getSeconds());
        	}

            
        }
    };
	private static Timer timer = new Timer(500,aL);;

	public Speed(TileMap tm, Block[][] blocks){
		super(tm,blocks);
		this.image = art.returnImage("BoltPower");
		System.out.println("Speed Up at: " + (xPos-5)/Block.BLOCK_SIZE + "," + (yPos-5)/Block.BLOCK_SIZE);
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
		tank.setSpeed(tank.getSpeed()/2);
		if(tank.getSide() == Sprite.PLAYER1)System.out.println("Speed Up for greenshroom");
		else System.out.println("Speed Up for redshroom");
		lol = tank;
		lol.setSpeedPower(Sprite.ACTIVATED);
		timer.start();
	}
}