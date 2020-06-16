
public class GameBoard {
	private Object[][] board;
	private int boardSize;
	
	//Constructor
	public GameBoard(int size){
		boardSize = size;
		CreateBoard();
	}
	
	//Creates the gameboard and sets pawns in the first two rows and the last two rows
	private void CreateBoard() {
		board = new Object[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(i == 0 || i == 1) {
					Pawn temp = new Pawn(true);
					board[i][j] = temp;
				} else if (i == boardSize-1 || i == boardSize-2){ 
					Pawn temp = new Pawn(false);
				 	board[i][j] = temp;
				} else {
					board[i][j] = null;
				}
			}
		}
		return;
	}
	
	//Gets the object at a specific row and column
	//returns null if there is no piece there or it is out of bounds
	public Object GetPiece(int row, int col) {
		return null;
	}
	
	//Returns a string that contains the visualization of the gameboard
	public String ToString() {
		return "not implemented";
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
		return false;
	}
	
	//returns a string with the game stats for each player so far
	public String GameStats() {
		return "";
	}
}
