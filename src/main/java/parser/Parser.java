package parser;

public class Parser {
    enum CommandsList {

    }

    public static String parseLine(String line) {
        line = line.toLowerCase();

        // Single word commands - no argument
        switch (line) {
            case "i":
            case "inventory":
                break;
            case "l":
            case "look":
            case "location":
                break;
            case "yell":
                break;
        }

        // Commands with arguments
        return null;
    }
}
