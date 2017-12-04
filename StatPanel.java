package instantiation;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.HashMap;

public class StatPanel extends JPanel implements Constants{
	private JTextArea textArea;
	private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private HashMap<String, Stat> collection;
	public StatPanel() {
		collection = new HashMap<String, Stat>();
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH, STAT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		buildGUI();
	}

	private void buildGUI() {
		scrollPanel = new JPanel();
		scrollPanel.setSize(new Dimension(STAT_PANEL_WIDTH-20, STAT_PANEL_HEIGHT-20));
		scrollPanel.setLayout( new BoxLayout( scrollPanel, BoxLayout.Y_AXIS ) );
		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);	
        	addPanels(new Stat(1));
        	addPanels(new Stat(2));
        	addPanels(new Stat(3));
        	addPanels(new Stat(4));
        	addPanels(new Stat(5));			
		add(scrollPane, BorderLayout.CENTER);	

	}

	public void addPanels(Stat panel){
		collection.put(panel.getSpriteName(), panel);
		scrollPanel.add(panel);
	}

	public void removePanel(){
		scrollPanel.remove(collection.get("namae"+collection.size()));
		collection.remove("namae"+collection.size());
		this.repaint();
	}


	public void updateStat(){}
	public void updateOrder(){}

}