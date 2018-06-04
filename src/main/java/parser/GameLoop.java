package parser;

import GameEngine.StartingTheGame;
import world.Direction;
import world.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class GameLoop {
    private static final CommandBTreeNode commandBTree = new CommandBTreeNode();
    private static Map<Command, CommandAction> executeMap = generateExecuteMap();
    private static List<Trigger> autoTriggers = new ArrayList<>();
    private static List<ConditionalTrigger> conditionalTriggers = new ArrayList<>();


    private GameLoop() {
    }


    public static void main(String[] args) {
        StartingTheGame.init();

        outer:
        while (true) {
            String line = readStringFromCmd();

            switch (line.toLowerCase()) {
                case "exit":
                case "quit":
                case "q":
                    System.out.println("Byee");
                    break outer;
                case "help":
                    System.out.println("Commands: \n"
                                               + "No arguments: n/s/e/w/location/inventory/quit\n"
                                               + "Arguments: examine/take/drop");
                    break;
                default:
                    executeLine(line);
                    break;
            }
        }
    }


    private static String readStringFromCmd() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String string;
        string = null;

        try {
            string = br.readLine();
        } catch (IOException ioe) {
            System.err.println("Fatal input error");
        }
        return string;
    }


    public static void executeLine(String line) {
        // Command doesn't matter, event will auto-trigger
        if (autoTriggers.size() > 0) {
            autoTriggers.remove(0).action();
            return;
        }

        String outline;
        try {
            final ParsedCommand parsedCommand = commandBTree.find(line.toLowerCase());
            // Check if other actions are blocked, if so check whether the given action was ok
            if (conditionalTriggers.size() == 0 || !(conditionalTriggers.get(0) instanceof BlockingTrigger)
                    || (((BlockingTrigger) conditionalTriggers.get(0)).acceptableAction(parsedCommand)))
            {
                outline = executeMap.get(parsedCommand.getCommand()).execute(parsedCommand.getArguments());

                if (conditionalTriggers.size() > 0 && (conditionalTriggers.get(0) instanceof BlockingTrigger)
                        && (conditionalTriggers.get(0).condition()))
                {
                    // Doesn't print the outline
                    conditionalTriggers.get(0).action();
                    conditionalTriggers.remove(0);
                    return;
                }
            }
            else {
                System.out.println(((BlockingTrigger) conditionalTriggers.get(0)).blockingText());
                return;
            }
        } catch (IllegalArgumentException e) {
            outline = e.getMessage();
        }
        System.out.println(outline);

        if (conditionalTriggers.size() > 0 && conditionalTriggers.get(0).condition()) {
            conditionalTriggers.get(0).action();
            conditionalTriggers.remove(conditionalTriggers.get(0));
        }
    }


    private static Map<Command, CommandAction> generateExecuteMap() {
        final Map<Command, CommandAction> executeMap = new HashMap<>();

        executeMap.put(Command.MOVE, (args -> Player.move(Direction.valueOf(args.toUpperCase()))));
        executeMap.put(Command.NORTH, (args -> Player.move(Direction.NORTH)));
        executeMap.put(Command.SOUTH, (args -> Player.move(Direction.SOUTH)));
        executeMap.put(Command.EAST, (args -> Player.move(Direction.EAST)));
        executeMap.put(Command.WEST, (args -> Player.move(Direction.WEST)));

        executeMap.put(Command.INVENTORY, (args -> Player.getInventoryString()));
        executeMap.put(Command.LOCATION, (args -> Player.getPlayerLocationString()));

        executeMap.put(Command.EXAMINE, (Player::examine));
        executeMap.put(Command.TOUCH, (Player::touch));
        executeMap.put(Command.TAKE, (Player::take));
        executeMap.put(Command.DROP, (Player::drop));

        executeMap.put(Command.YELL, (args -> "AAARRRGGGHHH!!!"));
        /*
        executeMap.put(Command.SAY, (args -> ));
        executeMap.put(Command.SPELLBOOK, (args -> ));
        executeMap.put(Command.CAST, (args -> ));
        */
        return executeMap;
    }


    public static void addTrigger(Trigger trigger) {
        if (trigger instanceof ConditionalTrigger) {
            conditionalTriggers.add((ConditionalTrigger) trigger);
        }
        else {
            autoTriggers.add(trigger);
        }
    }


    interface CommandAction {
        String execute(String args);
    }



    public interface Trigger {
        void action();
    }



    public interface ConditionalTrigger extends Trigger {
        boolean condition();
    }



    // If the condition blocks other actions from happening
    public interface BlockingTrigger extends ConditionalTrigger {
        /*
           Returns true if the given command is executable within the block
         */
        boolean acceptableAction(ParsedCommand parsedCommand);


        /*
           What to say when the user inputs a command that doesn't fulfil the condition
         */
        String blockingText();
    }
}
