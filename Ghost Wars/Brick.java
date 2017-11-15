import java.awt.Graphics;
import javax.swing.JPanel;

public class Brick extends Block {
    private int life;
    private boolean occupied;

	public Brick(int xPos, int yPos, boolean passable,int type) {              // instantiates a Brick block using the same method as 
		super(xPos, yPos, passable,type);                                      // a Border block but with 30 life points since Brick blocks
        this.image = art.returnImage("Brick");                                 // are breakable         
        life = 30;
	}

    public boolean getOccupied(){
        return this.occupied;
    }
    public void setOccupied(){                                                 // sets the value of occupied to true if the Brick block
        this.occupied = true;                                                  // contains a powerup
    }

    
    public void collisionResponse(Sprite s){                                   // outlines protocol if in the event a player collides with the brick
        if(this.xPos == s.xPos+s.size) s.xPos--;
        else if(this.xPos+this.size == s.xPos) s.xPos++;
        else if(this.yPos == s.yPos+s.size) s.yPos--;
        else s.yPos++;  
    }
    public synchronized void collisionResponse(Missile m){                     // outlines protocol for when the Brick block is hit by missiles
        m.setVisible(false);                                                   // the missiles disappear upon hitting and the Brick block
        this.life -= m.getDamage();                                            // image is changed according to how many lilfe points it has left
        if(this.life == 20) this.image = art.returnImage("Brick1");            // when Brick block is finally destroyed, its image is replaced
        if(this.life == 10) this.image = art.returnImage("Brick2");            // with that of a floor block and is declared "passable" 
        if(this.life <= 0){                                                    // i.e. the player can pass through the area
            this.image = art.returnImage("Floor");
            this.passable = true;
        }
    }
}