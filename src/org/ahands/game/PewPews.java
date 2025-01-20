package org.ahands.game;

import java.util.ArrayList;
import java.util.List;

public class PewPews {

    private List<Pew> pewList = new ArrayList<Pew>();
    private final List<Integer>   pewPurgeList = new ArrayList<Integer>();


    public void addPew(Pew pew) {
        pewList.add(pew);
    }

    public void setPews(List<Pew> pews) {
        pewList = pews;
    }

    public List<Pew> getPews() {
        return pewList;
    }

    public Pew getPew(int index) {
        return pewList.get(index);
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
        pewPurgeList.add(index);
    }

    public void removePew(Pew pew) {
        pewPurgeList.add(pewList.indexOf(pew));
    }

    public void purge() {
        for (Integer i : pewPurgeList) {
            if (pewList.size() > 1) {
                pewList.remove((int) i);
            }
        }
        pewPurgeList.clear();
    }

    public void clear() {
        pewList.clear();
    }
}
