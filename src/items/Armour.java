package src.items;

import src.characters.Hero;

public class Armour extends Item implements Equippable {

    Material material;
    Quality quality;
    int defence;

    public Armour(Material material, Quality quality) {
        this.material = material;
        this.quality = quality;
        this.defence = material.defence + quality.defence;
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
