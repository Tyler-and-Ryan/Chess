import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Alexa {
	
	GameBoard game;
	
	public static final int KING = 20;
	public static final int QUEEN = 10;
	public static final int CASTLE = 5;
	public static final int BISHOP = 5;
	public static final int HORSE = 3;
	public static final int PAWN = 1;
	
	int moveCounter;
	
	//Constructor
	public Alexa() {
		game = new GameBoard();
		moveCounter = 0;
		
	}
	
	
	public void UpdateBoard(GameBoard newBoard) {
		game.CopyBoard(newBoard);
	}
	
	//currently only does random moves
	public Point[] GenerateMove(){
		//Position 0 is the original row/col
		//position 1 is the new row/col
		Point[] move = new Point[2];
		
		Random rand = new Random();
		boolean validMove = false;
		
		Object piece = null;
		ArrayList<Point> options = null;
		
		//Gets a piece on the board to move
		boolean goodPiece = false;
		while(goodPiece == false) {
			int pieceNumber = rand.nextInt(16);
			Object[] AIPieces = game.getPlayerTwoPieces();
			piece = AIPieces[(pieceNumber%AIPieces.length)];
					
			if(piece.GetStatus() == true && !game.refreshCheck(false)) {
				options = game.LegalMoves(piece.GetRow(), piece.GetCol());
				if(options != null) {
					goodPiece = true;
				}
			}
		}
			
		//Gets location of new move and original location
		Point original = new Point(piece.GetRow(),piece.GetCol());
		int randomOption = rand.nextInt(options.size());
		Point moveLocation = options.get(randomOption%options.size());
			
		move[0] = original;
		move[1] = moveLocation;
		
		
		return move;
	}
}
