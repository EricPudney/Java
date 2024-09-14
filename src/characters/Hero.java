package src.characters;
import java.io.Console;
import java.io.IOException;

import src.Dungeon;
import src.Inventory;
import src.items.Item;

public class Hero extends Character {
    public double evasion;
    Weapon weapon;
    Race race;
    Type type;
    public int[] currentLocation = new int[2];
    public int gold = 0;
    private int xp = 0;
    public int level = 1;
    public boolean foundTreasure = false;
    public boolean encounter = false;
    public Inventory inventory;

    static Console c = System.console();

    public Hero(int attack, int health, double evasion, Type type, Race race, Weapon weapon, String name) {
        this.attack = attack;
        this.health = health;
        this.maxHealth = health;
        this.evasion = evasion;
        this.weapon = weapon;
        this.type = type;
        this.race = race;
        this.name = name;
        this.inventory = new Inventory(8);
    }

    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp / 20 >= level) {
            this.levelUp();
        }
    }

    public void levelUp() {
        this.level += 1;
        this.maxHealth += 1;
        this.health += 1;
        if (this.level % 5 == 0) {
            this.attack += 1;
        }
        System.out.printf("%s levelled up and is now level %d!\n", this.name, this.level);
    }

    public static void printHelpText() {
        System.out.println("HOW TO PLAY: Use the n, e, w and s keys to indicate which direction you want to move in. You can also press h for help, c for information about your character, i to see your inventory, and p for your position in the dungeon. If you find an item you can press t to add it to your inventory.");
    }

    public void command(Dungeon dungeon) throws Exception {
        boolean noError = false;
        Moves move = null;
        while (!noError && this.isAlive) {
        String newMove = c.readLine("What do you want to do?");
            try {
                move = Moves.valueOf(newMove);
                noError = true;
            }
            catch (Exception e) {
                if (newMove.equals("p")) {
                    System.out.println(dungeon);
                }
                else if (newMove.equals("h")) {
                    printHelpText();
                }
                else if (newMove.equals("c")) {
                    System.out.println(this);
                }
                else if (newMove.equals("i")) {
                    System.out.println(this.inventory);
                }
                else if (newMove.equals("t")) {
                    Item item = dungeon.items[this.currentLocation[0]][this.currentLocation[1]];
                    if (item == null) {
                        System.out.println("There is nothing here to take!");
                    }
                    if (item != null && this.inventory.addToInventory(item, this)) {
                        dungeon.items[this.currentLocation[0]][this.currentLocation[1]] = null;
                        System.out.printf("You added the %s to your inventory.\n", item.name);
                    };
                }
                else{
                    System.out.println(newMove + " is not a valid choice. Please try again.");
                }
            }
        }
        changeLocation(move, dungeon);
    }

    
    public void changeLocation(Moves command, Dungeon dungeon) throws Exception {
        switch(command) {
            case n:
                if (this.currentLocation[0] <= dungeon.width - 2) {
                    this.currentLocation[0] = this.currentLocation[0] += 1;
                    System.out.println("You move through the gloomy dungeon...");
                }
                else {
                    throw new Exception("You can't go that way!");
                }
                break;
            case e:
                if (this.currentLocation[1] > 0) {
                    this.currentLocation[1] = this.currentLocation[1] -= 1;
                    System.out.println("You move through the gloomy dungeon...");
                }
                else {
                    throw new Exception("You can't go that way!");
                }
                break;
            case w:
                if (this.currentLocation[1] <= dungeon.height - 2) {
                    this.currentLocation[1] = this.currentLocation[1] += 1;
                    System.out.println("You move through the gloomy dungeon...");
                }
                else {
                    throw new Exception("You can't go that way!");
                }
                break;
            case s:
                if (this.currentLocation[0] > 0) {
                    this.currentLocation[0] = this.currentLocation[0] -= 1;
                    System.out.println("You move through the gloomy dungeon...");
                }
                else {
                    throw new Exception("You can't go that way!");
                }
                break;
        }
    }
    
    public String toString() {
        String returnValue = "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.";
        return returnValue;
    }

    public void encounter(Minion enemy, Dungeon dungeon) {
        System.out.printf("%s: attack %d, health %d. Fight or run?\n", enemy.name, enemy.attack, enemy.health);
        boolean evaded = false;
        while (!evaded && enemy.isAlive && this.isAlive) {
        String decision = c.readLine("Press a to attack or x to try to escape.\n");
            if (decision.equals("i")) {
                System.out.println(this.toString());
            }
            else if (decision.equals("a")) {
                this.attack(enemy);
                if (enemy.isAlive) {
                    enemy.attack(this);
                }
                if (!enemy.isAlive) {
                    this.addXp(5);
                }
            }
            else if (decision.equals("x")) {
                double rng = Math.random();
                if (this.evasion > rng) {
                    evaded = true;
                    // not a great solution, it would be better to leave the monsters array as protected, but can't think of a better way to do this.
                    dungeon.monsters[enemy.currentLocation[0]][enemy.currentLocation[1]] = null;
                    this.addXp(2);
                    System.out.println("You have successfully evaded the monster.");
                }
                else {
                    System.out.printf("You failed to evade the %s!\n", enemy.name);
                    enemy.attack(this);
                }
            }
            else {
                System.out.println(decision + " is not a valid choice. Please try again.");
            }
        }
    }

    public static Hero CharacterCreation() throws IOException {
        Type type = Type.warrior;
        Race race = Race.human;
        Weapon weapon = Weapon.sword;
        boolean noError = false;
        while (!noError) {
            String typeChosen = c.readLine("Choose your character class (warrior, ranger or mage): ");
                try {
                    type = Type.valueOf(typeChosen);
                    noError = true;
                }
                catch (Exception e) {
                    System.err.println(typeChosen + " is not a valid choice. Please try again.");
                }
            }
        noError = false;
        while (!noError) {
            String raceChosen = c.readLine("Choose your character race (human, dwarf or elf): ");
            try {
                race = Race.valueOf(raceChosen);
                noError = true;
            }
            catch (Exception e) {
                System.err.println(raceChosen + " is not a valid choice. Please try again.");
            }
        }
        noError = false;
        while (!noError) {
            String weaponChosen = c.readLine("Choose your weapon (sword, staff or bow): ");
            try {
                weapon = Weapon.valueOf(weaponChosen);
                noError = true;
            }
            catch (Exception e) {
                System.err.println(weaponChosen + " is not a valid choice. Please try again.");
            }
        }
        
        String name = c.readLine("Give your character a name: ");

        int attack = 3;
        int health = 8;
        double evasion = 0.5;
        switch (type) {
            case warrior:
                evasion = 0.4;
                break;
            case mage:
                attack = 4;
                health = 5;
                break;
            case ranger:
                attack = 2;
                evasion = 0.6;
                break;
        }
        switch (race) {
            case human:
                break;
            case elf:
                evasion = evasion += 0.1;
                health = health - 2;
                break;
            case dwarf:
                evasion = evasion -= 0.1;
                health = health + 1;
                break;
        }
        Hero PC = new Hero(attack, health, evasion, type, race, weapon, name);
        return PC;
    }
}

enum Race {
    human,
    dwarf,
    elf
}
enum Weapon {
    sword, 
    staff,
    bow
}

enum Type {
    warrior, 
    ranger,
    mage
}

enum Moves {
    n,
    e,
    w,
    s
}
