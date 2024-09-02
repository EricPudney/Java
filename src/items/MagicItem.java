package src.items;

import src.characters.Hero;


public class MagicItem extends Item {
    BuffType buffType;
    double buff;

    static String[][] healthItemList = {
        {"Enchanted armour", "A suit of light armour that boosts your health!"},
        {"Talisman of protection", "A protective charm worn around the neck!"},
        {"Ring of resistance", "This ring helps you block enemy attacks!"}
    };
    
    static String[][] attackItemList = {
        {"Hurty ring", "A ring that makes your enemies bleed faster!"},
        {"Ouchy stone", "An enchanted stone that increses your damage output!"},
        {"Weapon salve", "A magical preparation that increases your damage!"}
    };
    
    static String[][] evasionItemList = {
        {"Sneaky boots", "Footwear that magically muffles your steps!"},
        {"Cloak of shadows", "A magical cape that helps you blend into the shadows!"},
        {"Silent headband", "An ornate golden tiara that conceals any noise you make!"}
    };

    public MagicItem(){
        super();
    }

    public MagicItem(String name, String description, int value, BuffType buffType, double buff) {
        super(name, description, value);
        this.buffType = buffType;
        this.buff = buff;
    }

    public void applyBuff(Hero player) {
        switch (this.buffType) {
            case attack:
                player.attack += this.buff;
                break;
            case health:
                player.health += this.buff;
                break;
            case evasion:
                player.evasion += this.buff;
                break;
        }
    }

    public static MagicItem generateMagicItem() {
        MagicItem item = new MagicItem();
        double rng = Math.random();
        int rng2 = Math.round((float)Math.random() * (healthItemList.length -1));
        if (rng <= 0.6) {
            item.name = healthItemList[rng2][0];
            item.description = healthItemList[rng2][1];
            item.buffType = BuffType.health;
            item.buff = 1;
        }
        else if (rng <= 0.9) {
            item.name = attackItemList[rng2][0];
            item.description = attackItemList[rng2][1];
            item.buffType = BuffType.attack;
            item.buff = 1;
        }
        else {
            item.name = evasionItemList[rng2][0];
            item.description = evasionItemList[rng2][1];
            item.buffType = BuffType.evasion;
            item.buff = 0.1;
        }
        item.value = 20;
        return item;
    }
}

enum BuffType {
    attack,
    health,
    evasion
}
