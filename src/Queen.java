
public class Queen extends Object{
	boolean player;
	int moveCounter;
	String pieceName;
	boolean active;
	int row;
	int col;
	
	//Queen constructor that tracks:
	//Which player the Queen is controlled by (true = P1, false = P2)
	//How many times the Queen has moved (moveCounter) 
	public Queen(boolean player, int row, int col) {
		this.player = player;
		moveCounter = 0;
		pieceName = "Queen";
		active = true;
		this.row = row;
		this.col = col;
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
	//returns the row the piece is in
	public int GetRow(){
		return row;
	}
	
	//returns the column the piece is in
	public int GetCol() {
		return col;
	}
	
	public void ChangeLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean GetStatus() {
		return active;
	}
	
	public void ChangeStatus(boolean status) {
		active = status;
	}
}
