package instantiation;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class StatPanel extends JPanel implements Observer {
	private JTextArea textArea;

	public StatPanel() {
		buildGUI();
	}

	private void buildGUI() {
		textArea = new JTextArea(20, 20);
		add(textArea);
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