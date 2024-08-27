class Minion extends Character{
    
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

    public void attack(Hero player) {
        System.out.printf("%s attacked %s, doing %d damage!\n", this.name, player.name, this.attack);
        player.health = player.health -= this.attack;
        if (player.health <= 0) {
            System.out.printf("%s killed %s!\n", this.name, player.name);
            player.isAlive = false;
        }
    }
    
    public Minion(String name, int damage, int health, int[] location){
        this.name = name;
        this.attack = damage;
        this.health = health;
        this.currentLocation = location;
    }

    public String toString() {
        String description = this.name.concat(": attack " + this.attack + ", health " + this.health);
        return description;
    }

    public static Minion generateMonster(int xloc, int yloc) {
        int adjIndex = Math.round((float)Math.random() * 5);
        int nounIndex = Math.round((float)Math.random() * 5);
        String name = adj[adjIndex].concat(noun[nounIndex]);
        int damage = Math.round((float)Math.random() * 5) + 1;
        int health = Math.round((float)Math.random() * 5) + 1;
        int[] location = {xloc, yloc};
        return new Minion(name, damage, health, location);
    }
}