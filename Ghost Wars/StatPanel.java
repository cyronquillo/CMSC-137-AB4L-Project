import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Graphics;													//import statements
import java.awt.Graphics2D;
import java.awt.Color;

public class StatPanel extends JPanel implements Runnable{
	public static final int WIDTH = 250;
	public static final int HEIGHT = 600;						//attributes for the statpanel
	private static ArtLibrary art = new ArtLibrary();
	private boolean side;


	public StatPanel(boolean side){
		this.side = side;															//identifies the side the stat panel is at
	}

	public void paintComponent(Graphics g){
		try{
	        super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			Graphics2D g2d1 = (Graphics2D)g;

			g2d.setColor(Color.GREEN);
			if(this.side == Sprite.PLAYER1){
	            g.drawImage(art.returnImage("BG"), 0, 0, this);				//prints the statpanel's background for player1
	            g2d1.setColor(Color.BLACK);
				g2d1.drawRoundRect(108, 268, 103, 17, 10, 10);

	            if(GamePanel.redPlayer.isSpedUp() == Sprite.WEARING_OFF || GamePanel.redPlayer.isSpedUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("BoltPowerBW"), 108, 225, 30, 30, this);
	            } else{
	            	g.drawImage(art.returnImage("BoltPower"), 108, 225, 30, 30, this);
	            }
	            if(GamePanel.redPlayer.isDamageUp() == Sprite.WEARING_OFF || GamePanel.redPlayer.isDamageUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("StarPowerBW"), 183, 225, 30, 30, this);
	            } else{																								//draws the powerup status for the red player
	            	g.drawImage(art.returnImage("StarPower"), 183, 225, 30, 30, this);
	            }
	            if(GamePanel.redPlayer.isHealthUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("HeartPowerBW"), 145, 225, 30, 30, this);
	            } else{
	            	g.drawImage(art.returnImage("HeartPower"), 145, 225, 30, 30, this);
	            }
				g2d.setColor(setColor(GamePanel.redPlayer));						//prints the player's current health
				g2d.fillRoundRect(110, 270, this.getStatLife(GamePanel.redPlayer), 14,10,10);
			}
	        else{
	            g.drawImage(art.returnImage("BG2"), 0, 0, this);		//prints the statpanel's background for player 2
	            g2d1.setColor(Color.BLACK);
	            g2d1.drawRoundRect(108, 268, 103, 17, 10, 10);
	            if(GamePanel.greenPlayer.isSpedUp() == Sprite.WEARING_OFF || GamePanel.greenPlayer.isSpedUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("BoltPowerBW"), 108, 225, 30, 30, this);
	            } else{
	            	g.drawImage(art.returnImage("BoltPower"), 108, 225, 30, 30, this);
	            }																									//draws the powerups status for the green player
	            if(GamePanel.greenPlayer.isDamageUp() == Sprite.WEARING_OFF || GamePanel.greenPlayer.isDamageUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("StarPowerBW"), 183, 225, 30, 30, this);
	            } else{
	            	g.drawImage(art.returnImage("StarPower"), 183, 225, 30, 30, this);
	            }
	            if(GamePanel.greenPlayer.isHealthUp() == Sprite.STOPPED){
	            	g.drawImage(art.returnImage("HeartPowerBW"), 145, 225, 30, 30, this);
	            } else{
	            	g.drawImage(art.returnImage("HeartPower"), 145, 225, 30, 30, this);
	            }
				g2d.setColor(setColor(GamePanel.greenPlayer));						//prints the player's current health
				g2d.fillRoundRect(110, 270, this.getStatLife(GamePanel.greenPlayer), 14, 10, 10);
			}
			if(Sprite.paused == true){
				Graphics2D g2dPause = (Graphics2D)g;
				g2dPause.setColor(new Color(0,0,0,160));								//if the game is paused it gets painterd over by a silghtly transparent rectangle
				g2dPause.fillRect(0,0,250,600);
			}
		} catch(Exception e){}
	}

	public int getStatLife(Sprite tank){													//gets the player's health
		return tank.getLife();
	}

	public Color setColor(Sprite tank){
		Color color = Color.GREEN;																	//sets the color of the health meter.

		if(this.getStatLife(tank) == 100 && this.getStatLife(tank) >= 70){
			color = Color.GREEN;
		}
		else if (this.getStatLife(tank) <= 60 && this.getStatLife(tank) >= 40){
			color = Color.ORANGE;
		}
		else if (this.getStatLife(tank) <= 30 && this.getStatLife(tank) >= 0){
			color = Color.RED;
		}

		return color;
	}

	public void run(){
		while(true){
			this.repaint();																					//repaints with no delay
		}
	}
}
