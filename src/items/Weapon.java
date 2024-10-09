package src.items;

// not currently in use
public class Weapon extends Item implements Equippable {
    public double block;
    public double shield;
    public boolean twoHanded;
    public damageType damage;
    public int damageBoost;
    @Override
    public boolean equip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equip'");
    }
    @Override
    public boolean unEquip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unEquip'");
    }
}

// ??? could be made relevant with additions to Minion class
enum damageType {
    slashing,
    crushing,
    piercing
}