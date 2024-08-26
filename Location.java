public class Location {

    public static String describeLocation(Dungeon dungeon, Character player) {
        
        String returnValue = "You move through the gloomy dungeon. ";
        int x = player.currentLocation[0];
        int y = player.currentLocation[1];
        if (x == 0) {
            returnValue = returnValue.concat("There is no way south. ");
        }
        if (x == dungeon.width - 1) {
            returnValue = returnValue.concat("There is no way north. ");
        }
        if (y == 0) {
            returnValue = returnValue.concat("There is no way east. ");
        }
        if (y == dungeon.height - 1) {
            returnValue = returnValue.concat("There is no way west. ");
        }
        switch (dungeon.grid[x][y]) {
            case 'X':
                returnValue = returnValue.concat("You have encoutered a monster! ");
                dungeon.grid[x][y] = 'E';
                player.encounter = true;
                break;
            case 'T':
                returnValue = returnValue.concat("You have discovered the treassure! ");
                dungeon.grid[x][y] = 'E';
                player.foundTreasure = true;
                break;
            case 'E':
                returnValue = returnValue.concat("You have been here before. ");
                break;
            default:
                returnValue = returnValue.concat("This is a new part of the dungeon. ");
                dungeon.grid[x][y] = 'E';
                break;
        }
        return returnValue;
    }

  /*
    public String toString() {
        String returnValue = "";
        return returnValue;
    }
  */
  
  
}

enum Moves {
    n,
    e,
    w,
    s
}
