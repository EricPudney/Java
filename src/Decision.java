package src;

import java.io.Console;

public class Decision {
    
    static Console c = System.console();

    public static boolean checkLoop(Commands[] options, Commands option) {
        for (Commands o: options) {
            if(o.equals(option))
                return true;
        }
        return false;
    }
    
    public static Actions makeDecision(String prompt, Commands[] options) {
        boolean validCommand = false;
        String input = "";
        while (!validCommand) {
            input = c.readLine(prompt);
            try {
                Commands option = Commands.valueOf(input);
                validCommand = checkLoop(options, option);
                if ((!validCommand)) {
                    System.out.println("You can't do that here!");    
                }
            }
            catch (Exception e) {
                System.out.println("Please enter a valid command.");
            }
        }
        
        switch (Commands.valueOf(input)) {
            case a:
                return Actions.attack;
            case c:
                return Actions.characterInfo;
            case t:
                return Actions.take;
            case e:
                return Actions.east;
            case h:
                return Actions.help;
            case i:
                return Actions.inventory;
            case n:
                return Actions.north;
            case s:
                return Actions.south;
            case w:
                return Actions.west;
            case x:
                return Actions.evade;
            default:
                return null;        
            }
    }

    public static boolean makeYesNoDecision(String prompt) {
        boolean noError = false;
        while (!noError) {
            String input = c.readLine(prompt);
            if (input.equals("y")) {
                return true;
            }
            else if (input.equals("n")) {
                return false;
            }
            {
                System.out.println("Please enter a valid option.");
            }
        }
        return false;
    }

    public enum Commands {
        n,
        e,
        w,
        s,
        c,
        i,
        h,
        t,
        a,
        x
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
        inventory
    }
}
