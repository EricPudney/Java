public class Item {

    String name;
    String description;
    int value;
    int healthBuff = 0;
    int attackBuff = 0;

    static String[][] itemList = {
        {"Necklace", "A simple gold necklace"},
        {"Ring", "A silver ring encrusted with rubies"},
        {"Finery", "beautifully embroidered silk robes"},
        {"Platinum ore", "A lump of precious metal"},
        {"Ancient book", "A finely printed tome in full calf binding"}
    };
    
    static String[][] magicItemList = {
        {"Enchanted armour", "A suit of light armour that boosts your health!"},
        {"Weapon salve", "A magical preparation that increases your damage!"}
    };

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Item(String name, String description, int value, int healthBuff, int attackBuff) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.healthBuff = healthBuff;
        this.attackBuff = attackBuff;
    }

    public void applyBuff(Hero player) {
            player.health += healthBuff;
            player.attack += attackBuff;
    }

    public static Item generateItem() {
        double rng = Math.random();
        Item item;
        if (rng >= 0.8) {
            int rng2 = Math.round((float)Math.random() * (magicItemList.length -1));
            String name = magicItemList[rng2][0];
            String description = magicItemList[rng2][1];
            // very bad solution here, will do for now
            int healthBuff = 0;
            int attackBuff = 0;
            switch (rng2) {
                case 0:
                    healthBuff = 1;
                    break;
                case 1:
                    attackBuff = 1;
                    break;
            }
            item = new Item(name, description, 20, healthBuff, attackBuff);
        }
        else {
            int rng2 = Math.round((float)Math.random() * (itemList.length -1));
            String name = itemList[rng2][0];
            String description = itemList[rng2][1];
            item = new Item(name, description, 5);
        }
        return item;
    }
}
