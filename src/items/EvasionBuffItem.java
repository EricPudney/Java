package src.items;

import src.characters.Hero;

public class EvasionBuffItem extends Item implements MagicItem {
    double buff;
    
    static String[][] evasionItemList = {
        {"Sneaky boots", "Footwear that magically muffles your steps!"},
        {"Cloak of shadows", "A magical cape that helps you blend into the shadows!"},
        {"Silent headband", "An ornate golden tiara that conceals any noise you make!"}
    };

    public EvasionBuffItem(){
        super();
    }

    public EvasionBuffItem(String name, String description, int value, double buff) {
        super(name, description, value);
        this.buff = buff;
    }

    public static EvasionBuffItem generateEvasionBuffItem() {
        EvasionBuffItem item = new EvasionBuffItem();
        int index = rng.nextInt(evasionItemList.length -1);
        item.name = evasionItemList[index][0];
        item.description = evasionItemList[index][1];
        item.buff = 0.1;
        item.value = 20;
        return item;
    }

    public void applyBuff(Hero player) {
        player.evasion += buff;
    }
}
