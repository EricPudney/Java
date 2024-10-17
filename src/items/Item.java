package src.items;

public class Item {
    public String name;
    public String description;
    public int value;

    static String[][] itemList = {
        {"Necklace", "A simple gold necklace"},
        {"Ring", "A silver ring encrusted with rubies"},
        {"Finery", "beautifully embroidered silk robes"},
        {"Platinum ore", "A lump of precious metal"},
        {"Ancient book", "A finely printed tome in full calf binding"}
    };

    public Item(){
    }

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public static Item generateTreasure(int width, int height) {
        String name = "Treasure";
        String description = "A pile of valuables to be sold in town!";
        int value = width * height;
        return new Item(name, description, value);
    }

    public static Item generateItem() {
        int rng = Math.round((float)Math.random() * (itemList.length -1));
        String name = itemList[rng][0];
        String description = itemList[rng][1];
        return new Item(name, description, 5);
    }

    public static Item generateMagicItem() {
        double rng = Math.random();
        if (rng <= 0.6) {
            return HealthBuffItem.generateHealthBuffItem();
        }
        else if (rng <= 0.9) {
            return AttackBuffItem.generateAttackBuffItem();
        }
        else {
            return EvasionBuffItem.generateEvasionBuffItem();
        }
    }
}
