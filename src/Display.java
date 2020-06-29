import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Display {
	JFrame canvas;
	Container gameboard;
	int width, height;
	GameBoard game;
	
	//Creates the game display
	public Display(int width, int height, GameBoard game) {
		canvas = new JFrame();
		canvas.setTitle("Chess (Version 0.5)");
		this.width = width;
		this.height = height;
		this.game = game;
		canvas.setSize(width,height);
		
		//Menu bar
		MenuBar();
		
		//Sets up game board
		gameboard = new Container();
		gameboard.setSize(700,700);
		
		RefreshBoard();
		
		JLabel playerTwoName = new JLabel("Player Two");
		canvas.add(playerTwoName);
		playerTwoName.setBounds(350, 0, 100, 20);
		
		JLabel playerOneName = new JLabel("Player One");
		canvas.add(playerOneName);
		playerOneName.setBounds(350, 700, 100, 20);
		
		canvas.setLayout(null);
		canvas.setVisible(true);
		
		
	}
	
	//Imports the new gameboard
	public void UpdateBoard(GameBoard game) {
		this.game = game;
	}
	
	//Refreshes/redraws a new gameboard
	public void RefreshBoard() {
		Container refresh = new Container();
		
		refresh.setSize(700,700);
		
		for(int i = 8; i >= 0; i--) {
			for(int j = 0; j <= 8; j++) {
				JLabel temp = null;
				
				//Adds appropriate box content to the label which will later be inserted into the grid
				if(j == 0 && i != 0) {
					temp = new JLabel("Row " + i);
				} else if (i != 0) {
					
					//Game Content Squares
					if(game.GetPiece(i-1, j-1) != null) {
						temp = new JLabel(game.GetPiece(i-1,j-1).toString());
					} else {
						temp = new JLabel("Empty");
					}
				}
				if(i == 0) {
					if(j == 0) {
						temp = new JLabel("");
					} else if(j == 1) {
						temp = new JLabel("Column A");
					} else if (j == 2) {
						temp = new JLabel("Column B");
					} else if (j == 3) {
						temp = new JLabel("Column C");
					} else if (j == 4) {
						temp = new JLabel("Column D");
					} else if (j == 5) {
						temp = new JLabel("Column E");
					} else if (j == 6) {
						temp = new JLabel("Column F");
					} else if (j == 7) {
						temp = new JLabel("Column G");
					} else if (j == 8) {
						temp = new JLabel("Column H");
					}
				}
				
				//Adds box content to board
				refresh.add(temp);
			}

		}
		
		gameboard.setVisible(false);
		refresh.setLayout(new GridLayout(9,9));
		canvas.add(refresh);
		refresh.setBounds(100, 50, 600, 600);
		canvas.validate();
		gameboard = refresh;
		
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
