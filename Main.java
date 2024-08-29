public class Main {
    
    public static boolean firstTurn = true;

    public static void main(String[] args) throws Exception {
        // Victory counter, initiate character creation
        int successfulRuns = 0;
        Hero player = Hero.CharacterCreation();
        // initiate dungeon loop
        while (player.isAlive) {
            // create a small dungeon, set initial position in dungeon
            Dungeon dungeon = new Dungeon(5 + (successfulRuns * 2), 5  + (successfulRuns * 2));
            player.currentLocation[0] = 0;
            player.currentLocation[1] =  Math.round(dungeon.width/2);
    
            // marks initial location as explored
            dungeon.grid[0][player.currentLocation[1]] = 'E';
            // needed to skip redescription of location when an invalid command is entered
            boolean skipDescription = false;
    
            // initiates game loop
            while (player.isAlive && !player.foundTreasure) {
                int[] here = {player.currentLocation[0], player.currentLocation[1]};
                if (!skipDescription) {
                    System.out.println(dungeon.describeLocation(player)); 
                }
                // resets skip description variable to false in case of an earlier invalid command
                skipDescription = false;
                // unique description for first turn, only appears once
                firstTurn = false;
                // ends game loop if loss or victory conditions met
                if (player.foundTreasure && (dungeon.monsters[here[0]][here[1]] == null || !dungeon.monsters[here[0]][here[1]].isAlive)) {
                    break;
                }
                // starts encounter with a monster
                if (dungeon.monsters[here[0]][here[1]] != null && dungeon.monsters[here[0]][here[1]].isAlive) {
                    player.encounter(dungeon.monsters[here[0]][here[1]]);
                }
                // handles player movement
                try {
                    player.command(dungeon);
                }
                // handles invalid commands
                catch (Exception e) {
                    if (e.getMessage().equals("You can't go that way!")) {
                        System.out.println(e.getMessage());
                        skipDescription = true;
                    }
                }
            }
            // Victory and loss messages
            if (!player.isAlive) {
                System.out.printf("%s was killed in the horrible dungeon!", player.name);
            }
            else if (player.foundTreasure) {
                successfulRuns += 1;
                player.foundTreasure = false;
                firstTurn = true;
                System.out.printf("Hooray! %s found the treasure! you now have %d gold. Now it is time for another adventure in a larger dungeon!\n\n\n", player.name, player.gold);
            }
        }
    }
}
