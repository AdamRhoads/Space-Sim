package Run;

import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Lstr.Keylisten;
import Lstr.Mouselisten;
import Managers.BodyManager;

public class PlsRun implements Runnable{
	
	private static int mousePanel, counter = 0;
	private String[][] sarr;
	private String[][] oarr;
	private String[][] barr;
	
	
	private SPanel sPanel;
	private UPanel uPanel;
	private Thread mainThread;
	public BodyManager bodyManager;
	private static JFrame frame;
	private static JPanel panel;
	
	private static double deltaTime;
	private Keylisten keylisten;
	private Mouselisten mouselisten;
	private PlsRun plsRun;
	
	
	public PlsRun() {
		plsRun = this;
		mousePanel = 0;
		sarr = new String[Const.getHeightSPanel()][Const.getWidthSPanel()];
		oarr = new String[Const.getHeightSPanel()][Const.getWidthSPanel()];
		barr = new String[Const.getHeightSPanel()][Const.getWidthSPanel()];
		for(int i = 0; i < sarr.length; i++) {
			for(int j = 0; j < sarr[i].length; j++) {
				if(i % 2 == 0) {
					sarr[i][j] = "g";
				} else {
					sarr[i][j] = "r";
				}
			}
		}
		bodyManager = new BodyManager();
		for(int i = 0; i < barr.length; i++) {
			for(int j = 0; j < barr[i].length; j++) {
				if("a".equals(barr[i][j])) {
					int x = (int) j * barr[i].length; //(point.x / (Const.getXRes() / aarr[0].length));
					int y = (int) i * barr.length; //(point.y / (Const.getYRes() / aarr.length));
					BodyManager.addList(new PB.Body(x, y, 10, 20));
				}
			}
		}
		
		init();
	}
	
	
	
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new JFrame();
				panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				sPanel = new SPanel(sarr, oarr, barr);
				uPanel = new UPanel(plsRun);
				keylisten = new Keylisten(sPanel, uPanel, plsRun);
				mouselisten = new Mouselisten(sPanel, uPanel, keylisten, plsRun);
				frame.addKeyListener(keylisten);
				frame.addMouseListener(mouselisten);
				panel.add(sPanel);
				panel.add(uPanel);
				frame.add(panel);
				frame.setFocusable(true);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				startMainLoop();
			}
		});
	}
	
	private void startMainLoop() {
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000 / 60;
		double timePerUpdate = 1000000000 / 30;
		long previousTime = System.nanoTime();
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		double deltaU = 0;
		double deltaF = 0;
		while(true) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if(deltaU >= 1) {
				deltaTime = deltaU;
				update();
				updates++;
				deltaU--;
			}
			if(deltaF >= 1) {
				updateG();
				deltaF--;
				frames++;
			}
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS " + frames + " | UPS: " + updates);
				updates = 0;
				frames = 0;
			}
		}
		
	}

	public void update() {
		BodyManager.updateBodies();
		sPanel.tilt();
		sPanel.velocity();
	}
	public void updateG() {
		sPanel.repaint();
		uPanel.repaint();
	}
	public String[][] getSarr() {
		return sarr;
	}
	public String[][] getOarr() {
		return oarr;
	}
	public static double getDeltaTime() {
		return deltaTime;
	}
	public static int getMousePanel() {
		updateMousePanel();
		return mousePanel;
	}
	public static void updateMousePanel() {
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		SwingUtilities.convertPointFromScreen(point, frame);
		if(point.y < Const.getYResSPanel()) {
			setMouseInt(0);
		} else {
			setMouseInt(1);
		}
	}
	public static void setMouseInt(int x) {
		mousePanel = x;
	}
	public SPanel getSPanel() {
		return sPanel;
	}
	public UPanel getUPanel() {
		return uPanel;
	}
	public int getCounter() {
		return counter;
	}
	public void addCounter() {
		counter++;
	}

}

