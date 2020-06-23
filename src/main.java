
// Main client
public class main {

	public static void main(String[] args) {

		//Nani
		GameBoard game1 = new GameBoard();
		System.out.println(game1.toString());
		//game1.GameStats();
		System.out.println("====================================");
		//game1.MovePiece(1, 0, 2, 0, false);
		game1.RemovePiece(1, 0);
		System.out.println(game1.toString());
		game1.MovePiece(0, 0, 6, 0, true);
		System.out.println(game1.toString());
		game1.MovePiece(6, 0, 6, 1, true);
		System.out.println(game1.toString());
		game1.ClearBoard();
		//game1.GameStats();
		
	}

}
