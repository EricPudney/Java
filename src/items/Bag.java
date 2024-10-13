package src.items;

import src.characters.Hero;

public class Bag extends Item implements Equippable{
    int spaces;

    public Bag(int spaces) {
        super();
        this.name = "Traveller's bag";
        this.description = "A very useful bag which allows you to carry more items.";
        this.value = 20;
        this.spaces = spaces;
    }

    public void expandInventory(Hero player) {
        player.inventory.maxSize += spaces;
    }

    public void shrinkInventory(Hero player) {
        player.inventory.maxSize -= spaces;
    }

    @Override
    public void equip(Hero player) {
        System.out.printf("You have equipped the %s!\n", name);
        expandInventory(player);
    }

    @Override
    public void unEquip(Hero player) {
        System.out.printf("You have unequipped the %s!\n", name);
        shrinkInventory(player);
    }
}
