package Board;

import Units.Unit;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Dhruv on 5/19/2016.
 *
 * Global functions to interact with static map containing all units.
 */
public class Global {
    //2d representation of map
    private static Unit[][] grid;

    //Initialize new map with given length and width. Populate 20% of map with both armies
    public static Pair<Army, Army> initialize(int length, int width) {
        grid = new Unit[length][width];
        int armySize = length * width / 10;

        Army army1 = new Army(armySize);
        Army army2 = new Army(armySize);

        populate(army1, armySize, "Army-1");
        populate(army2, armySize, "Army-2");

        return new Pair<>(army1, army2);
    }

    //Add units to army and place randomly on map
    private static void populate(Army army, int size, String team) {
        Random rand = new Random();
        int x;
        int y;
        for (int i = 0; i < size; i++) {
            Unit newUnit = Unit.getRandomUnit(team);
            army.insert(newUnit);
            do {
                x = rand.nextInt(grid.length);
                y = rand.nextInt(grid[0].length);
            } while (grid[x][y] != null);

            grid[x][y] = newUnit;
            newUnit.setStartPosition(x, y);
        }
    }

    //Remove unit from map
    public static void kill(Unit deadUnit) {
        if (deadUnit != null) {
            grid[deadUnit.getX()][deadUnit.getY()] = null;
        }
    }

    //Finds closest enemy unit from given location
    public static Unit getClosestEnemy(int x, int y, String team) {
        return enemyInRange(x, y, Math.max(grid.length, grid[0].length), team);
    }

    //Returns true if position is occupied
    public static boolean unitAtCoords(int x, int y) {
        return grid[x][y] != null;
    }

    //Use modified Breadth First Search to find any Unit in given range from given location
    public static Unit enemyInRange(int x, int y, int range, String team) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        LinkedList<Pair<Integer, Integer>> bfsCoords = new LinkedList<>();
        LinkedList<Integer> bfsRange = new LinkedList<>();

        bfsCoords.add(new Pair<>(x, y));
        bfsRange.add(range);
        while (!bfsCoords.isEmpty()) {
            Pair<Integer, Integer> xy = bfsCoords.remove();
            x = xy.getKey();
            y = xy.getValue();
            range = bfsRange.remove();
            if (x >= grid.length || y >= grid[0].length || x < 0 || y < 0)
                continue;
            if (visited[x][y])
                continue;
            visited[x][y] = true;
            if (range == -1)
                continue;
            if (grid[x][y] != null && !grid[x][y].getTeam().equals(team))
                return grid[x][y];
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    bfsCoords.add(new Pair<>(i, j));
                    bfsRange.add(range - 1);
                }
            }
        }
        System.out.println("Enemy not found");
        return null;
    }

    //Move unit from start position to end position
    public static void move(int startX, int startY, int endX, int endY) {
        grid[endX][endY] = grid[startX][startY];
        grid[startX][startY] = null;
    }
}
