public class Grass extends Block {
	
	public Grass(int xPos, int yPos, boolean passable, int type) {			// instantiates a Grass block that a player can hide behind
		super(xPos, yPos, passable, type);
        image = art.returnImage("Grass");
	}

}