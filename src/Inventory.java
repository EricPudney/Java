package src;

import java.io.Console;
import java.util.ArrayList;

import src.characters.Hero;
import src.items.*;

public class Inventory {
    public ArrayList<Item> items;
    public int maxSize;
    static Console c = System.console();

    public Inventory(int spaces) {
        this.items = new ArrayList<Item>();
        this.maxSize = spaces;
    }

    public boolean addToInventory(Item item, Hero player) {
        if (items.size() >= maxSize) {
            System.out.println("There is no room left in your inventory!");
            return false;
        }
        else {
            items.add(item);
            if (item instanceof MagicItem) {
                ((MagicItem) item).applyBuff(player);
            }
            return true;
        }
    }

    public boolean removeFromInventory(int index) {
        if (items.remove(index) instanceof Item) {
            return true;
        }
        else {
            return false;
        }
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
