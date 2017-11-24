package instantiation;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;

public class ServerChat implements Constants, Runnable{

	private ServerSocket sSocket;
	private Socket cSocket;
	private Thread t;
	public static ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
	
	public ServerChat(){
		System.out.println("Server now listens to port " + PORT + "...");


		// initialization of server socket
		try {
			sSocket = new ServerSocket(PORT);
		} catch(IOException ioe){
			System.out.println(ioe);
		}
		t =  new Thread(this);
		t.start();

	}

	public void run(){
		//initialization of client sockets and create a corresponding thread
		while (true) {
			try{
				cSocket = sSocket.accept();
				threads.add(new ClientThread(cSocket));
				threads.get(threads.size()-1).start();
				System.out.println("Current number of clients: " + threads.size());
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}
}


