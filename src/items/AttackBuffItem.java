package src.items;

import src.characters.Hero;

public class AttackBuffItem extends Item implements MagicItem, Equippable {
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
        int index = rng.nextInt(attackItemList.length -1);
        item.name = attackItemList[index][0];
        item.description = attackItemList[index][1];
        item.buff = 1;
        item.value = 20;
        return item;
    }

    public void applyBuff(Hero player) {
        player.attack += buff;
    }

    public void removeBuff(Hero player) {
        player.attack -= buff;
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
