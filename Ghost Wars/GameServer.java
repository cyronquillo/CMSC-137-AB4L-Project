import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameServer implements Runnable {
	public static final int start_game=0;
	public static final int game_running=1;
	public final int wait_for_players=2;

	String playerData;
	int playerCount = 0;
	DatagramSocket serverSocket = null;
	GameState game;

	int gameStage = wait_for_players;

	int numPlayers=2;

	Thread thread = new Thread(this);

	public GameServer (int numPlayers) {
		this.numPlayers = numPlayers;

		try {
			serverSocket = new DatagramSocket(1331);
			serverSocket.setSoTimeout(50000);
		}
		catch (IOException e) {
			System.out.println("GameServer()");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("GameServer()");
			e.printStackTrace();
		}

		game = new GameState();
		thread.start();
	}


	public void broadcast(String msg) {
		for (Iterator ite = game.getPlayers().keySet().iterator();ite.hasNext();) {
			String name = (String)ite.next();
			Player player = (Player)game.getPlayers().get(name);
			send(player, msg); 
		}
	}

	public void send(Player player, String msg) {
		DatagramPacket packet;
		byte buffer[] = msg.getBytes();
		packet = new DatagramPacket(buffer, buffer.length, player.getAddress(), player.getPort());

		try{
			serverSocket.send(packet);
		}
		catch(IOException e) {
			System.out.println("send()");
			e.printStackTrace();
		}
	}

	public void run() {
		while(true){
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try{
				serverSocket.receive(packet);
				System.out.println("here");
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			playerData = new String(buffer);
			playerData = playerData.trim();

			switch(gameStage) {
				case wait_for_players:
					if (playerData.startsWith("CONNECT")) {
						String tokens[] = playerData.split(" ");
						Player player = new Player(tokens[1],packet.getAddress(), packet.getPort());
						System.out.println("Player connected: " + tokens[1]);
						game.update(tokens[1].trim(), player);
						broadcast("CONNECTED" +tokens[1]);
						playerCount++;

						if(playerCount == numPlayers) {
							gameStage = start_game;
						}
					}

					break;

				case start_game:
					System.out.println("Game State: START");
					broadcast("START");
					gameStage = game_running;
					break;

				case game_running:
					if (playerData.startsWith("PLAYER")) {
						String[] playerInfo = playerData.split(" ");
						String pname = playerInfo[1];
						int x = Integer.parseInt(playerInfo[2].trim());
						int y = Integer.parseInt(playerInfo[3].trim());
						Player player = (Player)game.getPlayers().get(pname);
						player.setX(x);
						player.setY(y);
						game.update(pname, player);
						broadcast(game.toString());
					}

					break;
			}
		}
	}

	public static void main(String args[]) {
		if (args.length != 1){
			System.exit(1);
		}

		new GameServer(Integer.parseInt(args[0]));			
	}
}