package parser;

import applet.MyPanel;
import world.Direction;
import world.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*
   Bridge between gui and back end used to parse and execute lines
 */
public class Executioner {
    private static final CommandBTreeNode commandBTree = new CommandBTreeNode();
    private static List<Trigger> autoTriggers = new ArrayList<>();
    private static List<ConditionalTrigger> conditionalTriggers = new ArrayList<>();
    private static Map<Command, CommandAction> executeMap = generateExecuteMap();


    private Executioner() {
    }


    public static void executeLine(String line) {
        String outline = "";
        ParsedCommand parsedCommand = null;
        try {
            parsedCommand = commandBTree.find(line.toLowerCase());
        } catch (IllegalArgumentException e) {
            outline = e.getMessage();
        }

        // Command doesn't matter (can even be faulty, as long as it's not set to always work), event will auto-trigger
        if (autoTriggers.size() > 0 && (parsedCommand == null || !parsedCommand.getCommand().isAlwaysWorks())) {
            autoTriggers.remove(0).action();
            return;
        }
        if (parsedCommand == null) {
            MyPanel.appendLineToTextArea(outline);
            return;
        }

        if (parsedCommand.getCommand().isAlwaysWorks() || isBlockingTriggerAction(parsedCommand)) {
            try {
                outline = executeMap.get(parsedCommand.getCommand()).execute(parsedCommand.getArguments());
            } catch (IllegalArgumentException e) {
                outline = e.getMessage();
            }

            if (isBlockingTriggerSatisfied()) {
                conditionalTriggers.remove(0).action();
                return; // Doesn't print the outline
            }
        }
        else {
            MyPanel.appendLineToTextArea(((BlockingTrigger) conditionalTriggers.get(0)).blockingText());
            return;
        }
        MyPanel.appendLineToTextArea(outline);

        if (conditionalTriggers.size() > 0 && conditionalTriggers.get(0).condition()) {
            conditionalTriggers.remove(0).action();
        }
    }


    /*
       Check whether commands are blocked, if they are, check if the given action is ok
     */
    private static boolean isBlockingTriggerAction(ParsedCommand parsedCommand) {
        return conditionalTriggers.size() == 0 || !(conditionalTriggers.get(0) instanceof BlockingTrigger)
                || (((BlockingTrigger) conditionalTriggers.get(0)).acceptableAction(parsedCommand));
    }


    private static boolean isBlockingTriggerSatisfied() {
        return conditionalTriggers.size() > 0 && (conditionalTriggers.get(0) instanceof BlockingTrigger)
                && (conditionalTriggers.get(0).condition());
    }


    private static Map<Command, CommandAction> generateExecuteMap() {
        final Map<Command, CommandAction> executeMap = new HashMap<>();

        executeMap.put(Command.QUIT, (args -> {
            MyPanel.quit();
            return null;
        }));
        executeMap.put(
                Command.HELP,
                (args -> "~ Basic Commands: \nNo arguments: n/s/e/w/location/inventory/hint/quit\nArguments: "
                        + "examine/take/drop")
        );
        executeMap.put(Command.HINT, (args -> getHint()));
        executeMap.put(Command.CLS, (args -> {
            MyPanel.clearScreen();
            return null;
        }));

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


    private static String getHint() {
        if (conditionalTriggers.size() > 0 && autoTriggers.size() == 0) {
            return conditionalTriggers.get(0).hint();
        }
        else {
            // TODO
            return "I got nothin' (I haven't written a hint for here yet, yell at me)";
        }
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
