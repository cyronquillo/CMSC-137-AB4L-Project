package instantiation;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.util.Random;												//import statement
import java.util.ArrayList;

public class GhostWarsServer implements Runnable, Constants {

	String player_data; // contains message from player
	
	int client_count;
	int curr_client_count;
	
	DatagramSocket server_socket;
	
	GameState game;
	int game_stage;
	Thread t;
	int[][] occupance;
	Random rand;

	public GhostWarsServer(int client_count){
		rand = new Random();
		occupance = new int[client_count][2];
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

	public boolean is_occupied(int x, int y){
		for(int i = 0; i < curr_client_count; i++){
			System.out.println("hala pumasok");
			if(occupance[curr_client_count][0] == x && 
				occupance[curr_client_count][1] == y)
					return true;
		}
		return false;
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
						int x,y;
						String data_tokens[] = player_data.split(" ");
						String name = data_tokens[1].trim();
						do{
							x = rand.nextInt(20);
							y = rand.nextInt(15);
						}while((is_occupied(x,y)) || 
							(game.map.getTileMap().getMap())[y][x] != TILE_FLOOR);
						occupance[curr_client_count][0] = x;
						occupance[curr_client_count][1] = y;
						x = (x * FRAME_WIDTH / 20);
						y = (y * FRAME_HEIGHT / 15);
						Sprite sprite = new Sprite(name, packet.getAddress(), packet.getPort(), curr_client_count, this, game, x, y);
						curr_client_count++;
						game.update(name, sprite);
						System.out.println("player " + curr_client_count + " has entered.");
						broadcast("CONNECTED " + name + " " + (curr_client_count-1) + " " + x + " " + y);
						

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

  						// broadcast(game.toString());
  					}
  					if (player_data.startsWith("MISSILE")) {
  						String[] missile_state = player_data.split(" ");
  						String src = missile_state[1];
  						int x = Integer.parseInt(missile_state[2].trim());
  						int y = Integer.parseInt(missile_state[3].trim());
  						String position = missile_state[4].trim();

  						ArrayList<Missile> mi = game.getMissiles();
  						boolean zero_ammo = false;
  						for(int i =0; i < mi.size(); i++){
  							if(mi.get(i).getSource().equals(src)){
  								zero_ammo = true;
  							}
  						}
  						if(zero_ammo == false)
	  						game.addMissile(new Missile(x, y, src, position, game.getMissiles(), this, game));
  					}
  					// if (game.missileCount() != 0){
  					// 	game.updateMissiles();
  					// 	broadcast(game.toString());
  					// }
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
		new ServerChat();
	}


}