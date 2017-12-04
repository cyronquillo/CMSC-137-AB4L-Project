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
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Font;

public class GhostWarsClient extends JPanel implements Runnable, Constants {
	public JFrame frame;
	private ChatPanel chatPanel;
	private StatPanel statPanel;
	private JPanel cardPanel;
	private JPanel gamePanel;
	private WinPanel winPanel;
	private int prev_x, prev_y,x,y;
	private Thread t;
	private String server_ip;
	private boolean is_connected;
	private DatagramSocket socket;
	private String server_data;
	private String position;
	public ChatAccess access;
	public HashMap<String, ClientSprite> csHash;
	public HashMap<String, ClientMissile> missileArr;
	private int map[][];
	private KeyHandler kh;
	private Boolean is_dead;
	private boolean is_paused;
	private boolean is_game_over;
	private String pauser;
	private boolean is_waiting;
	private Container c;
	private String final_data[];
	
	// essential sprite details
	public String player_name;
	private String color;
	private int bullet_size;
	private int life;
	private int health;
	private int speed;


	
	public GhostWarsClient(String server_ip, String player_name){
		super();
		is_game_over = false;
		is_waiting = true;
		this.is_dead = false;
		this.map = new int[MAP_HEIGHT][MAP_WIDTH];
        access = new ChatAccess();
        missileArr = new HashMap<String, ClientMissile>();
        csHash = new HashMap<String, ClientSprite>();
		this.setOpaque(true);
		this.position = "Up";
		this.bullet_size = BULLET_SIZE;
		Random rand = new Random();

		this.health = INIT_HEALTH;
		this.life = INIT_LIFE;
		this.speed = NORMAL_SPEED;


		this.is_connected = false;
		this.server_ip = server_ip;
		this.player_name = player_name;
		this.frame = new JFrame(APP_NAME + ":" + player_name);
		try{
			socket = new DatagramSocket();
			socket.setSoTimeout(100);
		} catch(Exception e){}
		winPanel = new WinPanel(new JLabel("Congrats"), this);
		gamePanel = new JPanel(new BorderLayout());
		cardPanel = new JPanel(new CardLayout());
		chatPanel = new ChatPanel(access, this);
		chatPanel.setPreferredSize(new Dimension(CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT));
		statPanel = new StatPanel();
		gamePanel.add(chatPanel, BorderLayout.WEST);
		gamePanel.add(this, BorderLayout.CENTER);
		gamePanel.add(statPanel, BorderLayout.EAST);

		cardPanel.add(gamePanel, GAME_PANEL);
		cardPanel.add(winPanel, WIN_PANEL);
		this.setFocusable(true);


		kh = new KeyHandler(this);
		frame.addKeyListener(kh);
		frame.addMouseListener(new MouseAction());
		// this.add(new JLabel("GG!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(CHAT_PANEL_WIDTH+FRAME_WIDTH+STAT_PANEL_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		c = frame.getContentPane();
		c.add(cardPanel);
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
	private String[] getFinalData(){
		return this.final_data;
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
					Image img = gfx.returnImage(color + position);
					ClientSprite spr = new ClientSprite(object[1].trim(), x, y, color, this.position, img);
					csHash.put(object[1].trim(),spr);
					is_connected = true;
					System.out.println("Connected to the server boi!");
					String name = server_data.split(" ")[1].trim();
					System.out.println("init once");
					if(!player_name.equals(name)){
						statPanel.updatePanel(new Stat(name, INIT_LIFE, INIT_HEALTH, BULLET_SIZE, NORMAL_SPEED, color));
					}
					this.repaint();
					chatPanel.repaint();
					statPanel.repaint();
				} else {
					System.out.println("Connecting..");
					send("CONNECT " + player_name);
				}
			} else {
				if(server_data.startsWith("START")){
					is_waiting = false;
					sfx.returnAudio("Welcome").play(false);
					statPanel.repaint();	
					this.repaint();
					chatPanel.repaint();
				}
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
							int size = Integer.parseInt(object[6]);
							int life = Integer.parseInt(object[7]);
							int health = Integer.parseInt(object[8]);
							int speed = Integer.parseInt(object[9]);
							String original_color = state.split("\\.")[0]; 
							if(is_dead){
								img = gfx.returnImage("SpriteRIP");
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
								this.bullet_size = size;
								this.life = life;
								this.health = health;
								this.speed = speed;
								this.color = color;
								chatPanel.repaint();
							} else{
								statPanel.updatePanel(new Stat(name, life, health, size, speed, original_color));
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
						}

					}
					this.repaint();
				} else if(server_data.startsWith("MAP")){
					String map[] = server_data.split("\n");
					for(int i = 0; i < MAP_HEIGHT; i++){
						String row[] = map[i+1].split(" ");
						for(int j = 0; j < MAP_WIDTH; j++){
							this.map[i][j] =  Integer.parseInt(row[j]);
						}
					}
				} else if(server_data.startsWith("AUDIO")) {
					String aud[] = server_data.split(" ");
					if(aud[1].equals("Resurrect") || aud[1].equals("Lost") ){
						if(this.player_name.equals(aud[2])){
							System.out.println("AUDIO RCVD:" + aud[1]);
							sfx.returnAudio(aud[1].trim()).play(false);	
						}
					}else{
						if(aud[1].equals("Victory") && is_dead == true){
							continue;
						}
						System.out.println("AUDIO RCVD:" + aud[1]);
						sfx.returnAudio(aud[1].trim()).play(false);
					}
				} else if(server_data.startsWith("PAUSE")){
					System.out.println("CLIENT RCVD: " + server_data);
					String pause[] = server_data.split(" ");
					this.is_paused = Boolean.parseBoolean(pause[1]);
					if(this.is_paused == PAUSED){
						PAUSE_AUDIO.play(true);
						this.pauser = pause[2];
					} else{
						PAUSE_AUDIO.stop();
					}
					this.repaint();
				} else if(server_data.startsWith("GAMEOVER")){
					is_game_over = true;
					System.out.println(server_data);
					this.repaint();
				}

			} 
		}
		/*SWITCH TO RESULTS PANEL*/
	}

	public void paintComponent(Graphics g){
    	super.paintComponent(g);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		Image img = null;
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				int inc = 0; 
				if(this.map[i][j] == TILE_FLOOR){
					img = gfx.returnImage(GROUND);
				} else if(this.map[i][j] == TILE_CORNER){
					img = gfx.returnImage(CORNER);
				} else if(this.map[i][j] == HORIZONTAL_BORDER){
					img = gfx.returnImage(HORIZONTAL);
				} else if(this.map[i][j] == HORIZONTAL_LEFT_BORDER){
					img = gfx.returnImage(HORIZONTAL_LEFT);
				} else if(this.map[i][j] == HORIZONTAL_RIGHT_BORDER){
					img = gfx.returnImage(HORIZONTAL_RIGHT);
				} else if(this.map[i][j] == VERTICAL_BORDER){
					img = gfx.returnImage(VERTICAL);
				} else if(this.map[i][j] == STEELEST_BLOCK){
					img = gfx.returnImage(STEELEST);
				} else if(this.map[i][j] == STEELER_BLOCK){
					img = gfx.returnImage(STEELER);
				} else if(this.map[i][j] == STEEL_BLOCK){
					img = gfx.returnImage(STEEL);
				} else if(this.map[i][j] == VERTICAL_UP_BORDER){
				 	img = gfx.returnImage(VERTICAL_UP);
				} else if(this.map[i][j] == VERTICAL_DOWN_BORDER){
					img = gfx.returnImage(VERTICAL_DOWN);
				} else if(this.map[i][j] == HEALTH_UP){
					img = gfx.returnImage(GROUND);
					g.drawImage(img,j*40,i*40, BLOCK_SIZE, BLOCK_SIZE, null);
					img = gfx.returnImage(HEALTH);
					inc = 4;
				} else if(this.map[i][j] == SPEED_UP){
					img = gfx.returnImage(GROUND);
					g.drawImage(img,j*40,i*40, BLOCK_SIZE, BLOCK_SIZE, null);
					img = gfx.returnImage(SPEED);
					inc = 4;
				} else if(this.map[i][j] == DAMAGE_UP){
					img = gfx.returnImage(GROUND);
					g.drawImage(img,j*40,i*40, BLOCK_SIZE, BLOCK_SIZE, null);
					img = gfx.returnImage(DAMAGE);
					inc = 4;
				}
				g.drawImage(img,j * 40 + inc,i * 40 + inc, BLOCK_SIZE - inc*2, BLOCK_SIZE - inc*2, null);
			}
		}
    	for(String key: csHash.keySet()){
    		ClientSprite csp = csHash.get(key);
		 	g.drawImage(csp.img, csp.x, csp.y, SPRITE_SIZE, SPRITE_SIZE, null);
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
		
		// pause drawing
		if(this.is_paused == PAUSED){
			Color curr = g.getColor();
			g.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
			g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
			g.drawImage(gfx.returnImage("pause"), 0, 0, 1000, 800, null);			//paints the pause image
		}

		if(this.is_waiting == true){
			Color curr = g.getColor();
			g.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
			g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
			g.drawImage(gfx.returnImage("waiting"), 0, 0, 1000, 800, null);
			g.setColor(curr);
		}

		if(this.is_dead == true){
			Color curr = g.getColor();
			g.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
			g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

			/*INSERT YOU LOSE HERE*/
			g.drawImage(gfx.returnImage("you-lose"), 0, 0, 1000, 800, null);
			g.setColor(curr);
		} else if(this.is_game_over == true){
			Color curr = g.getColor();
			g.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
			g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

			/*INSERT YOU WIN HERE*/
			g.drawImage(gfx.returnImage("you-win"), 0, 0, 1000, 800, null);
			g.setColor(curr);
		}

		if(this.is_game_over == true){
			g.drawImage(gfx.returnImage("view-results"), 0, 0, 1000, 800, null);

		}
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

    public String getName(){
		return this.player_name;
	}

    public int getBulletSize(){
    	return this.bullet_size;
    }

    public int getLife(){
    	return this.life;
    }

    public int getHealth(){
    	return this.health;
    }

    public String getCurrSpriteColor(){
    	return this.color;
    }

    public int getSpeed(){
    	return this.speed;
    }


    public int getXVal(){
    	return this.x;
    }
    public int getYVal(){
    	return this.y;
    }
    public boolean isDead(){
    	return this.is_dead;
    }

    public boolean isPaused(){
    	return this.is_paused;
    }

    public boolean isGameOver(){
    	return this.is_game_over;
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
			if(!src.isDead() && !src.isPaused() && !src.isGameOver()){
				int x = src.getXVal();
				int y = src.getYVal();
				prev_x = x;
				prev_y = y;

				switch(ke.getKeyCode()){
					case KeyEvent.VK_DOWN:
						y += speed;
						position = "Down";
						moveDown = true;
						break;
					case KeyEvent.VK_UP:
						y -= speed;
						position = "Up";
						moveUp = true;
						break;
					case KeyEvent.VK_LEFT:
						x -= speed;
						position = "Left";
						moveLeft = true;
						break;
					case KeyEvent.VK_RIGHT:
						x += speed;
						position = "Right";					
						moveRight = true;
						break;
					case KeyEvent.VK_SPACE:
						if (moveDown) {
							y += speed;
							position = "Down";
						}
						else if (moveUp) {
							y -= speed;
							position = "Up";
						}
						else if (moveLeft) {
							x -= speed;
							position = "Left";
						}
						else if (moveRight) {
							x += speed;
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
					case KeyEvent.VK_P:
						System.out.println("PAUSED");
						send("PAUSE true " + src.player_name);
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
			} else if( src.isPaused() || (src.isDead() && !src.isGameOver())){
				switch(ke.getKeyCode()){
					case KeyEvent.VK_ENTER:
						chatPanel.setFocus();
						break;	
				}
			}  else if(src.isGameOver()){
				switch(ke.getKeyCode()){
					case KeyEvent.VK_ENTER:
						chatPanel.setFocus();
						break;	
					case KeyEvent.VK_SPACE:
						winPanel.updateResults(server_data);
						CardLayout resultsCard = (CardLayout)cardPanel.getLayout();
						resultsCard.show(cardPanel, WIN_PANEL);
						break;
				}
			}
			if( src.isPaused() && src.pauser.equals(src.player_name)){
		 		switch(ke.getKeyCode()){
					case KeyEvent.VK_P:
						System.out.println("UNPAUSED");
						src.pauser = "";
						send("PAUSE false " + src.player_name);
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


