package world;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



public class RoomObject {
    private String name;
    private Set<String> alternateNames;
    private String touchText;
    private String examineText;
    private boolean breakIfTouched;
    // TODO: Something to add to the end of a room description


    public RoomObject(String name, String[] alternateNames, String touchText, String examineText,
                      boolean breakIfTouched)
    {
        this.name = name;
        this.alternateNames = new HashSet<>(Arrays.asList(alternateNames));
        this.touchText = touchText;
        this.examineText = examineText;
        this.breakIfTouched = breakIfTouched;
    }


    public String getName() {
        return name;
    }


    public Set<String> getAllNames() {
        final Set<String> returnSet = new HashSet<>();
        if (alternateNames != null) {
            returnSet.addAll(alternateNames);
        }
        returnSet.add(name);
        return returnSet;
    }


    public String getExamineText() {
        return examineText;
    }


    public String getTouchText() {
        return touchText;
    }


    public boolean isBreakIfTouched() {
        return breakIfTouched;
    }


    public String getTakeText() {
        return "You can't take that.";
    }


    public String getDropText() {
        return "How are you even holding this?";
    }
}
