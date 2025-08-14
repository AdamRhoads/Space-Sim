package PB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Body {
	
	private double x, y, velocityX, velocityY, accelerationX = 0, accelerationY = 0, mass = 1, G = .9, radius;
	private Color color;
	private CopyOnWriteArrayList<Trail> trails;
	private BufferedImage img;
	
	
	public Body(int x, int y, double mass, double radius) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		velocityX = 0;
		velocityY = 0;
		this.radius = radius;
		
	}
	public Body(int x, int y, double mass, double radius, Color color) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		velocityX = 0;
		velocityY = 0;
		this.radius = radius;
		this.color = color;
		trails = new CopyOnWriteArrayList<Trail>();
	}
	public Body(int x, int y, double mass, double radius, double vX, double vY) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		velocityX = vX;
		velocityY = vY;
		this.radius = radius;
	}
	public Body(int x, int y, double mass, double radius, double vX, double vY, Color color, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		velocityX = vX;
		velocityY = vY;
		this.radius = radius;
		this.color = color;
		trails = new CopyOnWriteArrayList<Trail>();
		this.img = img;
	}

	public void updateAcceleration(ArrayList<Body> copy) {
		double dampening = 1e-2;
		accelerationX = 0;
		accelerationY = 0;
		for(Body b: copy) {
			if(!b.equals(this)) {
				double dX = b.getX() - x;
				double dY = b.getY() - y;
				double distanceSqared = (dX * dX) + (dY * dY) + dampening;
				double r = Math.sqrt(distanceSqared);
				if(r < this.radius + b.radius) {
					continue;
				}
				double acc = G * b.getMass() / distanceSqared;
				accelerationX += acc * dX / r;
				accelerationY += acc * dY / r;
			}
		}
	}
	public void updateVelocity(double t) {
		velocityX += accelerationX * t; 
		velocityY += accelerationY * t;
		
//		velocityX *= 0.65;
//		velocityY *= 0.65;
	}
	public void updatePosition(double t) {
		trails.add(new Trail((int) x, (int) y, color));
		if(trails.size() > 20) {
			trails.remove(0);
		}
		x = (x + (velocityX * t) + ( .5 * accelerationX * t * t));
		y = (y + (velocityY * t) + ( .5 * accelerationY * t * t));
	}



	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public CopyOnWriteArrayList<Trail> getTrails() {
		return trails;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getVelocityX() {
		return velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public double getAccelerationX() {
		return accelerationX;
	}
	public double getAccelerationY() {
		return accelerationY;
	}
	public double getMass() {
		return mass;
	}
	public String getType() {
		return "Body";
	}
	public BufferedImage getImg() {
		return img;
	}
	public double getRadius() {
		return radius;
	}

}
