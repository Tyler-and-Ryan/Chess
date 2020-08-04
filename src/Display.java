import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.LabelUI;

import javafx.scene.layout.Border;

import java.awt.event.*;
import java.util.ArrayList;

public class Display {
	private JFrame canvas;
	private JPanel gameboard;
	private Container alertbox;
	private JButton[][] squares;
	private int originalRow, originalCol;
	private GameBoard game;
	private int width, height;
	private boolean currentPlayer;
	private JLabel alertText;
	private JLabel playerTwoName;
	private JLabel playerOneName;
	private Font labelFont;
	private boolean AIenabled;
	
	
	//Creates the game display
	public Display(int width, int height, GameBoard game) {
		//creates frame and instantiates class members
		canvas = new JFrame();
		canvas.setTitle("Chess (Version 0.8)");
		this.width = width;
		this.height = height;
		this.game = game;
		originalRow = -1;
		originalCol = -1;
		currentPlayer = true;
		AIenabled = false;

		canvas.setSize(width,height);
		
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
		
		//creates player name or computer labels on the board
		
		if(AIenabled) {
			playerTwoName = new JLabel("Computer");
			playerTwoName.setForeground(new Color(47,141,255));
		} else {
			playerTwoName = new JLabel("Player Two");
			playerTwoName.setForeground(new Color(255,255,255));
		}
		
		playerTwoName.setBounds(420,5,200,200);
		labelFont = playerTwoName.getFont();
		playerTwoName.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
				
		playerOneName = new JLabel("Player One");
		playerOneName.setBounds(420,770,200,200);
		playerOneName.setForeground(new Color(255,255,255));
		playerOneName.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		
		//Sets up game board
		gameboard = new JPanel();
		RefreshBoard();
				
		//Sets up canvas layering
		JLayeredPane layers = new JLayeredPane();
		canvas.setLayeredPane(layers);
		layers.add(gameboard, 1);
		gameboard.setBounds(50, 150, 800, 700);
		canvas.setBackground(new Color(0,0,0));
		
		//Adds playernames to layer
		layers.add(playerOneName, new Integer(1));
		layers.add(playerTwoName, new Integer(1));

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
				if(AIenabled == false) {
					AIenabled = true;
					GameBoard newGame = new GameBoard();
					game = newGame;
					currentPlayer = true;
					ConstructCanvas();
				} else {
					SetAlert("AI is already enabled");
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
				AIenabled = false;
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
		Toolkit val = Toolkit.getDefaultToolkit();
		canvas.setLocation(val.getScreenSize().width/5,val.getScreenSize().height/5);
		
		
		//creates alertbox
		alertText = new JLabel();
		alertText.setBounds((width/2)-50, 20, 300, 100);
		alertText.setForeground(new Color(255,0,255));
		layers.add(alertText, new Integer(1));
		alertText.setVisible(false);

		//creates the lost pieces dashboard
		ArrayList<Object> lostPieces = new ArrayList();
		int playerOneCount = 0;
		Object[] playerOnePieces = game.getPlayerOnePieces();
		Object[] playerTwoPieces = game.getPlayerTwoPieces();
		for(int i = 0; i < playerOnePieces.length; i++) {
			if(playerOnePieces[i].GetStatus() == false) {
				playerOneCount++;
				lostPieces.add(lostPieces.size(),playerOnePieces[i]);
			}
			if(playerTwoPieces[i].GetStatus() == false) {
				lostPieces.add(0,playerTwoPieces[i]);
			}
		}
		
		
		
		//Gets the correct height for the display
		int depth = 100+(lostPieces.size()*50);
		
		JPanel lostPiecesDisplay = new JPanel();
		int gridSize = 0;
		if(lostPieces.size() > 2) {
			gridSize = lostPieces.size()-2;
		}
		lostPiecesDisplay.setLayout(new GridLayout(4 + gridSize,2));
		
		//generates the dashboard
		for(int i = 0; i < 4+gridSize; i++) {
			JLabel tempLeft;
			JLabel tempRight;
			if(i == 0 || (i-(1+playerOneCount)) == 0) {
				if(i == 0) {
					tempLeft = new JLabel("Player Two");
					tempRight = new JLabel("");
					tempLeft.setForeground(new Color(255, 51, 51));
				} else {
					tempLeft = new JLabel("Player One");
					tempRight = new JLabel("");
					tempLeft.setForeground(new Color(102, 255, 51));
				}
			} else {
				if(!lostPieces.isEmpty()) {
					tempLeft = new JLabel(lostPieces.get(0).toString());
					tempRight = new JLabel("Last seen at " + "(" + lostPieces.get(0).GetRow() + ", " + lostPieces.get(0).GetCol() + ")");
					lostPieces.remove(0);
					
					tempLeft.setForeground(Color.WHITE);
					tempRight.setForeground(Color.WHITE);
				} else {
					tempLeft = new JLabel("");
					tempRight = new JLabel("");
				}
			}
			
			tempLeft.setPreferredSize(new Dimension(50,100));
			tempRight.setPreferredSize(new Dimension(50,100));
			
			lostPiecesDisplay.add(tempLeft);
			lostPiecesDisplay.add(tempRight);
		}
		
		//Prevents display from getting too long
		if(depth <= 600) {
			lostPiecesDisplay.setBounds(900, 150, 300, depth);
		} else {
			lostPiecesDisplay.setBounds(900, 150, 300, 600);
		}
		
		lostPiecesDisplay.setBackground(menuBackground);
		layers.add(lostPiecesDisplay,1);
		
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
		Font labelFont = playerTwoName.getFont();
		
		//Displays whose turn it is by making that players name label bold and yellow
		if(!AIenabled) {
			if (currentPlayer) {
				playerOneName.setFont(new Font(labelFont.getName(), Font.BOLD, 30));
				playerOneName.setForeground(new Color(255,255,0));
				playerTwoName.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
				playerTwoName.setForeground(new Color(255,255,255));
			} else {
				playerTwoName.setFont(new Font(labelFont.getName(), Font.BOLD, 30));
				playerTwoName.setForeground(new Color(255,255,0));
				playerOneName.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
				playerOneName.setForeground(new Color(255,255,255));
			}
		}
		
		
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
									if(squares[i][j] == gameboard.getComponent(0).getComponentAt(x-55-canvas.getLocation().x, y-180-canvas.getLocation().y)) {
										
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
													
													boolean continuePermission = true;
													
													//If the player is in check, it makes sure that if you move a piece the player gets out of check
													if(game.refreshCheck(currentPlayer)) {
														
														//Creates a copy of the board
														GameBoard moveCheck = new GameBoard();
														moveCheck.CopyBoard(game);
														moveCheck.MovePiece(originalRow, originalCol, i, j, currentPlayer);
														
														//If the move still leaves them in check they will not loose permission to continue with the move
														if(moveCheck.refreshCheck(currentPlayer)){
															if(currentPlayer) {
																SetAlert("Player One is still in check");
															} else {
																SetAlert("Player Two is still in check");
															}
															continuePermission = false;
														}
													}
													
													//If the move will not leave the player in check (if they are)
													if(continuePermission) {
														//If the move is valid
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
															
															
															if(!AIenabled) {
																if(currentPlayer == true) {
																currentPlayer = false;
																} else {
																currentPlayer = true;
																}
															} else {
																//current player is always true
																Alexa computer = new Alexa();
																computer.UpdateBoard(game);
																Point[] AIMove = new Point[2];
																AIMove = computer.GenerateMove();
																
																game.MovePiece(AIMove[0].x, AIMove[0].y, AIMove[1].x, AIMove[1].y, false);
																
															}
															
															
															ConstructCanvas();
															//Checks if the next player is in check
															if(game.refreshCheck(currentPlayer)) {
																if(currentPlayer) {
																	SetAlert("Player One is in check");
																} else {
																	SetAlert("Player Two is in check");
																}
															}
														} else {
															SetAlert("Invalid Move");
														}
													} else {
														if(currentPlayer) {
															SetAlert("Not Player One's turn");
														} else {
															SetAlert("Not Player Two's turn");
														}
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
		alertText.setText(text);
		alertText.setVisible(true);
	}
	
	public void DisableAlert() {
		alertText.setVisible(false);
	}
}
