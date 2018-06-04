package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class CommandBTreeNode {
    private CommandBTreeNode left = null;
    private CommandBTreeNode right = null;
    private ValidCommand data;


    /*
        Creates a balanced binary search tree for all commands listed in CommandStrings.txt
     */
    public CommandBTreeNode() {
        final Scanner scanner;
        try {
            scanner = new Scanner(new File("src/main/resources/CommandStrings.txt"));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Fatal error: cannot generate commands list");
        }

        final List<ValidCommand> allCommands = new ArrayList<>();
        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            final String[] splitLine = line.split(",");
            if (!(splitLine.length == 2 || splitLine.length == 3)) {
                throw new IllegalStateException("Fatal error: badly formatted command input '" + line + "'");
            }

            final Command command = Command.valueOf(splitLine[0].toUpperCase());
            final boolean hasArguments = Boolean.parseBoolean(splitLine[1]);
            allCommands.add(new ValidCommand(splitLine[0].toLowerCase(), command, hasArguments));
            if (splitLine.length == 3) {
                for (String commandStr : splitLine[2].split(";")) {
                    allCommands.add(new ValidCommand(commandStr, command, hasArguments));
                }
            }
        }

        if (allCommands.size() == 0) {
            throw new IllegalStateException("Fatal error: no commands loaded");
        }
        init(allCommands.toArray(new ValidCommand[allCommands.size()]));
    }


    /*
        Creates a balanced binary search tree for all commands listed in the array
     */
    private void init(ValidCommand[] allCommands) {
        Arrays.sort(allCommands);

        // If uneven sides, left will be 1 level deeper
        final int centreIndex = Math.floorDiv(allCommands.length, 2);
        data = allCommands[centreIndex];

        if (allCommands.length > 1) {
            final ValidCommand[] leftArray = new ValidCommand[centreIndex];
            System.arraycopy(allCommands, 0, leftArray, 0, centreIndex);
            left = new CommandBTreeNode(leftArray);

            final int rightArrayLength = allCommands.length - centreIndex - 1;
            if (rightArrayLength > 0) {
                final ValidCommand[] rightArray = new ValidCommand[rightArrayLength];
                System.arraycopy(allCommands, centreIndex + 1, rightArray, 0, rightArrayLength);
                right = new CommandBTreeNode(rightArray);
            }
        }
    }


    /*
        Creates a balanced binary search tree for all commands listed in the array
        - Used for testing -
     */
    private CommandBTreeNode(ValidCommand[] allCommands) {
        if (allCommands.length == 0) {
            throw new IllegalArgumentException("Array must contain at least one item");
        }

        init(allCommands);
    }


    /*
        Total nodes in the tree
        - Used for testing -
     */
    private int size() {
        int total = 1;
        if (left != null) {
            total += left.size();

            if (right != null) {
                total += right.size();
            }
        }
        return total;
    }


    /*
        Finds a match for the command at the beginning of the line
        Checks that an argument is given or omitted as needed
     */
    ParsedCommand find(String line) {
        final String commandString = data.getCommandString();
        int comparison;

        // If the line is shorter than the proposed command then move to the left
        if (commandString.length() <= line.length()) {
            comparison = line.substring(0, commandString.length()).compareTo(commandString);
        }
        else {
            comparison = line.compareTo(commandString.substring(0, line.length()));
            // Cannot be a complete match because the line is too short
            if (comparison == 0) {
                comparison = -1;
            }
        }

        if (comparison < 0 && left != null) {
            return left.find(line);
        }
        else if (comparison == 0) {
            // Check line.length is correct for arguments needed
            if ((!data.hasArguments() && commandString.length() == line.length()) || (data.hasArguments()
                    // + 1 for space after string
                    && line.length() > commandString.length() + 1 && line.charAt(commandString.length()) == ' '))
            {
                if (data.hasArguments()) {
                    return new ParsedCommand(data, line.substring(commandString.length() + 1));
                }
                else {
                    return new ParsedCommand(data);
                }
            }
            else if (right != null) {
                return right.find(line);
            }
        }
        else if (right != null) {
            return right.find(line);
        }

        throw new IllegalArgumentException("I don't understand what you want to do");
    }
}
