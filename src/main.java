import java.util.Scanner;


// Main client
public class main {

	
 //variables 
static GameBoard game1 = new GameBoard();
static boolean player = true;
static boolean fail = true;
	
	public static void main(String[] args) {
		
		//creates board
		Display display = new Display();
				
		if(true) {
			Scanner closeCanvas = new Scanner(System.in);
			if(closeCanvas.next().equals("quit")) {
				display.CloseBoard();
				System.out.println("closing canvas");
			}
		}
		
		Scanner userInput = new Scanner(System.in);
		String currentPieceLoc;
		String moveToLoc;
		
		System.out.println(game1.toString());
		System.out.println("P1 gets the first turn. Are you ready to begin the game?");
		while (!userInput.next().equals("quit")) {
			System.out.println("Which square contains the piece you would like to move next?");
			currentPieceLoc = userInput.next();
			System.out.println("Which square would you like your selected piece to move to?");
			moveToLoc = userInput.next();
			fail = attemptMove(currentPieceLoc, moveToLoc);
			if (fail) {
				//you entered something wrong
			} else {
				player = !player;
				System.out.println(game1.toString());
				System.out.println("If you would like to quit, then type \"quit\"");
			}
		}
	}

	//interprets the user input containing a letter representing a column and a number representing a row and converts it to row/col ints
	public static boolean attemptMove(String currentPieceLoc, String moveToLoc) {
		int row = Integer.parseInt(currentPieceLoc.substring(1,2));
		int col = -1;
		int moveToRow = Integer.parseInt(moveToLoc.substring(1,2));
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
		
		return game1.MovePiece(row, col, moveToRow, moveToCol, player);
	}
}
