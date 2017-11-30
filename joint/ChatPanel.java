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

public class ChatPanel extends JPanel implements Observer {
    private JTextArea chatArea;
    private JTextField chatBox;
    private JButton sendButton;
    private ChatAccess chatAccess;

    public ChatPanel(ChatAccess chatAccess) {
        this.chatAccess = chatAccess;
        chatAccess.addObserver(this);
        buildGUI();
    }

    private void buildGUI() {
        chatArea = new JTextArea(20, 20);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(new JScrollPane(chatArea), c);
            
        chatBox = new JTextField();
        chatBox.setPreferredSize(new Dimension(223, 100));
        c.gridy = 1;
        add(chatBox, c);

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