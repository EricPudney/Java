package src.items;

import src.Main;
import src.characters.Hero;

public class EvasionBuffItem extends Item implements MagicItem, Equippable {
    double buff;
    
    static String[][] evasionItemList = {
        {"Sneaky boots", "Footwear that magically muffles your steps!"},
        {"Cloak of shadows", "A magical cape that helps you blend into the shadows!"},
        {"Silent headband", "An ornate golden tiara that conceals any noise you make!"}
    };

    public EvasionBuffItem(){
        super();
    }

    public static EvasionBuffItem generateEvasionBuffItem() {
        EvasionBuffItem item = new EvasionBuffItem();
        int index = Main.rng.nextInt(evasionItemList.length -1);
        item.name = evasionItemList[index][0];
        item.description = evasionItemList[index][1];
        item.buff = 0.1;
        item.value = 20;
        return item;
    }

    public void applyBuff(Hero player) {
        player.evasion += buff;
    }
    
    public void removeBuff(Hero player) {
        player.evasion -= buff;
    }

    @Override
    public void equip(Hero player) {
        System.out.printf("You have equipped the %s!\n", name);
        applyBuff(player);
    }

    @Override
    public void unEquip(Hero player) {
        System.out.printf("You have unequipped the %s!\n", name);
        removeBuff(player);    
    }
}
