package parser;

import applet.MyPanel;
import world.Direction;
import world.Player;

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


    public static String getHint() {
        if (conditionalTriggers.size() > 0) {
            return conditionalTriggers.get(0).hint();
        }
        else {
            // TODO
            return "I got nothin' (I haven't written a hint for her yet, yell at me)";
        }
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
            if (parsedCommand.getCommand() == Command.LOCATION || conditionalTriggers.size() == 0
                    || !(conditionalTriggers.get(0) instanceof BlockingTrigger)
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
                MyPanel.appendLineToTextArea(((BlockingTrigger) conditionalTriggers.get(0)).blockingText());
                return;
            }
        } catch (IllegalArgumentException e) {
            outline = e.getMessage();
        }
        MyPanel.appendLineToTextArea(outline);

        if (conditionalTriggers.size() > 0 && conditionalTriggers.get(0).condition()) {
            conditionalTriggers.get(0).action();
            conditionalTriggers.remove(conditionalTriggers.get(0));
        }
    }


    private static Map<Command, CommandAction> generateExecuteMap() {
        final Map<Command, CommandAction> executeMap = new HashMap<>();

        executeMap.put(Command.MOVE, (Player::move));
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


        String hint();
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
