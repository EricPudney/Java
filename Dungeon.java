import java.util.Arrays;

public class Dungeon {
    int width;
    int height;
    char[][] grid;
    Minion[][] monsters;

    public Dungeon(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        this.monsters = new Minion[width][height];
        double size = Math.sqrt(width * height);
        while (size > 0) {
            int x = Math.round((float)Math.random() * (width - 1));
            int y = Math.round((float)Math.random() * (height - 1));
            if (monsters[x][y] == null) {
                monsters[x][y] = Minion.generateMonster(x, y);
                size -= 1;
            };
        }
        boolean treasure = false;
        while (treasure == false) {
            int x = Math.round((float)Math.random() * (width - 1));
            int y = Math.round((float)Math.random() * (height - 1));
            if (grid[x][y] == '\u0000') {
                grid[x][y] = 'T';
                treasure = true;
            };
        }

    }

    public String describeLocation(Hero player) {
        if (Main.firstTurn) {
            String intro = "You stand at the entrance of a dark and gloomy dungeon. You can move through the dungeon to the north, east, and west.";
            return intro;
        }
        String returnValue = "You move through the gloomy dungeon. ";
        int x = player.currentLocation[0];
        int y = player.currentLocation[1];
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
            returnValue = returnValue.concat("You have discovered the treassure! ");
            this.grid[x][y] = 'E';
            player.foundTreasure = true;
            break;
            case 'E':
            returnValue = returnValue.concat("You have been here before. ");
            break;
            default:
            returnValue = returnValue.concat("This is a new part of the dungeon. ");
            this.grid[x][y] = 'E';
            break;
        }
        if (this.monsters[x][y] != null) {
            if (this.monsters[x][y].isAlive) {
                returnValue = String.format(returnValue, "You have encountered a monster! A %s stands before you, blocking the way.", monsters[x][y].name);
            }
            else {
                returnValue = String.format(returnValue, "There is a dead %s here.", monsters[x][y].name);
            }
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