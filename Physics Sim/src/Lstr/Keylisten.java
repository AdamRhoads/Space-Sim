package Lstr;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Run.PlsRun;
import Run.SPanel;
import Run.UPanel;

public class Keylisten implements KeyListener{
	
	private SPanel sPanel;
	private UPanel uPanel;
	private PlsRun plsRun;
	
	
	public Keylisten(SPanel sPanel, UPanel uPanel, PlsRun plsRun) {
		this.sPanel = sPanel;
		this.uPanel = uPanel;
		this.plsRun = plsRun;
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			plsRun.addCounter();
			break;
		case KeyEvent.VK_A:
			sPanel.setTiltLeftT();
			break;
		case KeyEvent.VK_D:
			sPanel.setTiltRightT();
			break;
		case KeyEvent.VK_W:
			sPanel.setForwards();
			break;
		case KeyEvent.VK_S:
			sPanel.setBackwards();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			sPanel.setTiltLeftF();
			break;
		case KeyEvent.VK_D:
			sPanel.setTiltRightF();
			break;
		case KeyEvent.VK_W:
			sPanel.stopForwards();
			break;
		case KeyEvent.VK_S:
			sPanel.stopBackwards();
			break;
		}
	}
	

}
