package src.characters;

import java.util.Random;

public class Minion extends Character{
    
    public static String[] adj = {"Hairy ", "Fearsome ", "Ugly ", "Ravenous ", "Woeful ", "Savage "};
    public static String[] noun = {"Goblin", "Ogre", "Hobgoblin", "Spider", "Gnome", "Centaur"};

    private static final Random rng = new Random();

    public Minion(String name, int damage, int health){
        this.name = name;
        this.attack = damage;
        this.health = health;
    }

    public String toString() {
        return this.name.concat(": attack " + this.attack + ", health " + this.health);
    }

    public static Minion generateMonster() {
        int adjIndex = rng.nextInt(adj.length - 1);
        int nounIndex = rng.nextInt(noun.length - 1);
        String name = adj[adjIndex].concat(noun[nounIndex]);
        int damage = rng.nextInt(2, 6);
        int health = rng.nextInt(4, 8);
        return new Minion(name, damage, health);
    }
}