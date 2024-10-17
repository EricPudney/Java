package src.items;

import src.characters.Hero;

// not currently in use
public class Weapon extends Item implements Equippable {
    double block;
    double shield;
    // boolean twoHanded;
    Material material;
    Quality quality;
    int attack;
    // public damageType damage;
    // public int damageBoost;

    public Weapon(Material material, Quality quality) {
        this.material = material;
        this.quality = quality;
        this.attack = material.attack + quality.attack;
    }

    @Override
    public void equip(Hero player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equip'");
    }
    
    @Override
    public void unEquip(Hero player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unEquip'");
    }
}