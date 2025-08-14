package Run;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import Lstr.Keylisten;
import Lstr.Mouselisten;
import PB.Asteroid;
import PB.Body;
import PB.Planet;
import PB.Trail;
import Managers.BodyManager;

public class SPanel extends JPanel {
	String[][] sarr;
	String[][] oarr;
	String[][] barr;
	private int xScaleDif;
	private int yScaleDif;
	private PointerInfo pointer;
	private Graphics g;
	private Graphics2D g2d;
	private ArrayList<Point> points;
	private ArrayList<Point[]> pairsArr;
	private double tilt, velocity, vX = 0, vY = 0;
	private boolean isTiltLeft = false, isTiltRight = false, forwards = false, backwards = false;
	
	
	public SPanel(String[][] sarr, String[][] oarr, String[][] barr) {
		setFocusable(true);      
		requestFocusInWindow();
		pointer = MouseInfo.getPointerInfo();
		addMouseMotionListener(null);
		this.sarr = sarr;
		this.oarr = oarr;
		this.barr = barr;
		xScaleDif = Const.getXResSPanel() / Const.getWidthSPanel();
		yScaleDif = Const.getYResSPanel() / Const.getHeightSPanel();
		pairsArr = new ArrayList<Point[]>();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(Const.getXResSPanel(), Const.getYResSPanel());
	}
	
	
	public void paintOverlay() {
		pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, this);
		int x = (int) (point.x / (Const.getXResSPanel() / sarr[0].length));
		int y = (int) (point.y / (Const.getYResSPanel() / sarr.length));
		if("g".equals(oarr[y][x]) ) {
			oarr[y][x] = "null";
		} else if("r".equals(oarr[y][x])) {
			oarr[y][x] = "g";
		} else {
			oarr[y][x] = "r";
		}
		distance();
		repaint();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		this.g = g;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		this.g2d = g2d;
		//background coloring
		int cellWidth = Const.getXResSPanel() / sarr[0].length;
		int cellHeight = Const.getYResSPanel() / sarr.length;
		for (int i = 0; i < sarr.length; i++) {
	        for (int j = 0; j < sarr[i].length; j++) {
	            if (sarr[i][j].equals("g")) {
	                g2d.setColor(Color.BLACK);
	            } else {
	                g2d.setColor(Color.BLACK);
	            }
	            g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
	        }
	    }
		for (int i = 0; i < oarr.length; i++) {
	        for (int j = 0; j < oarr[i].length; j++) {
	            if ("g".equals(oarr[i][j])) {
	                g2d.setColor(Color.GREEN);
	            } else {
	                g2d.setColor(Color.RED);
	            }
	            if(oarr[i][j] != null) {
	            	g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth * Const.getOverlayRes(), cellHeight * Const.getOverlayRes());
	            }
	        }
	    }
		
		
