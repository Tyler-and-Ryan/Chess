import java.awt.Point;
import java.util.ArrayList;

public class Alexa {
	
	GameBoard game;
	
	public static final int KING = 20;
	public static final int QUEEN = 10;
	public static final int CASTLE = 7;
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
		
		
		//gets all the possible legal moves available
		Object[] AIPieces = game.getPlayerTwoPieces();
		
		//sorts the best legal move to position 0 in the array list
		int maxScore = -1;
		for(int i = 0; i < AIPieces.length; i++) {
			int row = AIPieces[0].GetRow();
			int col = AIPieces[0].GetCol();
			
			
			if(game.GetPiece(row,col) != null) {
				//Gets legal moves for current option, it will be null if no legal moves are possible
				ArrayList<Point> temp = new ArrayList<Point>();
				temp = game.LegalMoves(row, col);
				
				//Finds the most valuable move for each option if it exists.
				if(temp != null) {
					for(int k = 0; k < temp.size(); k++) {
						//Resets move score to test a new legal move
						int moveScore = 0;
						if(game.GetPiece(temp.get(k).x, temp.get(k).y) != null) {
							if(game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Pawn") {
								moveScore = PAWN;
							} else if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Queen") {
								moveScore = QUEEN;
							} else if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Castle") {
								moveScore = CASTLE;
							} else if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Bishop") {
								moveScore = BISHOP;
							} else if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Horse") {
								moveScore = HORSE;
							} else if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "King") {
								moveScore = KING;
							}
						}
						
						//If it is the best move
						if(moveScore > maxScore) {
							Point[] bestChoice = new Point[2];
							bestChoice[0] = new Point(row, col);
							bestChoice[1] = new Point(temp.get(k).x, temp.get(k).y);
							move = bestChoice;
							maxScore = moveScore;
						}
						
					}
				}	 
			}
			
		}
		
		//if best move score = 0 then select random piece and move it
		if(maxScore == 0) {
			boolean goodMove = false;
			while(goodMove == false) {
				int val = (int)(Math.random() * AIPieces.length);
				
				if((game.LegalMoves(AIPieces[val].GetRow(), AIPieces[val].GetCol()) != null) && (game.GetPiece(AIPieces[val].GetRow(), AIPieces[val].GetCol()) != null)) {
					ArrayList<Point> temp = new ArrayList<Point>();
					temp = game.LegalMoves(AIPieces[val].GetRow(), AIPieces[val].GetCol());
					Point[] bestChoice = new Point[2]; 
					bestChoice[0] = new Point(AIPieces[val].GetRow(), AIPieces[val].GetCol());
					bestChoice[1] = temp.get(0);
					move = bestChoice;
					goodMove = true;
				}
			}
		}
		
		
		//Gets the origin of the best move and the final destination for the move
		moveCounter++;
		return move;
	}
}
