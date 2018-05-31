package parser;

import java.util.Optional;



public class ParsedCommand {
    private ValidCommand commandString;
    private String arguments = null;


    public ParsedCommand(ValidCommand commandString, String arguments) {
        if (commandString.hasArguments() && (arguments == null || arguments.equals(""))) {
            throw new IllegalArgumentException(
                    "Arguments cannot be null for command " + commandString.getCommandString());
        }

        this.commandString = commandString;
        this.arguments = arguments;
    }


    public ParsedCommand(ValidCommand commandString) {
        this(commandString, null);
    }


    public ValidCommand getCommandString() {
        return commandString;
    }


    public Optional<String> getArguments() {
        return Optional.of(arguments);
    }
}
