
// Main client
public class main {

	public static void main(String[] args) {

		//Nani
		GameBoard game1 = new GameBoard();
		System.out.println(game1.toString());
		//game1.GameStats();
		System.out.println("====================================");
		game1.MovePiece(6, 0, 5, 0, false);
		System.out.println(game1.toString());
		System.out.println("0,0: " + game1.GetPiece(2, 0).toString());
		//game1.GameStats();
		
	}

}
