package instantiation;
import java.awt.Image;

public class ClientSprite{
	
	public String name;
	public int x;
	public int y;
	public String state;
	public String color;
	public String position;
	public Image img;
	public ClientSprite(String name, int x, int y, String color, String position, Image img){
		this.name = name;
		this.x = x;
		this.y = y;
		this.state = state;
		this.color = color;
		this.position = position;
		this.img = img;
	}
}