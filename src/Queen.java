
public class Queen extends Object{
	boolean player;
	int moveCounter;
	String pieceName;
	
	//Queen constructor that tracks:
	//Which player the Queen is controlled by (true = P1, false = P2)
	//How many times the Queen has moved (moveCounter) 
	public Queen(boolean player) {
		this.player = player;
		moveCounter = 0;
		pieceName = "Q";
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
