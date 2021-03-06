package parser;

public class ValidCommand implements Comparable<ValidCommand> {
    private String commandString;
    private Command command;
    private boolean hasArguments;


    // TEST METHOD
    public ValidCommand(String commandString) {
        this.commandString = commandString;
    }


    public ValidCommand(String commandString, Command command, boolean hasArguments) {
        this.commandString = commandString;
        this.command = command;
        this.hasArguments = hasArguments;
    }


    public int compareTo(ValidCommand cs) {
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