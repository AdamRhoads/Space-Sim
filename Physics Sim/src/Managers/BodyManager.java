package Managers;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BodyManager {
	
	
	private static CopyOnWriteArrayList<PB.Body> bList;
	private static CopyOnWriteArrayList<CopyOnWriteArrayList<PB.Trail>> trailList;
	                             
	public BodyManager() {
		bList = new CopyOnWriteArrayList<PB.Body>();
		trailList = new CopyOnWriteArrayList<CopyOnWriteArrayList<PB.Trail>>();
	}
	
	public static CopyOnWriteArrayList<PB.Body> getBList() {
		return bList;
	}
	
	public static void updateBodies() {
		double time = Run.PlsRun.getDeltaTime();
		ArrayList<PB.Body> copy = new ArrayList<>(bList);
		for(PB.Body a: copy) {
			a.updateAcceleration(copy);
		}
		for(PB.Body a: copy) {
			a.updateVelocity(time);
		}
		for(PB.Body a: copy) {
			a.updatePosition(time);
		}
		trailList.removeAll(trailList);
		for(PB.Body a: copy) {
			trailList.add(a.getTrails());
		}
	}

	public static CopyOnWriteArrayList<CopyOnWriteArrayList<PB.Trail>> getTrailList() {
		return trailList;
	}

	public static void addList(PB.Body b) {
		bList.add(b);	
	}
	

}
