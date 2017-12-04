package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseListener;													//import statements
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Font;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;



public class WinPanel extends JPanel implements Observer, Constants{
	HashMap<Integer, ClientSprite> sprCollection;
	public WinPanel(JLabel label, GhostWarsClient copy){
		sprCollection = new HashMap<Integer, ClientSprite>();
		setPreferredSize(new Dimension(1500, 800));
		this.repaint();
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
	}



	public void paintComponent(Graphics g){
		g.clearRect(0,0,1500, 800);
		try{
			// Image icon = new ImageIcon("gfx/panels/win_"+ sprCollection.get(1).color +".gif").getImage();
			System.out.println(sprCollection.get(1).color);
			Image img = ImageIO.read(new File("gfx/panels/win_"+ sprCollection.get(1).color +".jpg"));
			g.drawImage(img, 0, 0, 1500, 800, null );
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		int i = 1;
		boolean done = false; 
		Image img = null;
		g.setFont(g.getFont().deriveFont(105f));
		g.setColor(Color.WHITE);
		while(sprCollection.containsKey(i)){

			ClientSprite cs = sprCollection.get(i);
			try{
				System.out.println("here is color: " + cs.color);
				img = ImageIO.read(new File("gfx/panels/rank_"+ cs.color +".jpg"));
			} catch(Exception e){
				System.out.println(e);
			}

			switch(i){
				case 1:
					g.drawImage(img, 450, 275, 600, 250, null);
					g.setFont(g.getFont().deriveFont(105f));
					g.setColor(Color.WHITE);
					g.drawString("1", 510, 435);
					g.setColor(Color.BLACK);
					g.setFont(g.getFont().deriveFont(40f));
					g.drawString(cs.name, 680, 415);

					break;
				case 2:
					g.drawImage(img, 100, 175, 300, 125, null ); // 2nd
					g.setFont(g.getFont().deriveFont(40f));	
					g.setColor(Color.WHITE);
					g.drawString("2", 135, 250);
					g.setColor(Color.BLACK);
					g.setFont(g.getFont().deriveFont(25f));
					g.drawString(cs.name, 215, 245);

				
					break;
				case 3:
					g.drawImage(img, 1100, 475, 300, 125, null ); // 3rd
					g.setFont(g.getFont().deriveFont(40f));	
					g.setColor(Color.WHITE);
					g.drawString("3", 1135, 550);
					g.setColor(Color.BLACK);
					g.setFont(g.getFont().deriveFont(25f));
					g.drawString(cs.name, 1215, 545);

					break;
			}
			i++;
			if(img == null) break;
		}

	}

	public void updateResults(String res){
		System.out.println(res);
		String[] objects = res.split(":");
			for(int i = 0; i < objects.length; i++){
				String[] object = objects[i].split(" ");
				if(object[0].startsWith("PLAYER")){
					String name = object[1].trim();
					String color = object[4].split("\\.")[0];
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