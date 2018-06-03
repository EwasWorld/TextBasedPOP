package world;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



public class RoomObject {
    private String name;
    private Set<String> alternateNames;
    private String touchText;
    private String takeText;
    private String dropText;
    private String examineText;
    private boolean breakIfTouched;


    public RoomObject(String name, String[] alternateNames, String touchText, String takeText, String dropText,
                      String examineText, boolean breakIfTouched)
    {
        this.name = name;
        this.alternateNames = new HashSet<>(Arrays.asList(alternateNames));
        this.touchText = touchText;
        this.takeText = takeText;
        this.dropText = dropText;
        this.examineText = examineText;
        this.breakIfTouched = breakIfTouched;
    }


    public RoomObject(String name, String[] alternateNames, String touchText, String takeText, String dropText,
                      String examineText)
    {
        this(name,alternateNames, touchText, takeText, dropText, examineText, false);
    }


    public RoomObject(String name, String examineText) {
        this(name, new String[]{}, "", "", "", examineText, false);
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


    public String getTakeText() {
        return takeText;
    }


    public String getDropText() {
        return dropText;
    }


    public String getTouchText() {
        return touchText;
    }


    public boolean isBreakIfTouched() {
        return breakIfTouched;
    }
}
