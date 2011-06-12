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
		final int pewListSize = pewList.size();
		final int count = pewListSize / threadSize;
		final int shareCount = count * index;
		onScreenPewList = new ArrayList<Pew>();

		final List<Pew> tmpLst;
		if (index == threadSize) {
			tmpLst = pewList.subList(shareCount, pewListSize);
		} else {
			tmpLst = pewList.subList(shareCount, count * (index + 1));
		}

		for (Pew pew : tmpLst) {
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
