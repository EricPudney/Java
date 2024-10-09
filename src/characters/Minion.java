package src.characters;

import java.util.Random;

public class Minion extends Character{
    
    public static String[] adj = {"Hairy ", "Fearsome ", "Ugly ", "Ravenous ", "Woeful ", "Savage "};
    public static String[] noun = {"Goblin", "Ogre", "Hobgoblin", "Spider", "Gnome", "Centaur"};

    private static Random rng = new Random();

    // two systems at the moment, can delete one when location class complete
    public Minion(String name, int damage, int health, int[] location){
        this.name = name;
        this.attack = damage;
        this.health = health;
        this.currentLocation = location;
    }

    public Minion(String name, int damage, int health){
        this.name = name;
        this.attack = damage;
        this.health = health;
    }

    public String toString() {
        String description = this.name.concat(": attack " + this.attack + ", health " + this.health);
        return description;
    }

    // two systems at the moment, can delete one when location class complete
    public static Minion generateMonster(int xloc, int yloc) {
        int adjIndex = rng.nextInt(adj.length - 1);
        int nounIndex = rng.nextInt(noun.length - 1);
        String name = adj[adjIndex].concat(noun[nounIndex]);
        int damage = rng.nextInt(5) + 1;
        int health = rng.nextInt(5) + 1;
        int[] location = {xloc, yloc};
        return new Minion(name, damage, health, location);
    }

    public static Minion generateMonster() {
        int adjIndex = rng.nextInt(adj.length - 1);
        int nounIndex = rng.nextInt(noun.length - 1);
        String name = adj[adjIndex].concat(noun[nounIndex]);
        int damage = rng.nextInt(5) + 1;
        int health = rng.nextInt(5) + 1;
        
        return new Minion(name, damage, health);
    }
}