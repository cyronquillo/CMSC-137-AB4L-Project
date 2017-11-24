package instantiation;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GhostWarsClient extends JPanel implements Runnable, Constants{
	private JFrame frame;
	private int x,y,x_speed,y_speed, prev_x, prev_y;
	private Thread t;
	private String player_name;
	private String server_ip;
	private boolean is_connected;
	private DatagramSocket socket;
	private String server_data;
	private BufferedImage offscreen;

	public GhostWarsClient(String server_ip, String player_name){
		this.x = 10;
		this.y = 10;
		this.x_speed = 5;
		this.y_speed = 5;


		this.is_connected = false;
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.frame = new JFrame(APP_NAME + ":" + player_name);
		
		try{
			socket = new DatagramSocket();
			socket.setSoTimeout(100);
		} catch(Exception e){}

		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);

		offscreen=(BufferedImage)this.createImage(FRAME_WIDTH, FRAME_HEIGHT);

		frame.addKeyListener(new KeyHandler());

		t = new Thread(this);
		t.start();
	}

	public void send(String message){
		try{
			byte[] buffer = message.getBytes();
			InetAddress address = InetAddress.getByName(server_ip);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
			socket.send(packet);
		} catch(Exception e){}
	}

	private void printDataString(String data){
		if(!data.equals("")){
			System.out.println(data);
		}
	}

	public void run(){
		while(true){
			try { 
				Thread.sleep(0);
			} catch(Exception e){}

			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try{
				socket.receive(packet);
			} catch(Exception e){}
			
			server_data = new String(buffer);
			server_data = server_data.trim();
			// printDataString(server_data);

			if (!is_connected){
				if (server_data.startsWith("CONNECTED")){
					is_connected = true;
					System.out.println("Connected to the server boi!");
					String name = server_data.split(" ")[1].trim();
				} else {
					System.out.println("Connecting..");
					send("CONNECT " + player_name);
				}
			} else {
				// frame.setVisible(true);
				if(server_data.startsWith("PLAYER")){
					offscreen.getGraphics().clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
					String[] sprites = server_data.split(":");
					for(int i = 0; i < sprites.length; i++){
						String[] sprite = sprites[i].split(" ");
						String name = sprite[1].trim();
						int x = Integer.parseInt(sprite[2]);
						int y = Integer.parseInt(sprite[3]);
						String state = sprite[4].trim();
						String[] temp = state.split("\\.");
						String color = temp[0];
						String position = temp[1];
						Image img = gfx.returnImage(color + position);
						offscreen.getGraphics().drawImage(img, x, y, 40, 40, null);
						offscreen.getGraphics().drawString(name, x, y);
					}
					frame.repaint();
				}
			} 


		}
	}

	public void paintComponent(Graphics g){
		g.drawImage(offscreen, 0, 0, null);
	}

	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			String position = "";
			prev_x = x;
			prev_y = y;
			switch(ke.getKeyCode()){
				case KeyEvent.VK_DOWN:
					y += y_speed;
					position = "Down";
					break;
				case KeyEvent.VK_UP:
					y -= y_speed;
					position = "Up";
					break;
				case KeyEvent.VK_LEFT:
					x -= x_speed;
					position = "Left";
					break;
				case KeyEvent.VK_RIGHT:
					x += x_speed;
					position = "Right";					
					break;
			}
			if (prev_x != x || prev_y != y){
				send("PLAYER " 
					+ player_name + " " 
					+ x + " " 
					+ y + " " 
					+ position
				);
			}
		}
	}

	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage: java GhostWarsClient <server IP> <player name>");
			System.exit(1);
		}

		new GhostWarsClient(args[0],args[1]);
		new ClientChat(args[0], args[1]);
	}



}