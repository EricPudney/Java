import java.util.ArrayList;

public class Character {
    int attack; 
    int health;
    String name;
    boolean isAlive = true;
    int[] currentLocation;
    // placed here in case items added to monsters as well as heroes later
    ArrayList<Item> inventory;

    public void attack(Character target) {
        System.out.printf("%s attacks %s, doing %d damage!\n", this.name, target.name, this.attack);
        target.health = target.health -= this.attack;
        if (target.health <= 0) {
            target.isAlive = false;
            System.out.printf("%s killed %s!\n", this.name, target.name);
        }
    }
}
