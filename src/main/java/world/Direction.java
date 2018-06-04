package world;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;


    static Direction getOpposite(Direction direction) {
        switch (direction) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                throw new IllegalStateException("Fatal Error: Invalid direction");
        }
    }
}
