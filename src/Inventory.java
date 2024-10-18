package src;

import java.util.ArrayList;

import src.items.*;
import static src.characters.Hero.scanner;

public class Inventory extends ArrayList<Item> {
    public int maxSize;

    public Inventory(int spaces) {
        this.maxSize = spaces;
    }

    public Inventory() {
        // used for dungeon items only - they have no spaces property
    }

    public boolean addToInventory(Item item) {
        if (this.size() >= maxSize) {
            System.out.println("There is no room left in your inventory!");
            return false;
        }
        this.add(item);
        return true;
    }

    public Item selectFromInventory(String purpose) {
        while (true) {
            System.out.println(this);
            if (this.size() == 0) {
                return null;
            }
            System.out.printf("Enter the number of the item you wish to %s or x to cancel.\n", purpose);
            String input = scanner.nextLine();
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
