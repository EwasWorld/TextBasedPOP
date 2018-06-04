package parser;


public class ParsedCommand {
    private Command command;
    private String arguments = null;


    public ParsedCommand(ValidCommand commandString) {
        this(commandString, null);
    }


    public ParsedCommand(ValidCommand validCommand, String arguments) {
        if (validCommand.hasArguments() && (arguments == null || arguments.equals(""))) {
            throw new IllegalArgumentException(
                    "Arguments cannot be null for command " + validCommand.getCommandString());
        }

        this.command = validCommand.getCommand();
        this.arguments = arguments;
    }


    public Command getCommand() {
        return command;
    }


    public String getArguments() {
        return arguments;
    }
}
