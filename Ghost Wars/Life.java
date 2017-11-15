import javax.swing.Timer;
import java.awt.event.ActionListener;											//import statements
import java.awt.event.ActionEvent;

public class Life extends PowerUps{
	public static final int SPAN = 5000;
	Timer timer;
	private int seconds;																		//attributes
	Sprite lol;
	ActionListener aL;

	public Life(TileMap tm, Block[][] blocks){
		super(tm,blocks);
		this.image = art.returnImage("HeartPower");						//constructor for the life powerup. uses the powerup superclass for construction of the instance
		System.out.println("Life Up at: " + (xPos-5)/Block.BLOCK_SIZE+ "," + (yPos-5)/Block.BLOCK_SIZE);		//shows the powerup's position in the terminal
		seconds = 0;
		aL = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	if(Sprite.paused == false){
	                if(getSeconds() == SPAN){
	                	lol.setHealthPower(Sprite.STOPPED);		//sets the action performed when the life powerup's timer is at the specified delay interval
	                	timer.stop();
	                	//System.out.println("STOPPED");				//stops the blinking for the statpanel
	                }
	                else{
	                    if(getSeconds() % 1000 == 0) lol.setHealthPower(Sprite.STOPPED);
	                    else lol.setHealthPower(Sprite.ACTIVATED);		//activates the ability of the powerup. makes the statpanel equivalent blink ;)
	                    //System.out.println("WEARING OFF");
	                }
	               	setSeconds(timer.getDelay());
	               	//System.out.println(getSeconds());				//sets the seconds attribute of the powerup
            	}


            }
        };
        timer = new Timer(500,aL);											//instantiates the timer using the actionListener
        this.type = PowerUps.LIFE;											//makes the type into the powerup's LIFE constant

	}

	public int getSeconds(){
		return this.seconds;															//returns the powerup's seconds
	}
	public void setSeconds( int num){										//increments the powerup's seconds
		this.seconds += num;
	}
	public void collisionResponse(Sprite tank){					//this shows how the powerup responds to the collision with a sprite instance
		tank.setLife(20);
		if(tank.getSide() == Sprite.PLAYER1)System.out.println("Life Up for greenshroom");
		else System.out.println("Life Up for redshroom");
		lol = tank;
		lol.setHealthPower(Sprite.ACTIVATED);
		timer.start();

	}
}
