package world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Room {
    private String name;
    private Map<Direction, Room> exits = new HashMap<>();
    // When true move() is disabled
    private boolean exitsLocked;
    // TODO: Prevent two objects in a room having the same name or alternate names
    private ItemsSet roomObjects;
    private String firstEntranceText;
    private String laterEntranceText;
    private Set<Person> peopleInRoom = new HashSet<>();
    private boolean enteredBefore = false;


    public Room(String name, String firstEntranceText,
                String laterEntranceText)
    {
        this(name, false, firstEntranceText, laterEntranceText);
    }


    public Room(String name, boolean exitsLocked, String firstEntranceText,
                String laterEntranceText)
    {
        this.name = name;
        this.exitsLocked = exitsLocked;
        roomObjects = new ItemsSet();
        this.firstEntranceText = firstEntranceText;
        this.laterEntranceText = laterEntranceText;
    }


    public void setExitsLocked(boolean exitsLocked) {
        this.exitsLocked = exitsLocked;
    }


    Room move(Direction direction) {
        if (!exits.keySet().contains(direction)) {
            throw new IllegalArgumentException("There's no exit that way");
        }
        if (exitsLocked) {
            throw new IllegalArgumentException("You can't go that way");
        }

        return exits.get(direction);
    }


    String getRoomText() {
        String returnString;
        if (!enteredBefore) {
            enteredBefore = true;
            returnString = "~ " + name + " ~\n" + firstEntranceText;
        }
        else {
            returnString = name + "\n" + laterEntranceText;
        }

        if (exits.size() > 0) {
            returnString += "\n" + getExitsString();
        }
        return returnString;
    }


    String getExitsString() {
        if (exits.size() == 0) {
            return "";
        }

        StringBuilder returnString;

        if (exits.size() == 1) {
            returnString = new StringBuilder("Exit is ");
        }
        else {
            returnString = new StringBuilder("Exits are ");
        }

        for (Direction direction : exits.keySet()) {
            returnString.append(direction.toString().toLowerCase());
            returnString.append(", ");
        }

        return returnString.substring(0, returnString.length() - 2);
    }


    public void addBidirectionalExit(Room room, Direction direction) {
        exits.put(direction, room);
        room.exits.put(Direction.getOpposite(direction), this);
    }


    public void addSingleDirectionalExit(Room room, Direction direction) {
        exits.put(direction, room);
    }


    public TakableItem removeRoomObject(String objectName) {
        return roomObjects.remove(objectName);
    }


    public void addRoomObject(RoomObject roomObject) {
        roomObjects.add(roomObject);
    }


    String touchRoomObject(String objectName) {
        RoomObject roomObject = getRoomObject(objectName);
        if (roomObject.isBreakIfTouched()) {
            roomObjects.remove(roomObject);
        }
        return roomObject.getTouchText();
    }


    private RoomObject getRoomObject(String objectName) {
        return roomObjects.getFromString(objectName);
    }


    String examineRoomObject(String objectName) {
        return getRoomObject(objectName).getExamineText();
    }


    public int roomObjectsSize() {
        return roomObjects.size();
    }


    private void personEnters(Person person) {
        peopleInRoom.add(person);
    }


    private void personExits(Person person) {
        peopleInRoom.remove(person);
    }


    public boolean contains(String objectName) {
        return roomObjects.contains(objectName);
    }


    public boolean contains(RoomObject roomObject) {
        return roomObjects.contains(roomObject);
    }
}
