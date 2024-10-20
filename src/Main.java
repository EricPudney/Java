package src;

import src.areas.Dungeon;
import src.areas.Shop;
import src.characters.Hero;

import java.util.Random;


public class Main {

    public static Random rng = new Random();

    public static boolean firstTurn = true;

    static int successfulRuns = 0;

    public static void printHelpText() {
        System.out.println("HOW TO PLAY: \nUse the n, e, w and s keys to indicate which direction you want to move in. \nYou can also enter h to print this help text again, c for information about your character, m to see a map of the dungeon, and i to interact with your inventory. \nIf you find an item you can enter t to add it to your inventory.\n");
    }

    public static int dungeonSize(int runs) {
        return 3 + rng.nextInt(4) + runs;
    }

    public static void endAdventure(Hero player) throws InterruptedException {
        successfulRuns += 1;
        player.foundTreasure = false;
        firstTurn = true;
        Thread.sleep(1500);
        System.out.printf("Hooray! %s found the treasure! \n\n", player.name);
        Thread.sleep(1500);
        System.out.print("Back in town, you visit the shop before heading out on another adventure.\n\n");
        Thread.sleep(1500);
        Shop shop = new Shop(20 + (successfulRuns * 5));
        shop.shopVisit(player);
        Thread.sleep(1500);
    }

    public static void main(String[] args) throws Exception {
        // Victory counter, initiate character creation
        Hero player = Hero.CharacterCreation();

        // initiate game loop
        while (player.isAlive) {
            if (firstTurn) {
                printHelpText();
                System.out.println("You enter the dungeon...");
            }
            // create a small dungeon, set initial position in dungeon
            Dungeon dungeon = new Dungeon(dungeonSize(successfulRuns), dungeonSize(successfulRuns));
            player.currentLocation = dungeon.locations[dungeon.height - 1][Math.round((float) dungeon.width / 2)];

            // initiates dungeon loop
            while (player.isAlive && !player.foundTreasure) {
                System.out.println(player.currentLocation.describeLocation());
                player.currentLocation.explored = true;
                firstTurn = false;

                // starts encounter with a monster
                if (player.currentLocation.enemy != null && player.currentLocation.enemy.isAlive) {
                    player.encounter(player.currentLocation.enemy);
                    if (player.isAlive) {
                        System.out.println(player.currentLocation.describeLocation());
                    }
                }

                // handles player movement/other input
                if (!player.foundTreasure && player.isAlive) {
                    player.command(dungeon);
                }
            }
            // Victory and loss messages
            if (!player.isAlive) {
                System.out.printf("%s was killed in the horrible dungeon!", player.name);
            }
            // reset everything, visit shop & increase victory count for a new dungeon
            else {
                endAdventure(player);
            }
        }
    }
}
