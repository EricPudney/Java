package src.characters;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

import src.Decision;
import src.Decision.Actions;
import src.Decision.Commands;
import src.Dungeon;
import src.Inventory;
import src.Location;
import src.Main;
import src.items.Equippable;
import src.items.Item;

public class Hero extends Character {
    public double evasion;
    Race race;
    Type type;
    public Location currentLocation;
    public int gold = 0;
    private int xp = 0;
    public int level = 1;
    public boolean foundTreasure = false;
    public boolean encounter = false;
    public Inventory inventory;
    // not yet in use
    private ArrayList<Equippable> equippedItems;
    


    static Console c = System.console();

    public Hero(int attack, int health, int shield, double initiative, double dodge, double block, double evasion, Type type, Race race, String name) {
        this.attack = attack;
        this.health = health;
        this.shield = shield;
        this.initiative = initiative;
        this.dodge = dodge;
        this.block = block;
        this.maxHealth = health;
        this.evasion = evasion;
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
            this.evasion += 0.05;
            this.block += 0.05;
            this.dodge += 0.05;
        }
        System.out.printf("%s levelled up and is now level %d!\n", this.name, this.level);
    }
    
    public void command (Dungeon dungeon) throws Exception {
        Commands[] commands = {Commands.n, Commands.e, Commands.w, Commands.s, Commands.c, Commands.i, Commands.h, Commands.t, Commands.m};
        Actions action = Decision.makeDecision("What do you want to do?", commands);
        switch (action) {
            case take:
                takeItem(dungeon);
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

    public void takeItem(Dungeon dungeon) {
        Inventory locationItems = currentLocation.items;
        if (locationItems.size() == 0) {
            System.out.println("There is nothing here to take!");
            return;
        }
        else if (locationItems.size() == 1) {
            Item item = locationItems.remove(0);
            if (this.inventory.addToInventory(item, this)) {
                System.out.printf("You added the %s to your inventory.\n", item.name);
                if (item.name.equals("Treasure")) {
                    foundTreasure = true;
                }
            }
            else {
                locationItems.add(item);
            }
        }
        else {
            Item item = locationItems.selectFromInventory("take");
            if (this.inventory.addToInventory(item, this)) {
                locationItems.remove(item);
                System.out.printf("You added the %s to your inventory.\n", item.name);
                if (item.name.equals("Treasure")) {
                    foundTreasure = true;
                }
            }
            else {
                locationItems.add(item);
            }
        }
    }

    public void equipItem() {
        if (this.inventory.size() == 0) {
            System.out.println(this.inventory);
            return;
        }
        Item selectedItem = this.inventory.selectFromInventory("equip");
        if (!(selectedItem instanceof Equippable)) {
            System.out.println("That item cannot be equipped!");
            return;
        }
        else if (equippedItems.size() >= 5) {
            System.out.println("You cannot equip any more items!");
            return;
        }
        else {
            for (Equippable item : equippedItems) {
                if (item.getClass() == selectedItem.getClass()) {
                    System.out.printf("You have already equipped a %s!\n", item.getClass().getSimpleName().toLowerCase());
                    return;
                }
            }
            this.equippedItems.add((Equippable) selectedItem);
            ((Equippable) selectedItem).equip();
        }
    }

    public void viewInventory(Dungeon dungeon) {
        if (this.inventory.size() == 0) {
            System.out.println(this.inventory);
            return;
        }
        Item droppedItem = this.inventory.selectFromInventory("drop");
            if (droppedItem != null) {
                if (this.inventory.removeFromInventory(droppedItem, this)) {
                    System.out.printf("You dropped the %s.\n", droppedItem.name);
                    currentLocation.items.add(droppedItem);
                    }
                }
    }

    // Where should this be??
    public int[] findCurrentLocation(Dungeon dungeon) {
        int[] position = {0, 0};
        for (int i = 0; i < dungeon.locations.length; i++) {
            for (int j = 0; j < dungeon.locations[i].length; j++) {
                if (dungeon.locations[i][j] == currentLocation) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public void moveNorth(Dungeon dungeon) {
        if (!currentLocation.noWayNorth) {
            int[] position = findCurrentLocation(dungeon);
            currentLocation = dungeon.locations[position[0] - 1][position[1]];
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            System.out.println("You can't go that way!\n");
        }
    }
     
    public void moveEast(Dungeon dungeon) {
        if (!currentLocation.noWayEast) {
            int[] position = findCurrentLocation(dungeon);
            currentLocation = dungeon.locations[position[0]][position[1] + 1];
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            System.out.println("You can't go that way!\n");
        }
    }

    public void moveWest(Dungeon dungeon) {
        if (!currentLocation.noWayWest) {
            int[] position = findCurrentLocation(dungeon);
            currentLocation = dungeon.locations[position[0]][position[1] - 1];
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            System.out.println("You can't go that way!\n");
        }
    }

    public void moveSouth(Dungeon dungeon) {
        if (!currentLocation.noWaySouth) {
            int[] position = findCurrentLocation(dungeon);
            currentLocation = dungeon.locations[position[0] + 1][position[1]];
            System.out.println("You move through the gloomy dungeon...");
        }
        else {
            System.out.println("You can't go that way!\n");
        }
    }
    
    public String toString() {
        return "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.";
    }

    public void encounter(Minion enemy) {
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
                        currentLocation.enemy = null;
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
        
        String name = c.readLine("Give your character a name: ");

        int attack = 3;
        int health = 8;
        int shield = 1;
        double initiative = 0.5;
        double dodge = 0;
        double block = 0;
        double evasion = 0.45;
        switch (type) {
            case warrior:
                evasion = 0.4;
                block = 0.3;
                dodge = 0.05;
                shield = 2;
                break;
            case mage:
                attack = 4;
                health = 5;
                block = 0.1;
                dodge = 0.15;
                break;
            case ranger:
                attack = 2;
                evasion = 0.5;
                block = 0.15;
                dodge = 0.25;
                break;
        }
        switch (race) {
            case human:
                break;
            case elf:
                initiative = initiative += 0.05;
                evasion = evasion += 0.05;
                dodge = dodge += 0.05;
                health = health - 2;
                break;
            case dwarf:
                initiative = initiative -= 0.05;
                evasion = evasion -= 0.05;
                dodge = dodge -= 0.05;
                health = health + 2;
                break;
        }
        return new Hero(attack, health, shield, initiative, dodge, block, evasion, type, race, name);
    }
}

enum Race {
    human,
    dwarf,
    elf
}

enum Type {
    warrior, 
    ranger,
    mage
}
