package src.characters;
import java.util.ArrayList;
import java.util.Scanner;

import src.Decision;
import src.Decision.Actions;
import src.Decision.Commands;
import src.areas.Dungeon;
import src.Inventory;
import src.areas.Location;
import src.Main;
import src.items.Equippable;
import src.items.Item;
import src.items.Usable;

public class Hero extends Character {
    public double evasion;
    final Race race;
    final Type type;
    public Location currentLocation;
    public int gold = 0;
    private int xp = 0;
    public int level = 1;
    public boolean foundTreasure = false;
    public Inventory inventory;
    private final ArrayList<Equippable> equippedItems;

    public static Scanner scanner = new Scanner(System.in);

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
        this.equippedItems = new ArrayList<>();
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
    
    public void command (Dungeon dungeon) {
        Commands[] commands = {Commands.n, Commands.e, Commands.w, Commands.s, Commands.c, Commands.i, Commands.h, Commands.t, Commands.m, Commands.q};
        Actions action = Decision.makeDecision("What do you want to do?", commands);
        switch (action) {
            case take:
                takeItem();
                break;
            case inventory:
                if (this.inventory.isEmpty()) {
                    System.out.println(this.inventory);
                    return;
                }
                System.out.println(this.inventory);
                inventoryInteraction(Decision.makeInventoryDecision("Enter q to equip an item, d to drop an item, u to use a consumable item or x to exit.", new Commands[]{Commands.q, Commands.d, Commands.u, Commands.x}));
                break;
            case help:
                Main.printHelpText();
                break;
            case map:
                System.out.println(dungeon.toString(this));
                break;
            case characterInfo:
                System.out.println(this);
                break;
            case equip:
                equipItem();
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
            case null:
                break;
            default:
                System.out.println("Something has gone wrong with the command method.");
        }
    }

    public void inventoryInteraction(Actions action) {
        switch (action) {
            case use -> useItem();
            case equip -> equipItem();
            case drop -> dropFromInventory();
            case exit -> {
            }
        }
    }

    public void takeItem() {
        Inventory locationItems = currentLocation.items;
        if (locationItems.isEmpty()) {
            System.out.println("There is nothing here to take!");
        }
        else if (locationItems.size() == 1) {
            Item item = locationItems.removeFirst();
            if (this.inventory.addToInventory(item)) {
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
            if (this.inventory.addToInventory(item)) {
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
        Item selectedItem = this.inventory.selectFromInventory("equip");
        if (!(selectedItem instanceof Equippable)) {
            System.out.println("That item cannot be equipped!");
        }
        else if (equippedItems.size() >= 5) {
            System.out.println("You cannot equip any more items!");
        }
        else {
            for (Equippable item : equippedItems) {
                if (item.getClass() == selectedItem.getClass()) {
                    System.out.printf("You have already equipped a %s!\n", item.getClass().getSimpleName().toLowerCase());
                    return;
                }
            }
            this.inventory.remove(selectedItem);
            this.equippedItems.add((Equippable) selectedItem);
            ((Equippable) selectedItem).equip(this);
        }
    }

    public void dropFromInventory() {
        Item droppedItem = this.inventory.selectFromInventory("drop");
            if (droppedItem != null) {
                if (this.inventory.remove(droppedItem)) {
                    System.out.printf("You dropped the %s.\n", droppedItem.name);
                    currentLocation.items.add(droppedItem);
                    }
                }
    }

    public void useItem() {
        Item usedItem = this.inventory.selectFromInventory("use");
        if (!(usedItem instanceof Usable)) {
            System.out.println("That item cannot be used!");
            return;
        }
        if (((Usable) usedItem).useItem(this)) {
            this.inventory.remove(usedItem);
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
        return "You are " + this.name + " the brave " + this.race + " " + this.type + "!\n" + "Attack: " + this.attack + "; Health: " + this.health + "\nYou have " + this.gold + " gold coins.\nYou are level " + this.level + " and have " + this.xp + " experience points.\n You have the folowing items equipped: " + equippedItems;
    }

    public void encounter(Minion enemy) {
        System.out.printf("%s: attack %d, health %d. Fight or run?\n", enemy.name, enemy.attack, enemy.health);
        boolean evaded = false;
        while (!evaded && enemy.isAlive && this.isAlive) {
            Commands[] commands = {Commands.c, Commands.h, Commands.x, Commands.a};
            Actions action = Decision.makeDecision("What do you want to do? Press a to attack or x to try to escape.", commands);
            switch (action) {
                case characterInfo:
                    System.out.println(this);
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
                case null:
                    break;
                default:
                    System.out.println("Something has gone wrong with the encounter method");
                    break;
            }
        }
    }

    public static Hero CharacterCreation() {
        Type type = Type.warrior;
        Race race = Race.human;
        boolean noError = false;
        System.out.println("Choose your character class (warrior, ranger or mage): ");
        while (!noError) {
            String typeChosen = scanner.nextLine();
                try {
                    type = Type.valueOf(typeChosen);
                    noError = true;
                }
                catch (Exception e) {
                    System.err.println(typeChosen + " is not a valid choice. Please try again.");
                }
            }
        noError = false;
        System.out.println("Choose your character race (human, dwarf or elf): ");
        while (!noError) {
            String raceChosen = scanner.nextLine();
            try {
                race = Race.valueOf(raceChosen);
                noError = true;
            }
            catch (Exception e) {
                System.err.println(raceChosen + " is not a valid choice. Please try again.");
            }
        }
        System.out.println("Give your character a name: ");
        String name = scanner.nextLine();

        int attack = 3;
        int health = 12;
        int shield = 1;
        double initiative = 0.5;
        double dodge = 0;
        double block = 0;
        double evasion = 0.45;
        switch (type) {
            case warrior:
                evasion -= 0.1;
                block += 0.3;
                dodge += 0.05;
                shield += 1;
                break;
            case mage:
                attack += 1;
                health -= 2;
                block += 0.1;
                dodge += 0.15;
                break;
            case ranger:
                attack -= 1;
                evasion += 0.05;
                block += 0.15;
                dodge += 0.25;
                break;
        }
        switch (race) {
            case human:
                break;
            case elf:
                initiative += 0.05;
                evasion += 0.05;
                dodge += 0.05;
                health -= 2;
                break;
            case dwarf:
                initiative -= 0.05;
                evasion -= 0.05;
                dodge -= 0.05;
                health += 2;
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
