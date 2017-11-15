import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	private static Socket cSocket;
	private static PrintStream output;
	private static BufferedReader input;
	private static BufferedReader inpLine;
	private static boolean bye = false;

	public static void main(String[] args) {
		int port;
		String hostIP;

		// args[0] = host IP;
		// args[1] = port number
		if(args.length < 2) {
			System.out.println("Usage: java Client <host IP> <port number>");
			return;
		} else{
			hostIP = args[0];
			port = Integer.valueOf(args[1]).intValue();
		}


		try{
			cSocket = new Socket(hostIP,port);
			inpLine = new BufferedReader(new InputStreamReader(System.in));
			output = new PrintStream(cSocket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
		} catch (UnknownHostException uhe){
			System.out.println(uhe);
		} catch (IOException ioe){
			System.out.println(ioe);
		}

		if(cSocket != null && input != null &&  output != null){
			try {
				Thread t1 = new Thread(new Client());
				t1.start();
				do{
					output.println(inpLine.readLine().trim());
				}while(!bye);
			
			} catch(IOException ioe){
				System.out.println(ioe);
			}
		}
	}
	public void run(){

		String response;
		try{
			while((response = input.readLine()) != null){
				System.out.println(response);
				if(response.equals("You have left the chat room.")){
					break;
				}
			}
			bye = true;
			output.close();
			input.close();
			cSocket.close();
			System.exit(0);

		} catch(IOException ioe){
			System.out.println(ioe);
		}
	}

}
