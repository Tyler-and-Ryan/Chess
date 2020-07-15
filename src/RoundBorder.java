import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoundBorder extends JPanel {
	private int x;
	private int y;
	private int width;
	private int height;
	
	RoundBorder(int x,int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, width, height, 50, 50);
		g.drawRoundRect(x, y, width, height, 50, 50);
	}
}
