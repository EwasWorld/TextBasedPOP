package parser;

// TODO: Move hasArgumentsHere
public enum Command {
    MOVE(false), INVENTORY(true), LOCATION(true), EXAMINE(false), TOUCH(false), TAKE(false), DROP(false), YELL(false),
    SAY(false), NORTH(false), SOUTH(false), EAST(false), WEST(false), SPELLBOOK(false), CAST(false), QUIT(true),
    HELP(true), HINT(true), CLS(true);

    // TODO: Implement
    // if arguments are required and the user hasn't given them(false), say this
    private String badUserArguments;
    // Works when commands are disabled and over auto triggers
    private boolean alwaysWorks;


    Command(boolean alwaysWorks) {
        this.alwaysWorks = alwaysWorks;
    }


    /*
       All enum values and the strings they can be represented by
     */
    public static String[] getCommandArray() {
        return new String[]{"MOVE,true,walk;go", "NORTH,false,n", "SOUTH,false,s", "EAST,false,e",
                "WEST,false,w", "INVENTORY,false,i", "LOCATION,false,l;look", "EXAMINE,true,x;look at;read",
                "TOUCH,true,caress", "TAKE,true,take;grab;pick up;get", "DROP,true", "YELL,false", "SAY,true",
                "SPELLBOOK,false,spell book;s", "CAST,true,c", "HELP,false,h", "QUIT,false,exit;q",
                "CLS,false,clear screen", "HINT,false"};
    }


    public boolean isAlwaysWorks() {
        return alwaysWorks;
    }
}
