
// Main client
public class main {

	public static void main(String[] args) {

		//Nani
		GameBoard game1 = new GameBoard();
		System.out.println(game1.toString());
		//game1.GameStats();
		System.out.println("====================================");
		game1.MovePiece(1, 0, 2, 0, false);
		System.out.println(game1.toString());
		//game1.GameStats();
		
	}

}
