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
	
	//Returns a string that contains the visualization of the gameboard
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
			for(int i = 0; i < playerOnePieces.length; i++) {
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
		//Decrements row by one to match the board toString

		
		//checks if the piece being removed is out of bounds
		if(row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
			System.out.println("ILLEGAL MOVE - BRO UR BAD");
			System.out.println("Try a different move");
			return false;
		}	
		if(moveToRow < 0 || moveToCol < 0 || moveToRow >= boardSize || moveToCol >= boardSize) {
			System.out.println("ILLEGAL MOVE - BRO UR BAD");
			System.out.println("Try a different move");
			return false;
		}	
		
		//disables friendly fire
		if (board[moveToRow][moveToCol] != null) {
			if (board[row][col].getPlayer() == board[moveToRow][moveToCol].getPlayer()) {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
			}
			return false;
		}
		
		//checks if there is no piece at the location
		if(board[row][col] == null) {					
			System.out.println("ILLEGAL MOVE - BRO UR BAD");
			System.out.println("Try a different move");
			return false;
		}
				
		if (board[row][col].toString().equals("Pawn")) {
			//Case for taking over a piece
			if(IsLegalPawn(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else if (board[row][col].toString().equals("Castle")) {
			//Case for taking over a piece
			if(IsLegalCastle(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else if (board[row][col].toString().equals("King")) {
			//Case for taking over a piece
			if(IsLegalKing(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else if (board[row][col].toString().equals("Horse")) {
			//Case for taking over a piece
			if(IsLegalHorse(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else if (board[row][col].toString().equals("Queen")) {
			//Case for taking over a piece
			if(IsLegalQueen(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else if (board[row][col].toString().equals("Bishop")) {
			//Case for taking over a piece
			if(IsLegalBishop(row, col, moveToRow, moveToCol) && board[row][col].isGamePiece()) {
				RemovePiece(moveToRow, moveToCol);
			} else {
				System.out.println("ILLEGAL MOVE - BRO UR BAD");
				System.out.println("Try a different move");
				return false;
			}
		} else {
			System.out.println("Couldnt identify type of piece");
			return false;
		}
		
		//moves the piece
		board[row][col].ChangeLocation(moveToRow, moveToCol);
		board[row][col].moved();
		toString();
		board[moveToRow][moveToCol] = board[row][col];
		board[row][col] = null;
		return true;
	}
	
	//returns true if move was successful and false if it couldn't move the piece
	//takes in parameters of the location of the piece being moved & the location the player wants to move the piece to 
	//This method is dependent on P1 (true) being bottom of the board and P2 (false) being the top of the board
	//This is also dependent on [0,0] being the top left of the board
	private boolean IsLegalPawn(int row, int col, int moveToRow, int moveToCol) {
		
		if ((col != moveToCol) && (board[moveToRow][moveToCol] == null)) {                                       //pawn tries to move sideways
			return false;
		} else if (((row == 1) || (row == 6)) && (Math.abs(moveToRow - row) == 2) 
				  && (board[moveToRow][moveToCol] == null) && (moveToCol == col)) {                              //pawn tries to move two spaces forward 
			return true;
		} else if ((Math.abs(moveToRow - row) == 1) && (moveToCol == col)) {                                     //pawn tries to move one space forward 
			return true;
		} else if (board[moveToRow][moveToCol].isGamePiece()) {
			if ((board[row][col].getPlayer() == true) && (Math.abs((row - moveToRow)) == 1) && 
			   ((Math.abs(row-moveToRow) == 1 && moveToCol-col == 1)  
			   || (row - moveToRow == 1 && Math.abs(moveToCol - col) == 1))) {                                   //P1 pawn tries to capture piece by moving diagonal
				return true;
			}
		} else if (board[row][col].isGamePiece()) {
			if ((board[row][col].getPlayer() == false) && ((moveToRow - row) == 1) 
			   && (Math.abs(moveToCol - col) == 1)) {                                                            //P2 pawn tries to capture piece by moving diagonal
				return true;
			}
		}

		return false;
	}
	
	//This checks if the location to move the castle piece is legal
	private boolean IsLegalCastle (int row, int col, int moveToRow, int moveToCol) {
		if(row == moveToRow || col == moveToCol) {
			boolean pieceInBetween = false;
			if(col == moveToCol) {
				int start;
				int distance; 
				if(row < moveToRow) {
					start = row;
					distance = Math.abs(moveToRow-row);
				} else if  (moveToRow < row) {
					start = moveToRow;
					distance = Math.abs(row-moveToRow);
				} else {
					return false;
				}
				
				for(int i = start+1; i <= start+distance; i++) {
					if(board[i][col] != null && i < moveToRow) {
						return false;
					}
				}
				return true;
			} else {
				int start;
				int distance; 
				if(col < moveToCol) {
					start = col;
					distance = Math.abs(moveToCol-col);
				} else if  (moveToCol < col) {
					start = moveToCol;
					distance = Math.abs(col-moveToCol);
				} else {
					return false;
				}
				for(int i = start+1; i <= start+distance; i++) {
					if(board[row][i] != null && i < moveToCol) {
						return false;
					}
				}
				return true;
			}
		} else {
			return false;
		}
	}
	
	//Checks if the king can move to the intended spot
	private boolean IsLegalKing(int row, int col, int moveToRow, int moveToCol) {
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
		
		
		if((distance > 1) || (distance == 0)) {
			return false;
		}
		return true;
	}
	
	//Checks if the Bishop can move to the intended spot
	private boolean IsLegalBishop(int row, int col, int moveToRow, int moveToCol) {
		
		if(moveToRow > row && moveToCol > col){
			int i = row+1;
			int j = col+1;
			while(i != moveToRow && i < boardSize && j < boardSize && j != moveToCol && i >= 0 && j >= 0) {
				if(board[i][j] != null) {
					return false;
				}
				i++;
				j++;
			}
		}
		if(moveToRow > row && moveToCol < col){
			int i = row+1;
			int j = col-1;
			while(i != moveToRow && i < boardSize && j < boardSize && j != moveToCol && i >= 0 && j >= 0) {
				if(board[i][j] != null) {
					return false;
				}
				i++;
				j--;
			}
			
		}
		if(moveToRow < row && moveToCol > col){
			int i = row-1;
			int j = col+1;
			while(i != moveToRow && i < boardSize && j < boardSize && j != moveToCol && i >= 0 && j >= 0) {
				if(board[i][j] != null) {
					return false;
				}
				i--;
				j++;
			}
		}
		if(moveToRow < row && moveToCol < col){
			int i = row-1;
			int j = col-1;
			while(i != moveToRow && i < boardSize && j < boardSize && j != moveToCol && i >= 0 && j >= 0) {
				if(board[i][j] != null) {
					return false;
				}
				i--;
				j--;
			}
		}
		return true;
	}
	
	//Checks if the queen can move to the intended spot
	private boolean IsLegalQueen(int row, int col, int moveToRow, int moveToCol) {
		if(IsLegalCastle(row,col,moveToRow,moveToCol) == true || IsLegalBishop(row,col,moveToRow,moveToCol)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Checks if the king can move to the intended spot
	private boolean IsLegalHorse(int row, int col, int moveToRow, int moveToCol) {
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
}
