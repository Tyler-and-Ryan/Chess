import java.awt.*;
import java.awt.event.*;

public class Display {
	Frame board;
	
	public Display() {
		board = new Frame();
		board.setSize(500,500);
		board.setVisible(true);
		
		//Menu bar
		
		MenuBar panel = new MenuBar();
		Menu menu = new Menu("Options");
		MenuItem quit = new MenuItem("quit");
		menu.add(quit);
		panel.add(menu);
		board.setMenuBar(panel);
		
		//action listener
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.setVisible(false);
			}
		});
		
		board.setVisible(true);
		
		
	}

	
	public void CloseBoard() {
		board.setVisible(false);
	}
}
