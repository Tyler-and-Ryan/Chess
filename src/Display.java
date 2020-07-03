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
	
	
	//Creates the game display
	public Display(int width, int height, GameBoard game) {
		//creates frame and instantiates class members
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
		gameboard = new JPanel();
		RefreshBoard();
		
		//creates alertbox
		alertbox = new Container();
		alertbox.setBackground(new Color(255,0,255));
		JLabel alertText = new JLabel();
		alertText.setForeground(new Color(255,0,255));
		alertText.setText("INVALID MOVE");
		alertbox.add(alertText);
		canvas.add(alertText);
		alertText.setBounds((width/2)-50, 0, 300, 100);
		
		//Sets up canvas layering
		JLayeredPane layers = new JLayeredPane();
		canvas.setLayeredPane(layers);
		layers.add(gameboard, 1);
		gameboard.setBounds(50, 150, 800, 700);
		layers.add(alertText, 1);
		canvas.setBackground(new Color(0,0,0));
		
		//Menu bar
		MenuBar();
		
		//Background Animation
		//SoonTM
		
		//Makes the gameboard visible once set up is complete
		canvas.setLayout(new BorderLayout());
		canvas.setVisible(true);
		
		
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
		
		//Adds player names to the board
		JLabel playerTwoName = new JLabel("Player Two");
		playerTwoName.setForeground(new Color(255,255,255));
		playerTwoName.setHorizontalAlignment(JLabel.CENTER);
		playerTwoName.setVerticalAlignment(JLabel.CENTER);
		
				
		JLabel playerOneName = new JLabel("Player One");
		playerOneName.setForeground(new Color(255,255,255));
		playerOneName.setHorizontalAlignment(JLabel.CENTER);
		playerOneName.setVerticalAlignment(JLabel.CENTER);

		
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
									if(squares[i][j] == gameboard.getComponentAt(x-100, y-200)) {
										
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
		
	
		//Correctly orients the gameboard layout
		JPanel temp = new JPanel(new BorderLayout());
		temp.add(playerTwoName, BorderLayout.NORTH );
		temp.add(refresh, BorderLayout.CENTER);
		temp.add(playerOneName, BorderLayout.SOUTH);

		temp.setBackground(new Color(0,0,0));
		
		gameboard = temp;
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
				game.ClearBoard();
				GameBoard newGame = new GameBoard();
				game = newGame;
				gameboard.setVisible(false);
				RefreshBoard();
			}
		});
		
	}
}
