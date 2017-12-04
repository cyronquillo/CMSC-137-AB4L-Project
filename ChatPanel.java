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
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.InputEvent;
import java.awt.Robot;

public class ChatPanel extends JPanel implements Observer, Constants {
    private JTextArea chatArea;
    private JTextField chatBox;
    private JButton sendButton;
    private ChatAccess chatAccess;
    private Robot robot;
    private GhostWarsClient client;


    public ChatPanel(ChatAccess chatAccess, GhostWarsClient client) {
        this.chatAccess = chatAccess;
        this.client = client;
        chatAccess.addObserver(this);
        buildGUI();
    }

    private void buildGUI() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 250));
        panel.setOpaque(false);

        chatArea = new JTextArea(20, 20);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(panel, c);
        c.gridy = 1;
        //chatArea.setBounds(15, 300, 220, 300);
        add(new JScrollPane(chatArea), c);
            
        chatBox = new JTextField();
        chatBox.setPreferredSize(new Dimension(220, 50));
        //chatBox.setBounds(15, 630, 220, 50);
        //add(chatBox);
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

                try {
                    robot = new Robot();
                    int x = (int) chatBox.getLocationOnScreen().getX();
                    int y = (int) chatBox.getLocationOnScreen().getY();
                    robot.mouseMove(x+CHAT_PANEL_WIDTH, y);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                catch(Exception ex) {}
            }
        };
        chatBox.addActionListener(sendButtonListener);
    }

    public void setFocus() {
        chatBox.requestFocus();
    }

    public void paintComponent(Graphics g){
        // Color def = g.getColor();
        // g.setColor(Color.WHITE);
        // g.fillRect(0, 0, CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT);
        // g.setColor(def);

        try{
            String color = client.getCurrSpriteColor();
            g.drawImage(ImageIO.read(new File("gfx/panels/chat_stat_panel_" + color + ".jpg")),0,0, CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT, null);
        }catch(Exception e){}



        g.drawString("Name: " + client.getName(), 20, 40);
        g.drawString("Health: ", 20, 65);
        g.fillRoundRect(110, 40, client.getHealth(), 14, 10, 10);
        g.drawString("Life: ", 140, 65);
        
        Image img = gfx.returnImage(client.getCurrSpriteColor() + "Down");
        for(int i = 0; i < client.getLife(); i++){
            g.drawImage(img, 150 + (20) * (i+1),50, 15, 15, null);
        }

        String speed = "1x";
        switch(client.getSpeed()){
            case FAST_SPEED:
                speed = "2x";
                break;
            case FASTER_SPEED:
                speed = "2.3x";
                break;
            case FASTEST_SPEED:
                speed = "2.5x";
                break;
        }

        g.drawString("Speed: " + speed, 20, 80);
        int damage = 40;
        if(client.getBulletSize() == BULLET_SIZE){
            damage = 20;
        }
        g.drawString("Damage: " + damage, 140, 80);
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
