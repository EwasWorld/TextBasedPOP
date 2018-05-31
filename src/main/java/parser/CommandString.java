package parser;

public class CommandString implements Comparable<CommandString> {
    private String commandString;
    private Command command;
    private boolean hasArguments;


    // For test purposes
    public CommandString(String commandString) {
        this.commandString = commandString;
    }


    public int compareTo(CommandString cs) {
        return commandString.compareTo(cs.commandString);
    }


    public String getCommandString() {
        return commandString;
    }
}
