package src;
import java.io.Console;
import java.util.ArrayList;

import src.characters.Hero;
import src.items.*;

public class Shop {
    ArrayList<Item> stock;
    int gold;

    static Console c = System.console();

    public Shop(int gold) {
        this.gold = gold;
    }

    public void shopVisit(Hero player) {
        boolean decisionMade = false;
        while (!decisionMade) {
        String decision = c.readLine("Do you want to sell your valuable items (y/n)?");
            if (decision.equals("y")) {
                try {
                 //   this.sellGoods(player);
                    decisionMade = true;
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            else if (decision.equals("n")) {
                decisionMade = true;
            }
            else {
                System.out.println("Please enter y or n.");
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
