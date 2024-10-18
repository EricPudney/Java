package src.items;

public enum Material {
    COPPER(5, 10),
    IRON(10, 15),
    STEEL(15, 20),
    MITHRIL(20, 25);

    public final int attack;
    public final int defence;

    Material(int attack, int defence) {
        this.attack = attack;
        this.defence = defence;
    }
}
