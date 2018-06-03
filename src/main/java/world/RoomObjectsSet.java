package world;

import java.util.HashSet;
import java.util.Set;



public class RoomObjectsSet {
    private Set<RoomObject> roomObjects;


    public RoomObjectsSet() {
        roomObjects = new HashSet<>();
    }


    public RoomObject getFromString(String objectName) {
        for (RoomObject roomObject : roomObjects) {
            for (String name : roomObject.getAllNames()) {
                if (objectName.equalsIgnoreCase(name)) {
                    return roomObject;
                }
            }
        }
        throw new IllegalArgumentException("There is no such object");
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory: ");
        for (RoomObject roomObject : roomObjects) {
            sb.append(roomObject.getName());
            sb.append(", ");
        }
        sb.delete(sb.lastIndexOf(","), sb.length());
        return sb.toString();
    }

    public RoomObject remove(String objectName) {
        final RoomObject roomObject = getFromString(objectName);
        roomObjects.remove(roomObject) ;
        return roomObject;
    }

    public void remove(RoomObject roomObject) {
        roomObjects.remove(roomObject) ;
    }


    public void add(RoomObject roomObject) {
        roomObjects.add(roomObject);
    }


    public boolean contains(String objectName) {
        try {
            getFromString(objectName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public boolean contains(RoomObject roomObject) {
        return roomObjects.contains(roomObject);
    }
}
