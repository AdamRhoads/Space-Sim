package PB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Run.Const;

public class Planet extends Body{
	
	private static double mass = 1000, radius = Const.getPRadius();
	private static BufferedImage img;
	
	public Planet(int x, int y) {
		super(x, y, mass, radius, Color.cyan);
	}
	public Planet(int x, int y, double vX, double vY) {
		super(x, y, mass, radius, vX, vY, Color.cyan, makeImg());
	}
	
	public String getType() {
		return "Planet";
	}
	private static BufferedImage makeImg() {
		img = new BufferedImage((int) radius * 2, (int) radius * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.blue);
		//g.fillOval((int) radius / 2, (int) radius / 2, (int) radius / 2, (int) radius / 2);
		g.fillOval(0, 0, (int) radius * 2, (int) radius * 2);
		return img;
	}
	public BufferedImage returnImg() {
		return img;
	}
}