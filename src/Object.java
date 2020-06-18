
public class Object {
	
	//this is the object parent class since each game piece is a type of object
	public Object() {
	}
	
	//overrides java toString and returns the 'Pawn'
	public String toString() {
		return "null";
	}

	public boolean isGamePiece() {
		return false;
	}
	
	public int moveCount() {
		return -1;
	}
}
