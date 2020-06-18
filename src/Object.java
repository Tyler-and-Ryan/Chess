
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
	//this method serves no purpose other than appeasing eclipse
	//if this method is being called during the logic of an isLegal method, something is wrong
	public boolean getPlayer() {
		return false;
	}
}
