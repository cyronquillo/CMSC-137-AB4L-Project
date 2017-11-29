package instantiation;
import java.awt.Rectangle;

public abstract class RenderableObject{
	//Attributes
	protected int xPos;
	protected int yPos;
	protected Rectangle bound;
	protected int size;													
	protected boolean passable;

	//Acts as superclass for renderable objects
	public RenderableObject(int x, int y, boolean passable, int size){
		this.xPos = x;
		this.yPos = y;
		this.passable = passable;										
		this.size = size;
		//instantiates the renderableobject. this class is not directly used, only as a superclass
		this.bound = new Rectangle(x-1, y-1, size+2, size+2);
	}

	//Checks if other renderable objects intersect with this renderable object
	public boolean isTouched(RenderableObject object){			
		if(this.getBounds(0,0).intersects(object.getBounds(0,0))){
			return true;
		}
		else return false;
	}

	//Gets the bound of this object
	public Rectangle getBounds(int x, int y){
		return new Rectangle(xPos+x, yPos+y, this.size, this.size);
	}

	//Returns if this objeect is passable or not
	public boolean getPassable(){
		return this.passable;
	}

	/*
	 * Getters and Setters
	 */
	
	//Gets the x position of hits object
	public int getXPos(){
		return this.xPos;
	}

	//Gets the y position of hits object
	public int getYPos(){
		return this.yPos;
	}
}