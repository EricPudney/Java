public class MagicItem extends Item {
    int healthBuff;
    int attackBuff;

    static String[][] magicItemList = {
        {"Enchanted armour", "A suit of light armour that boosts your health!"},
        {"Weapon salve", "A magical preparation that increases your damage!"}
    };

    public MagicItem(String name, String description, int value, int healthBuff, int attackBuff) {
        super(name, description, value);
        this.healthBuff = healthBuff;
        this.attackBuff = attackBuff;
    }

    public void applyBuff(Hero player) {
        player.health += healthBuff;
        player.attack += attackBuff;
    }

    public static MagicItem generateMagicItem() {
        MagicItem item;
        int rng = Math.round((float)Math.random() * (magicItemList.length -1));
        String name = magicItemList[rng][0];
        String description = magicItemList[rng][1];
        // very bad solution here, will do for now
        int healthBuff = 0;
        int attackBuff = 0;
        switch (rng) {
            case 0:
                healthBuff = 1;
                break;
            case 1:
                attackBuff = 1;
                break;
        }
        item = new MagicItem(name, description, 20, healthBuff, attackBuff);
        return item;
    }
}
