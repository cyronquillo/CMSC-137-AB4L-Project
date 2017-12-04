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
import javax.swing.border.LineBorder;

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
        panel.setPreferredSize(new Dimension(230, 200));
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
        add(new JScrollPane(chatArea), c);
            
        chatBox = new JTextField();
        chatBox.setPreferredSize(new Dimension(220, 50));
        c.gridy = 2;
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
        g.fillRect(0, 0, CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT);
        // g.setColor(def);

        try{
            String color = client.getCurrSpriteColor();
            g.drawImage(ImageIO.read(new File("gfx/panels/chat_stat_panel_" + color + ".jpg")),0,0, CHAT_PANEL_WIDTH, CHAT_PANEL_HEIGHT, null);
        }catch(Exception e){}



        g.drawString("Name: " + client.getName(), 20, 40);
        g.drawString("Health: ", 20, 85);
        
        g.fillRoundRect(75, 74, INIT_HEALTH, 14, 10, 10);
        Color col = getHealthColor(client.getHealth());
        Color prev = g.getColor();
        g.setColor(col);
        g.fillRoundRect(75, 74, client.getHealth(), 14, 10, 10);
        g.setColor(prev);
        g.drawString("Life: ", 20, 130);
        

        Image img = gfx.returnImage(client.getCurrSpriteColor() + "Down");
        for(int i = 0; i < client.getLife(); i++){
            g.drawImage(img, 30 + (20) * (i+1),115, 15, 15, null);
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

        g.drawString("Speed: " + speed, 20, 180);
        int damage = 40;
        if(client.getBulletSize() == BULLET_SIZE){
            damage = 20;
        }
        g.drawString("Damage: " + damage, 20, 230);
    }

    public Color getHealthColor(int health){
        if(health <= 20){
            return Color.RED;
        } else if(health <= 60){
            return Color.YELLOW;
        } else{
            return Color.GREEN;
        }
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
