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
import java.util.HashMap;
import java.util.ArrayList;

public class GhostWarsClient extends JPanel implements Runnable, Constants {
	private JFrame frame;
	private ChatPanel chatPanel;
	private StatPanel statPanel;
	private int x_speed,y_speed, prev_x, prev_y,x,y;
	private Thread t;
	private String player_name;
	private String server_ip;
	private boolean is_connected;
	private DatagramSocket socket;
	private String server_data;
	private BufferedImage offscreen;
	private BufferedImage offscreenMissile;
	private String position;
	public ChatAccess access;
	public HashMap<String, ClientSprite> csHash;
	public HashMap<String, ClientMissile> missileArr;
	private int map[][];
	private KeyHandler kh;
	private Boolean is_dead;
	private int bullet_size;

	public GhostWarsClient(String server_ip, String player_name){
		super();
		this.is_dead = false;
		this.map = new int[MAP_HEIGHT][MAP_WIDTH];
        access = new ChatAccess();
        missileArr = new HashMap<String, ClientMissile>();
        csHash = new HashMap<String, ClientSprite>();
		this.setOpaque(true);
		this.position = "Up";
		this.bullet_size = BULLET_SIZE;
		Random rand = new Random();

		
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
		chatPanel = new ChatPanel(access);
		statPanel = new StatPanel();
		frame.add(chatPanel, BorderLayout.WEST);
		frame.add(this, BorderLayout.CENTER);
		frame.add(statPanel, BorderLayout.EAST);
		this.setFocusable(true);
		kh = new KeyHandler(this);
		frame.addKeyListener(kh);
		frame.addMouseListener(new MouseAction());
		// this.add(new JLabel("GG!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(CHAT_PANEL_WIDTH+FRAME_WIDTH+STAT_PANEL_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
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
				if(server_data.startsWith("MAP")){
					String map[] = server_data.split("\n");
					for(int i = 0; i < MAP_HEIGHT; i++){
						String row[] = map[i+1].split(" ");
						for(int j = 0; j < MAP_WIDTH; j++){
							this.map[i][j] =  Integer.parseInt(row[j]);
						}
					}
				} else if (server_data.startsWith("CONNECTED")){
					String object[] = server_data.split(" ");
					int col = Integer.parseInt(object[2]);
					this.x = Integer.parseInt(object[3]);
					this.y = Integer.parseInt(object[4]);
					String color = getSpriteColor(col);


					/*TESTING START*/
					if(color.equals("blue")){
						this.bullet_size = BIG_BULLET_SIZE;
					}
					/*TESTING END*/
					Image img = gfx.returnImage(color + position);
					ClientSprite spr = new ClientSprite(object[1].trim(), x, y, color, this.position, img);
					csHash.put(object[1].trim(),spr);
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
					String[] objects = server_data.split(":");
					for(int i = 0; i < objects.length; i++){
						String[] object = objects[i].split(" ");
						if(object[0].startsWith("PLAYER")){
							String name = object[1].trim();
							int x = Integer.parseInt(object[2]);
							int y = Integer.parseInt(object[3]);
							
							Image img = null;
							String color = "";
							String state = object[4].trim();
							Boolean is_dead = Boolean.parseBoolean(object[5]);
							if(is_dead){
								img = gfx.returnImage(state);
								color = "white";
								String position = "dead";
							} else{
								String[] temp = state.split("\\.");
								color = temp[0];
								String position = temp[1];
								img = gfx.returnImage(color + position);
							}
							if(this.player_name.equals(name)){
								this.x = x;
								this.y = y;
								this.is_dead = is_dead;
							}
							ClientSprite spr = new ClientSprite(name, x, y, color, position, img);
							csHash.put(name,spr);
						} else if(object[0].startsWith("MISSILE")){
							String src = object[1].trim();
							int x = Integer.parseInt(object[2]);
							int y = Integer.parseInt(object[3]);
							boolean is_collided = Boolean.parseBoolean(object[4]);
							int size = Integer.parseInt(object[5]);

							missileArr.put(src, new ClientMissile(src, x, y, size, is_collided));
							// offscreen.getGraphics().drawString(src + " lol", x, y);
							// offscreen.getGraphics().fillOval(x, y, 10, 10);
						}

					}
					this.repaint();
				}else if(server_data.startsWith("MISSILE")){
					System.out.println("posible ba to?");
				}
			} 


		}
	}

	public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	// g.fillOval(100,100,10,10);
		// g.drawImage(offscreen, 0, 0, this);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		Image img = null;
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				if(this.map[i][j] == TILE_FLOOR){
					img = gfx.returnImage(GROUND);
				} else if(this.map[i][j] == TILE_CORNER){
					img = gfx.returnImage(CORNER);
				} else if(this.map[i][j] == HORIZONTAL_BORDER){
					img = gfx.returnImage(HORIZONTAL);
				} else if(this.map[i][j] == VERTICAL_BORDER){
					img = gfx.returnImage(VERTICAL);
				}
				g.drawImage(img,j*40,i*40, BLOCK_SIZE, BLOCK_SIZE, null);
			}
		}
    	for(String key: csHash.keySet()){
    		ClientSprite csp = csHash.get(key);
		 	g.drawImage(csp.img, csp.x, csp.y, BLOCK_SIZE, BLOCK_SIZE, null);
			Color curr = g.getColor();
			g.setColor(this.getColor(csp.color));
			g.drawString(csp.name, csp.x, csp.y);
			g.setColor(curr);
    	}
    	for(String key: missileArr.keySet()){
    		if(missileArr.get(key).is_collided == NOT_COLLIDED){
    			Color curr = g.getColor();
    			String color = csHash.get(missileArr.get(key).src).color;
    			g.setColor(this.getColor(color));
				g.fillOval(missileArr.get(key).x, missileArr.get(key).y, missileArr.get(key).size, missileArr.get(key).size);
    			g.setColor(curr);
    			
    		}
    	}
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

    public Color getColor(String col){
    	Color color = null;
    	switch(col){
    		case "red":
    			color = Color.RED;
    			break;
    		case "blue":
    			color = Color.BLUE;
    			break;
    		case "orange":
    			color = Color.ORANGE;
    			break;
    		case "pink":
    			color = Color.PINK;
    			break;
    		case "white":
    			color = Color.WHITE;
    			break;
    	}
    	return color;
    }
    public int getXVal(){
    	return this.x;
    }
    public int getYVal(){
    	return this.y;
    }
    public int getBulletSize(){
    	return this.bullet_size;
    }
    public boolean isDead(){
    	return this.is_dead;
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
		GhostWarsClient src;
		boolean moveLeft = false;
		boolean moveRight = false;
		boolean moveDown = false;
		boolean moveUp = false;

		public KeyHandler(GhostWarsClient src){
			this.src = src;
		}

		public void keyPressed(KeyEvent ke){
			if(!src.isDead()){
				int x = src.getXVal();
				int y = src.getYVal();
				prev_x = x;
				prev_y = y;

				switch(ke.getKeyCode()){
					case KeyEvent.VK_DOWN:
						y += y_speed;
						position = "Down";
						moveDown = true;
						break;
					case KeyEvent.VK_UP:
						y -= y_speed;
						position = "Up";
						moveUp = true;
						break;
					case KeyEvent.VK_LEFT:
						x -= x_speed;
						position = "Left";
						moveLeft = true;
						break;
					case KeyEvent.VK_RIGHT:
						x += x_speed;
						position = "Right";					
						moveRight = true;
						break;
					case KeyEvent.VK_SPACE:
						if (moveDown) {
							y += y_speed;
							position = "Down";
						}
						else if (moveUp) {
							y -= y_speed;
							position = "Up";
						}
						else if (moveLeft) {
							x -= x_speed;
							position = "Left";
						}
						else if (moveRight) {
							x += x_speed;
							position = "Right";
						}

						send("MISSILE "
							+ player_name + " "
							+ x + " "
							+ y + " "
							+ position + "wards "
							+ src.getBulletSize()
						);
						break;
					case KeyEvent.VK_ENTER:
						chatPanel.setFocus();
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
			} else{
				switch(ke.getKeyCode()){
					case KeyEvent.VK_ENTER:
						chatPanel.setFocus();
						break;	
				}
			}
			
		}

		public void keyReleased(KeyEvent ke) {
			switch(ke.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					moveRight = false;
					break;
				case KeyEvent.VK_LEFT:
					moveLeft = false;
					break;
				case KeyEvent.VK_UP:
					moveUp = false;
					break;
				case KeyEvent.VK_DOWN:
					moveDown = false;
					break;
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
			try{
            	GhostWarsClient game = new GhostWarsClient(server, name);
            	game.access.InitSocket(server,PORT, name);
            	game.begin();           
	   		} catch (IOException ex) {
	            System.out.println("Cannot connect to " + server + ":" + PORT);
	            ex.printStackTrace();
	            System.exit(0);
	        }
	    }
    }
}


