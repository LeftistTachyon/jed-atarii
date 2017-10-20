package jedatarii;

public enum Direction {
    NORTH(0), SOUTH(2), WEST(3), EAST(1);

    private int representation;

    private Direction(int representation) {
        this.representation = representation;
    }

    public static Direction fromString(String s) {
        if(s.equalsIgnoreCase("up")) {
            return NORTH;
        } else if(s.equalsIgnoreCase("down")) {
            return SOUTH;
        } else if(s.equalsIgnoreCase("left")) {
            return WEST;
        } else if(s.equalsIgnoreCase("right")) {
            return EAST;
        } else {
            return null;
        }
    }

    public static Direction relativeString(String s) {
        if(s.equalsIgnoreCase("forward")) {
            return NORTH;
        } else if(s.equalsIgnoreCase("backward")) {
            return SOUTH;
        } else if(s.equalsIgnoreCase("left")) {
            return WEST;
        } else if(s.equalsIgnoreCase("right")) {
            return EAST;
        } else {
            return null;
        }
    }

    public static Direction fromRepresentation(int representation) {
        for(Direction d:Direction.values()) {
            if(d.representation == representation) return d;
        }
        return null;
    }

    public int getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        switch(this) {
            case EAST:
                return "East";
            case NORTH:
                return "North";
            case SOUTH:
                return "South";
            case WEST:
                return "West";
        }
        return null;
    }

    public String toDirectionString() {
        switch(this) {
            case EAST:
                return "Right";
            case NORTH:
                return "Up";
            case SOUTH:
                return "Down";
            case WEST:
                return "Left";
        }
        return null;
    }

    public String toRelativeString() {
        switch(this) {
            case EAST:
                return "Forward";
            case NORTH:
                return "Backward";
            case SOUTH:
                return "Down";
            case WEST:
                return "Left";
        }
        return null;
    }

    public Direction rotate(int degrees) {
        int outputRepresentation = representation;
        switch(degrees) {
            case 90:
                outputRepresentation += 1;
                break;
            case 180:
                outputRepresentation += 2;
                break;
            case -90:
                outputRepresentation += 3;
                break;
            default:
                return null;
        }
        outputRepresentation %= 4;
        return fromRepresentation(outputRepresentation);
    }
}