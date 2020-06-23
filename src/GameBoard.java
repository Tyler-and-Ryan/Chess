import java.lang.StringBuilder;

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
	
	//Creates the gameboard and sets pawns in the first two rows and the last two rows
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
						temp = new Queen(false, i, j);
					}
				} else if (j == 4){
					if(i == 0) {
						temp = new King(true, i, j);
					}
					if(i == boardSize-1) {
						temp = new King(false, i, j);
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
	
	//Gets the object at a specific row and column
	//returns null if there is no piece there or it is out of bounds
	public Object GetPiece(int row, int col) {
		return board[row][col];
	}
	
	//Returns a string that contains the visualization of the gameboard
	public String toString() {
		StringBuilder returnVal = new StringBuilder("  _   _   _   _   _   _   _   _  \n");
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
		returnVal.append("  A   B   C   D   E   F   G   H    ");
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
		//checks if the piece being removed is out of bounds
		if(row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
			return false;
		}
		
		//checks if there is no piece at the location
		if(board[row][col] == null) {
			return false;
		}
		
		//removed the piece from the correct game piece array
		if(board[row][col].getPlayer()) {
			for(int i = 0; i < playerOnePieces.length; i++) {
				if(board[row][col].GetRow() == playerOnePieces[i].GetRow() && board[row][col].GetCol() == playerOnePieces[i].GetCol()) {
					playerOnePieces[i] = null;
					break;
				}
			}
		} else {
			for(int i = 0; i < playerOnePieces.length; i++) {
				if(board[row][col].GetRow() == playerTwoPieces[i].GetRow() && board[row][col].GetCol() == playerTwoPieces[i].GetCol()) {
					playerTwoPieces[i] = null;
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
	public void MovePiece(int row, int col, int moveToRow, int moveToCol, boolean player) {
		if (board[row][col].toString().equals("Pawn")) {
			//Case for taking over a piece
			if(IsLegalPawn(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				return;
			}
		} else {
			System.out.println("Couldnt identify type of piece");
			return;
		}
		
		//moves the piece
		board[row][col].ChangeLocation(moveToRow, moveToCol);
		board[row][col].moved();
		toString();
		board[moveToRow][moveToCol] = board[row][col];
		board[row][col] = null;
	}
	
	//returns true if move was successful and false if it couldn't move the piece
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	//This method is dependent on P1 (true) being bottom of the board and P2 (false) being the top of the board
	//This is also dependent on [0,0] being the top left of the board
	private boolean IsLegalPawn(int row, int col, int moveToRow, int moveToCol) {
		
		if ((col != moveToCol) && (board[moveToRow][moveToCol] == null)) {                                       //pawn tries to move sideways 
			return false;
		} else if (((row == 2) || (row == 7)) && (Math.abs(moveToRow - row) == 2) 
				  && (board[moveToRow][moveToCol] == null) && (moveToCol == col)) {                              //pawn tries to move two spaces forward 
			return true;
		} else if ((Math.abs(moveToRow - row) == 1) && (moveToCol == col)) {                                     //pawn tries to move one space forward 
			return true;
		} else if (board[moveToRow][moveToCol].isGamePiece()) {
			if ((board[row][col].getPlayer() == true) && ((row - moveToRow) == 1) 
			   && (Math.abs(moveToCol - col) == 1)) {                                                            //P1 pawn tries to capture piece by moving diagonal
				return true;
			}
		} else if (board[row][col].isGamePiece()) {
			if ((board[row][col].getPlayer() == false) && ((moveToRow - row) == 1) 
			   && (Math.abs(moveToCol - col) == 1)) {                                                            //P2 pawn tries to capture piece by moving diagonal
				return true;
			}
		}
		//fail safe to appease eclipse
		return false;
	}
	
	//returns a string with the game stats for each player so far
	public void GameStats() {
		System.out.println("\n============PLAYER ONE STATS============");
		for(int i = 0; i < boardSize*2; i++) {
			if(playerOnePieces[i] != null) {
				System.out.println(playerOnePieces[i].toString() + " has moved " + playerOnePieces[i].moveCount() + " at " + playerOnePieces[i].GetRow() + "," + playerOnePieces[i].GetCol());
			}
		}
		System.out.println("============PLAYER TWO STATS============");
		for(int i = (boardSize*2)-1; i >= 0; i--) {
			if(playerTwoPieces[i] != null) {
				System.out.println(playerTwoPieces[i].toString() + " has moved " + playerTwoPieces[i].moveCount()+ " at " + playerTwoPieces[i].GetRow() + "," + playerTwoPieces[i].GetCol());
			}
		}
		return;
	}
}
