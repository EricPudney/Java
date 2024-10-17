package src.items;

import src.characters.Hero;

public interface Equippable {
    void equip(Hero player);
    void unEquip(Hero player);
}
