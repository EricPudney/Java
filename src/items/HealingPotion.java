package src.items;

import src.characters.Character;

public class HealingPotion extends Item implements Usable {

    public HealingPotion() {
        super("Healing Potion", "A potion that restores your health to its maximum level", 10);
    }

    @Override
    public boolean useItem(Character character) {
        if (character.health == character.maxHealth) {
            System.out.println("Health is already at max!");
            return false;
        }
        character.health = character.maxHealth;
        return true;
    }
}
