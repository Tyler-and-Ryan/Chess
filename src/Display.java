import java.awt.*;
import java.awt.event.*;

public class Display {
	Frame board;
	public Display() {
		board = new Frame();
		board.setSize(500,500);
		board.setVisible(true);
		Button quit = new Button("Quit");
		quit.addActionListener(new ActionListener() {
			public void closeCanvas() {
				CloseBoard();
			}
		});
		quit.setSize(100,100);
		board.add(quit);
	}
	
	public void CloseBoard() {
		board.setVisible(false);
	}
}
