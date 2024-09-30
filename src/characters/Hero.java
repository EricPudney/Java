package src.characters;
import java.io.Console;
import java.io.IOException;

import src.Decision;
import src.Decision.Actions;
import src.Decision.Commands;
import src.Dungeon;
import src.Inventory;
import src.Main;
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
    
    public void command (Dungeon dungeon) throws Exception {
        Commands[] commands = {Commands.n, Commands.e, Commands.w, Commands.s, Commands.c, Commands.i, Commands.h, Commands.t, Commands.m};
        Actions action = Decision.makeDecision("What do you want to do?", commands);
        switch (action) {
            case take:
                takeitem(dungeon);
                break;
            case inventory:
                viewInventory(dungeon);
                break;
            case help:
                Main.printHelpText();
                break;
            case map:
                System.out.println(dungeon.toString(this));
                break;
            case characterInfo:
                System.out.println(this.toString());
                break;

            case north:
                moveNorth(dungeon);
                break;
            case east:
                moveEast(dungeon);
                break;
            case south:
                moveSouth(dungeon);
                break;
            case west:
                moveWest(dungeon);
                break;
            default:
                System.out.println("Something has gone wrong with the command method.");
        }
    }

    public void takeitem(Dungeon dungeon) {
        Item item = dungeon.items[this.currentLocation[0]][this.currentLocation[1]].get(0);
                if (item == null) {
                    System.out.println("There is nothing here to take!");
                }
                if (item != null && this.inventory.addToInventory(item, this)) {
                    dungeon.items[this.currentLocation[0]][this.currentLocation[1]].remove(0);
                    System.out.printf("You added the %s to your inventory.\n", item.name);
                };
    }

    public void viewInventory(Dungeon dungeon) {
        if (this.inventory.items.size() == 0) {
            System.out.println(this.inventory);
            return;
        }
        Item droppedItem = this.inventory.selectFromInventory("drop");
                if (droppedItem != null) {
                if (this.inventory.removeFromInventory(droppedItem, this)) {
                    System.out.printf("You dropped the %s.\n", droppedItem.name);
                    dungeon.items[this.currentLocation[0]][this.currentLocation[1]].add(droppedItem);
                    }
                }
    }

    public void moveNorth(Dungeon dungeon) throws Exception {
        if (this.currentLocation[0] <= dungeon.width - 2) {
            this.currentLocation[0] = this.currentLocation[0] += 1;
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            throw new Exception();
        }
    }
    
    public void moveEast(Dungeon dungeon) throws Exception {
        if (this.currentLocation[1] > 0) {
            this.currentLocation[1] = this.currentLocation[1] -= 1;
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            throw new Exception();
        }
    }

    public void moveWest(Dungeon dungeon) throws Exception {
        if (this.currentLocation[1] <= dungeon.height - 2) {
            this.currentLocation[1] = this.currentLocation[1] += 1;
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            throw new Exception();
        }
    }

    public void moveSouth(Dungeon dungeon) throws Exception {
        if (this.currentLocation[0] > 0) {
            this.currentLocation[0] = this.currentLocation[0] -= 1;
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            throw new Exception();
        }
    }
    
    public String toString() {
        String returnValue = "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.";
        return returnValue;
    }

    public void encounter(Minion enemy, Dungeon dungeon /* 2nd param only needed for the shoddy solution below; see comment */) {
        System.out.printf("%s: attack %d, health %d. Fight or run?\n", enemy.name, enemy.attack, enemy.health);
        boolean evaded = false;
        while (!evaded && enemy.isAlive && this.isAlive) {
            Commands[] commands = {Commands.c, Commands.h, Commands.x, Commands.a};
            Actions action = Decision.makeDecision("What do you want to do? Press a to attack or x to try to escape.", commands);
            switch (action) {
                case characterInfo:
                    System.out.println(this.toString());
                    break;
                case help:
                    Main.printHelpText();
                    break;
                case evade:
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
                    break;
                case attack:
                    this.attack(enemy);
                    if (enemy.isAlive) {
                        enemy.attack(this);
                    }
                    if (!enemy.isAlive) {
                        this.addXp(5);
                    }
                    break;
                default:
                    System.out.println("Something has gone wrong with the encounter method");
                    break;
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
        double evasion = 0.45;
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
                evasion = 0.5;
                break;
        }
        switch (race) {
            case human:
                break;
            case elf:
                evasion = evasion += 0.05;
                health = health - 2;
                break;
            case dwarf:
                evasion = evasion -= 0.05;
                health = health + 1;
                break;
        }
        return new Hero(attack, health, evasion, type, race, weapon, name);
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
