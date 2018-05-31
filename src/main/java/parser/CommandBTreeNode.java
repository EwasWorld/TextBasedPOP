package parser;

import java.util.Arrays;



public class CommandBTreeNode {
    private CommandBTreeNode left = null;
    private CommandBTreeNode right = null;
    private CommandString data;


    public CommandBTreeNode(CommandString[] allCommands) {
        if (allCommands.length == 0) {
            throw new IllegalArgumentException("Array must contain at least one item");
        }
        Arrays.sort(allCommands);

        // If uneven sides, left will be 1 level deeper
        final int centreIndex = Math.floorDiv(allCommands.length, 2);
        data = allCommands[centreIndex];

        if (allCommands.length > 1) {
            final CommandString[] leftArray = new CommandString[centreIndex];
            System.arraycopy(allCommands, 0, leftArray, 0, centreIndex);
            left = new CommandBTreeNode(leftArray);

            final int rightArrayLength = allCommands.length - centreIndex - 1;
            if (rightArrayLength > 0) {
                final CommandString[] rightArray = new CommandString[rightArrayLength];
                System.arraycopy(allCommands, centreIndex + 1, rightArray, 0, rightArrayLength);
                right = new CommandBTreeNode(rightArray);
            }
        }
    }


    public int size() {
        int total = 1;
        if (left != null) {
            total += left.size();

            if (right != null) {
                total += right.size();
            }
        }
        return total;
    }


    // TODO: change to compare startwith
    public CommandString find(String commandString) {
        final int comparison = commandString.compareTo(data.getCommandString());

        if (comparison < 0 && left != null) {
            return left.find(commandString);
        }
        else if (comparison == 0) {
            return data;
        }
        else if (right != null) {
            return right.find(commandString);
        }

        throw new IllegalArgumentException("I don't understand what you want to do");
    }
}
