package src.items;

public enum Quality {
    POOR(0, 0),
    FAIR(1, 2),
    GOOD(2, 3),
    EXCELLENT(3, 5),
    MAGNIFICENT(5, 8);

    public final int attack;
    public final int defence;

    Quality(int attack, int defence) {
        this.attack = attack;
        this.defence = defence;
    }
}
