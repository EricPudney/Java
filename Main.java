import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        Dungeon dungeon = new Dungeon(5, 5);
        Character player = Character.CharacterCreation();
        player.currentLocation[0] = 0;
        player.currentLocation[1] =  Math.round(dungeon.width/2);

        dungeon.grid[0][player.currentLocation[1]] = 'E';
        dungeon.introductionText();
        
        while (player.isAlive && !player.foundTreasure) {
            try {
                if (player.encounter) {
                    Minion enemy = Minion.generateMonster();
                    System.out.println(enemy.toString());
                    System.out.printf("%s bravely attacks the monster!\n", player.name);
                    player.fightMonster(enemy);
                    player.encounter = false;
                    if (!player.isAlive) {
                        break;
                    }
                }
                player.command(dungeon);                 
                System.out.println(Location.describeLocation(dungeon, player));
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
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
