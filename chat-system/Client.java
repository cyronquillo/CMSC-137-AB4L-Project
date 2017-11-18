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

public class Client {
    static class ChatAccess extends Observable {
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

    // Client UI
    static class ChatFrame extends JFrame implements Observer {
        private JTextArea chatArea;
        private JTextField chatBox;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        private void buildGUI() {
            chatArea = new JTextArea(20, 50);
            chatArea.setEditable(false);
            chatArea.setLineWrap(true);
            add(new JScrollPane(chatArea), BorderLayout.CENTER);

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            chatBox = new JTextField();
            sendButton = new JButton("Send");
            box.add(chatBox);
            box.add(sendButton);

            // Action listeners
            ActionListener sendButtonListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = chatBox.getText();
                    if (str != null && str.trim().length() > 0)
                        chatAccess.send(str);
                    chatBox.selectAll();
                    chatBox.requestFocus();
                    chatBox.setText("");
                }
            };
            chatBox.addActionListener(sendButtonListener);
            sendButton.addActionListener(sendButtonListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    chatArea.append(finalArg.toString());
                    chatArea.append("\n");
                }
            });
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Client <server-ip> <port>");
        }
        else {
            String server = args[0];
            int port = Integer.valueOf(args[1]).intValue();
            ChatAccess access = new ChatAccess();

            JFrame frame = new ChatFrame(access);
            frame.setTitle("GHOST WARS - connected to " + server + ":" + port);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

            try {
                access.InitSocket(server,port);
            } catch (IOException ex) {
                System.out.println("Cannot connect to " + server + ":" + port);
                ex.printStackTrace();
                System.exit(0);
            }
        }
        
    }
}