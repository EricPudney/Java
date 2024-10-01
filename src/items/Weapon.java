package src.items;

// not currently in use
public class Weapon extends Item {
    public double block;
    public double shield;
    public boolean twoHanded;
    public damageType damage;
    public int damageBoost;
}

// ??? could be made relevant with additions to Minion class
enum damageType {
    slashing,
    crushing,
    piercing
}