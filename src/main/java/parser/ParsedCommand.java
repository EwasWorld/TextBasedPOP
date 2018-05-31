package parser;

import java.util.Optional;



public class ParsedCommand {
    private CommandString commandString;
    private String arguments = null;


    public ParsedCommand(CommandString commandString, String arguments) {
        if (commandString.hasArguments() && (arguments == null || arguments.equals(""))) {
            throw new IllegalArgumentException(
                    "Arguments cannot be null for command " + commandString.getCommandString());
        }

        this.commandString = commandString;
        this.arguments = arguments;
    }


    public ParsedCommand(CommandString commandString) {
        this(commandString, null);
    }


    public CommandString getCommandString() {
        return commandString;
    }


    public Optional<String> getArguments() {
        return Optional.of(arguments);
    }
}
