import java.util.ArrayList;

public class Shop {
    ArrayList<Item> stock;
    int gold;

    public Shop(int gold) {
        this.gold = gold;

    }

    public void buyGoods(Hero player) {
        int price = 0;
        for (Item item : player.inventory) {
            // could simplify this if statement by having separate item classes.
            if (item.healthBuff == 0 && item.attackBuff == 0 && this.gold > item.value) {
                price += item.value;
                this.gold -= item.value;
                System.out.printf("The shopkeeper bought your %s for %d!\n", item.name, item.value);
            }
        }
        player.gold += price;
        System.out.printf("You now have %d gold.\n", player.gold);
    }
}
