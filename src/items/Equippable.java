package src.items;

import src.characters.Hero;

public interface Equippable {
    public void equip(Hero player);
    public void unEquip(Hero player); 
}
