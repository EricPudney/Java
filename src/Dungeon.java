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
        locations = new Location[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean noWaySouth = i == 0;
                boolean noWayNorth = i == height - 1;
                boolean noWayEast = j == 0;
                boolean noWayWest = j == width - 1;
                locations[i][j] = new Location(noWaySouth, noWayNorth, noWayEast, noWayWest);
            }
        }

        // places the treasure somewhere in the dungeon - height and width need to be switched I think:::?
        int x = Main.rng.nextInt(this.width);
        int y = Main.rng.nextInt(this.height);
        // create a treasure class to go here!
        locations[x][y].items.add(Item.generateMagicItem());
    }

    public String toString(Hero player) {
        String map = "Dungeon: \n\n Key: \n X marks your current position\n - indicates an unexplored region\n [ ] indicates a previously explored room in the dungeon\n [i] indicates an explored location with an item in it";
        for (int i = this.height - 1; i >=0; i--) {
            map = map.concat("\n | ");
            for (int j = this.width - 1; j >= 0 ; j--) {
                if (player.currentLocation[0] == i && player.currentLocation[1] == j) {
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