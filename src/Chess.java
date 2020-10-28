// Main client
public class Chess {

	
 //variables 
static GameBoard game1 = new GameBoard();
static boolean player = true;
static boolean success = true;
static boolean check = false;
	
	public static void main(String[] args) {
		
		//creates board
		Display display = new Display(1300,1000, game1);
		display.ConstructCanvas();
		return;
	}
}

