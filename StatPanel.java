package instantiation;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatPanel extends JPanel implements Observer, Constants{
	private JTextArea textArea;
	private JScrollPane scrollPane;
    private JPanel scrollPanel;

	public StatPanel() {
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
			// textArea = new JTextArea(20,20);
			// scrollPanel.add(textArea);
			// JTextArea a1 = new JTextArea(20,20);
			// scrollPanel.add(a1);
			// JTextArea a2 = new JTextArea(20,20);
			// scrollPanel.add(a2);
			// JTextArea a3 = new JTextArea(20,20);
			// scrollPanel.add(a3);
			// JTextArea a4 = new JTextArea(20,20);
			// scrollPanel.add(a4);
		add(scrollPane, BorderLayout.CENTER);	

	}

	public void addPanels(Stat panel){
		scrollPanel.add(panel);
	}
	
	public void update(Observable o, Object arg) {
        final Object finalArg = arg;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(finalArg.toString());
                textArea.append("\n");
            }
        });
    }
}