package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Graphics;

public class Stat extends JPanel implements Constants{
	String name;

	public Stat(int count){
		super();
		name = "namae"+count;
		buildGUI(count);
		this.repaint();
	}


	private void buildGUI(int count) {
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH, STAT_PANEL_WIDTH));
		add(new JLabel("hello "+ count));
	}

	public String getSpriteName(){
		return this.name;
	}

	public void paintComponent(Graphics g){
		g.drawRect(0,0,STAT_PANEL_WIDTH, STAT_PANEL_WIDTH);
		// this.repaint();
	}








}