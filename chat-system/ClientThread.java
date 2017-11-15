import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.net.Socket;
import java.io.IOException;

public class ClientThread extends Thread{
	private BufferedReader input;
	private PrintStream output;
	private Socket cSocket;
	private ArrayList<ClientThread> threads;

	public ClientThread(Socket cSocket, ArrayList<ClientThread> threads){
		this.cSocket = cSocket;
		this.threads = threads;
	}

	public void run(){
		ArrayList<ClientThread> threads = this.threads; // personal list of threads 
		try{
			input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			output = new PrintStream(cSocket.getOutputStream());
			String usr;

			output.println("Enter your name below ");
			usr = input.readLine().trim();

			//notify other clients that a new client has entered
			output.println("Hello " + usr + "!\n[NOTE: type 'bye' and press Enter to leave the room anytime]");
			synchronized (this) {
				for(int i = 0; i < threads.size(); i++){
					if(threads.get(i) != this){
						threads.get(i).output.println(usr +" has entered the chat room!");
					}
				}
			}


			while(true){
				String line = input.readLine();
				if(line.equals("bye")) {
					break;
				}
				synchronized(this){
					for(int i = 0; i < threads.size(); i++){
						threads.get(i).output.println("[" + usr + "]: " + line);
					}
				}
			}
			synchronized(this){
				for(int i = 0; i < threads.size(); i++){
					if(threads.get(i) != this){
						threads.get(i).output.println(usr + " has left the chat room :(");
					}
				}
			}
			output.println("You have left the chat room.");


			synchronized(this){
				while(threads.size() > 0) threads.remove(0);
			}

			input.close();
			output.close();
			cSocket.close();
		} catch (IOException ioe){
			System.out.println(ioe);
		}
	}


}