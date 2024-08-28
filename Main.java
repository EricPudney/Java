public class Main {
    
    public static boolean firstTurn = true;

    public static void main(String[] args) throws Exception {

        Dungeon dungeon = new Dungeon(5, 5);
        Hero player = Hero.CharacterCreation();
        player.currentLocation[0] = 0;
        player.currentLocation[1] =  Math.round(dungeon.width/2 - 1);

        dungeon.grid[0][player.currentLocation[1]] = 'E';
        boolean skipDescription = false;

        while (player.isAlive && !player.foundTreasure) {
            int[] here = {player.currentLocation[0], player.currentLocation[1]};
            if (!skipDescription) {
                System.out.println(dungeon.describeLocation(player)); 
            }
            skipDescription = false;
            firstTurn = false;
            if (player.foundTreasure && (dungeon.monsters[here[0]][here[1]] == null || !dungeon.monsters[here[0]][here[1]].isAlive)) {
                break;
            }
            if (dungeon.monsters[here[0]][here[1]] != null && dungeon.monsters[here[0]][here[1]].isAlive) {
                player.encounter(dungeon.monsters[here[0]][here[1]]);
            }
            try {
                player.command(dungeon);
            }
            catch (Exception e) {
                if (e.getMessage().equals("You can't go that way!")) {
                    System.out.println(e.getMessage());
                    skipDescription = true;
                }
            }
        }
        if (!player.isAlive) {
            System.out.printf("%s was killed in the horrible dungeon!", player.name);
        }
        else if (player.foundTreasure) {
            System.out.printf("Hooray! %s found the treasure!", player.name);
        }           
    }
}
