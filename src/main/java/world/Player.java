package world;

public class Player {
    public enum PlayerClass {WIZARD}



    private static PlayerClass playerClass = null;
    private static Room playerLocation;
    private static RoomObjectsSet inventory = new RoomObjectsSet();


    public static void setPlayerClass(PlayerClass playerClass) {
        if (Player.playerClass != null) {
            throw new IllegalStateException("Cannot change player class");
        }

        Player.playerClass = playerClass;
    }


    public static String setPlayerLocation(Room playerLocation) {
        Player.playerLocation = playerLocation;
        return getPlayerLocationString();
    }


    public static String move(Direction direction) {
        playerLocation = playerLocation.move(direction);
        return playerLocation.getRoomText();
    }


    public static String getPlayerLocationString() {
        return playerLocation.getRoomText();
    }


    public static Room getPlayerLocation() {
        return playerLocation;
    }


    public static String getInventoryString() {
        return inventory.toString();
    }


    public static String take(String objectName) {
        final RoomObject roomObject = playerLocation.removeRoomObject(objectName);
        inventory.add(roomObject);
        return roomObject.getTakeText();
    }


    public static String drop(String objectName) {
        final RoomObject roomObject = inventory.remove(objectName);
        playerLocation.addRoomObject(roomObject);
        return roomObject.getDropText();
    }


    public static String touch(String objectName) {
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


    public static String examine(String objectName) {
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
