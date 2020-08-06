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
		
		Object piece = null;
		
		ArrayList<Point[]> options = new ArrayList<Point[]>();
		
		//gets all the possible legal moves available
		Object[] AIPieces = game.getPlayerTwoPieces();
		for(int i = 0; i < AIPieces.length; i++) {
			piece = AIPieces[i];
		
			if(piece.GetStatus() == true && !game.refreshCheck(false)) {
				ArrayList<Point> legalMoves = game.LegalMoves(piece.GetRow(), piece.GetCol());
				if(legalMoves != null) {
					for(int j = 0; j < legalMoves.size(); j++) {
						Point[] temp = new Point[2];
						temp[0] = new Point(piece.GetRow(), piece.GetCol());
						temp[1] = legalMoves.get(j);
						options.add(temp);
					}
				}
			}
		}
		
		//sorts the best legal move to position 0 in the arraylist
		int maxScore = 0;
		for(int i = 0; i < options.size(); i++) {
			Point[] moveTemp = options.get(i);
			int row = moveTemp[1].x;
			int col = moveTemp[1].y;
			int moveScore = 0;
			
			if(game.GetPiece(row,col) != null) {
				if(game.GetPiece(row, col).toString() == "Pawn") {
					moveScore = PAWN;
				} else if (game.GetPiece(row, col).toString() == "Queen") {
					moveScore = QUEEN;
				} else if (game.GetPiece(row, col).toString() == "Castle") {
					moveScore = CASTLE;
				} else if (game.GetPiece(row, col).toString() == "Bishop") {
					moveScore = BISHOP;
				} else if (game.GetPiece(row, col).toString() == "Horse") {
					moveScore = HORSE;
				} else if (game.GetPiece(row, col).toString() == "King") {
					moveScore = KING;
				} 
			}
			
			if(moveScore > maxScore) {
				moveScore = maxScore;
				options.add(0, options.get(i));
				options.remove(options.get(i+1));
			}
			
		}
		
		//Gets the origin of the best move and the final destination for the move
		move = options.get(0);
		
		moveCounter++;
		
		return move;
	}
}
