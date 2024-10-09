package src.items;

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

    public void removeBuff(Hero player) {
        player.maxHealth -= buff;
        player.health -= buff;
    }

    @Override
    public boolean equip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equip'");
    }

    @Override
    public boolean unEquip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unEquip'");
    }
}
