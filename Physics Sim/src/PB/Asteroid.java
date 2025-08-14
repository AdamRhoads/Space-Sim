package PB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Asteroid extends Body{
	
	private static double mass = 200, radius = Run.Const.getARadius();
	private static BufferedImage img;
	
	
	public Asteroid(int x, int y) {
		super(x,y, mass, radius, 0, 0, Color.white, makeImg());
		
	}
	
	public Asteroid(int x, int y, double vX, double vY) {
		super(x, y, mass, radius, vX, vY, Color.white, makeImg());
	}
	public String getType() {
		return "Asteroid";
	}
	private static BufferedImage makeImg() {
		img = new BufferedImage((int) radius * 2, (int) radius * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.white);
		g.fillOval(0, 0, (int) radius * 2, (int) radius * 2);
		return img;
	}
	public BufferedImage returnImg() {
		return img;
	}
}