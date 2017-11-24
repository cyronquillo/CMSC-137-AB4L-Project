package instantiation;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;


public class GhostWarsServer implements Runnable, Constants {

	String player_data; // contains message from player
	
	int client_count;
	int curr_client_count;
	
	DatagramSocket server_socket;
	
	GameState game;
	
	int game_stage;

	Thread t;

	public GhostWarsServer(int client_count){
		game_stage = WAITING_FOR_PLAYERS;
		this.client_count = client_count;
		curr_client_count = 0;
		try{
			server_socket = new DatagramSocket(PORT);
			server_socket.setSoTimeout(100);
		} catch (IOException ioe) {
			System.err.println("Could not listen to port: " + PORT);
			System.exit(-1);
		} catch (Exception e){}

		game = new GameState();

		System.out.println("Game has been launched. Waiting for players.");
		
		t = new Thread(this);
		t.start();
	}

	public void broadcast(String message){
		for(String name: game.getPlayers().keySet()){

			Sprite sprite = game.getPlayers().get(name);
			send(sprite, message);
		}
	}

	public void send(Sprite sprite, String message){
		byte buffer[] = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, sprite.getIP(), sprite.getPort());
		try {
			server_socket.send(packet);
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	private void printDataString(String data){
		if(!data.equals("")){
			System.out.println(data);
		}
	}

	public void run(){
		while(true){
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try{
				server_socket.receive(packet);
			}catch (Exception e){}

			player_data = new String(buffer);
			player_data = player_data.trim();
			//printDataString(player_data); // checking


			switch(game_stage){
				case WAITING_FOR_PLAYERS:
					if (player_data.startsWith("CONNECT")) {
						String data_tokens[] = player_data.split(" ");
						String name = data_tokens[1].trim();
						Sprite sprite = new Sprite(name, packet.getAddress(), packet.getPort(), curr_client_count);
						curr_client_count++;
						game.update(name, sprite);
						System.out.println("player " + curr_client_count + " has entered.");
						broadcast("CONNECTED " + name);
						if(curr_client_count == client_count){

							game_stage = GAME_START;
						}
  					}
  					break;
  				case GAME_START:
  					System.out.println("Game State: START");
  					broadcast("START");
  					game_stage = IN_PROGRESS;
  					break;
  				case IN_PROGRESS:
  					if (player_data.startsWith("PLAYER")) {
  						String[] sprite_state = player_data.split(" ");
  						String name = sprite_state[1];
  						int x = Integer.parseInt(sprite_state[2].trim());
  						int y = Integer.parseInt(sprite_state[3].trim());
  						String position = sprite_state[4].trim();
  						Sprite sprite = game.getPlayers().get(name);
  						sprite.setX(x);
  						sprite.setY(y);
  						sprite.setState(sprite.getColor() + "." + position);

  						game.update(name, sprite);

  						broadcast(game.toString());

  					}
  					break;

			}



		}
	}

	public static void main(String[] args){
		if (args.length != 1){
			System.out.println("Usage: java GhostWarsServer <number of players>");
			System.exit(1);
		}

		new GhostWarsServer(Integer.parseInt(args[0]));
		System.out.println("yay chat naman");
		new ServerChat();
	}


}