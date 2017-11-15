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

public class Menu{

	private static JFrame frame = new JFrame("ShroomCity");
	public static JPanel panel = new JPanel(new CardLayout());
	private static JPanel center = new JPanel();
	private static JPanel holder = new JPanel(new BorderLayout());
	private static JPanel instructionPanel = new JPanel(new BorderLayout());
	private static JPanel redWin = new JPanel(new GridLayout());
	private static JPanel greenWin = new JPanel(new GridLayout());
	private static GamePanel gamePanel = new GamePanel();
	private static StatPanel panelp1 = new StatPanel(true);
	private static StatPanel panelp2 = new StatPanel(false);										//static attributes
	private static JButton play = new JButton();
	private static JButton howTo = new JButton();
	private static JButton exit = new JButton();
	private static JLabel playLabel = new JLabel(new ImageIcon("images/icon/play.png"));
	private static JLabel instructionLabel = new JLabel(new ImageIcon("images/icon/how_to_play.png"));
	private static JLabel exitLabel = new JLabel(new ImageIcon("images/icon/exit.png"));
	private static JLabel howToLabel = new JLabel();
	private static String PAUSE = "Pause";
	private static String DISPLAY = "Home";
	private static String START = "Game";
	private static String INSTRUCTION = "How to Play";
	private static String EXIT = "Exit";
	public static String RED_MUSHROOM_WIN = "Red Mushroom Wins";
	public static String GREEN_MUSHROOM_WIN = "Green Mushroom Wins";
	private static boolean pause = false;
	private static ArtLibrary art = new ArtLibrary();										//has an art library attribute for to-used images
	private static AudioLibrary audio = new AudioLibrary();							//has an audio library attribute for to-used audios

	public Menu(){
		AudioSource aud = audio.returnAudio("Intro");
		aud.play(true);																					//plays the audio corresponding
		frame.setPreferredSize(new Dimension(1300,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIconImage(art.returnImage("Coin"));

		Container container = frame.getContentPane();

		JPanel menu = new JPanel(); //display the panel that has

		menu.setLayout(new BorderLayout());			//ssets the borderlayout
		menu.setOpaque(false);									//sets opaque to false

		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
		center.setBackground(Color.BLACK);
		JPanel logoPane = new JPanel();
		JLabel logo = new JLabel();							//puts the logo in the panel
		logo.setIcon(new ImageIcon("images/icon/Logo1.gif"));
		logoPane.setBackground(Color.BLACK);
		logoPane.add(logo);

		JPanel topRed = new JPanel();
		JLabel redShroom = new JLabel(new ImageIcon("images/icon/redstart.gif"));
		topRed.setPreferredSize(new Dimension(1300,50));
		topRed.setBackground(Color.BLACK);			//hold the gif at the top
		topRed.setOpaque(true);
		topRed.add(redShroom);

		JPanel bottomGreen = new JPanel();
		JLabel greenShroom = new JLabel(new ImageIcon("images/icon/greenstart.gif"));
		bottomGreen.setPreferredSize(new Dimension(1300, 50));
		bottomGreen.setBackground(Color.BLACK);		//hold the gif at the bottom
		bottomGreen.add(greenShroom);

		gamePanel.setPreferredSize(new Dimension(800,600));		//changes the panel's size

		panelp1.setPreferredSize(new Dimension(250,600));
		panelp1.setBackground(Color.BLACK);

		panelp2.setPreferredSize(new Dimension(250,600));
		panelp2.setBackground(Color.BLACK);										//same

		center.add(logoPane);
		center.setBackground(new Color(0,0,0,255));						//adds the logo and buttons
		addButtons();

		menu.add(topRed, BorderLayout.NORTH);
		menu.add(center, BorderLayout.CENTER);								//adds the displays
		menu.add(bottomGreen, BorderLayout.SOUTH);

		holder.add(gamePanel, BorderLayout.CENTER);
		holder.add(panelp1, BorderLayout.WEST);
		holder.add(panelp2, BorderLayout.EAST);

		howToLabel.setIcon(new ImageIcon("images/layout/howto.png"));
		instructionPanel.add(howToLabel);											//adds the instructionPanel

		JLabel redLabel = new JLabel(new ImageIcon("images/victory/redshroomw.gif"));
		redWin.add(redLabel);																	//shows the victory screen for the respective players

		JLabel greenLabel = new JLabel(new ImageIcon("images/victory/greenshroomw.gif"));
		greenWin.add(greenLabel);

		panel.add(menu, Menu.DISPLAY);
		panel.add(holder, Menu.START);
		panel.add(instructionPanel, Menu.INSTRUCTION);				//adds to the panel w/ card layout
		panel.add(redWin, Menu.RED_MUSHROOM_WIN);
		panel.add(greenWin, Menu.GREEN_MUSHROOM_WIN);
		panel.setBackground(Color.BLACK);

		container.add(panel);

/**EVENT LISTENERS**/

		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				aud.stop();
				CardLayout homeCard = (CardLayout)panel.getLayout();
				homeCard.show(panel, Menu.START);
				frame.addKeyListener((KeyListener)gamePanel.getP1());
				frame.addKeyListener((KeyListener)gamePanel.getP2());

				Thread x = new Thread(gamePanel);
				Thread y = new Thread(panelp1);
        		Thread z = new Thread(panelp2);

				for(int i = 0; i < gamePanel.pUpsArray.pUps.size(); i++){
					Thread t = new Thread(gamePanel.pUpsArray.pUps.get(i));
					t.start();
				}

				gamePanel.player1.start();
				gamePanel.player2.start();
       			x.start();
       			y.start();
        		z.start();

				frame.setFocusable(true);
				frame.requestFocus();
				audio.returnAudio("Start").play(false);
			}
		});

		play.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				playLabel.setIcon(new ImageIcon("images/icon/play_hover.gif"));
			}
			public void mouseExited(MouseEvent e){
				playLabel.setIcon(new ImageIcon("images/icon/play.png"));
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

		howTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CardLayout howCard = (CardLayout)panel.getLayout();
				howCard.show(panel, Menu.INSTRUCTION);
			}
		});

		howTo.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				instructionLabel.setIcon(new ImageIcon("images/icon/how_to_play_hover.gif"));
			}
			public void mouseExited(MouseEvent e){
				instructionLabel.setIcon(new ImageIcon("images/icon/how_to_play.png"));
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

        howToLabel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				CardLayout displayCard = (CardLayout)panel.getLayout();
        		displayCard.show(panel, Menu.DISPLAY);
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

        exit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		System.exit(0);
        	}
        });

        exit.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				exitLabel.setIcon(new ImageIcon("images/icon/exit_hover.gif"));
			}
			public void mouseExited(MouseEvent e){
				exitLabel.setIcon(new ImageIcon("images/icon/exit.png"));
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

		redLabel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

		greenLabel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}

	/**END OF LISTENERS**/

	private void addButtons() {							//adds buttons
		play.add(playLabel);
		play.setBorder(null);
		play.setAlignmentX(Component.CENTER_ALIGNMENT);

		howTo.add(instructionLabel);
		howTo.setBorder(null);
		howTo.setAlignmentX(Component.CENTER_ALIGNMENT);

		exit.add(exitLabel);
		exit.setBackground(Color.BLACK);
		exit.setOpaque(true);
		exit.setBorder(null);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);

		center.add(play);
		center.add(Box.createRigidArea(new Dimension(0,10)));
		center.add(howTo);
		center.add(Box.createRigidArea(new Dimension(0,10)));
		center.add(exit);
		center.add(Box.createRigidArea(new Dimension(0,10)));
	}
}
