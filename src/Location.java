package src;

import src.characters.Minion;
import src.items.Item;

public class Location {
    public Minion enemy;
    public Inventory items;
    String description;
    boolean explored;
    public boolean noWaySouth;
    public boolean noWayNorth;
    public boolean noWayEast;
    public boolean noWayWest;


    static String[] texts = {"You stand in a dim and murky room. Green stuff oozes from the decaying bricks in the wall. ", 
    "By pale candlelight you make out the oblong shape of the dank and musty room. ", 
    "You are in a large chamber, its splendour now entirely faded. Pale gunk drips from the ceiling. ", 
    "You find yourself in a room that seems more like a cave and reeks of old cheese. ", 
    "A pale red light suffuses this chamber, which is entirely devoid of furniture. ", 
    "Ivy covers the walls of this room. ", 
    "An eerie whistling in the air turns your blood cold. ", 
    "The dungeon seems even darker and gloomier here, and somewhere in the distance you hear an eerie cackling. "};

    public Location(boolean noWaySouth, boolean noWayNorth, boolean noWayEast, boolean noWayWest) {
        this.items = new Inventory();
        this.explored = false;
        double prob = Main.rng.nextDouble();
        if (prob < 0.08) {
            enemy = Minion.generateMonster();
        }
        else if (prob > 0.92) {
            items.add(Item.generateItem());
        }
        int randomDescription = Main.rng.nextInt(texts.length);
        description = texts[randomDescription];
        this.noWaySouth = noWaySouth;
        this.noWayNorth = noWayNorth;
        this.noWayEast = noWayEast;
        this.noWayWest = noWayWest;
    }

    public String describeLocation() {
        String text = "";
        if (enemy != null && !enemy.isAlive) {
            text = String.format("There is a dead %s here.\n", enemy.name);
        }
        if (enemy != null && enemy.isAlive) {
            text = "You have encountered a monster! " + enemy + "\n";
            return description + text;
        }
        else if (items.size() > 0) {
            text = "You have found the following items: \n" + items + "\n";
        }
        if (noWaySouth) {
            text = text.concat("There is no way south from here.\n");
        }
        if (noWayNorth) {
            text = text.concat("There is no way north from here.\n");
        }
        if (noWayEast) {
            text = text.concat("There is no way east from here.\n");
        }
        if (noWayWest) {
            text = text.concat("There is no way west from here.\n");
        }
        return description + text;
    }
}
