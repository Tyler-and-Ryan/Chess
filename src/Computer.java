import java.awt.Point;
import java.util.ArrayList;

public class Computer {
	
	GameBoard game;
	
	public static final int KING = 20;
	public static final int QUEEN = 10;
	public static final int CASTLE = 5;
	public static final int BISHOP = 5;
	public static final int HORSE = 3;
	public static final int PAWN = 1;
	
	int moveCounter;
	
	//Constructor
	public Computer() {
		game = new GameBoard();
		moveCounter = 0;
		
	}
	
	
	public void UpdateBoard(GameBoard newBoard) {
		game.CopyBoard(newBoard);
	}
	
	public Point[] GenerateMove(){
		//Position 0 is the original row/col
		//position 1 is the new row/col
		Point[] move = new Point[2];
		
		
		
		
		
		return move;
	}
}
