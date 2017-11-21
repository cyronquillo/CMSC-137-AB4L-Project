package instantiation;
import java.net.InetAddress;
public class Sprite{
	private InetAddress ip;
	private int port;
	private String name;
	private int x,y;
	private int ith;
	private String color;
	private String state;
	private String position;
	public Sprite(String name, InetAddress ip, int port, int ith){
		this.ip = ip;
		this.name = name;
		this.port = port;
		this.ith = ith;
		switch(ith%4){
			case 0:
				this.color = "red";
				break;
			case 1:
				this.color = "blue";
				break;
			case 2:
				this.color = "orange";
				break;
			case 3:
				this.color = "pink";
				break;
		}
		this.position = "Up"; // default
		this.state = this.color + "." + this.position;
	}

	public InetAddress getIP(){
		return ip;
	}

	public int getPort(){
		return port;
	}

	public String getName(){
		return name;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public String getState(){
		return state;
	}

	public String getColor(){
		return color;
	}
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public void setState(String state){
		this.state = state;
		String[] temp = state.split("\\.");
		this.position = temp[1];
	}
	public String toString(){
		String return_string = "PLAYER " + name + " "
							 + x + " "
							 + y + " "
							 + state;
		return return_string;
	}
}