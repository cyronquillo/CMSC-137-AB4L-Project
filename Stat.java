package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.File;

public class Stat extends JPanel implements Constants{
	private String name;
	private int life;
	private int health;
	private int bullet_size;
	private int speed;
	private String color;

	public Stat(String name, int life, int health, int bullet_size, int speed, String color){
		super();
		this.name = name;
		this.life = life;
		this.health = health;
		this.bullet_size = bullet_size;
		this.speed = speed;
		this.color = color;

		buildGUI();
		this.repaint();
	}

	public void update(Stat panel_new){
		this.name = panel_new.name;
		this.life = panel_new.life;
		this.health = panel_new.health;
		this.bullet_size = panel_new.bullet_size;
		this.speed = panel_new.speed;
		this.color = panel_new.color;

		this.repaint();
	}
	private void buildGUI() {
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH, 250));
	}

	public String getSpriteName(){
		return this.name;
	}

	public void paintComponent(Graphics g){
		g.drawRect(0,0,250, 250);


		try{
            g.drawImage(ImageIO.read(new File("gfx/panels/stat_panel_" + color + ".jpg")),0,0, 250, 250, null);
        }catch(Exception e){}



       	g.drawString("Name: " + this.name, 20, 40);
        g.drawString("Health: ", 20, 85);
        
        g.fillRoundRect(75, 74, INIT_HEALTH, 14, 10, 10);
        Color col = getHealthColor(this.health);
        Color prev = g.getColor();
        g.setColor(col);
        g.fillRoundRect(75, 74, this.health, 14, 10, 10);
        g.setColor(prev);
        g.drawString("Life: ", 20, 130);
        

        Image img = gfx.returnImage(this.color + "Down");
        for(int i = 0; i < this.life; i++){
            g.drawImage(img, 30 + (20) * (i+1),115, 15, 15, null);
        }

        String speed = "1x";
        switch(this.speed){
            case FAST_SPEED:
                speed = "2x";
                break;
            case FASTER_SPEED:
                speed = "2.3x";
                break;
            case FASTEST_SPEED:
                speed = "2.5x";
                break;
        }

        g.drawString("Speed: " + speed, 20, 180);
        int damage = 40;
        if(this.bullet_size == BULLET_SIZE){
            damage = 20;
        }
        g.drawString("Damage: " + damage, 20, 230);
	}

 	public Color getHealthColor(int health){
        if(health <= 20){
            return Color.RED;
        } else if(health <= 60){
            return Color.YELLOW;
        } else{
            return Color.GREEN;
        }
    }








}