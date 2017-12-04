package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

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
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH-20, STAT_PANEL_WIDTH-20));
	}

	public String getSpriteName(){
		return this.name;
	}

	public void paintComponent(Graphics g){
		g.drawRect(0,0,STAT_PANEL_WIDTH, STAT_PANEL_WIDTH);

		/*try{
            g.drawImage(ImageIO.read(new File("gfx/panels/chat_stat_panel_" + color + ".jpg")),0,0, CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT, null);
        }catch(Exception e){}*/



        g.drawString("Name: " + this.name, 20, 40);
        g.drawString("Health: ", 20, 65);
        g.fillRoundRect(110, 40, this.health, 14, 10, 10);
        g.drawString("Life: ", 140, 65);
        
        Image img = gfx.returnImage(this.color + "Down");
        for(int i = 0; i < this.life; i++){
            g.drawImage(img, 150 + (20) * (i+1),50, 15, 15, null);
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

        g.drawString("Speed: " + speed, 20, 80);
        int damage = 40;
        if(bullet_size == BULLET_SIZE){
            damage = 20;
        }
        g.drawString("Damage: " + damage, 140, 80);
	}








}