package instantiation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
public class Stat extends JPanel implements Constants{
	String name;

	public Stat(int count){
		super();
		name = "namae"+count;
		buildGUI(count);
	}


	private void buildGUI(int count) {
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH, STAT_PANEL_WIDTH));
		add(new JLabel("hello "+ count));
	}

	public String getSpriteName(){
		return this.name;
	}






}