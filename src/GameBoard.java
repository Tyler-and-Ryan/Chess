import java.awt.Point;
import java.lang.StringBuilder;
import java.util.ArrayList;

public class GameBoard {
	private Object[][] board;
	private Object[] playerOnePieces;
	private Object[] playerTwoPieces;
	private int boardSize;
	
	//Constructor 
	//TODO: (add implementation later to have variable board size)
	public GameBoard(){
		boardSize = 8;
		CreateBoard();
	}
	
	//Creates the game board and sets pawns in the first two rows and the last two rows
	private void CreateBoard() {
		//Instantiates private members
		board = new Object[boardSize][boardSize];
		playerOnePieces = new Object[boardSize*2];
		playerTwoPieces = new Object[boardSize*2];
		
		int playerOneCounter = 0; 
		int playerTwoCounter = 0;
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Object temp = null;
				if(i == 1) {
					temp = new Pawn(true, i, j);
				} else if (i == boardSize-2){ 
					temp = new Pawn(false, i, j);
				} else if (j == 0 || j == boardSize-1){
					if(i == 0) {
						temp = new Castle(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new Castle(false, i, j);
					}
				} else if (j == 1 || j == boardSize-2){
					if(i == 0) {
						temp = new Horse(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new Horse(false, i, j);
					}
				} else if (j == 2 || j == boardSize-3){
					if(i == 0) {
						temp = new Bishop(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new Bishop(false, i, j);
					}
				} else if (j == 3){
					if(i == 0) {
						temp = new Queen(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new King(false, i, j);
					}
				} else if (j == 4){
					if(i == 0) {
						temp = new King(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new Queen(false, i, j);
					}
				}
				board[i][j] = temp;
				if(i == 0 || i == 1) {
					playerOnePieces[playerOneCounter] = temp;
					playerOneCounter++;
				}
				if(i == boardSize-1 || i == boardSize-2) {
					playerTwoPieces[playerTwoCounter] = temp;
					playerTwoCounter++;
				}
			}
		}
	}
	
	//Gets possible legal moves for a selected piece
	//returns null if no options are valid else returns points of valid move locations
	public ArrayList<Point> LegalMoves(int row, int col){
		ArrayList<Point> options = new ArrayList<Point>();
			
		if(board[row][col] != null) {
			for(int i = 0; i < boardSize; i++) {
				for(int j = 0; j < boardSize; j++) {
					if(board[row][col].toString() == "Pawn") {
						if(IsLegalPawn(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} else if(board[row][col].toString() == "Bishop") {
						if(IsLegalBishop(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} else if(board[row][col].toString() == "Castle") {
						if(IsLegalCastle(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} else if(board[row][col].toString() == "Horse") {
						if(IsLegalHorse(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} else if(board[row][col].toString() == "King") {
						if(IsLegalKing(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} else if(board[row][col].toString() == "Queen") {
						if(IsLegalQueen(row,col,i,j)) {
							Point temp = new Point(i,j);
							options.add(temp);
						}
					} 
				}
			}
		}
		
		if(options.size() == 0) {
			//no legal moves possible
			return null;
		} else {
			//contains legal moves
			return options;
		}
	}
	
	//Gets 
	public Object[] getPlayerOnePieces() {
		return playerOnePieces;
	}
	
	public Object[] getPlayerTwoPieces() {
		return playerTwoPieces;
	}
	
	//Gets the object at a specific row and column
	//returns null if there is no piece there or it is out of bounds
	public Object GetPiece(int row, int col) {
		return board[row][col];
	}
	
	//Returns a string that contains the visualization of the game board
	public String toString() {
		StringBuilder returnVal = new StringBuilder("	   [PLAYER TWO]\n");
		returnVal.append("  _   _   _   _   _   _   _   _  \n");
		for(int i = board.length-1; i >= 0; i--) {
			returnVal.append(i+1);
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == null) {
					returnVal.append("| | ");
				} else {
					returnVal.append("|" + board[i][j].toString().charAt(0) + "| ");
				}
			}
			returnVal.append("\n");
			if(i != 0) {
				returnVal.append("  -   -   -   -   -   -   -   -  \n");
			}
		}
		returnVal.append("  A   B   C   D   E   F   G   H    \n");
		returnVal.append("	   [PLAYER ONE]\n");
		return returnVal.toString();
	}
	
	//Clears the board of game pieces
	public void ClearBoard() {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = null;
			}
		}
		return;
	}
	
	//removed a piece from the board by taking in the location through int row & int col
	//returns true if remove was successful and false if it wasn't
	public boolean RemovePiece(int row, int col) {
		if(board[row][col] == null) {
			return false;
		}
		
		//removed the piece from the correct game piece array
		if(board[row][col].getPlayer()) {
			for(int i = 0; i < playerOnePieces.length; i++) {
				if(board[row][col].GetRow() == playerOnePieces[i].GetRow() && board[row][col].GetCol() == playerOnePieces[i].GetCol()) {
					board[row][col].ChangeStatus(false);
					break;
				}
			}
		} else {
			for(int i = 0; i < playerTwoPieces.length; i++) {
				if(board[row][col].GetRow() == playerTwoPieces[i].GetRow() && board[row][col].GetCol() == playerTwoPieces[i].GetCol()) {
					board[row][col].ChangeStatus(false);
					break;
				}
			}
		}
		
		//removes piece from board
		board[row][col] = null;
		return true;
	}
	
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	//Also checks that the player is only moving their pieces
	public boolean MovePiece(int row, int col, int moveToRow, int moveToCol, boolean player) {
		
		//checks if the piece being removed is out of bounds
		if(row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
			return false;
		}	
		if(moveToRow < 0 || moveToCol < 0 || moveToRow >= boardSize || moveToCol >= boardSize) {
			return false;
		}	
		
		if(board[row][col] == null) {
			return false;
		}
		
		if (board[row][col].toString().equals("Pawn")) {
			//Case for taking over a piece
			if(IsLegalPawn(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else if (board[row][col].toString().equals("Castle")) {
			//Case for taking over a piece
			if(IsLegalCastle(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else if (board[row][col].toString().equals("King")) {
			//Case for taking over a piece
			if(IsLegalKing(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else if (board[row][col].toString().equals("Horse")) {
			//Case for taking over a piece
			if(IsLegalHorse(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else if (board[row][col].toString().equals("Queen")) {
			//Case for taking over a piece
			if(IsLegalQueen(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else if (board[row][col].toString().equals("Bishop")) {
			//Case for taking over a piece
			if(IsLegalBishop(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				return false;
			}
		} else {
			System.out.println("Couldnt identify type of piece");
			return false;
		}
		
		
		//moves the piece
		board[row][col].ChangeLocation(moveToRow, moveToCol);
		board[row][col].moved();
		
		board[moveToRow][moveToCol] = board[row][col];
		board[row][col] = null;
		return true;
	}
	
	//returns true if move was successful and false if it couldn't move the piece
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	//This method is dependent on P1 (true) being bottom of the board and P2 (false) being the top of the board
	//This is also dependent on [0,0] being the top left of the board 🤡
	public boolean IsLegalPawn(int row, int col, int moveToRow, int moveToCol) {
		
		//disables friendly fire
		if (board[moveToRow][moveToCol] != null && board[row][col] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				return false;
			} 
		}
				
		//checks if there is no piece at the location
		if(board[row][col] == null) {					
			return false;
		}
		
		//Prevents incorrect vertical movements
		if(moveToRow <= row && (board[row][col].getPlayer() == true)) {
			return false;
		}
		if(moveToRow >= row && (board[row][col].getPlayer() == false)) {
			return false;
		}
		
		//P1 pawn tries to capture piece by moving diagonal
		if (board[moveToRow][moveToCol] != null) {
			if ((board[row][col].getPlayer() == true) && (Math.abs((row - moveToRow)) == 1)  && (board[moveToRow][moveToCol].getPlayer() != board[row][col].getPlayer())){                                   
				if((moveToCol == col+1) || (moveToCol == col-1)) {
					return true;
				} 
				
			}
		}
		
		//P2 pawn tries to capture piece by moving diagonal
		if (board[moveToRow][moveToCol] != null) {
			if ((board[row][col].getPlayer() == false) && (Math.abs((moveToRow - row)) == 1) && (board[moveToRow][moveToCol].getPlayer() != board[row][col].getPlayer())){                                 
				if((moveToCol == col+1) || (moveToCol == col-1)) {
					return true;
				} 		
			}
		}
		
		//pawn tries to move sideways
		if ((col != moveToCol) && (board[moveToRow][moveToCol] == null)) {                                    
			return false;
		}
		
		//pawn tries to move two spaces forward 
		if (((row == 1) || (row == 6)) && (Math.abs(moveToRow - row) == 2) 
				  && (board[moveToRow][moveToCol] == null) && (moveToCol == col)) {
			if(row == 1 && board[2][moveToCol] == null) {
				return true;
			}
			if(row == 6 && board[5][moveToCol] == null) {
				return true;
			}
			return false;
		}
		
		//pawn tries to move one space forward 
		if ((Math.abs(moveToRow - row) == 1) && (moveToCol == col) && board[moveToRow][moveToCol] == null) {                                   
			return true;
		}
		
		return false;
	}
	
	//This checks if the location to move the castle piece is legal
	public boolean IsLegalCastle (int row, int col, int moveToRow, int moveToCol) {
		//disables friendly fire
		if (board[moveToRow][moveToCol] != null && board[row][col] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				return false;
			}
		}
				
		//checks if there is no piece at the location
		if(board[row][col] == null) {					
			return false;
		}

		//castle cannot move ontop of itself
		if(row == moveToRow && col == moveToCol) {
			return false;
		}

		//castle must either stay in the same row or the same column
		if(row != moveToRow && col != moveToCol) {
			return false;
		}

		if(moveToCol == col) {
			//if the castle is staying in the same column but going up
			if(moveToRow > row) {
				//check every space in between the current pos and the moveToPos
				for(int i = row+1; i < moveToRow; i++) {
					//if there is a piece that the castle must jump over, return false
					if (board[i][col] != null) {
						return false;
					}
				}
			} else {
				//if the castle is staying in the same column but going down
				for(int i = row-1; i > moveToRow; i--) {
					//if there is a piece that the castle must jump over, return false
					if (board[i][col] != null) {
						return false;
					}
				}
			}
		} else {
			//if the castle is staying in the same row but going to the right
			if(moveToCol > col) {
				for(int i = col+1; i < moveToCol; i++) {
					//if there is a piece that the castle must jump over, return false
					if (board[row][i] != null) {
						return false;
					}
				}
			} else {
				for(int i = col-1; i > moveToCol; i--) {
					//if there is a piece that the castle must jump over, return false
					if (board[row][i] != null) {
						return false;
					}
				}
			}
		}
		//if no piece is found in the path the castle wants to move, return true
		return true;
	}
	
	//Checks if the king can move to the intended spot
	public boolean IsLegalKing(int row, int col, int moveToRow, int moveToCol) {
		//disables friendly fire
		if (board[moveToRow][moveToCol] != null && board[row][col] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				return false;
			} 
		}
				
		//checks if there is no king at the location original position
		if(board[row][col] == null) {					
			return false;
		}
		
		//Checks that the move is not further then one square
		int distance;
		if(row < moveToRow) {
			distance = Math.abs(moveToRow - row);
		} else if  (moveToRow < row) {
			distance = Math.abs(row - moveToRow);
		} else if (col < moveToCol) {
			distance = Math.abs(moveToCol - col);
		} else if (moveToCol < col) {
			distance = Math.abs(col - moveToCol);
		} else {
			return false;
		}
		
		//If the king move is more then one square or doesn't move at all then it is a bad move
		if((distance > 1) || (distance == 0)) {
			return false;
		}
		
		//Simulates potential move with a projected game board
		GameBoard moveCheck = new GameBoard();
		moveCheck.CopyBoard(this);
		
		moveCheck.MoveKing(row, col, moveToRow, moveToCol, board[row][col].getPlayer());
		
		//tests if player is in check after the move
		if(moveCheck.refreshCheck(board[row][col].getPlayer())) {
			System.out.println("CHECK RIP");
			return false;
		}
		
		//checks if the move is within one space of the other king
		Object currentKing = board[row][col];
		Object otherKing = null;
		
		boolean opponentPlayer;
		Object[] pieceList = null;
		if(board[row][col].getPlayer()) {
			opponentPlayer = false;
			pieceList = moveCheck.AccessPlayerPieces(opponentPlayer);
		} else {
			opponentPlayer = true;
			pieceList = moveCheck.AccessPlayerPieces(opponentPlayer);
		}
		
		for(int i = 0; i < pieceList.length; i++) {
			if(pieceList[i].toString() == "King") {
				otherKing = pieceList[i];
			}
		}
		
		//finds distance between the two kings
		int kingDistance = -1;
		if(currentKing.GetRow() < otherKing.GetRow()) {
			kingDistance = Math.abs(otherKing.GetRow() - currentKing.GetRow());
		} else if  (otherKing.GetRow() < currentKing.GetRow()) {
			kingDistance = Math.abs(currentKing.GetRow() - otherKing.GetRow());
		} else if (currentKing.GetCol() < otherKing.GetCol()) {
			kingDistance = Math.abs(otherKing.GetCol() - currentKing.GetCol());
		} else if (otherKing.GetCol() < currentKing.GetCol()) {
			kingDistance = Math.abs(currentKing.GetCol() - otherKing.GetCol());
		}

		//If the two kings is one or less then the move can't be done
		if(kingDistance <= 1) {
			return false;
		}
		
		return true;
		
	}
	
	//Method to help simulate a move for the king in islegalking
	public boolean MoveKing(int row, int col, int moveToRow, int moveToCol, boolean player) {
		
		//checks if the piece being removed is out of bounds
		if(row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
			return false;
		}	
		if(moveToRow < 0 || moveToCol < 0 || moveToRow >= boardSize || moveToCol >= boardSize) {
			return false;
		}	
		
		if(board[row][col] == null) {
			return false;
		}
		
		if (board[row][col].toString().equals("King") && board[moveToRow][moveToCol] != null) {
			//Case for taking over a piece
			RemovePiece(moveToRow, moveToCol);
		} /* else {
			System.out.println("Couldnt identify type of piece");
			return false;
		} */
		
		//moves the piece
		board[row][col].ChangeLocation(moveToRow, moveToCol);
		board[row][col].moved();
		
		board[moveToRow][moveToCol] = board[row][col];
		board[row][col] = null;
		return true;
	}
	
	
	//returns true if king is in checkmate, false if it isn't
    public boolean isCheckMate(int kingRow, int kingCol) {
		//TODO: right now this if statement only checks whether the king can move on the board without being in check or not. This doesnt account for other pieces being able to move in front of the
		//king to get them out of check
    	
    	//creates 3x3 array of pointers with King at center and sets the location of the squares in the grid
    	Point[][] possibleMoves = new Point[3][3];
    	for (int i = 0; i < possibleMoves.length; i++) {
    		for (int j = 0; j < possibleMoves.length; j++) {
    			if (i == 0 && kingRow != boardSize-1) {
    				//checks to make sure the left and right most possibilities are inside the board
    				if(((kingCol - 1 + j) >= 0 )&& ((kingCol - 1 + j) < boardSize)) {
    					possibleMoves[i][j] = new Point(kingRow + 1, kingCol - 1 + j);
    				} else {
        				possibleMoves[i][j] = null;
        			}
    			} else if (i == 1) {
    				if(((kingCol - 1 + j) >= 0) && ((kingCol - 1 + j) < boardSize)) {
    					possibleMoves[i][j] = new Point(kingRow, kingCol - 1 + j);
    				} else {
        				possibleMoves[i][j] = null;
        			}
    			} else if (i == 2 && kingRow != 0) {
    				if(((kingCol - 1 + j) >= 0 )&& ((kingCol - 1 + j) < boardSize)) {
    					possibleMoves[i][j] = new Point(kingRow - 1, kingCol - 1 + j);
    				} else {
        				possibleMoves[i][j] = null;
        			}
    			} else {
    				possibleMoves[i][j] = null;
    			}
    		}
    	}
    	
    	//Location of the king
    	possibleMoves[1][1] = new Point(kingRow, kingCol);
    	
    	Object[] friendlies;
    	Object[] enemies;
    	
    	if(board[kingRow][kingCol] == null) {
    		return false;
    	}
    	//puts game pieces into the correct object array
    	if (board[kingRow][kingCol].getPlayer() == true) {
    		friendlies = playerOnePieces;
    		enemies = playerTwoPieces;
    	} else {
    		friendlies = playerTwoPieces;
    		enemies = playerOnePieces;
    	}
    	
    	//places a null where all friendly pieces are within 1 tile of the king
    	for (int i = 0; i < friendlies.length; i++) {
    		if ((friendlies[i].GetRow() - kingRow == 1) && (friendlies[i].GetCol() - kingCol == -1)) {
    			possibleMoves[0][0] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == 1) && (friendlies[i].GetCol() - kingCol == 0)) {
    			possibleMoves[0][1] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == 1) && (friendlies[i].GetCol() - kingCol == 1)) {
    			possibleMoves[0][2] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == 0) && (friendlies[i].GetCol() - kingCol == -1)) {
    			possibleMoves[1][0] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == 0) && (friendlies[i].GetCol() - kingCol == 1)) {
    			possibleMoves[1][2] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == -1) && (friendlies[i].GetCol() - kingCol == -1)) {
    			possibleMoves[2][0] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == -1) && (friendlies[i].GetCol() - kingCol == 0)) {
    			possibleMoves[2][1] = null;
    		} else if ((friendlies[i].GetRow() - kingRow == -1) && (friendlies[i].GetCol() - kingCol == 1)) {
    			possibleMoves[2][2] = null;
    		}
    	}
    	
    	
    	//For saving the piece that actually checkmated the king
    	Point checkMateCause = new Point();
    	//tests whether the enemy pieces can move to any of the not null squares in possibleMoves Array
    	for (int j = 0; j < possibleMoves.length; j++) {
    		for (int k = 0; k < possibleMoves.length; k++) {
    				
    				//runs through the list of active enemy pieces
    				for (int i = 0; i < enemies.length; i++) {
    					//if one of the squares within 1 tile of the king is available to move to, tests whether any of the enemy pieces can attack that spot (putting the king in check if he moved)
    	    			if (possibleMoves[j][k] != null) {
    					
	    	    			//Tests if it is the pawn
	    					if (enemies[i].toString().equals("Pawn") && (enemies[i].GetStatus() == true)) {
	    						if (IsLegalPawn(enemies[i].GetRow(), enemies[i].GetCol(), (int)possibleMoves[j][k].getX(), (int)possibleMoves[j][k].getY())) {
	    							//if the possible move spot can be put in check, it is no longer a valid place for the king to go, thus turning the square null
	    							possibleMoves[j][k] = null;
	    							checkMateCause.x = enemies[i].GetRow();
	    							checkMateCause.y = enemies[i].GetCol();
	    						}
	    						
	    					} else if (enemies[i].toString().equals("Bishop") && (enemies[i].GetStatus() == true)) {
	    						if (possibleMoves[j][k] != null) {
	    							if (IsLegalBishop(enemies[i].GetRow(), enemies[i].GetCol(), kingRow, kingCol)) {
	    								//if bishop can check the king from bottom left or top right then the diagonal line of possible moves don't get the king out of check
	    								if (((enemies[i].GetRow() < kingRow) && (enemies[i].GetCol() < kingCol)) || ((enemies[i].GetRow() > kingRow) && (enemies[i].GetCol() > kingCol))) {
	    									possibleMoves[0][0] = null;
	    									possibleMoves[1][1] = null;
	    									possibleMoves[2][2] = null;
	    									checkMateCause.x = enemies[i].GetRow();
	    	    							checkMateCause.y = enemies[i].GetCol();
	        							//if bishop can check the king from top left or bottom right then the diagonal line of possible moves don't get the king out of check
	    								} else if (((enemies[i].GetRow() > kingRow) && (enemies[i].GetCol() < kingCol)) || ((enemies[i].GetRow() < kingRow) && (enemies[i].GetCol() > kingCol))) {
	    									possibleMoves[0][2] = null;
	    									possibleMoves[1][1] = null;
	    									possibleMoves[0][0] = null;
	    									checkMateCause.x = enemies[i].GetRow();
	    	    							checkMateCause.y = enemies[i].GetCol();
	    								}			
	    							}
	    						}
	    					} else if (enemies[i].toString().equals("Horse") && (enemies[i].GetStatus() == true)) {
	    						if (IsLegalHorse(enemies[i].GetRow(), enemies[i].GetCol(), (int)possibleMoves[j][k].getX(), (int)possibleMoves[j][k].getY())) {
	    							//if the possible move spot can be put in check, it is no longer a valid place for the king to go, thus turning the square null
	    							possibleMoves[j][k] = null;
	    							checkMateCause.x = enemies[i].GetRow();
	    							checkMateCause.y = enemies[i].GetCol();
	    						}
	   			
	   						} else if (enemies[i].toString().equals("Queen") && (enemies[i].GetStatus() == true)) {
	    						if (IsLegalQueen(enemies[i].GetRow(), enemies[i].GetCol(), kingRow, kingCol)) {
	    	    					//if queen can check the king from bottom left or top right then the diagonal line of possible moves don't get the king out of check
	    	   						if (((enemies[i].GetRow() < kingRow) && (enemies[i].GetCol() < kingCol)) || ((enemies[i].GetRow() > kingRow) && (enemies[i].GetCol() > kingCol))) {
	    	   							possibleMoves[2][0] = null;
	    	   							possibleMoves[1][1] = null;
	        							possibleMoves[0][2] = null;
	    	   							checkMateCause.x = enemies[i].GetRow();
		    							checkMateCause.y = enemies[i].GetCol();
	    	       					//if queen can check the king from top left or bottom right then the diagonal line of possible moves don't get the king out of check
	    	   						} else if (((enemies[i].GetRow() > kingRow) && (enemies[i].GetCol() < kingCol)) || ((enemies[i].GetRow() < kingRow) && (enemies[i].GetCol() > kingCol))) {
	        							possibleMoves[0][0] = null;
	    	   							possibleMoves[1][1] = null;
	    	   							possibleMoves[2][2] = null;
	        							checkMateCause.x = enemies[i].GetRow();
		    							checkMateCause.y = enemies[i].GetCol();
	   	    						} else if(enemies[i].GetRow() == kingRow || enemies[i].GetCol() == kingCol) {
	    	   						//needs to check if there are pieces in between in horizontal and vertical situations
	    								if (IsLegalCastle(enemies[i].GetRow(), enemies[i].GetCol(), kingRow, kingCol)) {
	    									//if the possible move spot can be put in check, it is no longer a valid place for the king to go, thus turning the square null
	   										if (enemies[i].GetRow() == kingRow) {
	   											possibleMoves[1][0] = null;
	   											possibleMoves[1][1] = null;
	   											possibleMoves[1][2] = null;
	   											checkMateCause.x = enemies[i].GetRow();
	   			    							checkMateCause.y = enemies[i].GetCol();
	    									} else if (enemies[i].GetCol() == kingCol) {
	    										possibleMoves[2][1] = null;
	    										possibleMoves[1][1] = null;
	    										possibleMoves[0][1] = null;
	    										checkMateCause.x = enemies[i].GetRow();
	    		    							checkMateCause.y = enemies[i].GetCol();
	    									} 
	    								}
	    							} else {
	    								//if the possible move spot can be put in check, it is no longer a valid place for the king to go, thus turning the square null
	    								possibleMoves[j][k] = null;
	    								checkMateCause.x = enemies[i].GetRow();
		    							checkMateCause.y = enemies[i].GetCol();
	    							}		
	    						}
	    					} else if (enemies[i].toString().equals("Castle") && (enemies[i].GetStatus() == true)) {
		    					if (IsLegalCastle(enemies[i].GetRow(), enemies[i].GetCol(), kingRow, kingCol)) {
		    						//if the possible move spot can be put in check, it is no longer a valid place for the king to go, thus turning the square null
		    						possibleMoves[j][k] = null;
		    						checkMateCause.x = enemies[i].GetRow();
	    							checkMateCause.y = enemies[i].GetCol();
		    					}
	    					}
	    				
	    					//eliminates other squares if an enemy can go there
	    					for(int z = 0; z < 3; z++) {
	    						for(int t = 0; t < 3; t++) {
	    							if(possibleMoves[z][t] != null) {
	    								if (enemies[i].toString().equals("Castle") && (enemies[i].GetStatus() == true)){
		    								if (IsLegalCastle(enemies[i].GetRow(), enemies[i].GetCol(), possibleMoves[z][t].x, possibleMoves[z][t].y)) {
		    									possibleMoves[z][t] = null;
		    								}
	    								} else if (enemies[i].toString().equals("Queen") && (enemies[i].GetStatus() == true)) {
	    									if (IsLegalQueen(enemies[i].GetRow(), enemies[i].GetCol(), possibleMoves[z][t].x, possibleMoves[z][t].y)) {
		    									possibleMoves[z][t] = null;
		    								}
	    								} else if (enemies[i].toString().equals("Bishop") && (enemies[i].GetStatus() == true)) {
	    									if (IsLegalBishop(enemies[i].GetRow(), enemies[i].GetCol(), possibleMoves[z][t].x, possibleMoves[z][t].y)) {
		    									possibleMoves[z][t] = null;
		    								}
	    								} else if (enemies[i].toString().equals("Horse") && (enemies[i].GetStatus() == true)) {
	    									if (IsLegalHorse(enemies[i].GetRow(), enemies[i].GetCol(), possibleMoves[z][t].x, possibleMoves[z][t].y)) {
		    									possibleMoves[z][t] = null;
		    								}
	    								} else if (enemies[i].toString().equals("Pawn") && (enemies[i].GetStatus() == true)) {
	    									if (IsLegalPawn(enemies[i].GetRow(), enemies[i].GetCol(), possibleMoves[z][t].x, possibleMoves[z][t].y)) {
		    									possibleMoves[z][t] = null;
		    								}
    	    							} else if (enemies[i].toString().equals("King") && (enemies[i].GetStatus() == true)) {
    	    								//LOGIC FOR A KING NOT BEING ABLE TO MOVE INTO THE IN BETWEEN SPACE FOR TWO KINGS
    	    							}
	    							}
	    						}
	    					}
    	    			}
    	    		}	
    			}
    		}
    	
    	//If all are null then the player is truly in checkmate, if not then there is a way they can block the king from being in checkmate
    	String[][] squaresToBlock = new String[8][8];
    	String checkMateCausePiece = "null";
    	if(board[checkMateCause.x][checkMateCause.y] != null) {
    		checkMateCausePiece = board[checkMateCause.x][checkMateCause.y].toString();
    	} else {
    		return false;
    	}
    	
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			if (checkMateCausePiece == "Bishop") {
    				if(IsLegalBishop(checkMateCause.x,checkMateCause.y,i, j)) {
    					if(board[i][j] != null) {
    						if(board[i][j].toString() == "King") {
    							break;
    						}
    					} else {
    						squaresToBlock[i][j] = "Open";
    					}
    				} else if(i == checkMateCause.x && j == checkMateCause.y) {
    					squaresToBlock[i][j] = "Open";
    				}
    			} else if (checkMateCausePiece == "Queen") {
    				if(IsLegalQueen(checkMateCause.x,checkMateCause.y,i, j)) {
    					if(board[i][j] != null) {
    						if(board[i][j].toString() == "King") {
    							break;
    						}
    					} else {
    						squaresToBlock[i][j] = "Open";
    					}
    				} else if(i == checkMateCause.x && j == checkMateCause.y) {
    					squaresToBlock[i][j] = "Open";
    				}
    			} else if (checkMateCausePiece == "Castle") {
    				if(IsLegalCastle(checkMateCause.x,checkMateCause.y,i, j)) {
    					if(board[i][j] != null) {
    						if(board[i][j].toString() == "King") {
    							break;
    						}
    					} else {
    						squaresToBlock[i][j] = "Open";
    					}
    				} else if(i == checkMateCause.x && j == checkMateCause.y) {
    					squaresToBlock[i][j] = "Open";
    				}
    			} else {
    				squaresToBlock[i][j] = null;
    			}
    		}
    	}
    	
    	
    	//Figures out which squares the checkmated player can block and if they can block one then checkmate method returns false since the player can still get out of checkmate
    	Object[] protectionDetail;
    	if (!board[checkMateCause.x][checkMateCause.y].getPlayer()) {
    		protectionDetail = playerOnePieces;
    	} else {
    		protectionDetail = playerTwoPieces;
    	}
    	
    	for(int i = 0; i < protectionDetail.length; i++) {
    		for(int j = 0; j < 8; j++) {
    			for(int k = 0; k < 8; k++) {
    				if(squaresToBlock[j][k] != null) {
    					if(protectionDetail[i].toString() == "Bishop") {
    						if(IsLegalBishop(protectionDetail[i].GetRow(),protectionDetail[i].GetCol(), j, k)){
    							return false;
        					}
    					} else if(protectionDetail[i].toString() == "Queen") {
    						if(IsLegalQueen(protectionDetail[i].GetRow(),protectionDetail[i].GetCol(), j, k)){
    							return false;
        					}
    					} else if(protectionDetail[i].toString() == "Castle") {
    						if(IsLegalCastle(protectionDetail[i].GetRow(),protectionDetail[i].GetCol(), j, k)){
    							return false;
        					}
    					}
    				}
        		}
    		}
    	}
    	
    	//tests if any squares are not null, if they aren't null at this point then they are a valid place for the king to move and its not checkmate. If none of the squares within 1 tile
    	//of the king are valid, the king has nowhere to move and is in checkmate.
    	for (int i = 0; i < possibleMoves.length; i++) {
    		for (int j = 0; j < possibleMoves.length; j++) {
    			if (possibleMoves[i][j] != null) {
    				return false;
    			}
    		}
    	}
    	//if none of the tiles within 1 square of king are available to move to, king is in checkmate
    	return true;
    }
	
	//Checks if the Bishop can move to the intended spot
	public boolean IsLegalBishop(int row, int col, int moveToRow, int moveToCol) {

		//disables friendly fire
		if (board[moveToRow][moveToCol] != null && board[row][col] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				return false;
			} 
		}
				
		//finds change in col and row to get from original location to new location
		int deltaCol = Math.abs(moveToCol - col);
		int deltaRow = Math.abs(moveToRow - row);
		int startRow;
		int startCol;

		//if this is true, the move is not diagonal
		if (deltaRow != deltaCol) {                                                                                     //not moving diagonal
			return false;
		}
		//if this is true, the bishop did not move
		if (deltaRow == 0 || deltaCol == 0) {                                                                           //not actually moving to a different spot
			return false;
		}
		if ((moveToRow > row) && (moveToCol > col)) {                                                                       //checking if bishop is jumping over any pieces
			//startRow and startCol are counters to test the bishop at each diagonal place that it can move
			startRow = 1;                                                                                               //when going up diagonal right
			startCol = 1;

			//while you are in between the original position and the new position
			while (startRow < deltaRow) {
				//if there is a piece on a spot along the path, return false
				if (board[row + startRow][col + startCol] != null) {
					return false;
				}
				startRow++;
				startCol++;
			}
		} else if (moveToRow > row && moveToCol < col) {                                                                //checking if bishop is jumping over any pieces
			startRow = 1;                                                                                               //when going up diagonal left
			startCol = -1;

			while (startRow < deltaRow) {
				if (board[row + startRow][col + startCol] != null) {
					return false;
				}
				startRow++;
				startCol--;
			}
		} else if (moveToRow < row && moveToCol > col) {                                                                 //checking if bishop is jumping over any pieces
			startRow = -1;                                                                                               //when going down diagonal right
			startCol = 1;

			while (startCol < deltaCol) {
				if (board[row + startRow][col + startCol] != null) {
					return false;
				}
				startRow--;
				startCol++;
			}
		} else {                                                                                                         //checking if bishop is jumping over any pieces
			startRow = -1;                                                                                               //when going down diagonal left
			startCol = -1;

			while (Math.abs(startCol) < deltaCol) {
				if (board[row + startRow][col + startCol] != null) {
					return false;
				}
				startRow--;
				startCol--;
			}
		}

		//if you try to move directly up/down or left/right then return false
		if(moveToRow == row || moveToCol == col) {
			return false;
		}
		
		return true;
	}
	
	//Checks if the queen can move to the intended spot
	public boolean IsLegalQueen(int row, int col, int moveToRow, int moveToCol) {
		if(IsLegalCastle(row,col,moveToRow,moveToCol) || IsLegalBishop(row,col,moveToRow,moveToCol)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Checks if the king can move to the intended spot
	public boolean IsLegalHorse(int row, int col, int moveToRow, int moveToCol) {
		//disables friendly fire
		if (board[moveToRow][moveToCol] != null && board[row][col] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				return false;
			} 
		}
				
		//checks if there is no piece at the location
		if(board[row][col] == null) {					
			return false;
		}
		
		int vertCounter = 0;
		int horizCounter = 0;
		
		if (Math.abs(moveToRow - row) == 2) {                                                       //Horse moves by 2 rows
			horizCounter = 2;
		}
		
		if (Math.abs(moveToRow - row) == 1) {                                                       //Horse moves by 1 row
			horizCounter = 1;
		}
		
		if (Math.abs(moveToCol - col) == 2) {                                                       //Horse moves by 2 columns
			vertCounter = 2;
		}
		
		if (Math.abs(moveToCol - col) == 1) {                                                       //Horse moves by 1 column
			vertCounter = 1;
		}
		
		if ((vertCounter == 1 && horizCounter == 2) || (vertCounter == 2 && horizCounter == 1)) {
			return true;
		}
		return false;
	}
	
	
	//Returns the gameboard
	public Object[][] AccessBoard(){
		Object[][] temp = new Object[boardSize][boardSize];
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(board[i][j] != null) {
					if(board[i][j].toString() == "Pawn") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new Pawn(true,i,j);
						} else {
							temp[i][j] = new Pawn(false,i,j);
						}
					} else if(board[i][j].toString() == "Horse") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new Horse(true,i,j);
						} else {
							temp[i][j] = new Horse(false,i,j);
						}
					} else if(board[i][j].toString() == "Castle") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new Castle(true,i,j);
						} else {
							temp[i][j] = new Castle(false,i,j);
						}
					} else if(board[i][j].toString() == "Queen") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new Queen(true,i,j);
						} else {
							temp[i][j] = new Queen(false,i,j);
						}
					} else if(board[i][j].toString() == "Bishop") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new Bishop(true,i,j);
						} else {
							temp[i][j] = new Bishop(false,i,j);
						}
					} else if(board[i][j].toString() == "King") {
						if(board[i][j].getPlayer()) {
							temp[i][j] = new King(true,i,j);
						} else {
							temp[i][j] = new King(false,i,j);
						}
					}
				} else {
					board[i][j] = null;
				}
			}
		}
		return temp;
	}
	
	//returns playerPieces array
	public Object[] AccessPlayerPieces(boolean player) {
		if(player) {
			return playerOnePieces;
		} else {
			return playerTwoPieces;
		}
	}
	
	//Copys over a 2D array of objects
	public void CopyPiecePositions(Object[][] copy) {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = copy[i][j];
			}
		}
	}
	
