package src;

import java.io.Console;
import java.util.ArrayList;

import src.characters.Hero;
import src.items.*;

public class Inventory {
    public ArrayList<Item> items;
    public int maxSize;
    boolean containsBag = false;
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
        else if (item instanceof Bag && containsBag) {
            System.out.println("Inventory already contains a bag!");
            return false;
        }
        else {
            items.add(item);
            if (item instanceof MagicItem) {
                ((MagicItem) item).applyBuff(player);
            }
            if (item instanceof Bag) {
                ((Bag) item).expandInventory(player);
                player.inventory.containsBag = true;
            }
            return true;
        }
    }

    public boolean removeFromInventory(Item item, Hero player) {
        if (this.items.remove(item)) {
            if (item instanceof MagicItem) {
                ((MagicItem) item).removeBuff(player);
            }
            if (item instanceof Bag) {
                ((Bag) item).shrinkInventory(player);
            }
            return true;
        }
        return false;
    }

    public Item selectFromInventory(String purpose) {
        boolean finished = false;
        while (!finished) {
            System.out.println(this);
            String input = c.readLine("Enter the number of the item you wish to %s or x to cancel.\n", purpose);
            if (input.equals("x")) {
                finished = true;
            }
            else {
                try {
                    int itemIndex = Integer.parseInt(input) - 1;
                    Item item = this.items.get(itemIndex);
                    if (item == null) {
                        System.out.println("Invalid item reference!\n");
                    }
                    return item;
                }
                catch (Exception e) {
                    System.out.println("Invalid input!\n");
                }
            }
        }
        return null;
    }

    public String toString() {
        String returnValue = "";
        int i = 1;
        for (Item item : this.items) {
            if (item != null) {
                String listItem = String.format("%d. %s: %s    value: %d\n", i, item.name, item.description, item.value);
                returnValue = returnValue.concat(listItem);
                i++;
            }
        }
        if (items.size() == 0) {
            returnValue = ("The inventory is empty!");
        }
        return returnValue;
    }
}
