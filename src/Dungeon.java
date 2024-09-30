package src;
import src.characters.Hero;
import src.characters.Minion;
import src.items.Item;

public class Dungeon {
    public int width;
    public int height;
    char[][] grid;
    public Minion[][] monsters;
    String[][] descriptions;
    public Inventory[][] items;
    String[] locations = {"You stand in a dim and murky room. Green stuff oozes from the decaying bricks in the wall. ", 
    "By pale candlelight you make out the oblong shape of the dank and musty room. ", 
    "You are in a large chamber, its splendour now entirely faded. Pale gunk drips from the ceiling. ", 
    "You find yourself in a room that seems more like a cave and reeks of old cheese. ", 
    "A pale red light suffuses this chamber, which is entirely devoid of furniture. ", 
    "Ivy covers the walls of this room. ", 
    "An eerie whistling in the air turns your blood cold. ", 
    "The dungeon seems even darker and gloomier here, and somewhere in the distance you hear an eerie cackling. "};
    
    public Dungeon(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        this.monsters = new Minion[width][height];
        this.descriptions = new String[width][height];
        this.items = (Inventory[][]) new Inventory[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                items[i][j] = new Inventory();
            }
        }

        // places the treasure somewhere in the dungeon
        int x = Main.rng.nextInt(width);
        int y = Main.rng.nextInt(height);
        grid[x][y] = 'T';

        // fills the dungeon with a suitable number of items and monsters
        double size = Math.sqrt(width * height);
        while (size > 0) {
            x = Main.rng.nextInt(width);
            y = Main.rng.nextInt(height);
            if (monsters[x][y] == null) {
                monsters[x][y] = Minion.generateMonster(x, y);
                size -= 0.5;
            };
            x = Main.rng.nextInt(width);
            y = Main.rng.nextInt(height);
            double rng = Math.random();
            
            // important to use standard add(), NOT the custom addToInventory() method
            if (grid[x][y] != 'T') {
                if (rng <= 0.8) {
                    items[x][y].add(Item.generateItem());
                }
                else {
                    items[x][y].add(Item.generateMagicItem());
                }
                size -= 0.5;
            }
        }

        // assigns each location in the dungeon a short descriptive text
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int descriptionIndex = Main.rng.nextInt(locations.length - 1);
                String randomDescription = locations[descriptionIndex];
                descriptions[i][j] = randomDescription;
            }
       }
    }

    public String describeLocation(Hero player) {
        int x = player.currentLocation[0];
        int y = player.currentLocation[1];
        if (this.monsters[x][y] != null && this.monsters[x][y].isAlive) {
            String msg = String.format("You have encountered a monster! A %s stands before you, blocking the way.", monsters[x][y].name);
            return msg;
        }
        String returnValue = (descriptions[x][y]);
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
        if (this.monsters[x][y] != null && !this.monsters[x][y].isAlive) {
            String msg = String.format("There is a dead %s here. ", monsters[x][y].name);
            returnValue = returnValue.concat(msg);
        }
        if (this.items[x][y].size() == 1) {
            Item item = this.items[x][y].get(0);
            String msg = String.format("You have found an item! There is a %s here. ", item.name);
            returnValue = returnValue.concat(msg);
        }
        else if(this.items[x][y].size() > 1) {
            String msg = "You have found multiple items: \n";
            for (Item item : this.items[x][y]) {
                msg = msg.concat("  " + item.name + " \n");
            }
            returnValue = returnValue.concat(msg);
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
        return returnValue;
    }

    public String toString(Hero player) {
        String description = "Dungeon: \n\n Key: \n X marks your current position\n - indicates an unexplored region\n [ ] indicates a previously explored room in the dungeon\n i indicates an explored location with an item in it";
         
        for (int i = this.width - 1; i >= 0; i--) {
            description = description.concat("\n | ");
            for (int j = this.height - 1; j >= 0; j--) {
                if (player.currentLocation[0] == i && player.currentLocation[1] == j) {
                       description = description.concat(" X ");
                   }
                else if (this.grid[i][j] == 'E' && this.items[i][j].size() == 0) {
                    description = description.concat("[ ]");
                }
                else if (this.grid[i][j] == 'E' && this.items[i][j].size() > 0) {
                    description = description.concat(" i ");
                }
                else {
                    description = description.concat(" - ");
                }
                description = description.concat(" | ");
            }
        }
        return description;
    }
}