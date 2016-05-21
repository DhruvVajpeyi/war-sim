package Units;

import Board.Global;

import java.util.Random;

/**
 * Created by Dhruv on 5/19/2016.
 *
 * Representation of a single Unit, including health, damage range, speed, range, and team
 */
public class Unit {
    private double totalHealth;
    private double currentHealth;
    private double minDamage;
    private double maxDamage;
    private int speed;
    private int range;
    private int x;
    private int y;
    private String team;
    private Unit target;

    //Constructor
    public Unit(double health, double minDamage, double maxDamage, int speed, int range, String team) {
        this.totalHealth = health;
        this.currentHealth = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.speed = speed;
        this.range = range;
        this.x = -1;
        this.y = -1;
        this.team = team;
        this.target = null;
    }

    //Static function that return a new Unit of random subclass
    public static Unit getRandomUnit(String team) {
        Random rand = new Random();
        switch (rand.nextInt(3)) {
            case 0:
                return new Archer(team);

            case 1:
                return new FootSoldier(team);

            case 2:
                return new Cavalry(team);

            default:
                return null;
        }
    }

    //Attack a target unit
    public void attack(Unit target) {
        target.hit(rollDamage());
    }

    //Move towards given x and y
    public void move(int x, int y) {
        int steps = 0;
        int startX = this.x;
        int startY = this.y;
        while (steps != speed && distance(x, y) > range) {
            if (x > this.x && !Global.unitAtCoords(this.x + 1, this.y)) {
                this.x = this.x + 1;
            } else if (x < this.x && !Global.unitAtCoords(this.x - 1, this.y)) {
                this.x = this.x - 1;
            } else if (y > this.y && !Global.unitAtCoords(this.x, this.y + 1)) {
                this.y = this.y + 1;
            } else if (y < this.y && !Global.unitAtCoords(this.x, this.y - 1)) {
                this.y = this.y - 1;
            }
            steps++;
        }
        Global.move(startX, startY, this.x, this.y);
    }

    //Take a hit from another unit for given damage
    public void hit(double damage) {
        this.currentHealth = this.currentHealth - damage;
    }

    //Set start position for unit
    public void setStartPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Get euclidean distance of unit from x and y
    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    //Roll random damage between min and max
    public double rollDamage() {
        return Math.random() * (maxDamage - minDamage) + minDamage;
    }

    //Check to see if unit is dead
    public boolean isDead() {
        return currentHealth <= 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public String getTeam() {
        return team;
    }

    public int getRange() {
        return range;
    }

    //Run one turn for unit. Picks target if it has none. Attacks target in range or moves towards it.
    public Unit run() {
        if (target == null || target.isDead())
            target = Global.getClosestEnemy(x, y, team);

        if (target.distance(x, y) <= range)
            attack(target);
        else
            move(target.getX(), target.getY());

        if (target.isDead())
            return target;
        else
            return null;
    }
}
