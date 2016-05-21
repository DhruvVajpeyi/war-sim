package Board;

import Units.Unit;

import java.util.ArrayList;

/**
 * Created by Dhruv on 5/19/2016.
 *
 * One army is a list of alive units. Includes functions to remove a dead unit, and take turns for each unit
 */
public class Army {
    private ArrayList<Unit> units;
    private int aliveCount;
    private int next;

    //Initialize army of given size. Does not add any units
    public Army(int size) {
        units = new ArrayList<>(size);
        aliveCount = size;
        next = 0;
    }

    //Return size of the army
    public int size() {
        return units.size();
    }

    //Add a new unit into the army
    public void insert(Unit newUnit) {
        units.add(newUnit);
    }

    //Get number of alive units
    public int getAliveCount() {
        return aliveCount;
    }

    //Remove a unit from the army
    public void kill(Unit deadUnit) {
        if (deadUnit != null) {
            units.remove(deadUnit);
            aliveCount--;
        }
        Global.kill(deadUnit);
    }

    //Use internal counter to take a turn
    public Unit nextTurn() {
        next = next % aliveCount;
        if (units.isEmpty())
            return null;
        Unit currentUnit = units.get(next);
        next = (next + 1) % aliveCount;
        return currentUnit.run();
    }
}
