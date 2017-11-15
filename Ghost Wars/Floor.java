import java.awt.Image;

public class Floor extends Block {
	private boolean occupied;

	public Floor(int xPos, int yPos, boolean passable, int type){		// instantiates a Floor block
		super(xPos, yPos, passable,type);								// the occupied variable in this instance determines 
		image = art.returnImage("Floor");								// if there is a player sprite on the Floor block	
        this.occupied = false;
	}

	public boolean getOccupied(){
		return this.occupied;
	}
	public void setOccupied(){
		this.occupied = true;
    }
}