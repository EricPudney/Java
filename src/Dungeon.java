package src;
import java.util.Arrays;
import java.util.Random;

import src.characters.Hero;
import src.characters.Minion;
import src.items.Item;
import src.items.MagicItem;

public class Dungeon {
    public int width;
    public int height;
    char[][] grid;
    Minion[][] monsters;
    String[][] descriptions;
    Item[][] items;
    String[] locations = {"You stand in a dim and murky room. Green stuff oozes from the decaying bricks in the wall. ", 
    "By pale candlelight you make out the oblong shape of the dank and musty room. ", 
    "You are in a large chamber, its splendour now entirely faded. Pale gunk drips from the ceiling. ", 
    "You find yourself in a room that seems more like a cave and reeks of old cheese. ", 
    "A pale red light suffuses this chamber, which is entirely devoid of furniture. ", 
    "Ivy covers the walls of this room. ", 
    "An eerie whistling in the air turns your blood cold. ", 
    "The dungeon seems even darker and gloomier here, and somewhere in the distance you hear an eerie cackling. "};
    
    private static Random rng = new Random();

    public Dungeon(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        this.monsters = new Minion[width][height];
        this.descriptions = new String[width][height];
        this.items = new Item[width][height];

        // fills the dungeon with a suitable number of items and onsters
        double size = Math.sqrt(width * height);
        while (size > 0) {
            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            if (monsters[x][y] == null) {
                monsters[x][y] = Minion.generateMonster(x, y);
                size -= 0.5;
            };
            x = rng.nextInt(width);
            y = rng.nextInt(height);
            if (items[x][y] == null) {
                double rng = Math.random();
                if (rng <= 0.8) {
                    items[x][y] = Item.generateItem();
                }
                else {
                    items[x][y] = Item.generateMagicItem();
                }
                size -= 0.5;
            };
        }

        // places the treasure hoard somewhere in the dungeon
        boolean treasure = false;
        while (treasure == false) {
            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            if (grid[x][y] == '\u0000') {
                grid[x][y] = 'T';
                treasure = true;
            };
        }

        // assigns each location in the dungeon a short descriptive text
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int descriptionIndex = rng.nextInt(locations.length - 1);
                String randomDescription = locations[descriptionIndex];
                descriptions[i][j] = randomDescription;
            }
       }
    }

    public String describeLocation(Hero player) {
        if (Main.firstTurn) {
            String intro = "HOW TO PLAY: Use the n, e, w and s keys to indicate which direction you want to move in. You can also press h for help, i for information about your character, and p for your position in the dungeon.\nYou stand at the entrance of a dark and gloomy dungeon. You can move through the dungeon to the north, east, and west.";
            return intro;
        }
        String returnValue = "You move through the gloomy dungeon. ";
        int x = player.currentLocation[0];
        int y = player.currentLocation[1];
        returnValue = returnValue.concat(descriptions[x][y]);
        if (x == 0) {
            returnValue = returnValue.concat("There is no way south. ");
        }
        if (x == this.width - 1) {
            returnValue = returnValue.concat("There is no way north. ");
        }
        if (y == 0) {
            returnValue = returnValue.concat("There is no way east. ");
        }
        if (y == this.height - 1) {
            returnValue = returnValue.concat("There is no way west. ");
        }
        switch (this.grid[x][y]) {    
            case 'T':
            returnValue = returnValue.concat("You have discovered the treasure! ");
            this.grid[x][y] = 'E';
            player.foundTreasure = true;
            player.gold += (this.width * this.height);
            break;
            case 'E':
            returnValue = returnValue.concat("You have been here before. ");
            break;
            default:
            returnValue = returnValue.concat("This is a new part of the dungeon. ");
            this.grid[x][y] = 'E';
            break;
        }
        if (this.items[x][y] != null) {
            Item item = this.items[x][y];
            player.inventory.add(item);
            String msg = String.format("You have found an item! You add the %s to your inventory. ", item.name);
            // check whether this works!
            if (item instanceof MagicItem) {((MagicItem) item).applyBuff(player);};
            returnValue = returnValue.concat(msg);
            items[x][y] = null;
        }
        if (this.monsters[x][y] != null) {
            String msg = "";
            if (this.monsters[x][y].isAlive) {
                msg = String.format("You have encountered a monster! A %s stands before you, blocking the way.", monsters[x][y].name);
            }
            else {
                msg = String.format("There is a dead %s here.", monsters[x][y].name);
            }
            returnValue = returnValue.concat(msg);
        }
        return returnValue;
    }

    public String toString() {
        String description = "Dungeon: \n";
        for (char[] row : this.grid) {
            description = description.concat(Arrays.toString(row)).concat("\n");
        }
        return description;
    }
}