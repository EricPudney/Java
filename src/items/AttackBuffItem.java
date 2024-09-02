package src.items;

import src.characters.Hero;

public class AttackBuffItem extends Item implements MagicItem {
    double buff;

    static String[][] attackItemList = {
        {"Hurty ring", "A ring that makes your enemies bleed faster!"},
        {"Ouchy stone", "An enchanted stone that increses your damage output!"},
        {"Weapon salve", "A magical preparation that increases your damage!"}
    };

    public AttackBuffItem(){
        super();
    }

    public AttackBuffItem(String name, String description, int value, double buff) {
        super(name, description, value);
        this.buff = buff;
    }

    public static AttackBuffItem generateAttackBuffItem() {
        AttackBuffItem item = new AttackBuffItem();
        int rng = Math.round((float)Math.random() * (attackItemList.length -1));
        item.name = attackItemList[rng][0];
        item.description = attackItemList[rng][1];
        item.buff = 1;
        item.value = 20;
        return item;
    }


    public void applyBuff(Hero player) {
        player.attack += buff;
    }
}