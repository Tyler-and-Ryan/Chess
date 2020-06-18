
public class Object {
	
	
	

	public int moveCount() {
		return -1;
	}
	//this method serves no purpose other than appeasing eclipse
	//if this method is being called during the logic of an isLegal method, something is wrong
	public boolean getPlayer() {
		return false;
	}
	
	//returns the row the piece is in
	public int GetRow(){
		return -1;
	}
		
	//returns the column the piece is in
	public int GetCol() {
		return -1;
	}

	//appeasing eclipse
	public boolean isGamePiece() {
		return false;
	}
}
