package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseListener;													//import statements
import java.awt.event.MouseEvent;



public class WinPanel extends JPanel implements Constants{
	public WinPanel(JLabel label, GhostWarsClient copy){
		// this.add(label);
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
	}


	public void updateResults(String res){
		System.out.println(res);

	}



}