	//Copy gameboard method
	public void CopyBoard(GameBoard copy) {
		
		//Should apply a deep copy of the copy board to this board
		board = copy.AccessBoard();
		
		Object[] tempOne = copy.AccessPlayerPieces(true);
		Object[] tempTwo = copy.AccessPlayerPieces(false);
		for(int i = 0; i < tempOne.length; i++) {
			playerOnePieces[i] = tempOne[i];
			playerOnePieces[i].ChangeLocation(tempOne[i].GetRow(), tempOne[i].GetCol());
		}
		for(int i = 0; i < tempTwo.length; i++) {
			playerTwoPieces[i] = tempTwo[i];
			playerTwoPieces[i].ChangeLocation(tempTwo[i].GetRow(), tempTwo[i].GetCol());
		}

	}
	
	
	//returns a string with the game stats for each player so far
	public void GameStats() {
		System.out.println("\n============PLAYER ONE STATS============");
		for(int i = 0; i < boardSize*2; i++) {
			System.out.print(playerOnePieces[i].toString() + " has moved " + playerOnePieces[i].moveCount() + " time(s) and is currently  ");
			if(playerOnePieces[i].GetStatus() == false) {
				System.out.print("not on the board and was last located at ");
			} else {					
				System.out.print(" on the board and is located at ");
			}
			System.out.println("(" + playerOnePieces[i].GetRow() + "," + playerOnePieces[i].GetCol() + ")");
		}
		System.out.println("============PLAYER TWO STATS============");
		for(int i = (boardSize*2)-1; i >= 0; i--) {
			System.out.print(playerTwoPieces[i].toString() + " has moved " + playerTwoPieces[i].moveCount() + " time(s) and is currently  ");
			if(playerTwoPieces[i].GetStatus() == false) {
				System.out.print("not on the board and was last located at ");
			} else {					
				System.out.print(" on the board and is located at ");
			}
			System.out.println("(" + playerTwoPieces[i].GetRow() + "," + playerTwoPieces[i].GetCol() + ")");
		}
		return;
	}
	
