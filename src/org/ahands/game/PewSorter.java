package org.ahands.game;

import java.util.ArrayList;
import java.util.List;

public class PewSorter implements Runnable {
	private List<Pew> pewList;
	private List<Pew> onScreenPewList;

	public PewSorter(List<Pew> pewList) {
		this.pewList = pewList;
	}

	@Override
	public void run() {
		onScreenPewList = new ArrayList<Pew>(50000);
		for (Pew pew : pewList) {
			final float pewX = pew.getX();
			final float pewY = pew.getY();
			if (!(pewX >= 1600 || pewX <= 0 || pewY >= 900 || pewY <= 0)) {
				pew.update();
				onScreenPewList.add(pew);
			}
		}
	}

	public List<Pew> getOnScreenPewList() {
		return onScreenPewList;
	}

}
