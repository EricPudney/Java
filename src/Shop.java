package src;
import java.io.Console;

import src.Decision.Actions;
import src.Decision.Commands;
import src.characters.Hero;
import src.items.*;

public class Shop extends Inventory{
    int gold;

    static Console c = System.console();

    public Shop(int gold) {
        this.gold = gold;
        generateStock();
        // no max size for shop inventory
    }

    public void shopVisit(Hero player) {
        while (true) {
            Commands[] commands = {Commands.s, Commands.b, Commands.a, Commands.x};
            Actions action = Decision.makeShopDecision("Please enter b to buy, s to sell, a to sell all junk items, or x to leave the shop.\n", commands);
            switch (action) {
                case sell:
                    Item itemSold = player.inventory.selectFromInventory("sell");
                        if (itemSold == null) {
                        return;
                        }
                    sellGoods(player, itemSold);
                    break;
                case sellAll:
                    sellJunkGoods(player);
                    break;
                case buy:
                    buyGoods(player);
                    break;
                case exit:
                    return;
                case null, default:
                    return;
            }
        }
    }

    private void generateStock() {
        // stock = 1 junk item, 1 magic item, 1 bag. will lead to magic item inflation & invincible heroes
        this.add(Item.generateItem());
        this.add(Item.generateMagicItem());
        // currently hard-coded here; could allow for larger bags?
        this.add(new Bag(5));
    }

    public void buyGoods(Hero player) {
        Item itemBought = this.selectFromInventory("buy");
        if (itemBought == null) {
            return;
        }
        if (player.gold >= itemBought.value) {
            if (player.inventory.addToInventory(itemBought, player) && this.remove(itemBought)) {
                System.out.printf("Bought %s for %d gold.\n", itemBought.name, itemBought.value);
                player.gold -= itemBought.value;
            }
        }
        else {
            System.out.println("You can't afford that!");
        }
    }
    
    public void sellGoods(Hero player, Item itemSold) {
        if (this.gold >= itemSold.value) {
            if (player.inventory.removeFromInventory(itemSold, player)) {
                System.out.printf("Sold %s for %d gold.\n", itemSold.name, itemSold.value);
                player.gold += itemSold.value;
                this.add(itemSold);
            }
            else {
                System.out.println("Something went wrong - unable to sell item.");
            }
        }
        else {
            System.out.printf("The shopkeeper can't afford to buy your %s!", itemSold.name);
        }
    }

    // enhanced for-loop doesn't work but this seems to be OK
    public void sellJunkGoods(Hero player) {
        int junkItems = 0;
        for (int i = 0; i < player.inventory.size(); i++) {
            Item item = player.inventory.get(i);
            if (item instanceof MagicItem || item instanceof Bag || item instanceof Weapon) {
                continue;
            }
            junkItems++;
            sellGoods(player, item);
            i--;
        }
        if (junkItems == 0) {
            System.out.println("You don't have any junk items to sell!\n");
        }
    }

    public void doctorVisit(Hero player) {
        boolean decisionMade = false;
        if (player.health == player.maxHealth) {
            System.out.println("You are in good health and don't need to visit the doctor!");
        }
        else if (player.gold < 10) {
            System.out.println("You can't afford to visit the doctor!");
        }
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
    }
}
