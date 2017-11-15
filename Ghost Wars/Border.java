import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Border extends Block {
	
	public Border(int xPos, int yPos, boolean passable, int type){         // instantiates a Border block by using the 
		super(xPos, yPos, passable,type);                                  // method from the its superclass Block and 
        image = art.returnImage("Wall");                                   // pulling the appropriate image from the ArtLibrary             
	}	
    
    public void collisionResponse(Sprite s){                               // outlines the protocol for when a ayer collides with the block
        if(this.xPos == s.xPos+s.size) s.xPos--;                           // the player gets pushed back to where it was before colliding with the block
        else if(this.xPos+this.size == s.xPos) s.xPos++;
        else if(this.yPos == s.yPos+s.size) s.yPos--;
        else s.yPos++;
    }
    public void collisionResponse(Missile m){                              // outlines the protocol for when a missile collides with the block
        m.setVisible(false);                                               // the missile disappears upon collision
    }
}