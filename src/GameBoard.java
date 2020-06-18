import java.lang.StringBuilder;

public class GameBoard {
	private Object[][] board;
	private int boardSize;
	
	//Constructor 
	//TODO: (add implementation later to have variable board size)
	public GameBoard(){
		boardSize = 8;
		CreateBoard();
	}
	
	//Creates the gameboard and sets pawns in the first two rows and the last two rows
	private void CreateBoard() {
		board = new Object[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Object temp = null;
				if(i == 1) {
					temp = new Pawn(true);
				} else if (i == boardSize-2){ 
					temp = new Pawn(false);
				} else if (j == 0 || j == boardSize-1){
					if(i == 0) {
						temp = new Castle(true);
					}
					if(i == boardSize-1) {
						temp = new Castle(false);
					}
				} else if (j == 1 || j == boardSize-2){
					if(i == 0) {
						temp = new Horse(true);
					}
					if(i == boardSize-1) {
						temp = new Horse(false);
					}
				} else if (j == 2 || j == boardSize-3){
					if(i == 0) {
						temp = new Bishop(true);
					}
					if(i == boardSize-1) {
						temp = new Bishop(false);
					}
				} else if (j == 3){
					if(i == 0) {
						temp = new Queen(true);
					}
					if(i == boardSize-1) {
						temp = new Queen(false);
					}
				} else if (j == 4){
					if(i == 0) {
						temp = new King(true);
					}
					if(i == boardSize-1) {
						temp = new King(false);
					}
				}
				board[i][j] = temp;
			}
		}
	}
	
	//Gets the object at a specific row and column
	//returns null if there is no piece there or it is out of bounds
	public Object GetPiece(int row, int col) {
		return null;
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
					returnVal.append("|" + board[i][j].toString() + "| ");
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
	
	//returns true if it was able to remove the piece and false if it couldn't
	public boolean RemovePiece(int row, int col) {
		return false;
	}
	
	//returns true if move was successful and false if it couldn't move the piece
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	//Also checks that the player is only moving their pieces
	public boolean MovePiece(int row, int col, int moveToRow, int moveToColumn, boolean player) {
		return false;
	}
	
	//returns true if move was successful and false if it couldn't move the piece
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	private boolean IsLegalPawn(int row, int col, int moveToRow, int moveToColumn) {
		if ((col != moveToColumn) && (board[moveToRow][moveToColumn] == null)) {                                    //pawn tries to move sideways
			return false;
		} else if (((row == 2) || (row == 7)) && (Math.abs(moveToRow - row) == 2) 
				  && (board[row][col] == null) && (moveToColumn == col)) {                                          //pawn tries to move two spaces forward
			return true;
		} else if ((Math.abs(moveToRow - row) == 1) && (moveToColumn == col)) {                                     //pawn tries to move one space forward
			return true;
		} else if ((board[row][col].getPlayer() == true) && ((row - moveToRow) == 1) 
				  && (Math.abs(moveToColumn - col) == 1) && (board[moveToRow][moveToColumn] != null)) {             //pawn tries to capture piece by moving diagonal
			return true;
		}
	}
	
	//returns a string with the game stats for each player so far
	public String GameStats() {
		return "";
	}
}
