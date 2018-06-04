package world;

import java.util.HashSet;
import java.util.Set;



public class ItemsSet {
    private Set<RoomObject> items;


    public ItemsSet() {
        items = new HashSet<>();
    }


    public RoomObject getFromString(String objectName) {
        for (RoomObject roomObject : items) {
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
        for (RoomObject roomObject : items) {
            sb.append(roomObject.getName());
            sb.append(", ");
        }
        sb.delete(sb.lastIndexOf(","), sb.length());
        return sb.toString();
    }

    public TakableItem remove(String objectName) {
        final RoomObject roomObject = getFromString(objectName);
        if (!(roomObject instanceof TakableItem)) {
            throw new IllegalArgumentException("Item cannot be removed.");
        }

        final TakableItem takableItem = (TakableItem) getFromString(objectName);
        items.remove(takableItem) ;
        return takableItem;
    }

    /*
        Remove an object from the set - may need to do this for a non-takable item if it is destroyed
    */
    public void remove(RoomObject roomObject) {
        items.remove(roomObject) ;
    }


    public void add(RoomObject roomObject) {
        items.add(roomObject);
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
        return items.contains(roomObject);
    }

    public int size() {
        return items.size();
    }
}
