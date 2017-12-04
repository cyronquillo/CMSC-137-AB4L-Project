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
    public HashMap<String, Stat> collection;
	public StatPanel() {
		collection = new HashMap<String, Stat>();
		setPreferredSize(new Dimension(STAT_PANEL_WIDTH, STAT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		buildGUI();
	}

	private void buildGUI() {
		scrollPanel = new JPanel();
		scrollPanel.setPreferredSize(new Dimension(STAT_PANEL_WIDTH-20, STAT_PANEL_HEIGHT-20));
		scrollPanel.setLayout( new BoxLayout( scrollPanel, BoxLayout.Y_AXIS ) );
		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);				
		add(scrollPane, BorderLayout.CENTER);	

	}

	// public void initPanel(Stat panel){
	// 	collection.put(panel.getSpriteName(), panel);
	// 	scrollPanel.add(panel);

	// 	scrollPanel.repaint();
	// 	this.repaint();
	// }

	public void updatePanel(Stat panel){
		if(!collection.containsKey(panel.getSpriteName())){
			collection.put(panel.getSpriteName(), panel);
			scrollPanel.add(panel);
		}
		else{
			collection.get(panel.getSpriteName()).update(panel);
		}

		//change line below to addSorted if time allows

		scrollPanel.repaint();
		this.revalidate();
		this.repaint();
	}

	public void paintComponent(Graphics g){
		for(String key: collection.keySet()){
			collection.get(key).repaint();
		}
	}



	public void updateStat(){}
	public void updateOrder(){}

}