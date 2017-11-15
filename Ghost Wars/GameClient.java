import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private GamePanel game;

	public GameClient(GamePanel game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		}
		catch(SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];			// send to server
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				socket.receive(packet);
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
				System.exit(1);
			}
			String message =new String(packet.getData());
			System.out.println("Server > " + new String(packet.getData()));
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 3000);
		try{
			socket.send(packet);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}