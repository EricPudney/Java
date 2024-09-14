package src;
import java.io.Console;

import src.characters.Hero;
import src.items.*;

public class Shop {
    Inventory stock;
    int gold;

    static Console c = System.console();

    public Shop(int gold) {
        this.gold = gold;
        stock = generateStock();
    }

    public void shopVisit(Hero player) {
        boolean doneShopping = false;
        while (!doneShopping) {
        String decision = c.readLine("Do you want to buy, sell, or exit the shop (b/s/x)?\n");
            if (decision.equals("b")) {
                this.buyGoods(player);
            }
            else if (decision.equals("s")) {
                this.sellGoods(player);
            }
            else if (decision.equals("x")) {
                doneShopping = true;
            }
            else {
                System.out.println("Please enter b to buy, s to sell, or x to leave the shop.\n");
            }
        }
    }

    private Inventory generateStock() {
        Inventory inventory = new Inventory(8);
        inventory.items.add(Item.generateItem());
        inventory.items.add(Item.generateMagicItem());
        inventory.items.add(new Bag());
        return inventory;
    }

    public void buyGoods(Hero player) {
        boolean finished = false;
        while (!finished) {
            System.out.println(this.stock);
            String input = c.readLine("Enter the number of the item you wish to buy or x to exit.\n");
            if (input.equals("x")) {
                finished = true;
            }
            else {
                try {
                    int itemIndex = Integer.parseInt(input) - 1;
                    Item itemBought = this.stock.items.get(itemIndex);
                        if (itemBought != null && player.gold >= itemBought.value) {
                            if (player.inventory.addToInventory(itemBought, player)) {
                                if (this.stock.removeFromInventory(itemIndex)) {
                                    System.out.printf("Bought %s for %d gold.\n", itemBought.name, itemBought.value);
                                    player.gold -= itemBought.value;
                                }
                                else {System.out.println("Something went wrong - unable to purchase item.");
                            }
                            }
                            else if (player.gold < itemBought.value) {
                                System.out.println("You can't afford that!");
                            }
                        }
                        else {
                            System.out.println("Invalid item reference!\n");
                        }
                }
                catch (Exception e) {
                    System.out.println("Invalid input!\n");
                }
            }
        }
    }
    
    public void sellGoods(Hero player) {
        boolean finished = false;
        while (!finished) {
            System.out.println(player.inventory);
            String input = c.readLine("The shopkeeper has %d gold. Enter the number of the item you wish to sell or x to exit.\n", this.gold);
            if (input.equals("x")) {
                finished = true;
            }
            else {
                try {
                    int itemIndex = Integer.parseInt(input) - 1;
                    if (player.inventory.items.get(itemIndex) != null) {
                        Item itemSold = player.inventory.items.get(itemIndex);
                        if (this.gold >= itemSold.value) {
                            player.gold += itemSold.value;
                            this.gold -= itemSold.value;
                            player.inventory.items.remove(itemSold);
                            System.out.printf("Sold %s for %d gold.\n", itemSold.name, itemSold.value);
                        }
                        else {
                            System.out.printf("The shopkeeper only has %d gold and can't afford your %s!\n", this.gold, itemSold.name);
                        }
                    }
                    else {
                        System.out.println("Invalid item reference!\n");
                    }
                }
                catch (Exception e) {
                    System.out.println("Invalid input!\n");
                }
            }
        }
    }

    public void doctorVisit(Hero player) {
        boolean decisionMade = false;
        while (!decisionMade && player.gold > 9) {
        String decision = c.readLine("Do you want the doctor to restore your health (costs 10 gold, y/n)?");
            if (decision.equals("y")) {
                    player.gold -= 10;
                    player.health = player.maxHealth;
                    decisionMade = true;
                    System.out.println("The doctor has restored you to full health.");
            }
            else if (decision.equals("n")) {
                decisionMade = true;
            }
            else {
                System.out.println("Please enter y or n.");
            }
        }
        if (player.gold < 10) {
            System.out.println("You can't afford to visit the doctor!");
        }
    }
}
