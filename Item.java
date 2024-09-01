public class Item {
    String name;
    String description;
    int value;

    static String[][] itemList = {
        {"Necklace", "A simple gold necklace"},
        {"Ring", "A silver ring encrusted with rubies"},
        {"Finery", "beautifully embroidered silk robes"},
        {"Platinum ore", "A lump of precious metal"},
        {"Ancient book", "A finely printed tome in full calf binding"}
    };

    public Item(){
    }

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public static Item generateItem() {
        int rng = Math.round((float)Math.random() * (itemList.length -1));
        String name = itemList[rng][0];
        String description = itemList[rng][1];
        Item item = new Item(name, description, 5);
        return item;
    }
}
