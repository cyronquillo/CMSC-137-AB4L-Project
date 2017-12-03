package instantiation;

import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;													//import statements
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Container;
import java.lang.Thread;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;

public class GhostWars implements Constants {
	public static JFrame frame = new JFrame(APP_NAME);
	public static JPanel panel = new JPanel(new CardLayout());
	private static JPanel instructionPanel = new JPanel();
	private static JPanel gamePanel = new JPanel();
	private static JPanel howToPanel = new JPanel();
	private static JButton play = new JButton();
	private static JButton howTo = new JButton();
	private static JButton exit = new JButton();
	private static JLabel instructions = new JLabel(new ImageIcon("gfx/layout/instructions.jpg"));
	Image backgroundImage;
	
	public GhostWars() {
		frame.setPreferredSize(new Dimension(STAT_PANEL_WIDTH + FRAME_WIDTH + CHAT_PANEL_WIDTH, FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Container container = frame.getContentPane();

		JPanel menu = new JPanel();
		menu.setPreferredSize(new Dimension(STAT_PANEL_WIDTH + FRAME_WIDTH + CHAT_PANEL_WIDTH, FRAME_HEIGHT));
		menu.setBackground(Color.BLACK);

		// background image
		JLabel bg = new JLabel(new ImageIcon("gfx/layout/background.jpg"));
		bg.setBounds(0,0,1500,800);

		// logo
		JLabel logo = new JLabel(new ImageIcon("gfx/layout/logo.gif"));
		logo.setBounds(350, 30, 800, 200);
		bg.add(logo);

		JLabel howToLabel = new JLabel(new ImageIcon("gfx/layout/howto.jpg"));
		howTo.add(howToLabel);
		howTo.setBorder(null);
		howTo.setBounds(200, 350, 300, 120);
		howTo.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				howToLabel.setIcon(new ImageIcon("gfx/layout/howto_hover.jpg"));
			}
			public void mouseExited(MouseEvent e){
				howToLabel.setIcon(new ImageIcon("gfx/layout/howto.jpg"));
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
		howTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout howCard = (CardLayout)panel.getLayout();
				//howCard.show(panel, Menu.HOWTO);
			}
		});
		bg.add(howTo);


		JLabel playLabel = new JLabel(new ImageIcon("gfx/layout/play.jpg"));
		play.add(playLabel);
		play.setBorder(null);
		play.setBounds(600, 350, 300, 120);

		play.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){
				new Details(frame);				
			}
			public void mouseEntered(MouseEvent e){

				playLabel.setIcon(new ImageIcon("gfx/layout/play_hover.jpg"));
			}
			public void mouseExited(MouseEvent e){
				playLabel.setIcon(new ImageIcon("gfx/layout/play.jpg"));
			}
			public void mousePressed(MouseEvent e){

			}
			public void mouseReleased(MouseEvent e){

			}

		});
		
		bg.add(play);

		JLabel exitLabel = new JLabel(new ImageIcon("gfx/layout/exit.jpg"));
		exit.add(exitLabel);
		exit.setBorder(null);
		exit.setBounds(1000, 350, 300, 120);
		exit.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				System.out.println("exiting");
				exitLabel.setIcon(new ImageIcon("gfx/layout/exit_hover.jpg"));
			}
			public void mouseExited(MouseEvent e){
				exitLabel.setIcon(new ImageIcon("gfx/layout/exit.jpg"));
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}

		});
		bg.add(exit);

		menu.add(bg);

		//panel.add(menu);

		container.add(menu);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GhostWars();
	}
}