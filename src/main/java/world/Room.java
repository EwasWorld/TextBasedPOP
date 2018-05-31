package world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



public class Room {
    private String name;
    private HashMap<Direction, Room> exits;
    private RoomObjectsSet roomObjects;
    private String firstEntranceText;
    private String laterEntranceText;
    private Set<Person> peopleInRoom = new HashSet<>();
    private boolean enteredBefore = false;


    public Room(String name, HashMap<Direction, Room> exits, RoomObjectsSet roomObjects, String firstEntranceText,
                String laterEntranceText)
    {
        this.name = name;
        this.exits = exits;
        this.roomObjects = roomObjects;
        this.firstEntranceText = firstEntranceText;
        this.laterEntranceText = laterEntranceText;
    }


    public String getName() {
        return name;
    }


    public Set<Direction> getExits() {
        return exits.keySet();
    }


    public Room move(Direction direction) {
        return exits.get(direction);
    }


    public String getRoomText() {
        if (!enteredBefore) {
            enteredBefore = true;
            return firstEntranceText;
        }
        else {
            return laterEntranceText;
        }
    }


    public RoomObject take(String objectName) {
        return roomObjects.remove(objectName);
    }


    public void drop(RoomObject roomObject) {
        roomObjects.add(roomObject);
    }


    public String touchRoomObject(String objectName) {
        return getRoomObject(objectName).getTouchText();
    }


    public String examineRoomObject(String objectName) {
        return getRoomObject(objectName).getExamineText();
    }


    private RoomObject getRoomObject(String objectName) {
        return roomObjects.getFromString(objectName);
    }


    private void personEnters(Person person) {
        peopleInRoom.add(person);
    }


    private void personExits(Person person) {
        peopleInRoom.remove(person);
    }
}
