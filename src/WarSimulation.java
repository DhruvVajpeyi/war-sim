import Board.Army;
import Board.Global;
import Units.Unit;
import javafx.util.Pair;

/**
 * Created by Dhruv on 5/19/2016.
 *
 * Main class. Initializes two armies and map. Then starts game loop where armies alternatate moves, with one unit each. Ends when either army is all dead.
 */
public class WarSimulation {
    public static void main(String[] args) {
        Pair<Army, Army> armies = Global.initialize(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        Army army1 = armies.getKey();
        Army army2 = armies.getValue();
        System.out.println("Simulation started");
        Unit dead = null;
        while (true) {
            dead = army1.nextTurn();
            army2.kill(dead);
            if (army2.getAliveCount() <= 0) {
                System.out.println("Army 1 wins");
                break;
            }
            dead = army2.nextTurn();
            army1.kill(dead);
            if (army1.getAliveCount() <= 0) {
                System.out.println("Army 2 wins");
                break;
            }
            System.out.println("Army 1: " + army1.getAliveCount());
            System.out.println("Army 2: " + army2.getAliveCount());
        }
    }
}
