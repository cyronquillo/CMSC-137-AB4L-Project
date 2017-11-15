import java.awt.Graphics;
import javax.swing.JPanel;

public class Steel extends Block {

	private static int HITS_TO_DESTROY = 2;
    private int life;

	public Steel(int xPos, int yPos, boolean passable,int type) {          // instantiates a Steel block with 90 life points
		super(xPos, yPos, passable,type);
		image = art.returnImage("Steel");
        this.life = 90;
	}
    
    public void collisionResponse(Sprite s){                               // outlines the protocol for the event wherein a player
        if(this.xPos == s.xPos+s.size) s.xPos--;                           // collides with the Steel block
        else if(this.xPos+this.size == s.xPos) s.xPos++;
        else if(this.yPos == s.yPos+s.size) s.yPos--;
        else s.yPos++;
    }

    public synchronized void collisionResponse(Missile m){                 // outlines the protocol for the event when the Steel block
        m.setVisible(false);                                               // is hit by a powered-up missile
        this.life -= m.getDamage();
        if(this.life == 60) this.image = art.returnImage("Steel1");
        if(this.life == 30) this.image = art.returnImage("Steel2");        // same process as for a regular Brick block
        if(this.life <= 0){
            this.image = art.returnImage("Floor");
            this.passable = true;
        }
    }
}