package world;

public class Player {
    enum PlayerClass {WIZARD}



    private PlayerClass playerClass;
    private Room playerLocation;
    private RoomObjectsSet inventory;


    public String move(Direction direction) {
        playerLocation = playerLocation.move(direction);
        return playerLocation.getRoomText();
    }


    public String getPlayerLocationString() {
        return playerLocation.getRoomText();
    }


    public String getInventoryString() {
        return inventory.toString();
    }


    public String take(String objectName) {
        final RoomObject roomObject = playerLocation.take(objectName);
        inventory.add(roomObject);
        return roomObject.getTakeText();
    }


    public void drop(String objectName) {
        playerLocation.drop(inventory.remove(objectName));
    }


    public String touch(String objectName) {
        try {
            return playerLocation.touchRoomObject(objectName);
        } catch (IllegalArgumentException e) {
            // Could still be in inventory
        }

        try {
            return inventory.getFromString(objectName).getTouchText();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("That object is nowhere to be seen");
        }
    }


    public String examine(String objectName) {
        try {
            return playerLocation.examineRoomObject(objectName);
        } catch (IllegalArgumentException e) {
            // Could still be in inventory
        }

        try {
            return inventory.getFromString(objectName).getExamineText();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("That object is nowhere to be seen");
        }
    }
}
