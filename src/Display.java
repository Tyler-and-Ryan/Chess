import java.awt.*;
import java.awt.event.*;

public class Display {
	Frame canvas;
	
	public Display() {
		canvas = new Frame();
		canvas.setSize(800,800);
		canvas.setVisible(true);
		
		//Menu bar
		MenuBar();
		
		//Sets up game board
		Panel gameboard = new Panel();
		gameboard.setSize(400,400);
		gameboard.setBounds(10, 10, 300, 300);
		Label playerOneName = new Label("Player One");
		Label playerTwoName = new Label("Player Two");
		gameboard.add(playerTwoName);
		gameboard.add(playerOneName);
		Color background = new Color(808080);
		gameboard.setBackground(background);
		canvas.add(gameboard);
		
		canvas.setVisible(true);
		
		
	}

	//Handles creation and actions for the menu bar
	public void MenuBar() {
		MenuBar panel = new MenuBar();
		Menu menu = new Menu("Options");
		MenuItem quit = new MenuItem("quit");
		menu.add(quit);
		panel.add(menu);
		canvas.setMenuBar(panel);
		
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setVisible(false);
			}
		});
	}
}
