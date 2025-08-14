package Lstr;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import Run.PlsRun;
import Run.SPanel;
import Run.UPanel;

public class Mouselisten implements MouseListener{
	
	private SPanel sPanel;
	private UPanel uPanel;
	private Keylisten keylisten;
	PlsRun plsRun;
	
	public Mouselisten(SPanel sPanel, UPanel uPanel, Keylisten keylisten, PlsRun plsRun) {
		this.sPanel = sPanel;
		this.uPanel = uPanel;
		this.keylisten = keylisten;
		this.plsRun = plsRun;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(PlsRun.getMousePanel() == 0) {
			switch(e.getButton()) {
			case MouseEvent.BUTTON1:
				if(plsRun.getCounter() % 2 == 0) {
					sPanel.paintAsteroid();
				} else {
					sPanel.paintPlanet();
				}
			}		
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
