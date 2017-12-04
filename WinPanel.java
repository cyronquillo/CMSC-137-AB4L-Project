package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseListener;													//import statements
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;



public class WinPanel extends JPanel implements Observer, Constants{
	HashMap<Integer, ClientSprite> sprCollection;
	public WinPanel(JLabel label, GhostWarsClient copy){
		sprCollection = new HashMap<Integer, ClientSprite>();
		setPreferredSize(new Dimension(1500, 800));
		JLabel bg = new JLabel(new ImageIcon("gfx/panels/win_blue.gif"));
		bg.setBounds(0, 0, 1500, 800);
		this.add(bg);

		JButton menu = new JButton();
		menu.add(label);
		menu.setBorder(null);
		menu.setBounds(0,0,1500,800);
		menu.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){
				copy.frame.dispose();
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}

		});
		this.add(menu);
		this.repaint();

	}



	public void paintComponent(Graphics g){
		g.clearRect(0,0,1500, 800);
		System.out.println("pumasok");
		try{
			// Image icon = new ImageIcon("gfx/panels/win_"+ sprCollection.get(1).color +".gif").getImage();
			// Image img = ImageIO.read(new File("gfx/panels/win_"+ sprCollection.get(1).color +".gif"));
			// if(img == null) System.out.println("WTFuck");
			// if(icon == null) System.out.println("WTFeck");
			// g.drawImage(icon, 0, 0, 1500, 800, null );
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		g.drawString("WTF",10,10);
	}

	public void updateResults(String res){
		System.out.println(res);
		String[] objects = res.split(":");
			for(int i = 0; i < objects.length; i++){
				String[] object = objects[i].split(" ");
				if(object[0].startsWith("PLAYER")){
					String name = object[1].trim();
					String color = object[4].split("\\.")[1];
					int rank = Integer.parseInt(object[10]);
					sprCollection.put(rank, new ClientSprite(name, color, rank)); 
				}
			}
	}

	public void update(Observable o, Object arg) {
        final Object finalArg = arg;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                repaint();
            }
        });
    }






}