package parser;

public enum Command {
    MOVE, INVENTORY, LOCATION, EXAMINE, TOUCH, TAKE, DROP, YELL, SAY, NORTH, SOUTH, EAST, WEST, SPELLBOOK, CAST;

    // TODO: Implement
    // if arguments are required and the user hasn't given them, say this
    private String badUserArguments;
}
