package Units;

/**
 * Created by Dhruv on 5/19/2016.
 *
 * A unit with low health, but a ranged attack. Meant to counter foot soldier.
 */
public class Archer extends Unit {
    public Archer(String team) {
        super(50, 30, 70, 1, 10, team);
    }

    @Override
    public void attack(Unit target) {
        double dist = distance(target.getX(), target.getY());
        double damage = rollDamage();
        if (dist < getRange() / 2)
            target.hit(damage / 2);
        else
            target.hit(damage);
    }

}
