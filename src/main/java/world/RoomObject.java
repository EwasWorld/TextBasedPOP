package world;

import java.util.HashSet;
import java.util.Set;



public class RoomObject {
    private String name;
    private Set<String> alternateNames;
    private String touchText;
    private String takeText;
    private String dropText;
    private String examineText;
    private boolean readable;


    public RoomObject(String name, Set<String> alternateNames, String touchText, String takeText, String dropText,
                      String examineText, boolean readable)
    {
        this.name = name;
        this.alternateNames = alternateNames;
        this.touchText = touchText;
        this.takeText = takeText;
        this.dropText = dropText;
        this.examineText = examineText;
        this.readable = readable;
    }


    public String getName() {
        return name;
    }


    public Set<String> getAllNames() {
        final Set<String> returnSet = new HashSet<>(alternateNames);
        returnSet.add(name);
        return returnSet;
    }


    public String getExamineText() {
        return examineText;
    }


    public String getTakeText() {
        return takeText;
    }


    public String getDropText() {
        return dropText;
    }


    public String getTouchText() {
        return touchText;
    }


    public String read() {
        String returnString = examineText;
        if (!readable) {
            returnString += " There doesn't seem to be anything on here to read.";
        }
        return returnString;
    }
}
