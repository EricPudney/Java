package src.characters;

public class Character {
    public int attack;
    public int maxHealth;
    public int health;
    public String name;
    public boolean isAlive = true;
    public int[] currentLocation;

    public void attack(Character target) {
        System.out.printf("%s attacks %s, doing %d damage!\n", this.name, target.name, this.attack);
        target.health = target.health -= this.attack;
        if (target.health <= 0) {
            target.isAlive = false;
            System.out.printf("%s killed %s!\n", this.name, target.name);
        }
    }
}
