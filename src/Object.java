
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
		System.out.println("trying to work with blank object instead of piece");
		return false;
	}
	
	//appeasing eclipse
	public void ChangeLocation(int row, int col) {
		System.out.println("trying to work with blank object instead of piece");
		return;
	}
	
	//appeasing eclipse
	public String toString() {
		return "BRUH UR BAD";
	}
	
	//appeasing eclipse
	public void moved() {
		System.out.println("trying to work with blank object instead of piece");
		return;
	}
	
	public boolean GetStatus() {
		return false;
	}
	
	public void ChangeStatus(boolean status) {
		System.out.println("Error");
		return;
	}
}
