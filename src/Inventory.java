package src;

import java.io.Console;

import src.characters.Hero;
import src.items.*;

public class Inventory {
    public Item[] items;
    static Console c = System.console();

    public Inventory(int spaces) {
        this.items = new Item[8];
    }

    public boolean addToInventory(Item item, Hero player) {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] == null) {
                this.items[i] = item;
                if (item instanceof MagicItem) {
                    ((MagicItem) item).applyBuff(player);
                }
                return true;
            }
        }
        System.out.println("There is no room left in your inventory!");
        return false;
    }

    public boolean removeFromInventory(int index) {
        if (this.items[index - 1] instanceof Item) {
            this.items[index - 1] = null;
            return true;
        }
        return false;
    }

    public String toString() {
        String returnValue = "The inventory contains: \n";
        int i = 1;
        for (Item item : this.items) {
            if (item != null) {
                String listItem = String.format("%d. %s: %s\n", i, item.name, item.description);
                returnValue = returnValue.concat(listItem);
                i++;
            }
        }
        if (returnValue.compareTo("The inventory contains: \n") == 0) {
            returnValue = returnValue.concat("Nothing at all!");
        }
        return returnValue;
    }
}
