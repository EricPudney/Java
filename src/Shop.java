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
        String decision = c.readLine("Do you want to buy, sell, or exit the shop (b/s/x)?");
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
                System.out.println("Please enter b to buy, s to sell, or x to leave the shop.");
            }
        }
    }

    private Inventory generateStock() {
        Inventory inventory = new Inventory(8);
        // stock to include new item types including healing potion and backpack (extends inventory)
        inventory.items[0] = new Item();
        return inventory;
    }

    public void buyGoods(Hero player) {
        System.out.println(this.stock);
        // choose item to buy by entering a number, or x to exit
    }
    
    public void sellGoods(Hero player) {
        System.out.println(player.inventory);
        // choose item to sell by entering a number, or x to exit
    }

    public void doctorVisit(Hero player) {
        boolean decisionMade = false;
        while (!decisionMade && player.gold > 9) {
        String decision = c.readLine("Do you want the doctor to restore your health (costs 10 gold, y/n)?");
            if (decision.equals("y")) {
                    player.gold -= 10;
                    player.health = player.maxHealth;
                    decisionMade = true;
            }
            else if (decision.equals("n")) {
                decisionMade = true;
            }
            else {
                System.out.println("Please enter y or n.");
            }
        }
    }
/*
    public String toString() {
        String returnValue = "The shop has the following items in stock: ";
        int i = 1;
        for (Item item : stock.items) {

        }
        return returnValue;
    }

 * public void sellGoods(Hero player) throws InterruptedException {
        int price = 0;
        List<Item> inventory = new ArrayList<>(player.inventory);
        for (Item item : inventory) {
            if (this.gold > item.value && !(item instanceof MagicItem)) {
                price += item.value;
                this.gold -= item.value;
                player.inventory.remove(item);
                System.out.printf("The shopkeeper bought your %s for %d gold!\n", item.name, item.value);
                Thread.sleep(750);
            }
            else if (this.gold < item.value && !(item instanceof MagicItem)) {
                System.out.println("The shopkeeper can't afford to buy any more of your valuables!");
            }
        }
        player.gold += price;
    }
 */
    
}
