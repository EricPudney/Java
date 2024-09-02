package src;
import java.util.ArrayList;
import java.util.List;

import src.characters.Hero;
import src.items.Item;
import src.items.MagicItem;

public class Shop {
    ArrayList<Item> stock;
    int gold;

    public Shop(int gold) {
        this.gold = gold;

    }

    public void buyGoods(Hero player) {
        int price = 0;
        List<Item> inventory = new ArrayList<>(player.inventory);
        for (Item item : inventory) {
            if (this.gold > item.value && !(item instanceof MagicItem)) {
                price += item.value;
                this.gold -= item.value;
                player.inventory.remove(item);
                System.out.printf("The shopkeeper bought your %s for %d gold!\n", item.name, item.value);
            }
        }
        player.gold += price;
        System.out.printf("You now have %d gold.\n", player.gold);
    }
}
