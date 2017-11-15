import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

/**
 * The main game server. It just accepts the messages sent by one player to
 * another player
 *
 */

public class GhostWarsServer implements Runnable, Constants{
	/**
	 * Placeholder for the data received from the player
	 */	 
	String playerData;
	
	/**
	 * The number of currently connected player
	 */
	int playerCount=0;
	/**
	 * The socket
	 */
    DatagramSocket serverSocket = null;
    
    /**
     * The current game state
     */
	public static GameState game;

	/**
	 * The current game stage
	 */
	int gameStage=WAITING_FOR_PLAYERS;
	
	/**
	 * Number of players
	 */
	int numPlayers;
	
	/**
	 * The main game thread
	 */
	Thread t = new Thread(this);
	
	/**
	 * Simple constructor
	 */
	public GhostWarsServer(int numPlayers){
		this.numPlayers = numPlayers;
		try {
            serverSocket = new DatagramSocket(PORT);
			serverSocket.setSoTimeout(100);
		} catch (IOException e) {
            System.err.println("Could not listen on port: "+PORT);
            System.exit(-1);
		}catch(Exception e){}
		//Create the game state
		game = new GameState();
		
		System.out.println("Game created...");
		
		//Start the game thread
		t.start();
	}
	
	/**
	 * Helper method for broadcasting data to all players
	 * @param msg
	 */
	public void broadcast(String msg){
		for(Iterator ite=game.getPlayers().keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			NetPlayer player=(NetPlayer)game.getPlayers().get(name);			
			send(player,msg);	
		}
	}


	/**
	 * Send a message to a player
	 * @param player
	 * @param msg
	 */
	public void send(NetPlayer player, String msg){
		DatagramPacket packet;	
		byte buff[] = msg.getBytes();		
		packet = new DatagramPacket(buff, buff.length, player.getAddress(),player.getPort());
		try{
			serverSocket.send(packet);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * The juicy part
	 */
	public void run(){
		while(true){
						
			// Get the data from players
			byte[] buff = new byte[256];
			DatagramPacket packet = new DatagramPacket(buff, buff.length);
			try{
     			serverSocket.receive(packet);
			}catch(Exception ioe){}
			
			/**
			 * Convert the array of bytes to string
			 */
			playerData=new String(buff);
			
			//remove excess bytes
			playerData = playerData.trim();
			if (!playerData.equals("")){
				System.out.println("Player Data:"+playerData);

			}
		
			// process
			switch(gameStage){
				  case WAITING_FOR_PLAYERS:
						//System.out.println("Game State: Waiting for players...");
						if (playerData.startsWith("CONNECT")){
							String tokens[] = playerData.split(" ");
							NetPlayer player=new NetPlayer(tokens[1],packet.getAddress(),packet.getPort(), playerCount);
							System.out.println("Player connected: "+tokens[1]);
							game.update(tokens[1].trim(),player);
							broadcast("CONNECTED "+tokens[1]);
							playerCount++;
							if (playerCount==numPlayers){
								gameStage=GAME_START;
							}
						}
					  break;	
				  case GAME_START:
					  System.out.println("Game State: START");
					  broadcast("START");
					  gameStage=IN_PROGRESS;
					  break;
				  case IN_PROGRESS:
					  	//System.out.println("Game State: IN_PROGRESS");
					  
					  	//Player data was received!
					  	if (playerData.startsWith("PLAYER")){
						  	//Tokenize:
						  	//The format: PLAYER <player name> <x> <y>
						  	String[] playerInfo = playerData.split(" ");					  
						  	String pname =playerInfo[1];
						  	int x = Integer.parseInt(playerInfo[2].trim());
						  	int y = Integer.parseInt(playerInfo[3].trim());
						  	String state = playerInfo[4];
						  	//Get the player from the game state
						  	NetPlayer player=(NetPlayer)game.getPlayers().get(pname);								  
						  	player.setX(x);
						  	player.setY(y);
						  	player.setState(state);
						  	//Update the game state
						  	game.update(pname, player);
						  	//Send to all the updated game state
						  	broadcast(game.toString());
					  	}
					  	/*if(playerData.startsWith("MISSILE")){
					  		System.out.println("Missile shooted!");
					  		String[] missileInfo = playerData.split(" ");
					  		String pname = missileInfo[1];
						  	int x = Integer.parseInt(missileInfo[2].trim());
						  	int y = Integer.parseInt(missileInfo[3].trim());
						  	int status = Integer.parseInt(missileInfo[4].trim());
  							int prevX = Integer.parseInt(missileInfo[5].trim());
							int prevY = Integer.parseInt(missileInfo[6].trim());

						  	NetPlayer player=(NetPlayer)game.getPlayers().get(pname);
						  	int j = 0;
						  	for( j = 0; j < player.ammo.size(); j++){
						  		if(prevY == player.ammo.get(j).getY() && prevX == player.ammo.get(j).getX()) break;
						  	}								  
							if(status == 0){
								player.ammo.get(j).setX(x);
								player.ammo.get(j).setY(y);
							}
							else{
								player.ammo.remove(player.ammo.get(j));
							}
							game.update(pname,player);

							broadcast(game.toString());
					  	}*/
					  	break;
			}				  
		}
	}	
	
	
	public static void main(String args[]){
		if (args.length != 1){
			System.out.println("Usage: java -jar GhostWarsServer <number of players>");
			System.exit(1);
		}
		
		new GhostWarsServer(Integer.parseInt(args[0]));
	}
}

