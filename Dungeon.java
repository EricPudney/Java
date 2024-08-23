import java.util.Arrays;

public class Dungeon {
    int width;
    int height;
    char[][] grid;

    public Dungeon(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        double size = Math.sqrt(width * height);
        int i = 0;
        while (i <= size) {
            int x = Math.round((float)Math.random() * (width - 1));
            int y = Math.round((float)Math.random() * (height - 1));
            if (grid[x][y] == '\u0000') {
                grid[x][y] = 'X';
                i += 1;
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

    public String toString() {
        String description = "Dungeon: \n";
        for (char[] row : this.grid) {
            description = description.concat(Arrays.toString(row)).concat("\n");
        }
        return description;
    }

    public void introductionText() {
        System.out.println("You stand at the entrance of a dark and gloomy dungeon. You can move through the dungeon to the north, east, and west.");
    }
}