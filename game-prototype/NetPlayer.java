import java.net.InetAddress;
import java.awt.Image;
import java.util.ArrayList;

// network player encapsulation
public class NetPlayer implements Constants{
	private InetAddress address; // ip of player
	private int port; // port of player
	private String name; // name of player
	private int x,y; // position of players
	private int nth;
	private String state;
	private String color;
	// public ArrayList<Missile> ammo = new ArrayList<Missile>();


	/**
	 * Constructor
	 * @param name
	 * @param address
	 * @param port
	 */
	public NetPlayer(String name,InetAddress address, int port, int playerCount){
		this.address = address;
		this.port = port;
		this.name = name;
		switch(playerCount%4){
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
		this.state = this.color + "Down";
	}
/*-----------------GETTERS----------------*/
	public InetAddress getAddress(){ 
		return address;
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
/*----------------------------------------*/


/*-----------------SETTERS----------------*/
	public void setX(int x){
		this.x=x;
	}

	public void setY(int y){
		this.y=y;		
	}

	public void setState(String state){
		this.state = state;
		// image = gfx.returnImage(state);
	}
/*
	public void removeMissile(Missile m){
		ammo.remove(m);
	}*/
/*----------------------------------------*/

	

	/**
	 * String representation. used for transfer over the network
	 */
	public String toString(){
		String retval="";
		retval+="PLAYER ";
		retval+=name+" ";
		retval+=x+" ";
		retval+=y+" ";
		retval+=state/*+" "*/;
		// retval+=ammo;
		return retval;
	}	
}
