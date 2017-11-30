package instantiation;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class GhostWarsClient extends JPanel implements Runnable, Constants{
	private JFrame frame;
	private ChatAccess access;
	private int x,y,x_speed,y_speed, prev_x, prev_y;
	private Thread t;
	private String player_name;
	private String server_ip;
	private boolean is_connected;
	private DatagramSocket socket;
	private String server_data;
	private BufferedImage offscreen;
	private BufferedImage offscreenMissile;
	private String position;

	public GhostWarsClient() {}

	public GhostWarsClient(String server_ip, String player_name, ChatAccess access){
		super();
		this.setOpaque(true);
		this.position = "Up";
		Random rand = new Random();

		this.x = rand.nextInt(14)+1 * (FRAME_WIDTH-40) / 15;
		this.y = rand.nextInt(14)+1 * (FRAME_HEIGHT-40) / 15;
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

		frame.setLayout(new BorderLayout());
		JPanel chatPanel = new ChatPanel(access);
		frame.add(chatPanel, BorderLayout.WEST);
		frame.add(this, BorderLayout.CENTER);
		this.setFocusable(true);
		frame.addKeyListener(new KeyHandler());
		frame.addMouseListener(new MouseAction());
		this.add(new JLabel("GG!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(223+FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);

		offscreen = (BufferedImage)this.createImage(FRAME_WIDTH, FRAME_HEIGHT);
		// offscreenMissile = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_RGF)
		this.repaint();
	}

	public void begin() {
		frame.requestFocus();
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
					int x = Integer.parseInt(server_data.split(" ")[2]);
					String color = getSpriteColor(x);
					Image img = gfx.returnImage(color + position);
					offscreen.getGraphics().drawImage(img, x+12, y, 40, 40, null);
					offscreen.getGraphics().drawString(player_name, x+12, y);
					is_connected = true;
					System.out.println("Connected to the server boi!");
					String name = server_data.split(" ")[1].trim();
					this.repaint();
				} else {
					System.out.println("Connecting..");
					send("CONNECT " + player_name);
				}
			} else {
				// frame.setVisible(true);
				if(server_data.startsWith("PLAYER")){
					offscreen.getGraphics().clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
					String[] objects = server_data.split(":");
					for(int i = 0; i < objects.length; i++){
						String[] object = objects[i].split(" ");
						if(object[0].startsWith("PLAYER")){
							String name = object[1].trim();
							int x = Integer.parseInt(object[2]);
							int y = Integer.parseInt(object[3]);
							String state = object[4].trim();
							String[] temp = state.split("\\.");
							String color = temp[0];
							String position = temp[1];
							Image img = gfx.returnImage(color + position);
							offscreen.getGraphics().drawImage(img, x, y, 40, 40, null);
							offscreen.getGraphics().drawString(name, x, y);
						} else if(object[0].startsWith("MISSILE")){
							String src = object[1].trim();
							int x = Integer.parseInt(object[2]);
							int y = Integer.parseInt(object[3]);
							// offscreen.getGraphics().drawString(src + " lol", x, y);
							offscreen.getGraphics().fillOval(x, y, 10, 10);

						}

					}
					this.repaint();
				}
			} 


		}
	}

	public void paintComponent(Graphics g){
    	super.paintComponent(g);
		g.drawImage(offscreen, 0, 0, null);

		// g.drawImage( offscreenMissile, 10,10, null);
	}

	public void update(Graphics g){
        paintComponent(g);
    }

    public String getSpriteColor(int x){
    	String color = "";
    	switch(x%4){
			case 0:
				color = "red";
				break;
			case 1:
				color = "blue";
				break;
			case 2:
				color = "orange";
				break;
			case 3:
				color = "pink";
				break;
		}
		return color;
    }

    class MouseAction implements MouseListener{
		public void mousePressed(MouseEvent e) {
			frame.requestFocus();
		}

	    public void mouseReleased(MouseEvent e) {}

	    public void mouseEntered(MouseEvent e) {}

	    public void mouseExited(MouseEvent e) {}

	    public void mouseClicked(MouseEvent e) {}
	}

	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
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
				case KeyEvent.VK_SPACE:
					send("MISSILE "
						+ player_name + " "
						+ x + " "
						+ y + " "
						+ position + "wards"
					);
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

		else {
            String server = args[0];
            String name = args[1];
            ChatAccess access = new ChatAccess();
            GhostWarsClient game = new GhostWarsClient(server, name, access);
           	

            try {
                access.InitSocket(server,PORT);
                game.begin();
            } catch (IOException ex) {
                System.out.println("Cannot connect to " + server + ":" + PORT);
                ex.printStackTrace();
                System.exit(0);
            }
        }
	}



}