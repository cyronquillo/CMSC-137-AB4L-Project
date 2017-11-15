import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;												//import statements
import java.util.HashMap;
import javax.swing.JPanel;
import java.awt.Graphics;

public abstract class RenderableObject{
	protected int xPos;
	protected int yPos;
	protected Rectangle bound;
	protected int size;														//attributes
	protected boolean passable;
	protected Image image;
	protected static ArtLibrary art = new ArtLibrary();

	//private static HashMap<String, ArrayList<RenderableObject>> objectMap = new HashMap<String, ArrayList<RenderableObject>>();

	protected int dx;
	protected int dy;

	public RenderableObject(int x, int y, boolean passable, int size){
		this.xPos = x;
		this.yPos = y;
		this.passable = passable;										//instantiates the renderableobject. this class is not directly used, only as a superclass
		this.size = size;
		this.bound = new Rectangle(x-1, y-1, size+2, size+2);
	}

	public void move(int x, int y){
		this.xPos += x;															//moves the renderableobject
		this.yPos += y;
	}

	public boolean isTouched(RenderableObject object){				//checks if the renderable object intersects any other renderable objects. used in collision response
		if(this.getBounds(this.getDx(), this.getDy()).intersects(object.getBounds(0,0))){
			return true;
		}
		else return false;
	}

/****GETTERS AND SETTERS****/

	public int getXPos(){
		return this.xPos;
	}
	public void setDx(int x){
		this.dx = x;
	}
	public void setDy(int y){
		this.dy = y;
	}
	public int getYPos(){
		return this.yPos;
	}

	public Rectangle getBounds(int x, int y){
		return new Rectangle(xPos+x, yPos+y, this.size, this.size);
	}

	public boolean getPassable(){
		return this.passable;
	}

	public Image getImage(){
		return this.image;
	}
	public int getDx(){
		return this.dx;
	}

	public int getDy(){
		return this.dy;
	}

	/****END OF GETTERS AND SETTERS****/

    public void collisionResponse(Sprite s){}							//a renderable object responds to collision but is left for the subclasses to implement. not abstract because of an error in the terminal
    public void collisionResponse(Missile m){}

	public abstract void paint(Graphics g, JPanel panel);		//a renderable object can paint its appearance. left for the subclasses to implement.

}
