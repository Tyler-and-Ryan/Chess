import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Display {
	JFrame canvas;
	Container gameboard;
	JButton[][] squares;
	int originalRow, originalCol;
	GameBoard game;
	int width, height;
	
	
	//Creates the game display
	public Display(int width, int height, GameBoard game) {
		canvas = new JFrame();
		canvas.setTitle("Chess (Version 0.6)");
		this.width = width;
		this.height = height;
		this.game = game;
		originalRow = -1;
		originalCol = -1;

		canvas.setSize(width,height);
		
		//Menu bar
		MenuBar();
		
		//Initalizes squares double array
		squares = new JButton[8][8];
		
		//Sets up game board
		Color background = new Color(74,102,104);
		gameboard = new Container();
		RefreshBoard();
		
		//Adds player names to the board
		JLabel playerTwoName = new JLabel("Player Two");
		canvas.add(playerTwoName);
		playerTwoName.setBounds((width/2)-50, 0, 100, 20);
		
		Container nameBox = new Container();
		JLabel playerOneName = new JLabel("Player One");
		canvas.add(nameBox);
		nameBox.setBounds((width/2)-50, (height-250), 100, 20);
		nameBox.add(playerOneName);
		playerOneName.setSize(100, 20);
		
		
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
		refresh.setLayout(new GridLayout(9,9));
		
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
					
					temp.addMouseListener(new MouseListener() {
						public void mousePressed(MouseEvent val) {}
						@Override
						public void mouseEntered(MouseEvent val) {}
						@Override
						public void mouseExited(MouseEvent val) {}
						@Override
						public void mouseClicked(MouseEvent val) {
							int x = val.getXOnScreen();
							int y = val.getYOnScreen();
							
							for(int i = 0; i < 8; i++) {
								for(int j = 0; j < 8; j++) {
									if(squares[i][j] == gameboard.getComponentAt(x-100, y-100)) {
										
										//Saves the selected square and checks if a move is needed
										if(originalRow == -1 && game.GetPiece(i, j) != null) {
											//saves the location if it is a first click on a square
											squares[i][j].setBackground(new Color(74,82,104));
											originalRow = i;
											originalCol = j;
										} else if (i == originalRow && j == originalCol){
											//resets background on a double click on a square
											squares[i][j].setBackground(new Color(74,102,104));
											originalRow = -1;
											originalCol = -1;
										} 
										
										if(originalRow != -1 && originalCol != -1){
											//moves square if the move is a valid game move
											if(squares[originalRow][originalCol] != squares[i][j]) {
												boolean currentPlayer = game.GetPiece(originalRow, originalCol).getPlayer();
												
												//If move is valid
												if(game.MovePiece(originalRow, originalCol, i, j, currentPlayer) == true) {
													squares[i][j].setText(squares[originalRow][originalCol].getText());
													squares[originalRow][originalCol].setText("");
													
													if(currentPlayer == true) {
														squares[i][j].setForeground(new Color(102, 255, 51));
													} else {
														squares[i][j].setForeground(new Color(255, 51, 51));
													}
													
													squares[originalRow][originalCol].setForeground(new Color(0,0,0));
													squares[originalRow][originalCol].setBackground(new Color(74,102,104));
													squares[i][j].setBackground(new Color(74,102,104));
													originalRow = -1;
													originalCol = -1;
												}
												
											}
										}
										return;
									} 
								}
							}
						}
						
						@Override
						public void mouseReleased(MouseEvent val) {}
					});
					squares[i-1][j-1] = temp;
					
					refresh.add(temp);
				} else {
					refresh.add(border);
				}
			}

		}
		gameboard.setVisible(false);
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
		JMenu menu = new JMenu("options");
		JMenuItem quit = new JMenuItem("quit");
		JMenuItem reset = new JMenuItem("reset game");
		menu.add(reset);
		menu.add(quit);
		panel.add(menu);
	
		canvas.setJMenuBar(panel);
		
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setVisible(false);
			}
		});
		
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//game.ClearBoard();
				GameBoard newGame = new GameBoard();
				game = newGame;
				gameboard.setVisible(false);
				RefreshBoard();
			}
		});
	}
}
