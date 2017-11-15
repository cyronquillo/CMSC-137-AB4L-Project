import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.Image;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * The game client itself!
 *
 */

public class GhostWarsClient extends JPanel implements Runnable, Constants{
	/**
	 * Main window
	 */
	JFrame frame= new JFrame();
	static int nthClient = 0;
	/**
	 * Player position, speed etc.
	 */
	int x=10,y=10,xspeed=5,yspeed=5,prevX,prevY;
	
	/**
	 * Game timer, handler receives data from server to update game state
	 */
	Thread t=new Thread(this);
	String color;
	/**
	 * Nice name!
	 */
	String name="Cyrez";
	
	/**
	 * Player name of others
	 */
	String pname;
	String initState = "redDown";
	/**
	 * Server to connect to
	 */
	String server="localhost";

	/**
	 * Flag to indicate whether this player has connected or not
	 */
	boolean connected=false;
	
	/**
	 * get a datagram socket
	 */
    DatagramSocket socket = new DatagramSocket();

	
    /**
     * Placeholder for data received from server
     */
	String serverData;
	
	/**
	 * Offscreen image for double buffering, for some
	 * real smooth animation :)
	 */
	BufferedImage offscreen;

	
	/**
	 * Basic constructor
	 * @param server
	 * @param name
	 * @throws Exception
	 */
	public GhostWarsClient(String server,String name) throws Exception{
		this.server=server;
		this.name=name;
		
		frame.setTitle(APP_NAME+":"+name);
		//set some timeout for the socket
		socket.setSoTimeout(100);
		
		//Some gui stuff i hate.
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
		
		//create the buffer
		offscreen=(BufferedImage)this.createImage(640, 480);
		
		//Some gui stuff again...
		frame.addKeyListener(new KeyHandler());		
		// frame.addMouseMotionListener(new MouseMotionHandler());
		frame.addMouseListener(new MouseAction());

		switch(nthClient){
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

		//tiime to play
		t.start();		
	}
	
	/**
	 * Helper method for sending data to server
	 * @param msg
	 */
	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
		
	}
	
	/**
	 * The juicy part!
	 */
	public void run(){
		while(true){
			/*try{
				Thread.sleep(1);
			}catch(Exception ioe){}*/
						
			//Get the data from players
			byte[] buff = new byte[256];
			DatagramPacket packet = new DatagramPacket(buff, buff.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){/*lazy exception handling :)*/}
			
			serverData=new String(buff);
			serverData=serverData.trim();
			
			//if (!serverData.equals("")){
			//	System.out.println("Server Data:" +serverData);
			//}

			//Study the following kids. 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				offscreen.getGraphics().clearRect(0, 0, 640, 480);
				if (serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for (int i=0;i<playersInfo.length;i++){
						String[] playerInfo = playersInfo[i].split(" ");
						String pname =playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);
						String state = playerInfo[4];
						/*
					  	NetPlayer player=(NetPlayer)GhostWarsServer.game.getPlayers().get(pname);

						ArrayList<Missile> ammo = player.ammo;
						for(int j = 0; j < ammo.size(); j++){
							offscreen.getGraphics().fillOval(ammo.get(j).getX(), ammo.get(j).getY(), 10, 10);

						}*/
						//draw on the offscreen image
						Image img = gfx.returnImage(state);
						offscreen.getGraphics().drawImage(img,x, y,30,30, null);
						offscreen.getGraphics().drawString(pname,x+7,y-5);

					}
				}
					//show the changes
					frame.repaint();		
			}			
		}
	}
	
	/**
	 * Repainting method
	 */
	public void paintComponent(Graphics g){
		g.drawImage(offscreen, 0, 0, null);
	}
	
	
	
	
	/*class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){
			x=me.getX();y=me.getY();
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}				
		}
	}*/
	
	class MouseAction implements MouseListener{
		public void mousePressed(MouseEvent e) {
			System.out.println("Mouse clicked!");
		}

	    public void mouseReleased(MouseEvent e) {}

	    public void mouseEntered(MouseEvent e) {}

	    public void mouseExited(MouseEvent e) {}

	    public void mouseClicked(MouseEvent e) {}
	}

	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			
			prevX=x;prevY=y;
			// if(ke.getKeyCode() != KeyEvent.VK_SPACE){
				switch (ke.getKeyCode()){
					case KeyEvent.VK_DOWN:
						y+=yspeed;
						initState = color+"Down";
						break;
					case KeyEvent.VK_UP:
						y-=yspeed;
						initState = color+"Down";
						break;
					case KeyEvent.VK_LEFT:
						x-=xspeed;
						initState = color+"Left";
						break;
					case KeyEvent.VK_RIGHT:
						x+=xspeed;
						initState = color+"Right";
						break;
				}
				if (prevX != x || prevY != y){
					send("PLAYER "
						+ name + " " 
						+ x + " "
						+ y + " "
						+ initState
					);
				}	
			// } else{
				/*Missile m = new Missile(name, x, y, initState, server);
				Thread t = new Thread(m);
				t.start();*/
			// }
		}
	}
	
	
	public static void main(String args[]) throws Exception{
		if (args.length != 2){
			System.out.println("Usage: java -jar GhostWarsClient <server> <player name>");
			System.exit(1);
		}

		new GhostWarsClient(args[0],args[1]);
	}
}
