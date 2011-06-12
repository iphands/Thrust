package org.ahands.game;

import java.util.ArrayList;
import java.util.List;

public class PewSorter implements Runnable {
	private List<Pew> pewList;
	private List<Pew> onScreenPewList;
	private int index, threadSize;

	public PewSorter(List<Pew> pewList, int index, int threadSize) {
		this.pewList = pewList;
		this.index = index;
		this.threadSize = threadSize;
	}

	@Override
	public void run() {
		final int count = pewList.size() / threadSize;
		onScreenPewList = new ArrayList<Pew>(50000);
		for (int i = count * (index), x = pewList.size(); i < x; i++) {
			if (i > count * (index + 1)) {
				break;
			}
			final Pew pew = pewList.get(i);
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
