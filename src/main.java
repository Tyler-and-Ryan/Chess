import java.util.Scanner;


// Main client
public class main {

	
 //variables 
static GameBoard game1 = new GameBoard();
static boolean player = true;
static boolean success = true;
static boolean check = false;
	
	public static void main(String[] args) {
		
		//creates board
		Display display = new Display(1000,1000, game1);
		
		//while()
		
		return;
	}

	//interprets the user input containing a letter representing a column and a number representing a row and converts it to row/col ints
	public static boolean attemptMove(String currentPieceLoc, String moveToLoc) {
		int row = Integer.parseInt(currentPieceLoc.substring(1,2)) - 1;
		int col = -1;
		int moveToRow = Integer.parseInt(moveToLoc.substring(1,2)) - 1;
		int moveToCol = -1;
		
		if(currentPieceLoc.substring(0,1).equals("A")) {
			col = 0;
		} else if(currentPieceLoc.substring(0,1).equals("B")) {
			col = 1;
		} else if(currentPieceLoc.substring(0,1).equals("C")) {
			col = 2;
		} else if(currentPieceLoc.substring(0,1).equals("D")) {
			col = 3;
		} else if(currentPieceLoc.substring(0,1).equals("E")) {
			col = 4;
		} else if(currentPieceLoc.substring(0,1).equals("F")) {
			col = 5;
		} else if(currentPieceLoc.substring(0,1).equals("G")) {
			col = 6;
		} else if(currentPieceLoc.substring(0,1).equals("H")) {
			col = 7;
		} else {
			System.out.println("Unrecognized input, try again");
		}
		
		if(moveToLoc.substring(0,1).equals("A")) {
			moveToCol = 0;
		} else if(moveToLoc.substring(0,1).equals("B")) {
			moveToCol = 1;
		} else if(moveToLoc.substring(0,1).equals("C")) {
			moveToCol = 2;
		} else if(moveToLoc.substring(0,1).equals("D")) {
			moveToCol = 3;
		} else if(moveToLoc.substring(0,1).equals("E")) {
			moveToCol = 4;
		} else if(moveToLoc.substring(0,1).equals("F")) {
			moveToCol = 5;
		} else if(moveToLoc.substring(0,1).equals("G")) {
			moveToCol = 6;
		} else if(moveToLoc.substring(0,1).equals("H")) {
			moveToCol = 7;
		} else {
			System.out.println("Unrecognized input, try again");
		}
		System.out.println("Row: " + row + " Col: " + col + " moveToRow: " + moveToRow + " moveToCol: " + moveToCol);
		return game1.MovePiece(row, col, moveToRow, moveToCol, player);
	}
}
	


