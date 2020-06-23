import java.util.Scanner;

// Main client
public class main {

	public static void main(String[] args) {

		//Nani
		GameBoard game1 = new GameBoard();
		System.out.println(game1.toString());
		//game1.GameStats();
		System.out.println("====================================");
		//game1.MovePiece(1, 0, 2, 0, false);
		game1.RemovePiece(1, 0);
		System.out.println(game1.toString());
		game1.MovePiece(0, 0, 6, 0, true);
		System.out.println(game1.toString());
		game1.MovePiece(6, 0, 6, 1, true);
		System.out.println(game1.toString());
		game1.ClearBoard();
		//game1.GameStats();
		
		Scanner userInput = new Scanner(System.in);
		String currentPieceLoc;
		String moveToLoc;
		
		System.out.println("P1 gets the first turn.");
		while (!userInput.next().equals("quit")) {
			System.out.println("Which square contains the piece you would like to move next?");
			currentPieceLoc = userInput.next();
			System.out.println("Which square would you like your selected piece to move to?");
			moveToLoc = userInput.next();
			
		}
	}

		//interprets the user input containing a letter representing a column and a number representing a row and converts it to row/col ints
		public void attemptMove(String currentPieceLoc, String moveToLoc) {
			int row;
			int col;
			int moveToRow;
			int moveToCol;
			
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
				return;
			}
		}
}
