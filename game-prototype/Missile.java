import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Missile implements Runnable,Constants{

	private DatagramSocket socket= null;

	private int initX;
	private int initY;
	private int prevX;
	private int prevY;
	private int moveX;
	private int moveY;
	private int speed;
	private String server;
	private String name;
	public static final int REMOVE_MISSILE = 1;
	public static final int MAINTAIN_MISSILE = 0;
	public Missile(String name, int posx, int posy, String facing, String server){
		try{
			socket = new DatagramSocket();
		}catch(Exception e){}
		this.server = server;
		this.initX = posx;
		this.initY = posy;
		this.speed = 5;
		this.moveX = 1;
		this.moveY = 1;

		this.prevX = this.initX;
		this.prevY = this.initY;
		if(facing.contains("Down")){
			this.moveY *= 1;
			this.moveX = 0;
		} else if(facing.contains("Left")){
			this.moveX *= -1;
			this.moveY = 0;
		} else if(facing.contains("Right")){
			this.moveX *= 1;
			this.moveY = 0;
		} else if(facing.contains("Up")){
			this.moveY *= -1;
			this.moveX = 0;
		}
	}


	public void send(String msg){
		try{
			byte[] buff = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buff, buff.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
	}


	public void setX(int x){
		this.prevX = this.initX;
		this.initX=x;
	}

	public void setY(int y){
		this.prevY = this.initY;
		this.initY=y;		
	}

	public int getX(){
		return this.initX;
	}


	public int getY(){
		return this.initY;
	}

	public void run(){
		while((initX > 0 && initX < FRAME_WIDTH) || 
			initY > 0 && initY < FRAME_HEIGHT){
			this.initX += this.moveX;
			this.initY += this.moveY;
			send("MISSILE " + name + " " + this.initX + " " + this.initY + " " + MAINTAIN_MISSILE + " " + this.prevX + " " + this.prevY);
		}
		send("MISSILE " + name + " " + this.initX + " " + this.initY + " " + REMOVE_MISSILE + " " + this.prevX + " " + this.prevY);
	}

}