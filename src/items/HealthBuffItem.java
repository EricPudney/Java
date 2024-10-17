package src.items;

import src.Main;
import src.characters.Hero;

public class HealthBuffItem extends Item implements MagicItem, Equippable {
    double buff;

    static String[][] healthItemList = {
        {"Enchanted armour", "A suit of light armour that boosts your health!"},
        {"Talisman of protection", "A protective charm worn around the neck!"},
        {"Ring of resilience", "This ring helps you block enemy attacks!"}
    };
    
    public HealthBuffItem(){
        super();
    }

    public static HealthBuffItem generateHealthBuffItem() {
        HealthBuffItem item = new HealthBuffItem();
        int index = Main.rng.nextInt(healthItemList.length -1);
        item.name = healthItemList[index][0];
        item.description = healthItemList[index][1];
        item.buff = 1;
        item.value = 20;
        return item;
    }

    // these methods could be replaced by equip() and unequip()?
    public void applyBuff(Hero player) {
        player.maxHealth += (int) buff;
        player.health += (int) buff;
    }

    public void removeBuff(Hero player) {
        player.maxHealth -= (int) buff;
        player.health -= (int) buff;
    }

    @Override
    public void equip(Hero player) {
        System.out.printf("You have equipped the %s!\n", name);
        applyBuff(player);
    }

    @Override
    public void unEquip(Hero player) {
        System.out.printf("You have unequipped the %s\n", name);
        removeBuff(player);
    }
}
