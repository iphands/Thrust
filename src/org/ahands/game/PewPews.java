package org.ahands.game;

import java.util.ArrayList;
import java.util.List;

public class PewPews {

	private List<Pew> pewList = new ArrayList<Pew>();

	public void addPew(Pew pew) {
		pewList.add(pew);
	}

	public void setPews(List<Pew> pews) {
		pewList = pews;
	}

	public List<Pew> getPews() {
		List<Pew> tmp = new ArrayList<Pew>();
		tmp.addAll(pewList);
		return tmp;
	}

	public void removePew(Pew pew) {
		pewList.remove(pewList.indexOf(pew));
		// pewList.remove(0);
	}

	public boolean hasPews() {
		// if (pewList.size() > 0) {
		// return true;
		// }
		// return false;
		return !pewList.isEmpty();
	}

	public int pewPewSize() {
		return pewList.size();
	}

	public void removePew(int index) {
		pewList.remove(index);
	}

	public void clear() {
		pewList.clear();
	}
}
