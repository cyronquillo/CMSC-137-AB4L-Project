import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Frame extends JFrame{
	ArtLibrary art = new ArtLibrary();

	public Frame(String game){
		super(game);
		setPreferredSize(new Dimension(1300,600)); // Corners of game arena: (401,0), (401,600), (1199,0), (1199,600)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(art.returnImage("Coin"));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}