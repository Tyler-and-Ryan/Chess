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
		game.ClearBoard();
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



		//KING IS IN CHECK -> SEE IF KING CAN MOVE ITSELF OUT OF CHECK
		//If king is in check, start looking for a new possible move to get out of check
		int kingRow = -1;
		int kingCol = -1;
		//Finds king piece and sets a copy King piece equal to its position for isLegal testing purposes
		for(int i = 0; i < AIPieces.length; i++) {
			if(AIPieces[i].toString() == "King") {
				kingRow = AIPieces[i].GetRow();
				kingCol = AIPieces[i].GetCol();
			}
		}
		King king = new King(kingRow, kingCol);
		if((game.refreshCheck(false) == true)) { //possible issue with refreshCheck - investigate
			System.out.println("CHECK DETECTED");
			GameBoard temp = new GameBoard();
			temp.CopyBoard(game);
			boolean ableToMoveKing = false;

			//looking for possible moves for the King to get out of check
			for(int j = 0; j < 8; j++) {
				for(int k = 0; k < 8; k++) {
					if((j == 6) && (k == 4)) {
						System.out.println("SHOULD BE LEGAL");
					}
					if(temp.IsLegalKing(king.GetRow(), king.GetCol(), j, k)) {  //IsLegalKing is also suspect method
						System.out.println("EXAMPLE1: R " + j + " C " + k);
						temp.MovePiece(king.GetRow(), king.GetCol(), j, k, false);
						if(temp.refreshCheck(false) == false) { //if a legal move was found, see if it gets you out of check
							move[0] = new Point(king.GetRow(), king.GetCol());
							move[1] = new Point(j,k);
							System.out.println("King to move to row " + j + " col " + k);
							maxScore = -1;
							ableToMoveKing = true;
							break;
						} else {
							temp.CopyBoard(game);
						}
					}
				}
			}

			//KING IS STILL IN CHECK -> SEE IF A PIECE CAN MOVE IN FRONT OF THE KING TO GET IT OUT OF CHECK
			//If the king cannot be moved to get itself out of check, then Alexa will try to throw
			// a piece in front of the king to get it out of check
			if(ableToMoveKing == false) {
				for(int AIPiece = 0; AIPiece < AIPieces.length; AIPiece++) { //for each AI piece
					for(int j = 0; j < 8; j++) {
						for(int k = 0; k < 8; k++) { //check every spot on the board for a legal move
							if(AIPieces[AIPiece].toString() == "Pawn") {
								if(temp.IsLegalPawn(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k)) {
									//if the move is legal, move a the piece as a test and check if the king is still in check
									temp.MovePiece(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k, false);
									if(temp.refreshCheck(false) == false) {
										ableToMoveKing = true; //if the king is no longer in check, then this is a viable move
									}
								}
							} else if(AIPieces[AIPiece].toString() == "Queen") {
								if(temp.IsLegalQueen(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k)) {
									temp.MovePiece(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k, false);
									if(temp.refreshCheck(false) == false) {
										ableToMoveKing = true;
									}
								}
							} else if(AIPieces[AIPiece].toString() == "Bishop") {
								if(temp.IsLegalBishop(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k)) {
									temp.MovePiece(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k, false);
									if(temp.refreshCheck(false) == false) {
										ableToMoveKing = true;
									}
								}
							} else if(AIPieces[AIPiece].toString() == "Horse") {
								if(temp.IsLegalHorse(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k)) {
									temp.MovePiece(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k, false);
									if(temp.refreshCheck(false) == false) {
										ableToMoveKing = true;
									}
								}
							} else if(AIPieces[AIPiece].toString() == "Castle") {
								if(temp.IsLegalCastle(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k)) {
									temp.MovePiece(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol(), j, k, false);
									if(temp.refreshCheck(false) == false) {
										ableToMoveKing = true;
									}
								}
							}
							if(ableToMoveKing == true) {
								move[0] = new Point(AIPieces[AIPiece].GetRow(), AIPieces[AIPiece].GetCol());
								move[1] = new Point(j,k);
								maxScore = -1;
								break;
							} else {
								//if this potential move does not get the king out of check,
								// it is not a viable move, so try again
								temp.CopyBoard(game);
							}
						}
					}
				}
			}
		} else if((game.isCheckMate(king.GetRow(), king.GetCol()) == true)) {
			System.out.println("Checkmate Line 181 in Alexa.java");
			return null;
		}




		//FIND THE BEST MOVE POSSIBLE
		for(int i = 0; i < AIPieces.length; i++) {
			int row = AIPieces[i].GetRow();
			int col = AIPieces[i].GetCol();

			if(game.GetPiece(row,col) != null) {
				//Gets legal moves for current option, it will be null if no legal moves are possible
				ArrayList<Point> temp = new ArrayList<Point>();
				temp = game.LegalMoves(row, col);

				//Finds the most valuable move for each option if it exists.
				if (temp != null) {
					for (int k = 0; k < temp.size(); k++) {
						//Resets move score to test a new legal move
						int moveScore = 0;
						if (game.GetPiece(temp.get(k).x, temp.get(k).y) != null) {
							if (game.GetPiece(temp.get(k).x, temp.get(k).y).toString() == "Pawn") {
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
						if (moveScore > maxScore) {
							move[0] = new Point(row, col);
							move[1] = new Point(temp.get(k).x, temp.get(k).y);
							maxScore = moveScore;
						}

					}
				}
			}
		}






		//MOVE A RANDOM PIECE
		//if not in checkmate and best move score = 0 then select random piece and move it
		if(maxScore == 0) {
			System.out.println("GOING RANDOMIZED");
			boolean goodMove = false;
			int[] values = new int[AIPieces.length];
			for(int init = 0; init < values.length; init++) {
				values[init] = 1;
			}
			while(goodMove == false) {
				int val = (int)(Math.random() * (AIPieces.length));

				//Selects a random piece for the stack and sets the move at index 0 of the pieces LegalMoves as the selected AIMove
				if((game.LegalMoves(AIPieces[val].GetRow(), AIPieces[val].GetCol()) != null) && (game.GetPiece(AIPieces[val].GetRow(), AIPieces[val].GetCol()) != null) && (values[val] != 0)) {
					ArrayList<Point> temp = new ArrayList<Point>();
					temp = game.LegalMoves(AIPieces[val].GetRow(), AIPieces[val].GetCol());
					move[0] = new Point(AIPieces[val].GetRow(), AIPieces[val].GetCol());
					move[1] = temp.get(0);
					goodMove = true;
					break;
				} else {
					//Gets rid of a piece that does nothing
					values[val] = 0;
					int max = 0;
					for(int i = 0; i < values.length; i++) {
						max +=  values[i];
					}

					if(max == 0) {
						System.out.println("[Error] No pieces to move, ending piece selection");
						break;
					}
				}
			}
		}

		if(move[0] == null) {
			System.out.println("move[0] == null");
		}
		if(move[1] == null) {
			System.out.println("move[1] == null");
		}

		//Gets the origin of the best move and the final destination for the move
		moveCounter++;
		//current position of the piece is stored in move[0]
		//new position of the piece is stored in move[1]
		return move;
	}
}
