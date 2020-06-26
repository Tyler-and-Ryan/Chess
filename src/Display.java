import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Display {
	JFrame canvas;
	
	//Constructor
	public Display() {
		canvas = new JFrame();
		canvas.setSize(800,800);
		canvas.setVisible(true);
		
		//Menu bar
		MenuBar();
		
		//Sets up game board
		JFrame header = new JFrame();
		
		JLabel playerOneName = new JLabel("Player One");
		JTextField playerTwoName = new JTextField("Player Two");
		
		Color background = new Color(000000);
		
		playerOneName.setBounds(100, 100, 300, 300);
		canvas.add(playerOneName);
		
		canvas.setLayout(null);
		canvas.setVisible(true);
		
		
	}

	//Handles creation and actions for the menu bar
	public void MenuBar() {
		JMenuBar panel = new JMenuBar();
		JMenu menu = new JMenu("Options");
		JMenuItem quit = new JMenuItem("quit");
		menu.add(quit);
		panel.add(menu);
	
		canvas.setJMenuBar(panel);
		
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setVisible(false);
			}
		});
	}
}
