package PB;

import java.awt.Color;

public class Trail {
	
	private int x, y;

	private Color color;
	
	public Trail(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}
}
