package parser;

import world.Direction;
import world.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;



public class GameLoop {
    public static final CommandBTreeNode commandBTree = new CommandBTreeNode();
    private static Map<Command, CommandAction> executeMap = generateExecuteMap();


    private GameLoop() {
    }


    public static void main(String[] args) {
        // TODO: Init

        while (true) {
            String line = readStringFromCmd();
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("e") || line.equalsIgnoreCase("quit") || line
                    .equalsIgnoreCase("q"))
            {
                System.out.println("Byee");
                break;
            }

            System.out.println(parseLine(line));
        }
    }


    public static String readStringFromCmd()
    {
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


    public static String parseLine(String line) {
        final ParsedCommand parsedCommand = commandBTree.find(line.toLowerCase());
        return executeMap.get(parsedCommand.getCommand()).execute(parsedCommand.getArguments());
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


    interface CommandAction {
        String execute(String args);
    }
}
