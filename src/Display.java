import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Display {
	private JFrame canvas;
	private JPanel gameboard;
	private Container alertbox;
	private JButton[][] squares;
	private int originalRow, originalCol;
	private GameBoard game;
	private int width, height;
	private boolean currentPlayer;
	private JLabel playerTwoName;
	private JLabel playerOneName;
	private JLabel alertText;
	
	
	//Creates the game display
	public Display(int width, int height, GameBoard game) {
		//creates frame and instantiates class members
		canvas = new JFrame();
		canvas.setTitle("Chess (Version 0.7)");
		this.width = width;
		this.height = height;
		this.game = game;
		originalRow = -1;
		originalCol = -1;
		currentPlayer = true;

		canvas.setSize(width,height);
		
		//Menu bar
		MenuBar();
		
		//Initalizes squares double array
		squares = new JButton[8][8];
		
		
		ConstructCanvas();
		
	}
	
	//Creates the display with correct layering
	public void ConstructCanvas() {
		
		//Colors
		Color menuBackground = new Color(77,75,72);
		Color menuText = new Color(255,255,255);
		
		//Initalizes squares double array
		squares = new JButton[8][8];
		
		//Sets up game board
		gameboard = new JPanel();
		RefreshBoard();

		
		//creates alertbox
		alertbox = new Container();
		JPanel temp = new JPanel();
		temp.setBackground(new Color(255,0,0));
		JLabel alertText = new JLabel();
		alertText.setForeground(new Color(255,0,255));
		alertText.setText("INVALID MOVE");
		alertbox.add(temp);
		alertbox.add(alertText);
		canvas.add(alertbox);
			
		alertText.setBounds((width/2)-50, 0, 300, 100);
		alertText.setVisible(true);

				
		//Sets up canvas layering
		JLayeredPane layers = new JLayeredPane();
		canvas.setLayeredPane(layers);
		layers.add(gameboard, 1);
		gameboard.setBounds(50, 150, 800, 700);
		canvas.setBackground(new Color(0,0,0));

		//Menu bar
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JMenuBar menu = new JMenuBar();
		JMenu itemlist = new JMenu("▼ Options");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem reset = new JMenuItem("Reset game");
		itemlist.add(reset);
		itemlist.add(quit);
		menu.add(itemlist);
		panel.setBackground(menuBackground);
		panel.setBounds(0, 0, width, 50);
		panel.add(menu);
		
		//Orients the option menu correctly on the panel and
		itemlist.setPreferredSize(new Dimension(100,50));
		menu.setBackground(menuBackground);
		itemlist.setForeground(menuText);
		menu.setBounds(0,0,100,50);
		
		quit.setPreferredSize(new Dimension(100,50));
		reset.setPreferredSize(new Dimension(100,50));
		quit.setBackground(menuBackground);
		quit.setForeground(menuText);
		reset.setBackground(menuBackground);
		reset.setForeground(menuText);
		
		//Sets AIOption button onto menu panel
		JButton AIOption = new JButton("♚ Play Computer");
		
		//Resets board and begins the next game with an AI
		AIOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AIOption.getBackground() != new Color(189,189,189)) {
					AIOption.setBackground(menuBackground);
				} else {
					AIOption.setBackground(new Color(189,189,189));
				}
			}
		});
		
		//Menu text animation - doesn't work yet
		itemlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("HI");
				if(itemlist.getText() == "▼ Options") {
					itemlist.setText("► Options");
				} else {
					System.out.println("HI");
					itemlist.setText("▼ Options");
				}
			}
		});
		
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setVisible(false);
			}
		});
		
		//Adds reset option functionality
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameBoard newGame = new GameBoard();
				game = newGame;
				currentPlayer = true;
				ConstructCanvas();
			}
		});
		
		AIOption.setPreferredSize(new Dimension(150,50));
		AIOption.setBackground(menuBackground);
		AIOption.setForeground(menuText);
		AIOption.setBounds(100,0,150,50);
		panel.add(AIOption);
		
		//Adds layers to panel
		layers.add(panel, 1);
		
		
				
		//Makes the gameboard visible once set up is complete
		canvas.setBounds(0, 100, width, height);
		canvas.setLayout(new BorderLayout());
		canvas.setVisible(true);

				
		//creates player name labels on the board
		//creates player names on the board
		playerTwoName = new JLabel("Player Two");
		playerTwoName.setBounds(460,60,100,100);
		playerTwoName.setForeground(new Color(255,255,255));
		layers.add(playerTwoName, new Integer(1));
				
		playerOneName = new JLabel("Player One");
		playerOneName.setBounds(460,800,100,100);
		playerOneName.setForeground(new Color(255,255,255));

		layers.add(playerOneName, new Integer(1));
		layers.add(playerOneName, new Integer(1));
		
		
		//creates alertbox
		alertText = new JLabel("INVALID MOVE");
		alertText.setBounds((width/2)-50, 20, 300, 100);
		alertText.setForeground(new Color(255,0,255));
		layers.add(alertText, new Integer(1));

	}
	
	public void BackgroundAnimation(Graphics g) {
		//SoonTM
	}
	
	//Imports the new gameboard
	public void UpdateBoard(GameBoard game) {
		this.game = game;
	}
	
	//Refreshes/redraws a new gameboard
	public void RefreshBoard() {
		
		//Builds board
		Container refresh = new Container();
		refresh.setLayout(new GridLayout(9,9));
		
		for(int i = 8; i >= 0; i--) {
			for(int j = 0; j <= 8; j++) {
				JButton temp = null;
				JLabel border = null;
				
				//Adds appropriate box content to the label which will later be inserted into the grid
				if(j == 0 && i != 0) {
					border = new JLabel("Row " + i);
					border.setForeground(new Color(255,255,255));
				} else if (i != 0) {
					
					//Game Content Squares
					if(game.GetPiece(i-1, j-1) != null) {
						temp = new JButton(game.GetPiece(i-1,j-1).toString());
					} else {
						temp = new JButton("");
					}
				}
				if(i == 0) {
					if(j == 0) {
						border = new JLabel("");
					} else if(j == 1) {
						border = new JLabel("      Column A");
					} else if (j == 2) {
						border = new JLabel("      Column B");
					} else if (j == 3) {
						border = new JLabel("      Column C");
					} else if (j == 4) {
						border = new JLabel("      Column D");
					} else if (j == 5) {
						border = new JLabel("      Column E");
					} else if (j == 6) {
						border = new JLabel("      Column F");
					} else if (j == 7) {
						border = new JLabel("      Column G");
					} else if (j == 8) {
						border = new JLabel("      Column H");
					}
					border.setForeground(new Color(255,255,255));
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
						public void mousePressed(MouseEvent val) {
							int x = val.getXOnScreen();
							int y = val.getYOnScreen();
							
							for(int i = 0; i < 8; i++) {
								for(int j = 0; j < 8; j++) {
									//Handles the event for the correct square
									if(squares[i][j] == gameboard.getComponent(0).getComponentAt(x-55, y-280)) {
										
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
													
												//If move is valid
												if(currentPlayer == game.GetPiece(originalRow, originalCol).getPlayer()) {
													//DisableAlert();
													if(game.MovePiece(originalRow, originalCol, i, j, currentPlayer) == true) {
														//Checks if the player is selecting their piece
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
														
														if(currentPlayer == true) {
															currentPlayer = false;
														} else {
															currentPlayer = true;
														}
													}
												} else {
													if(currentPlayer == true) {
														SetAlert("Not Player One's turn");
													} else {
														SetAlert("Not Player Two's turn");
													}
												} 
											}
										}
										return;
									}
								}
							}
						}
						@Override
						public void mouseEntered(MouseEvent val) {}
						@Override
						public void mouseExited(MouseEvent val) {}
						@Override
						public void mouseClicked(MouseEvent val) {}
						
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
		//gameboard.setVisible(false);
		Color background = new Color(74,102,104);
		refresh.setBackground(background);
		
	
		//Correctly orients the gameboard layout
		JPanel temp = new JPanel(new BorderLayout());
		temp.add(refresh, BorderLayout.CENTER);
		temp.setBackground(new Color(0,0,0));
		
		gameboard = temp;
	}
	
	//Changes the alert message and then sets the alert to be visible
	public void SetAlert(String text) {
		alertbox.setVisible(true);
	}
	
	public void DisableAlert() {
		alertbox.setVisible(false);
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
		//panel.setBackground(new Color(0,0,0));
		panel.setBounds(0, 0, 800, 200);
		canvas.setJMenuBar(panel);
		//Adds quit option functionality
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setVisible(false);
			}
		});
		
		//Adds reset option functionality
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameBoard newGame = new GameBoard();
				game = newGame;
				currentPlayer = true;
				ConstructCanvas();
			}
		});
		
	}
}
