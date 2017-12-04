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
	public int rank;
	public ClientSprite(String name, int x, int y, String color, String position, Image img){
		this.name = name;
		this.x = x;
		this.y = y;
		this.state = state;
		this.color = color;
		this.position = position;
		this.img = img;
	}

	public ClientSprite(String name, String color, int rank){
		this.rank = rank;
		this.color = color;
		this.name = name;
	}
}