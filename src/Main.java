package src;
import src.characters.Hero;
import java.util.Random;
import java.util.Arrays;


public class Main {

    public static Random rng = new Random();
    
    public static boolean firstTurn = true;

    public static void printHelpText() {
        System.out.println("HOW TO PLAY: \nUse the n, e, w and s keys to indicate which direction you want to move in. \nYou can also enter h to print this help text again, c for information about your character, m to see a map of the dungeon, and i to see or drop an item from your inventory. \nIf you find an item you can enter t to add it to your inventory.\n");
    }

    public static int dungeonSize(int runs) {
        return 3 + rng.nextInt(4) + runs;
    }

    public static void main(String[] args) throws Exception {
        // Victory counter, initiate character creation
        int successfulRuns = 0;
        Hero player = Hero.CharacterCreation();

        // initiate dungeon loop
        while (player.isAlive) {
            if (firstTurn) {
                printHelpText();
                System.out.println("You enter the dungeon...");
            }
            // create a small dungeon, set initial position in dungeon
            Dungeon dungeon = new Dungeon(dungeonSize(successfulRuns), dungeonSize(successfulRuns));

            player.currentLocation[0] = 0;
            player.currentLocation[1] =  Math.round(dungeon.width/2);
    
            // needed to skip redescription of location when an invalid command is entered
            boolean skipDescription = false;
    
            // initiates game loop
            while (player.isAlive && !player.foundTreasure) {
                int[] here = player.currentLocation;
                if (!skipDescription) {
                    System.out.println(dungeon.locations[here[0]][here[1]].describeLocation(player));
                    dungeon.locations[here[0]][here[1]].explored = true;
                }
                // resets skip description variable to false in case of an earlier invalid command, switches off first turn text
                skipDescription = false;
                firstTurn = false;
                // ends game loop if loss or victory conditions met
                if (player.foundTreasure && (dungeon.locations[here[0]][here[1]].enemy == null || !dungeon.locations[here[0]][here[1]].enemy.isAlive)) {
                    break;
                }
                // starts encounter with a monster
                if (dungeon.locations[here[0]][here[1]].enemy != null && dungeon.locations[here[0]][here[1]].enemy.isAlive) {
                    player.encounter(dungeon.locations[here[0]][here[1]].enemy, dungeon);
                    if (player.isAlive) {
                        System.out.println(dungeon.locations[here[0]][here[1]].describeLocation(player));
                    }
                }
                // handles player movement
                if (!player.foundTreasure && player.isAlive) {
                    try {
                    player.command(dungeon);
                    }
                // handles invalid directional commands
                    catch (Exception e) {
                        System.out.println("You can't go that way!");
                        skipDescription = true;
                    }
                }
            }
            // Victory and loss messages
            if (!player.isAlive) {
                System.out.printf("%s was killed in the horrible dungeon!", player.name);
            }
            // reset everything & increase victory count for a new dungeon
            else if (player.foundTreasure) {
                successfulRuns += 1;
                player.foundTreasure = false;
                firstTurn = true;
                Thread.sleep(1500);
                System.out.printf("Hooray! %s found the treasure! you now have %d gold. \n\n", player.name, player.gold);
                Thread.sleep(2000);
                System.out.printf("Back in town, you visit the shop before heading out on another adventure.\n\n");
                Thread.sleep(2000);
                Shop shop = new Shop(20 + (successfulRuns * 5));
                shop.shopVisit(player);
                shop.doctorVisit(player);
                Thread.sleep(2000);
            }
        }
    }
}
