import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;													//import statements
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Container;
import java.lang.Thread;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;	

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GhostWars extends JPanel implements Runnable {
	private static JFrame frame = new JFrame("Ghost Wars");
	public static JPanel panel = new JPanel(new CardLayout());
	private static JPanel center = new JPanel();
	private static JPanel holder = new JPanel(new BorderLayout());
	private static JPanel instructionPanel = new JPanel(new BorderLayout());
	private static JPanel redWin = new JPanel(new GridLayout());
	private static JPanel greenWin = new JPanel(new GridLayout());
	private static StatPanel panelp1 = new StatPanel(true);
	private static StatPanel panelp2 = new StatPanel(false);
	private static GamePanel gamePanel = new GamePanel();
	private static ArtLibrary art = new ArtLibrary();										//has an art library attribute for to-used images
	private static AudioLibrary audio = new AudioLibrary();
	public static Map map = new Map();
	public static Block[][] set = map.getBlocks();
	public static CollisionChecker cc = new CollisionChecker();
	public static Sprite redPlayer = new Sprite(Sprite.PLAYER2,map.getTileMap(), set);
	public static Sprite greenPlayer = new Sprite(Sprite.PLAYER1,map.getTileMap(), set);
	public Thread player1 = new Thread(redPlayer);
	public Thread player2 = new Thread(greenPlayer);
	public static PowerUpsArray pUpsArray = new PowerUpsArray(map.getTileMap(), set);
	Thread thread = new Thread(this);
	String name = "shi";
	String opponentName;
	String server = "localhost";
	boolean connected = false;
	DatagramSocket socket = new DatagramSocket();
	String serverData;
	int x=10,y=10,xspeed=2,yspeed=2,prevX,prevY;
	
	public GhostWars(String server, String name) throws Exception{
		this.server=server;
		this.name=name;

		holder.add(panelp2, BorderLayout.EAST);
		frame.setPreferredSize(new Dimension(1300,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIconImage(art.returnImage("Coin"));
		frame.setVisible(true);

		Container container = frame.getContentPane();

		JPanel menu = new JPanel(); //display the panel that has

		menu.setLayout(new BorderLayout());			//ssets the borderlayout
		menu.setOpaque(false);									//sets opaque to false

		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
		center.setBackground(Color.BLACK);

		gamePanel.setPreferredSize(new Dimension(800,600));		//changes the panel's size

		panelp1.setPreferredSize(new Dimension(250,600));
		panelp1.setBackground(Color.BLACK);

		panelp2.setPreferredSize(new Dimension(250,600));
		panelp2.setBackground(Color.BLACK);

		center.setBackground(new Color(0,0,0,255));

		holder.add(gamePanel, BorderLayout.CENTER);
		holder.add(panelp1, BorderLayout.WEST);

		panel.add(holder);

		panel.setBackground(Color.BLACK);

		container.add(panel);


		frame.addKeyListener((KeyListener)gamePanel.getP1());

		frame.setFocusable(true);
		frame.requestFocus();

		frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

		thread.start();
	}

	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){
				ioe.printStackTrace();
			}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){
				ioe.printStackTrace();
			}

			
			serverData=new String(buf);
			serverData=serverData.trim();

			//Study the following kids. 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				if (serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for (int i=0;i<playersInfo.length;i++){
						String[] playerInfo = playersInfo[i].split(" ");
						String pname =playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);					
					}
					//show the changes
					frame.repaint();
				}			
			}
			else{
				System.out.println("nothing");
			}			
		}
	}

	public void paintComponent(Graphics g){
		try{
			super.paintComponent(g);


			Brick brick = null;
			Steel steel = null;
			Border border = null;
			Grass grass = null;
			Floor floor = null;

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.STEEL){
	                    ((Steel)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.FLOOR){
						((Floor)set[i][j]).paint(g,this);
					}																											//paints all blocks by checking its type, one of its attributes
					if(set[i][j].getType() == Block.BRICK){
						((Brick)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.BORDER){
	                    border = (Border)set[i][j];
	                    g.drawImage(art.returnImage("Floor"), border.getXPos(), border.getYPos(), Block.BLOCK_SIZE, Block.BLOCK_SIZE, this);
						((Border)set[i][j]).paint(g,this);
					}
					if(set[i][j].getType() == Block.GRASS){ // for the floor under the grass
						grass = (Grass)set[i][j];
						g.drawImage(art.returnImage("Floor"), grass.getXPos(), grass.getYPos(), Block.BLOCK_SIZE, Block.BLOCK_SIZE, this);
					}

				}
			}

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.BRICK){
						brick = (Brick)set[i][j];
						for(PowerUps p : PowerUpsArray.pUps){								//paints a powerup block under a brick
							if(p.getXPos() - 5 == brick.getXPos() && p.getYPos()-5 == brick.getYPos()){
								if(brick.getPassable()){
									p.paint(g,this);
								}
							}
						}

					}
				}
			}


			redPlayer.paint(g,this);
			Iterator iter = redPlayer.getMissiles().iterator();				//paints the red player and its missiles
			while(iter.hasNext()){
				((Missile)iter.next()).paint(g,this);
			}

			for(int i = 0; i < TileMap.MAP_HEIGHT;i++){
				for(int j = 0; j < TileMap.MAP_WIDTH; j++ ){
					if(set[i][j].getType() == Block.GRASS){								//paints the grass blocks
						((Grass)set[i][j]).paint(g,this);
					}
				}
			}
			if(Sprite.paused == true){
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(new Color(0,0,0,160));											//paints the panel over by a rectangle when paused
				g2d.fillRect(0,0,800,600);
				g.drawImage(art.returnImage("Pause"), 140,175,500,250, this);			//paints the pause image
			}
		} catch(Exception e){}

	}

	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1331);
        	socket.send(packet);
        }catch(Exception e){}
		
	}

	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=x;prevY=y;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_DOWN:y+=yspeed;break;
			case KeyEvent.VK_UP:y-=yspeed;break;
			case KeyEvent.VK_LEFT:x-=xspeed;break;
			case KeyEvent.VK_RIGHT:x+=xspeed;break;
			}
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}	
		}
	}

	public static void main(String [] args){
		if (args.length != 2){
			System.out.println("Usage: java -jar circlewars-client <server> <player name>");
			System.exit(1);
		}

		try{
			new GhostWars(args[0],args[1]);
		}
		catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
	}
}