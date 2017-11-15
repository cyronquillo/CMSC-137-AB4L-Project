import java.net.InetAddress;

public class Player {
	private InetAddress address;
	private int port;
	private String name;
	private int x, y;

	public Player(String name, InetAddress address, int port) {
		this.address = address;
		this.port = port;
		this.name = name;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		String retval = "";
		retval+="PLAYER ";
		retval+=name+" ";
		retval+=x+" ";
		retval+=y;
		return retval;
	}
}