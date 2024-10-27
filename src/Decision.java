package src;

import static src.characters.Hero.scanner;

public abstract class Decision {
    

    public static boolean checkLoop(Commands[] options, Commands option) {
        for (Commands o: options) {
            if(o.equals(option))
                return true;
        }
        return false;
    }

    public static Commands checkInput(String prompt, Commands[] options) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            try {
                Commands option = Commands.valueOf(input);
                boolean validCommand = checkLoop(options, option);
                if ((!validCommand)) {
                    System.out.println("You can't do that here!");    
                }
                else {
                    return option;
                }
            }
            catch (Exception e) {
                System.out.println("Please enter a valid command.");
            }
        }
    }
    
    public static Actions makeDecision(String prompt, Commands[] options) {
        Commands input = checkInput(prompt, options);
        return switch (input) {
            case a -> Actions.attack;
            case c -> Actions.characterInfo;
            case t -> Actions.take;
            case e -> Actions.east;
            case h -> Actions.help;
            case m -> Actions.map;
            case i -> Actions.inventory;
            case n -> Actions.north;
            case s -> Actions.south;
            case w -> Actions.west;
            case x -> Actions.evade;
            default -> null;
        };
    }

    public static Actions makeShopDecision(String prompt, Commands[] options) {
        Commands input = checkInput(prompt, options);
        return switch (input) {
            case a -> Actions.sellAll;
            case s -> Actions.sell;
            case b -> Actions.buy;
            default -> Actions.exit;
        };
    }

    // not yet in use - secondary menu after inventory option selected to reduce commands
    public static Actions makeInventoryDecision(String prompt, Commands[] options) {
        Commands input = checkInput(prompt, options);
        return switch (input) {
            case q -> Actions.equip;
            case d -> Actions.drop;
            case u -> Actions.use;
            default -> Actions.exit;
        };
    }

    public enum Commands {
        n,
        e,
        w,
        s,
        c,
        i,
        h,
        m,
        t,
        a,
        x,
        b,
        q,
        u,
        d
    }

    public enum Actions{
        attack,
        characterInfo,
        take,
        evade,
        east,
        west,
        north,
        south,
        help,
        map,
        inventory,
        sell,
        buy,
        sellAll,
        exit, 
        equip,
        drop,
        use
    }
}