	//checks if the player passed through is in check, returns true if they are and false if they aren't
	public boolean refreshCheck(boolean player) {
		int kingRowLoc = -10000;
		int kingColLoc = -10000;
		Object[] playerPieces;
		Object[] opponentPieces;
		
		if (player) {
			playerPieces = playerOnePieces;
			opponentPieces = playerTwoPieces;
		} else {
			playerPieces = playerTwoPieces;
			opponentPieces = playerOnePieces;
		}

		//finds the king and sets kingRowLoc and kingColLoc equal to the king's location
		for (int i = 0; i < playerPieces.length; i++) {
			if (playerPieces[i].toString().equals("King")) {
				kingRowLoc = playerPieces[i].GetRow();
				kingColLoc = playerPieces[i].GetCol();
				break;
			}
		}

		//tests whether its check or checkmate
		if (isCheckMate(kingRowLoc, kingColLoc)) {
			completeGame(player);
		}
		
		for (int i = 0; i < opponentPieces.length; i++) {
			if (opponentPieces[i].toString().equals("Pawn") && (opponentPieces[i].GetStatus() == true)) {
				if (IsLegalPawn(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
					System.out.println("Pawn can check");
					return true;
				}
			} else if (opponentPieces[i].toString().equals("Bishop") && (opponentPieces[i].GetStatus() == true)) {
				if (IsLegalBishop(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
					System.out.println("Bishop can check at " + opponentPieces[i].GetRow() + " " + opponentPieces[i].GetCol());
					return true;
				}
			} else if (opponentPieces[i].toString().equals("Horse") && (opponentPieces[i].GetStatus() == true)) {
				if (IsLegalHorse(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
					System.out.println("Horse can check");
					return true;
				}
			} else if (opponentPieces[i].toString().equals("Castle") && (opponentPieces[i].GetStatus() == true)) {
				if (IsLegalCastle(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
					System.out.println("Castle can check");
					return true;
				}
			} else if (opponentPieces[i].toString().equals("Queen") && (opponentPieces[i].GetStatus() == true)) {
				if (IsLegalQueen(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
					
					//needs to check if there are pieces in between in horizontal and vertical situations
					if(opponentPieces[i].GetRow() == kingRowLoc || opponentPieces[i].GetCol() == kingColLoc) {
						if (IsLegalCastle(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)) {
							System.out.println("Queen Horizontally or Vertically can check");
							return true;
						} 
					} else {
						System.out.println("Queen can check");
						//Checks to make sure is can diagonally capture
						if(IsLegalBishop(opponentPieces[i].GetRow(),opponentPieces[i].GetCol(), kingRowLoc, kingColLoc)){
							return true;
						}
						
					}
					
				}
			} else {
				//System.out.println(opponentPieces[i].toString() + " IS NOT BEING CONSIDERED AT R: " + opponentPieces[i].GetRow() + " C: " + opponentPieces[i].GetCol());
			}
		}
		return false;
	}
	
	
	//quick text to give a congratulations message to whoever wins
	public void completeGame(boolean player) {
		System.out.println("Congrats nerd, you won 🤓");
		if (player) {
			System.out.println("Player one has conquered player two");
		} else {
			System.out.println("Player two has conquered player one");
		}
	}
}
