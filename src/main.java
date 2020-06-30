import java.util.Scanner;


// Main client
public class main {

	
 //variables 
static GameBoard game1 = new GameBoard();
static boolean player = true;
static boolean success = true;
static boolean check = false;
static boolean quit = false;
	
	public static void main(String[] args) {
		
		//creates board
		Display display = new Display(1000,1000, game1);
		
		Scanner userInput = new Scanner(System.in);
		String currentPieceLoc;
		String moveToLoc;
		
		System.out.println(game1.toString());
		
		
		//System.out.println("P1 gets the first turn. If you would like to abort the game, then type 'quit'. If not, type 'continue' and press enter.");
		while (!userInput.nextLine().equals("quit")) {
			/*
			System.out.println("Which square contains the piece you would like to move next?");
			currentPieceLoc = userInput.nextLine();
			System.out.println("Which square would you like your selected piece to move to?");
			moveToLoc = userInput.nextLine();
			success = attemptMove(currentPieceLoc, moveToLoc);
			if (success) {
				player = !player;
				System.out.println(game1.toString());
				if (player == true) {
					System.out.println("It is now Player 1's turn.");
				} else {
					System.out.println("It is now Player 2's turn.");
				}
				System.out.println("If you would like to abort the game, then type 'quit'. If not, type 'continue' and press enter.");
			} else {
				//something went wrong
			}*/
		}
		
		System.out.println(game1.toString());
		//display.UpdateBoard(game1);
		//display.RefreshBoard();
		
		//game1.GameStats();
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
	
	public static void completeGame() {
		System.out.println("Congrats nerd, you won 🤓");
		if (player) {
			System.out.println("Player one has conquered player two");
		} else {
			System.out.println("Player two has conquered player one");
		}
		quit = true;
	}
}
