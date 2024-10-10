package src;

import src.characters.Hero;
import src.items.Item;

public class Dungeon {
    public Location[][] locations;
    public int width;
    public int height;
    
    public Dungeon(int width, int height){
        this.width = width;
        this.height = height;
        locations = new Location[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean noWaySouth = i == height - 1;
                boolean noWayNorth = i == 0;
                boolean noWayEast = j == width - 1;
                boolean noWayWest = j == 0;
                locations[i][j] = new Location(noWaySouth, noWayNorth, noWayEast, noWayWest);
            }
        }

        // places the treasure somewhere in the dungeon
        int x = Main.rng.nextInt(this.width);
        int y = Main.rng.nextInt(this.height - 1);
        // create a treasure inner class to go here!
        locations[y][x].items.add(Item.generateTreasure(width, height));
    }

    public String toString(Hero player) {
        String map = "Dungeon: \n\n Key: \n X marks your current position\n - indicates an unexplored region\n [ ] indicates a previously explored room in the dungeon\n [i] indicates an explored location with an item in it";
        for (int i = 0; i < this.height; i++) {
            map = map.concat("\n | ");
            for (int j = 0; j < this.width; j++) {
                if (player.currentLocation == locations[i][j]) {
                    map = map.concat(" X ");
                }
                else if (locations[i][j].explored && locations[i][j].items.size() == 0) {
                    map = map.concat("[ ]");
                }
                else if (locations[i][j].explored && locations[i][j].items.size() > 0) {
                    map = map.concat("[i]");
                }
                else {
                    map = map.concat(" - ");
                }
                map = map.concat(" | ");
            }
        }
        map = map.concat("\n width: " + width + "\n height: " + height);
        return map;
    }

    
}