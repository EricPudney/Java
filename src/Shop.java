package src;
import java.io.Console;

import src.characters.Hero;
import src.items.*;

public class Shop {
    Inventory inventory;
    int gold;

    static Console c = System.console();

    public Shop(int gold) {
        this.gold = gold;
        inventory = generateStock();
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
        // currently hard-coded here; can be altered to allow for larger bags
        inventory.items.add(new Bag(5));
        return inventory;
    }

    public void buyGoods(Hero player) {
        Item itemBought = this.inventory.selectFromInventory("buy");
        if (itemBought == null) {
            return;
        }
        if (player.gold >= itemBought.value) {
            if (player.inventory.addToInventory(itemBought, player) && this.inventory.items.remove(itemBought)) {
                System.out.printf("Bought %s for %d gold.\n", itemBought.name, itemBought.value);
                player.gold -= itemBought.value;
            }
        }
        else {
            System.out.println("You can't afford that!");
        }
    }
    
    public void sellGoods(Hero player) {
        Item itemSold = player.inventory.selectFromInventory("sell");
        if (itemSold == null) {
            return;
        }
        if (this.gold >= itemSold.value) {
            if (player.inventory.removeFromInventory(itemSold, player)) {
                System.out.printf("Sold %s for %d gold.\n", itemSold.name, itemSold.value);
                player.gold += itemSold.value;
                this.inventory.items.add(itemSold);
            }
            else {
                System.out.println("Something went wrong - unable to sell item.");
            }
        }
        else {
            System.out.println("The shopkeeper can't afford that!");
        }
    }

    public void doctorVisit(Hero player) {
        boolean decisionMade = false;
        while (!decisionMade && player.health < player.maxHealth && player.gold > 9 ) {
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
        if (player.health == player.maxHealth) {
            System.out.println("You are in good health and don't need to visit the doctor!");
        }
        else if (player.gold < 10) {
            System.out.println("You can't afford to visit the doctor!");
        }
    }
}
