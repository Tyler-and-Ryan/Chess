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
		Color background = new Color(74,102,104);
		gameboard = new Container();
		//gameboard.setSize(800,800);
		RefreshBoard();
		
		//Adds player names to the board
		JLabel playerTwoName = new JLabel("Player Two");
		canvas.add(playerTwoName);
		playerTwoName.setBounds((width/2)-50, 0, 100, 20);
		
		JLabel playerOneName = new JLabel("Player One");
		canvas.add(playerOneName);
		playerOneName.setBounds((width/2)-50, (height-250), 100, 20);
		
		//Makes the gameboard visible once set up is complete
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
		
		
		for(int i = 8; i >= 0; i--) {
			for(int j = 0; j <= 8; j++) {
				JButton temp = null;
				JLabel border = null;
				
				//Adds appropriate box content to the label which will later be inserted into the grid
				if(j == 0 && i != 0) {
					border = new JLabel("Row " + i);
				} else if (i != 0) {
					
					//Game Content Squares
					if(game.GetPiece(i-1, j-1) != null) {
						temp = new JButton(game.GetPiece(i-1,j-1).toString());
					} else {
						temp = new JButton("Empty");
					}
				}
				if(i == 0) {
					if(j == 0) {
						border = new JLabel("");
					} else if(j == 1) {
						border = new JLabel("Column A");
					} else if (j == 2) {
						border = new JLabel("Column B");
					} else if (j == 3) {
						border = new JLabel("Column C");
					} else if (j == 4) {
						border = new JLabel("Column D");
					} else if (j == 5) {
						border = new JLabel("Column E");
					} else if (j == 6) {
						border = new JLabel("Column F");
					} else if (j == 7) {
						border = new JLabel("Column G");
					} else if (j == 8) {
						border = new JLabel("Column H");
					}
				}
				
				//Adds box content to board
				if(temp != null) {
					Color background = new Color(74,102,104);
					if(game.GetPiece(i-1, j-1) == null) {
						temp.setText("");
						temp.disable();
					} else if(game.GetPiece(i-1, j-1).getPlayer() == true) {
						Color playerOne = new Color(102, 255, 51);
						temp.setForeground(playerOne);
					} else {
						Color playerTwo = new Color(255, 51, 51);
						temp.setForeground(playerTwo);
					}
					temp.setBackground(background);
					refresh.add(temp);
				} else {
					refresh.add(border);
				}
			}

		}
		
		gameboard.setVisible(false);
		refresh.setLayout(new GridLayout(9,9));
		Color background = new Color(74,102,104);
		refresh.setBackground(background);
		canvas.add(refresh);
		refresh.setBounds(100, 50, 700, 700);
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
