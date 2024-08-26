class Minion {
    String name;
    int damage;
    int health;
    boolean isAlive = true;
    

    public static String[] adj = {"Hairy ", "Fearsome ", "Ugly ", "Ravenous ", "Woeful ", "Savage "};
    public static String[] noun = {"Goblin", "Ogre", "Hobgoblin", "Spider", "Gnome", "Centaur"};

    /*
     * 
     public void attack(Minion target, int damage) {
         System.out.printf("%s attacked %s!\n", this.name, target.name);
         target.health -= damage;
         if (target.health <= 0) {
             System.out.printf("%s killed %s!\n", this.name, target.name);
             target.isAlive = false;
         }
         else {
             System.out.printf("%s now has %d health remaining.\n", target.name, target.health);
         }
     }
     * 
     * 
     */

    public void attack(Character player) {
        System.out.printf("%s attacked %s, doing %d damage!\n", this.name, player.name, this.damage);
        player.health = player.health -= this.damage;
        if (player.health <= 0) {
            System.out.printf("%s killed %s!\n", this.name, player.name);
            player.isAlive = false;
        }
    }
    
    public Minion(String name, int damage, int health){
        this.name = name;
        this.damage = damage;
        this.health = health;
    }

    public String toString() {
        String description = this.name.concat(": attack " + this.damage + ", health " + this.health);
        return description;
    }

    public static Minion generateMonster() {
        int adjIndex = Math.round((float)Math.random() * 5);
        int nounIndex = Math.round((float)Math.random() * 5);
        String name = adj[adjIndex].concat(noun[nounIndex]);
        int damage = Math.round((float)Math.random() * 5) + 1;
        int health = Math.round((float)Math.random() * 5) + 1;
        return new Minion(name, damage, health);
    }
}