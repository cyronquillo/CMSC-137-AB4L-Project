import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;

public class Server{

	private static ServerSocket sSocket;
	private static Socket cSocket;

	private static ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
	
	// arg[0] = port number
	public static void main(String args[]){
		int port;
		if(args.length < 1){
			System.out.println("Usage: java Server <port number>");
			return;
		} else{
			port = Integer.valueOf(args[0]).intValue();
		}

		// initialization of server socket
		try {
			sSocket = new ServerSocket(port);
		} catch(IOException ioe){
			System.out.println(ioe);
		}

		//initialization of client sockets and create a corresponding thread
		while (true) {
			try{
				cSocket = sSocket.accept();
				threads.add(new ClientThread(cSocket, threads));
				threads.get(threads.size()-1).start();
				System.out.println("Current number of clients: " + threads.size());
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}
}


