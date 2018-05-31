package parser;

public class Parser {
    public static final CommandBTreeNode commandBTree = new CommandBTreeNode();

    public static String parseLine(String line) {
        final ParsedCommand parsedCommand = commandBTree.find(line.toLowerCase());

        return null;
    }
}
