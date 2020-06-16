
public class Pawn extends Object {
	boolean player;
	int moveCounter;
	String pieceName;
	
	//Pawn constructor that tracks:
	//Which player the pawn is controlled by (true = P1, false = P2)
	//How many times the pawn has moved (moveCounter) 
	//The name 'Pawn' stored in a string
	public Pawn(boolean player) {
		this.player = player;
		moveCounter = 0;
		pieceName = "Pawn";
	}
	
	//overrides java toString and returns the 'Pawn'
	public String toString() {
		return pieceName;
	}

	//returns the amount of times this pawn has moved within the one game 
	public int moveCount() {
		return moveCounter;
	}
	
	//returns which player this pawn is owned by
	public boolean getPlayer() {
		return player;
	}
	
	public void moved() {
		moveCounter++;
	}
}
