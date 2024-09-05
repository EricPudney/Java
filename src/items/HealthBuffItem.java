package src.items;

import src.characters.Hero;


public class HealthBuffItem extends Item implements MagicItem {
    double buff;

    static String[][] healthItemList = {
        {"Enchanted armour", "A suit of light armour that boosts your health!"},
        {"Talisman of protection", "A protective charm worn around the neck!"},
        {"Ring of resistance", "This ring helps you block enemy attacks!"}
    };
    
    public HealthBuffItem(){
        super();
    }

    public HealthBuffItem(String name, String description, int value, double buff) {
        super(name, description, value);
        this.buff = buff;
    }

    public static HealthBuffItem generateHealthBuffItem() {
        HealthBuffItem item = new HealthBuffItem();
        int index = rng.nextInt(healthItemList.length -1);
        item.name = healthItemList[index][0];
        item.description = healthItemList[index][1];
        item.buff = 1;
        item.value = 20;
        return item;
    }


    public void applyBuff(Hero player) {
        player.maxHealth += buff;
        player.health += buff;
    }
}
