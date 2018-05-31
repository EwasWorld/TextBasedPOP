package parser;

public class CommandString implements Comparable<CommandString> {
    private String commandString;
    private Command command;
    private boolean hasArguments;
    // if arguments are required and the user hasn't given them, say this
    private String badUserArguments;


    // For test purposes
    public CommandString(String commandString) {
        this.commandString = commandString;
    }


    public CommandString(String commandString, Command command, boolean hasArguments) {
        this.commandString = commandString;
        this.command = command;
        this.hasArguments = hasArguments;
    }


    public int compareTo(CommandString cs) {
        return commandString.compareTo(cs.commandString);
    }


    public String getCommandString() {
        return commandString;
    }


    public Command getCommand() {
        return command;
    }


    public boolean hasArguments() {
        return hasArguments;
    }
}
