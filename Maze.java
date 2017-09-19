package jedatarii;

import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private Node[][] nodesOfMaze;
    private boolean[][] sidewaysWalls, verticalWalls;
    private RecursiveDivision maze;
    private ConsoleUI console;
    
    public Maze(int length, int width) {
        nodesOfMaze = new Node[length][width];
        sidewaysWalls = new boolean[length][width+3];
        verticalWalls = new boolean[length+1][width+1];
        makeMaze();
    }
    
    private void makeMaze() {
        maze = new RecursiveDivision(nodesOfMaze[0].length,nodesOfMaze.length);
        maze.makeMaze();
        
        int vertCounter1 = 0, sideCounter1 = 0;
        boolean[][] booleanMaze = maze.getMaze();
        for(int i = 0;i<booleanMaze.length;i++) {
            if(i%2==0) {
                int sideCounter2 = 0;
                for(int k = 0;k<booleanMaze[i].length-1;k++) {
                    if(k%2 == 1) {
                        sidewaysWalls[sideCounter2][sideCounter1] = booleanMaze[i][k];
                        sideCounter2++;
                    }
                }
                sideCounter1++;
            } else {
                int vertCounter2 = 0;
                for(int k = 0;k<booleanMaze[i].length;k++) {
                    if(k%2 == 0) {
                        verticalWalls[vertCounter2][vertCounter1] = booleanMaze[i][k];
                        vertCounter2++;
                    }
                }
                vertCounter1++;
            }
        }
        int sideCounter = 0;
        for(int i = 0;i<booleanMaze[booleanMaze.length-1].length;i++) {
            if(i%2==1) {
                sidewaysWalls[sideCounter][sidewaysWalls[sideCounter].length-1] = booleanMaze[booleanMaze.length-1][i];
                sideCounter++;
            }
        }
        
        for(int i = 0;i<nodesOfMaze.length;i++) {
            for(int j = 0;j<nodesOfMaze[i].length;j++) {
                ArrayList<Boolean> walls = new ArrayList<>();
                walls.add(sidewaysWalls[i][j]);
                walls.add(verticalWalls[i+1][j]);
                walls.add(sidewaysWalls[i][j+1]);
                walls.add(verticalWalls[i][j]);
                nodesOfMaze[i][j] = new Node(walls);
            }
        }
        
        int entranceRow = maze.getEntranceRow();
        nodesOfMaze[0][entranceRow].setContainsPlayer(true);
    }

    class RecursiveDivision {
        static final boolean MAZE_WALL = true;
        static final boolean MAZE_PATH = false;

        private int rows, cols, act_rows, act_cols, entranceRow, exitRow;

        private boolean[][] board;

        public RecursiveDivision(int row, int col) {

            //initialize instance variables
            rows = row*2+1;
            cols = col*2+1;
            act_rows = row;
            act_cols = col;
            board = new boolean[rows][cols];



            //set the maze to empty
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    board[i][j] = MAZE_PATH;
                }
            }

            //make the outer walls
            for(int i=0; i<rows; i++){
                board[i][0] = MAZE_WALL;
                board[i][cols-1] = MAZE_WALL;
            }

            for(int i=0; i<cols; i++){
                board[0][i] = MAZE_WALL;
                board[rows-1][i] = MAZE_WALL;
            }


        }

        //storefront method to make the maze
        public void makeMaze() {
            makeMaze(0,cols-1,0,rows-1);
            makeOpenings();
        }


        //behind the scences actual mazemaking
        private void makeMaze(int left, int right, int top, int bottom) {
            int width = right-left;
            int height = bottom-top;

            //makes sure there is still room to divide, then picks the best
            //direction to divide into
            if(width > 2 && height > 2){

                if(width > height)
                    divideVertical(left, right, top, bottom);

                else if(height > width)
                    divideHorizontal(left, right, top, bottom);

                else if(height == width){
                    Random rand = new Random();
                    boolean pickOne = rand.nextBoolean();

                    if(pickOne)
                        divideVertical(left, right, top, bottom);
                    else
                        divideHorizontal(left, right, top, bottom);
                }
            }else if(width > 2 && height <=2){
                divideVertical(left, right, top, bottom);
            }else if(width <=2 && height > 2){
                divideHorizontal(left, right, top, bottom);
            }
        }


        private void divideVertical(int left, int right, int top, int bottom) {
            Random rand = new Random();

            //find a random point to divide at
            //must be even to draw a wall there
            int divide =  left + 2 + rand.nextInt((right-left-1)/2)*2;

            //draw a line at the halfway point
            for(int i=top; i<bottom; i++){
                board[i][divide] = MAZE_WALL;
            }

            //get a random odd integer between top and bottom and clear it
            int clearSpace = top + rand.nextInt((bottom-top)/2) * 2 + 1;

            board[clearSpace][divide] = MAZE_PATH;

            makeMaze(left, divide, top, bottom);
            makeMaze(divide, right, top, bottom);
        }

        private void divideHorizontal(int left, int right, int top, int bottom) {
            Random rand = new Random();

            //find a random point to divide at
            //must be even to draw a wall there
            int divide =  top + 2 + rand.nextInt((bottom-top-1)/2)*2;
            if(divide%2 == 1)
                divide++;

            //draw a line at the halfway point
            for(int i=left; i<right; i++){
                board[divide][i] = MAZE_WALL;
            }

            //get a random odd integer between left and right and clear it
            int clearSpace = left + rand.nextInt((right-left)/2) * 2 + 1;

            board[divide][clearSpace] = MAZE_PATH;

            //recur for both parts of the newly split section
            makeMaze(left, right, top, divide);
            makeMaze(left, right, divide, bottom);
        }

        public void makeOpenings() {

            Random rand = new Random(); //two different random number generators
            Random rand2 = new Random();//just in case

            //a random location for the entrance and exit
            entranceRow = rand.nextInt(act_rows-1);
            exitRow = rand2.nextInt(act_rows-1);
            
            int entrance_row = entranceRow * 2 + 1;
            int exit_row = exitRow * 2 + 1;

            //clear the location
            board[entrance_row][0] = MAZE_PATH;
            board[exit_row][cols-1] = MAZE_PATH;

        }

        public void printMaze() {
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    System.out.print((board[i][j])?"#":" ");
                }
                System.out.println();
            }
        }

        public boolean[][] getMaze() {
            return board;
        }
        
        public int getEntranceRow() {
            return entranceRow;
        }
        
        public int getExitRow() {
            return exitRow;
        }
    }

    @Override
    public String toString() {
        String[] sideways, vertical;
        
        sideways = new String[sidewaysWalls[0].length];
        for(int i = 0;i<sideways.length;i++) {
            sideways[i] = ".";
            for (boolean[] sidewaysWall : sidewaysWalls) {
                sideways[i] += (sidewaysWall[i]) ? "_." : " .";
            }
        }
        
        int[] coordinatesOfPlayer = containsPlayer();
        boolean isNull = coordinatesOfPlayer != null;
        vertical = new String[verticalWalls[0].length];
        for(int i = 0;i<vertical.length;i++) {
            vertical[i] = "";
            for(int j = 0;j<verticalWalls.length;j++) {
                if (isNull) {
                    boolean b = j == coordinatesOfPlayer[0] && i == coordinatesOfPlayer[1];
                    vertical[i] += (verticalWalls[j][i]) ? "|" : " ";
                    vertical[i] += (b) ? "x" : " ";
                } else {
                    vertical[i] += (verticalWalls[j][i]) ? "| " : "  ";
                }
            }
        }
        
        String output = "";
        for(int i = 0;i<vertical.length-1;i++) {
            output += sideways[i] + "\n";
            output += vertical[i] + "\n";
        }
        output += sideways[sideways.length-1];
        return output;
    }
    
    private int[] containsPlayer() {
        for(int i = 0;i<nodesOfMaze.length;i++) {
            for(int j = 0;j<nodesOfMaze[i].length;j++) {
                if(nodesOfMaze[i][j].containsPlayer()) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    public static void run(ConsoleUI console) throws InterruptedException {
        console.displayButtons(new String[]{"+1", "+5", "-1", "Go!"});
        int i = 2;
        boolean _continue = true;
        do {
            try {
                console.clear();
                console.println("What size of maze do you want?");
                console.println("Click the buttons to increase or decrease the size of the maze.");
                console.println("Click \"Go!\" when you\'re ready!\n");
                console.println("Maze size: " + i);
                String mazeSize = "";
                for(int j = 0;j<i;j++) {
                    mazeSize += "██"; // █ is Alt+219
                }
                for(int j = 0;j<i;j++) {
                    console.println(mazeSize);
                }
                String chosen = console.getNextButtonPress();
                switch(chosen) {
                    case "+1":
                        if(i < 50) i++;
                        break;
                    case "+5":
                        if(i < 46) i += 5;
                        else if(i < 50) i = 50;
                        break;
                    case "-1":
                        if(i > 2) i--;
                        break;
                    case "Go!":
                        _continue = false;
                        break;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
                console.clear();
                console.println("Please type two numbers seperated by a comma, without spaces.");
                _continue = true;
            }
        } while (_continue);
        console.clear();
        (new Maze(i,i)).internalRun(console);
    }
    
    private void internalRun(ConsoleUI console) throws InterruptedException {
        this.console = console;
        console.displayButtons(new String[]{"Easy", "Normal", "Hard"});
        console.println("What difficulty do you want to play?");
        String answer = console.getNextButtonPress();
        if (answer.equalsIgnoreCase("Easy")) {
            easyMode();
        } else if (answer.equalsIgnoreCase("Normal")) {
            normalMode();
        } else if (answer.equalsIgnoreCase("Hard")) {
            console.clear();
            hardMode();
        }
    }
    
    private void easyMode() throws InterruptedException {
        boolean again = false;
        console.displayNothing();
        do {
            int[] coordinates = containsPlayer();
            int x = coordinates[0];
            int y = coordinates[1];
            Node current = nodesOfMaze[x][y];
            if(!again) {
                console.clear();
                console.println(toString());
            }
            Direction d = console.getNextDirection(0);
            if(current.wallAt(d)) {
                console.println("Oops! There's a wall there.");
                again = true;
            } else if(nodesOfMaze[0][maze.getEntranceRow()].containsPlayer() && d == Direction.WEST) {
                console.println("Not so fast! You can\'t run away!");
                again = true;
            } else {
                again = false;
                switch(d) {
                    case WEST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x-1][y].setContainsPlayer(true);
                        break;
                    case EAST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x+1][y].setContainsPlayer(true);
                        break;
                    case NORTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y-1].setContainsPlayer(true);
                        break;
                    case SOUTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y+1].setContainsPlayer(true);
                        break;
                }
            }
        } while(!nodesOfMaze[nodesOfMaze.length-1][maze.getExitRow()].containsPlayer);
        console.clear();
        console.println("Congrats! You solved the maze!\n");
        console.println(toString() + "\n");
    }
    
    private void normalMode() throws InterruptedException {
        boolean again = false;
        console.displayNothing();
        ArrayList<int[]> visitedNodes = new ArrayList<>();
        do {
            int[] coordinates = containsPlayer();
            int x = coordinates[0];
            int y = coordinates[1];
            Node current = nodesOfMaze[x][y];
            visitedNodes.add(coordinates);
            if(!again) {
                console.clear();
                console.println(toStringWithVisibility(visitedNodes));
            }
            Direction d = console.getNextDirection(0);
            if(current.wallAt(d)) {
                console.println("Oops! There's a wall there.");
                again = true;
            } else if(nodesOfMaze[0][maze.getEntranceRow()].containsPlayer() && d == Direction.WEST) {
                console.println("Not so fast! You can\'t run away!");
                again = true;
            } else {
                again = false;
                switch(d) {
                    case WEST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x-1][y].setContainsPlayer(true);
                        break;
                    case EAST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x+1][y].setContainsPlayer(true);
                        break;
                    case NORTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y-1].setContainsPlayer(true);
                        break;
                    case SOUTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y+1].setContainsPlayer(true);
                        break;
                }
            }
        } while(!nodesOfMaze[nodesOfMaze.length-1][maze.getExitRow()].containsPlayer);
        console.clear();
        console.println(toString() + "\n");
        console.println("Congrats! You solved the maze!\n");
    }
    
    private String toStringWithVisibility(ArrayList<int[]> visibleCoordinates) {
        String[] sideways, vertical;
        
        sideways = new String[sidewaysWalls[0].length];
        int[] sidewaysCount = new int[sideways.length];
        for(int i:sidewaysCount) i = 0;
        for(int y = 0;y<sideways.length;y++) {
            sideways[y] = "";
            for(int x = 0;x<sidewaysWalls.length;x++) {
                if((sidewaysWallIsVisible(x, y, visibleCoordinates))) {
                    if(sidewaysWalls[x][y]) {
                        sideways[y] += (sidewaysCount[y] == 0 && x == 0)?".":"";
                        sideways[y] += "_.";
                    } else {
                        sideways[y] += (sidewaysCount[y] == 0 && x == 0)?".":"";
                        sideways[y] += " .";
                    }
                    sidewaysCount[y]++;
                } else {
                    sideways[y] += (sidewaysWallIsVisible(x+1, y, visibleCoordinates))?(sidewaysCount[y] == 0)?"  .":" .":"  ";
                }
            }
        }
        
        int[] coordinatesOfPlayer = containsPlayer();
        boolean isNull = coordinatesOfPlayer != null;
        vertical = new String[verticalWalls[0].length];
        for(int y = 0;y<vertical.length;y++) {
            vertical[y] = "";
            for(int x = 0;x<verticalWalls.length;x++) {
                if (isNull) {
                    boolean b = x == coordinatesOfPlayer[0] && y == coordinatesOfPlayer[1];
                    vertical[y] += (verticalWalls[x][y] && verticalWallIsVisible(x, y, visibleCoordinates))?"|":" ";
                    vertical[y] += (b) ? "x" : " ";
                } else {
                    vertical[y] += (verticalWalls[x][y] && verticalWallIsVisible(x, y, visibleCoordinates))?"| ":"  ";
                }
            }
        }
        
        String output = "";
        for(int i = 0;i<vertical.length-1;i++) {
            output += sideways[i] + "\n";
            output += vertical[i] + "\n";
        }
        output += constructBottomWall(visibleCoordinates, nodesOfMaze[0].length);
        return output;
    }
    
    private String constructBottomWall(ArrayList<int[]> visibleNodes, int width) {
        String output = "";
        int count = 0;
        for(int x = 0;x<sidewaysWalls.length;x++) {
            if((sidewaysWallIsVisible(x, width, visibleNodes))) {
                if(sidewaysWalls[x][width]) {
                    output += (count == 0 && x == 0)?".":"";
                    output += "_.";
                } else {
                    output += (count == 0 && x == 0)?".":"";
                    output += " .";
                }
                count++;
            } else {
                output += (sidewaysWallIsVisible(x+1, width, visibleNodes))?(count == 0)?"  .":" .":"  ";
            }
        }
        return output;
    }
    
    private boolean sidewaysWallIsVisible(int x, int y, ArrayList<int[]> visibleCoordinates) {
        for(int[] coordinate:visibleCoordinates) {
            if(coordinate[0] == x && (coordinate[1] == y || coordinate[1] == (y-1))) {
                return true;
            }
        }
        return false;
    }
    
    private boolean verticalWallIsVisible(int x, int y, ArrayList<int[]> visibleCoordinates) {
        for(int[] coordinate:visibleCoordinates) {
            if((coordinate[0] == x || coordinate[0] == (x-1)) && coordinate[1] == y) {
                return true;
            }
        }
        return false;
    }
    
    private void hardMode() {
        console.println("Welcome to the Maze....\n");
        int[] currentCoordinates, previousCoordinates = null;
        Direction facing = Direction.EAST;
        do {
            currentCoordinates = containsPlayer();
            if(previousCoordinates != null) {
                int[] transformation = transformation(previousCoordinates, currentCoordinates);
                if(transformation[0] == 0) {
                    if(transformation[1] == 1) {
                        facing = Direction.EAST;
                    } else if(transformation[1] == -1) {
                        facing = Direction.WEST;
                    } else {
                        facing = Direction.NORTH;
                    }
                } else if(transformation[0] == 1){
                    facing = Direction.NORTH;
                } else if(transformation[0] == -1) {
                    facing = Direction.SOUTH;
                } else {
                    facing = Direction.NORTH;
                }
            }
            int x = currentCoordinates[0];
            int y = currentCoordinates[1];
            Node current = nodesOfMaze[x][y];
            Node rotated = current.alignTo(facing);
            ArrayList<Boolean> rotatedWalls = rotated.getWalls();
            ArrayList<Integer> openings = new ArrayList<>();
            for(int i = 0;i<rotatedWalls.size();i++) {
                if(!rotatedWalls.get(i)) {
                    openings.add(i);
                }
            }
            ArrayList<String> openStrings = new ArrayList<>();
            for(int i:openings) {
                openStrings.add(Direction.fromRepresentation(i).toRelativeString());
            }
            Direction whichWay;
            console.print("You can go ");
            switch (openings.size()) {
                case 1:
                    console.println(Direction.fromRepresentation(openings.get(0)).toRelativeString().toLowerCase() + ".");
                    break;
                case 2:
                    console.println(Direction.fromRepresentation(openings.get(0)).toRelativeString().toLowerCase() + " and "
                            + Direction.fromRepresentation(openings.get(1)).toRelativeString().toLowerCase() + ".");
                    break;
                case 3:
                    console.println(Direction.fromRepresentation(openings.get(0)).toRelativeString().toLowerCase() + ", "
                            + Direction.fromRepresentation(openings.get(1)).toRelativeString().toLowerCase() + ", and "
                            + Direction.fromRepresentation(openings.get(2)).toRelativeString().toLowerCase() + ".");
                    break;
            }
            whichWay = whichRelativeWay();
            int backTo = rotated.rotationFrom(current.facing);
            Direction actual = whichWay.rotate(backTo);
            if(rotated.wallAt(whichWay)) {
                console.println("Oops, there's a wall there!\n");
            } else if(nodesOfMaze[0][maze.getEntranceRow()].containsPlayer() && actual == Direction.EAST) {
                console.println("Not so fast! You can\'t run away!\n");
            } else {
                switch(actual) {
                    case WEST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x-1][y].setContainsPlayer(true);
                        break;
                    case EAST:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x+1][y].setContainsPlayer(true);
                        break;
                    case NORTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y-1].setContainsPlayer(true);
                        break;
                    case SOUTH:
                        current.setContainsPlayer(false);
                        nodesOfMaze[x][y+1].setContainsPlayer(true);
                        break;
                }
                previousCoordinates = currentCoordinates;
                console.clear();
            }
        } while (!nodesOfMaze[nodesOfMaze.length-1][maze.getExitRow()].containsPlayer);
        console.clear();
        console.println(toString() + "\n");
        console.println("Congrats! You solved the maze!\n");
    }
    
    private int[] transformation(int[] from, int[] to) {
        if(from.length != to.length) return null;
        int[] output = new int[from.length];
        for(int i = 0;i<from.length;i++) {
            output[i] = to[i] - from[i];
        }
        return output;
    }
    
    private Direction whichWay() {
        console.displayButtons(new String[]{"Up", "Down", "Left", "Right"});
        Direction direction;
        console.println("Which way do you want to go?");
        String answer = console.getNextButtonPress();
        direction = Direction.fromString(answer);
        boolean isNull = direction != null;
        assert isNull:answer;
        return direction;
    }
    
    private Direction whichRelativeWay() {
        console.displayButtons(new String[]{"Forwards", "Backwards", "Left", "Right"});
        Direction direction;
        console.println("Which way do you want to go?");
        String answer = console.getNextButtonPress();
        direction = Direction.fromString(answer);
        boolean isNull = direction != null;
        assert isNull:answer;
        return direction;
    }
    
    class Node {
        private ArrayList<Boolean> walls;
        private Direction facing;
        private boolean containsPlayer = false;
        
        public Node(ArrayList<Boolean> walls) {
            this.walls = new ArrayList<>(walls);
            facing = Direction.NORTH;
        }
        
        public Node(ArrayList<Boolean> walls, Direction facing) {
            this.walls = new ArrayList<>(walls);
            this.facing = facing;
        }
        
        public Node rotateClockwise() {
            ArrayList<Boolean> wallsCopy = new ArrayList<>(walls);
            boolean temp = wallsCopy.remove(wallsCopy.size()-1);
            wallsCopy.add(0, temp);
            return new Node(wallsCopy);
        }
        
        public Node rotateCounterclockwise() {
            ArrayList<Boolean> wallsCopy = new ArrayList<>(walls);
            boolean temp = wallsCopy.remove(0);
            wallsCopy.add(temp);
            return new Node(wallsCopy);
        }
        
        public boolean wallAt(int i) {
            return walls.get(i);
        }
        
        public boolean wallAt(Direction d) {
            return walls.get(d.getRepresentation());
        }

        public ArrayList<Boolean> getWalls() {
            return walls;
        }

        public void setContainsPlayer(boolean containsPlayer) {
            this.containsPlayer = containsPlayer;
        }

        public boolean containsPlayer() {
            return containsPlayer;
        }
        
        public Node alignTo(Direction d) {
            int difference = facing.getRepresentation() - d.getRepresentation();
            switch(difference) {
                case 3:
                case -1:
                    return rotateClockwise();
                case -2:
                case 2:
                    return rotateCounterclockwise().rotateCounterclockwise();
                case -3:
                case 1:
                    return rotateCounterclockwise();
                default:
                    throw new EnumConstantNotPresentException(Direction.class, "Unknown");
            }
        }
        
        public Node alignWith(Node other) {
            return alignTo(other.facing);
        }
        
        public int rotationFrom(Direction d) {
            int difference = facing.getRepresentation() - d.getRepresentation();
            switch(difference) {
                case 3:
                case -1:
                    return 90;
                case -2:
                case 2:
                    return 180;
                case -3:
                case 1:
                    return -90;
                default:
                    throw new EnumConstantNotPresentException(Direction.class, "Unknown");
            }
        }

        @Override
        public String toString() {
            String output = (walls.get(0))?"._.\n":". .\n";
            output += (walls.get(3))?"|":" ";
            output += (containsPlayer)?"x":" ";
            output += (walls.get(1))?"|\n.":" \n.";
            output += (walls.get(2))?"_.":" .";
            return output;
        }
        
        public String getStringLevel(int level) {
            switch(level) {
                case 0:
                    return (walls.get(0))?"._.":". .";
                case 1:
                    String output = (walls.get(3))?"|":" ";
                    output += (containsPlayer)?"x":" ";
                    output += (walls.get(1))?"|":" ";
                    return output;
                case 2:
                    return (walls.get(2))?"._.":". .";
                default:
                    return null;
            }
        }
        
        public String getLimitedLevel(int level) {
            switch(level) {
                case 0:
                    return (walls.get(0))?"._":". ";
                case 1:
                    String output = (walls.get(3))?"|":" ";
                    output += (containsPlayer)?"x":" ";
                    return output;
                case 2:
                    return (walls.get(2))?"._":". ";
                default:
                    return null;
            }
        }
    }
}