
public class Horse extends Object{
	boolean player;
	int moveCounter;
	String pieceName;
	
	//Horse constructor that tracks:
	//Which player the Horse is controlled by (true = P1, false = P2)
	//How many times the Horse has moved (moveCounter) 
	public Horse(boolean player) {
		this.player = player;
		moveCounter = 0;
		pieceName = "Horse";
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
	
	public boolean isGamePiece() {
		return true;
	}
}
