package org.ahands.game;

import java.util.ArrayList;
import java.util.List;

public class PewPews {

	private List<Pew> pewList = new ArrayList<Pew>();

	public synchronized void addPew(Pew pew) {
		pewList.add(pew);
	}

	public synchronized void setPews(List<Pew> pews) {
		pewList = pews;
	}

	public synchronized List<Pew> getPews() {
		List<Pew> tmp = new ArrayList<Pew>();
		tmp.addAll(pewList);
		return tmp;
	}

	public synchronized void removePew(Pew pew) {
		pewList.remove(pewList.indexOf(pew));
		// pewList.remove(0);
	}

	public synchronized boolean hasPews() {
		// if (pewList.size() > 0) {
		// return true;
		// }
		// return false;
		return !pewList.isEmpty();
	}

	public synchronized int pewPewSize() {
		return pewList.size();
	}

	public void removePew(int index) {
		pewList.remove(index);
	}

	public void clear() {
		pewList.clear();
	}
}