//		for (int i = 0; i < aarr.length; i++) {
//	        for (int j = 0; j < aarr[i].length; j++) {
//	            if ("g".equals(aarr[i][j])) {
//	                g2d.setColor(Color.GRAY);
//	            }
//	            if(aarr[i][j] != null) {
//	            	g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth * Const.getOverlayRes(), cellHeight * Const.getOverlayRes());
//	            }
//	        }
//	    }
		CopyOnWriteArrayList<Body> bList = BodyManager.getBList();
		for(Body b: bList) {
			
			
			
//			if(b.getType().equals("Asteroid")) {
//				g2d.setColor(Color.GRAY);
////				g2d.fillRect((int) b.getX(), (int) b.getY(), cellWidth * Const.getOverlayRes(), cellHeight * Const.getOverlayRes());
//				g2d.fillOval((int) (b.getX() - Const.getARadius()), (int) (b.getY() - Const.getARadius()), (int) Const.getARadius() * 2, (int) Const.getARadius() * 2);
//			} else {
//				g2d.setColor(Color.BLUE);
//				g2d.fillOval((int) (b.getX() - Const.getPRadius()), (int) (b.getY() - Const.getPRadius()), (int) Const.getPRadius() * 2, (int) Const.getPRadius() * 2);
//			}
			g2d.drawImage(b.getImg(), (int) (b.getX() - b.getRadius()), (int) (b.getY() - b.getRadius()), getFocusCycleRootAncestor());
			//g2d.drawImage(b.getImg(), (int) (b.getX() - b.getRadius()), (int) (b.getY() - b.getRadius()), (int) b.getX(), (int) b.getY(), 0, 0, (int) b.getRadius() * 2, (int) b.getRadius() * 2, getFocusCycleRootAncestor());
			
			
			
			
			
//			if(b.getTrails() != null) {
//				g2d.setColor(b.getColor());
//				g2d.fillOval((int) b.getX(), (int) b.getY(), 4, 4);
//			}
		}
		for(CopyOnWriteArrayList<Trail> a: BodyManager.getTrailList()) {
			for(Trail t: a) {
				g2d.setColor(t.getColor());
				g2d.fillOval((int) t.getX(), (int) t.getY(), 4, 4);
			}
		}
		paintLine();
		g2d.dispose();
	}
	public void distance() {
		
		//get all points
		points = new ArrayList<Point>();
		for(int i = 0; i < oarr.length; i++) {
			for(int j = 0; j < oarr[i].length; j++) {
				if(oarr[i][j] != null) {
					points.add(new Point(j,i));
				}
			}
		}
		
		//pairs the points with the closest one
		if(points.size() > 1) {
			for(Point a: points) {
				Point closest = null;
				double minDistance = Double.MAX_VALUE;
				for(Point b: points) {
					if(!a.equals(b)) {
						double distance = a.distance(b);
						if(distance < minDistance) {
							minDistance = distance;
							closest = b;
						}	
					}
				
				}
				if(closest != null) {
					pairsArr.add(new Point[] {a, closest});
				}
			}
		}			    	    	    		
	}
	
	public void paintLine() {
//		g2d.setColor(Color.BLACK);
//		for(Point[] pair: pairsArr) {
//			g2d.drawLine((int) pair[0].getX() * xScaleDif + Const.getOverlayRes(), (int) pair[0].getY() * yScaleDif + Const.getOverlayRes(), (int) pair[1].getX() * xScaleDif + Const.getOverlayRes(), (int) pair[1].getY() * yScaleDif + Const.getOverlayRes());
//		}
		pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, this);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawLine(point.x, point.y, (int) vX * 10 + point.x, (int) vY * 10 + point.y);;
		//if(o != null && t != null) {
		//	g2d.drawLine((int) o.getX() * xScaleDif + Const.getOverlayRes(), (int) o.getY() * yScaleDif + Const.getOverlayRes(), (int) t.getX() * xScaleDif + Const.getOverlayRes(), (int) t.getY() * yScaleDif + Const.getOverlayRes());
		//}
	}

	public void paintAsteroid() {
		pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, this);
//		int x = (int) (point.x / (Const.getXRes() / aarr[0].length));
//		int y = (int) (point.y / (Const.getYRes() / aarr.length));
//		if("a".equals(aarr[y][x])) {
//			aarr[y][x] = "null";
//		} else {
//			aarr[y][x] = "a";
//		}
		int x = (int) (point.x);
		int y = (int) (point.y);
		BodyManager.addList(new Asteroid(x, y, vX, vY));
		repaint();
		
	}
	public void paintPlanet() {
		pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, this);
//		int x = (int) (point.x / (Const.getXRes() / aarr[0].length));
//		int y = (int) (point.y / (Const.getYRes() / aarr.length));
//		if("a".equals(aarr[y][x])) {
//			aarr[y][x] = "null";
//		} else {
//			aarr[y][x] = "a";
//		}
		int x = (int) (point.x);
		int y = (int) (point.y);
		BodyManager.addList(new Planet(x, y, vX, vY));
		repaint();
	}
	
	public void findPanels() {
		pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, this);
		if(point.x > 1) {
			PlsRun.setMouseInt(0);
		} else {
			PlsRun.setMouseInt(1);
		}
	}
	public void setTiltLeftT() {
		isTiltLeft = true;
	}
	public void setTiltRightT() {
		isTiltRight = true;
	}
	public void setTiltLeftF() {
		isTiltLeft = false;
	}
	public void setTiltRightF() {
		isTiltRight = false;
	}
	public void tilt() {
		if(isTiltRight == false && isTiltLeft == true) {
			tilt-= 5;
		} else if(isTiltLeft == false && isTiltRight == true) {
			tilt+= 5;
		}
	}
	public double getTilt() {
		return tilt;
	}
	public void setBackwards() {
		backwards = true;
	}
	public void setForwards() {
		forwards = true;
	}
	public void stopBackwards() {
		backwards = false;
	}
	public void stopForwards() {
		forwards = false;
	}
	public double getVelocity() {
		return velocity;
	}
	public void velocity() {
		if(forwards == false && backwards == true) {
			velocity--;
		} else if(forwards == true && backwards == false) {
			velocity++;
		}
		vX = Math.signum(tilt) * velocity * Math.cos(-1 * (90 - Math.abs(tilt)) * (Math.PI / 180));
		vY = velocity * Math.sin(-1 * (90 - Math.abs(tilt)) * (Math.PI / 180));
	}
	
}

