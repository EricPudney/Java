package src;

import java.io.Console;
import java.util.ArrayList;

import src.characters.Hero;
import src.items.*;

public class Inventory extends ArrayList<Item> {
    public int maxSize;
    boolean containsBag = false;
    static Console c = System.console();

    public Inventory(int spaces) {
        this.maxSize = spaces;
    }

    public Inventory() {
        // used for dungeon items only - they have no spaces property
    }

    public boolean addToInventory(Item item, Hero player) {
        if (this.size() >= maxSize) {
            System.out.println("There is no room left in your inventory!");
            return false;
        }
        else if (item instanceof Bag && containsBag) {
            System.out.println("Inventory already contains a bag!");
            return false;
        }
        else {
            this.add(item);
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
        if (this.remove(item)) {
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
        while (true) {
            System.out.println(this);
            if (this.size() == 0) {
                return null;
            }
            String input = c.readLine("Enter the number of the item you wish to %s or x to cancel.\n", purpose);
            if (input.equals("x")) {
                return null;
            }
            else {
                try {
                    int itemIndex = Integer.parseInt(input) - 1;
                    Item item = this.get(itemIndex);
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
    }

    public String toString() {
        if (this.size() == 0) {
            return "Inventory is empty!";
        }
        String returnValue = "";
        int i = 1;
        for (Item item : this) {
            String listItem = String.format("%d. %s: %s    value: %d\n", i, item.name, item.description, item.value);
            returnValue = returnValue.concat(listItem);
            i++;
        }
        return returnValue;
    }
}
