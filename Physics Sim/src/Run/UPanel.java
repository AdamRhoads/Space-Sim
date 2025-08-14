package Run;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PointerInfo;

import javax.swing.JPanel;

import Lstr.Keylisten;
import Lstr.Mouselisten;

public class UPanel extends JPanel{
	
	private SPanel sPanel;
	private PlsRun plsRun;
	
	public UPanel(PlsRun plsRun) {
		setFocusable(true);      
		requestFocusInWindow();
		addMouseMotionListener(null);
		sPanel = plsRun.getSPanel();
		this.plsRun = plsRun;
	}
	public Dimension getPreferredSize() {
		return new Dimension(Const.getXResUPanel(), Const.getYResUPanel());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if(plsRun.getCounter() % 2 == 0) {
			g2d.drawString("Tilt: " + sPanel.getTilt() + " Velocity: " + sPanel.getVelocity() + " Asteroid", 100, 100);
		} else {
			g2d.drawString("Tilt: " + sPanel.getTilt() + " Velocity: " + sPanel.getVelocity() + " Planet", 100, 100);
		}
	}
}
