package parser;

import GameEngine.StartingTheGame;
import world.Direction;
import world.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class GameLoop {
    private static final CommandBTreeNode commandBTree = new CommandBTreeNode();
    private static Map<Command, CommandAction> executeMap = generateExecuteMap();
    private static List<Trigger> autoTriggers = new ArrayList<>();
    private static Set<ConditionalTrigger> conditionalTriggers = new HashSet<>();


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
            outline = executeMap.get(parsedCommand.getCommand()).execute(parsedCommand.getArguments());
        } catch (IllegalArgumentException e) {
            outline = e.getMessage();
        }
        System.out.println(outline);

        // More than one trigger can happen
        for (ConditionalTrigger conditionalTrigger : conditionalTriggers) {
            if (conditionalTrigger.condition()) {
                conditionalTrigger.action();
                conditionalTriggers.remove(conditionalTrigger);
            }
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


        void action();
    }
}
