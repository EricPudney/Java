package src.areas;

import src.Decision;
import src.Decision.Actions;
import src.Decision.Commands;
import src.Inventory;
import src.characters.Hero;
import src.items.*;

public class Shop extends Inventory {
    int gold;

    public Shop(int gold) {
        this.gold = gold;
        generateStock();
        // no max size for shop inventory
    }

    public void shopVisit(Hero player) {
        for (int i = 0; i < player.inventory.size(); i++) {
            Item item = player.inventory.get(i);
            if (item.name.equals("Treasure")) {
                player.gold += item.value;
                player.inventory.remove(item);
                System.out.printf("You have sold the treasure for %d gold!\n", item.value);
            }
        }
        while (true) {
            Commands[] commands = {Commands.s, Commands.b, Commands.a, Commands.x};
            System.out.printf("You have %d gold.\n", player.gold);
            Actions action = Decision.makeShopDecision("Please enter b to buy, s to sell, a to sell all junk items, or x to leave the shop.\n", commands);
            switch (action) {
                case sell:
                    Item itemSold = player.inventory.selectFromInventory("sell");
                        if (itemSold == null) {
                        continue;
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
            }
        }
    }

    private void generateStock() {
        // stock = 1 junk item, 1 magic item, 1 bag. will lead to magic item inflation & invincible heroes
        this.add(Item.generateItem());
        this.add(Item.generateMagicItem());
        this.add(new HealingPotion());
        // currently hard-coded here; could allow for larger bags?
        this.add(new Bag(5));
    }

    public void buyGoods(Hero player) {
        Item itemBought = this.selectFromInventory("buy");
        if (itemBought == null) {
            return;
        }
        if (player.gold >= itemBought.value) {
            if (player.inventory.addToInventory(itemBought) && this.remove(itemBought)) {
                System.out.printf("Bought %s for %d gold.\n", itemBought.name, itemBought.value);
                player.gold -= itemBought.value;
                this.gold += itemBought.value;
            }
        }
        else {
            System.out.println("You can't afford that!");
        }
    }
    
    public boolean sellGoods(Hero player, Item itemSold) {
        System.out.printf("The shopkeeper has %d gold.\n", gold);
        if (this.gold >= itemSold.value) {
            if (player.inventory.remove(itemSold)) {
                System.out.printf("Sold %s for %d gold.\n", itemSold.name, itemSold.value);
                player.gold += itemSold.value;
                this.gold -= itemSold.value;
                this.add(itemSold);
                return true;
            }
            else {
                System.out.println("Something went wrong - unable to sell item.\n");
                return false;
            }
        }
        else {
            System.out.printf("The shopkeeper can't afford to buy your %s!\n", itemSold.name);
            return false;
        }
    }

    public void sellJunkGoods(Hero player) {
        int junkItems = 0;
        for (int i = 0; i < player.inventory.size(); i++) {
            Item item = player.inventory.get(i);
            if (item instanceof MagicItem || item instanceof Equippable || item instanceof Usable) {
                continue;
            }
            junkItems++;
            if (sellGoods(player, item)) {
                i--;
            }
        }
        System.out.printf("The shopkeeper has %d gold remaining.\n", gold);
        if (junkItems == 0) {
            System.out.println("You don't have any junk items to sell!\n");
        }
    }
}
