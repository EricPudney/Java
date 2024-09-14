package src.items;

import src.characters.Hero;

public class Bag extends Item {
    public Bag() {
        super();
        this.name = "Traveller's bag";
        this.description = "A very useful bag which allows you to carry more items.";
        this.value = 20;
    }

    public void expandInventory(Hero player) {
        player.inventory.maxSize += 5;
    }
}
