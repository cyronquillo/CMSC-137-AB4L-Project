package instantiation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ChatAccess extends Observable {
    private Socket socket;
    private OutputStream output;
    private static final String CRLF = "\r\n"; // newline


    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    // Creates socket and receiving thread
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        output = socket.getOutputStream();

        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null){
                        notifyObservers(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    notifyObservers(ex);
                }
            }
        };
        receivingThread.start();
    }


    // sends line
    public void send(String text) {
        try {
            output.write((text + CRLF).getBytes());
            output.flush();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }

    // closes the socket
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}