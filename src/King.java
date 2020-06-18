
public class King extends Object{
	boolean player;
	int moveCounter;
	String pieceName;
	
	//King constructor that tracks:
	//Which player the King is controlled by (true = P1, false = P2)
	//How many times the King has moved (moveCounter) 
	public King(boolean player) {
		this.player = player;
		moveCounter = 0;
		pieceName = "K";
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
