import java.awt.Graphics;
import javax.swing.JPanel;

public class Block extends RenderableObject {
	
	public static final int FLOOR = 1;						// classifies the blocks by number
	public static final int BRICK = 2;
	public static final int GRASS = 3;
	public static final int STEEL = 4;
	public static final int BORDER = 5;
	public static final int BLOCK_SIZE = 40;

	protected int type;										// determines what kind of block gets instantiated

	public Block(int xPos, int yPos,boolean passable, int type){		// instantiates a block
		super(xPos, yPos, passable, BLOCK_SIZE);
		this.type = type;
	}

	public int getType(){
		return this.type;
	}

	public void paint(Graphics g, JPanel panel){						// draws the image of the block
		g.drawImage(image,xPos,yPos,BLOCK_SIZE,BLOCK_SIZE,panel);
	}
}






