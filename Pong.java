package jedatarii;

public class Pong {
    private ConsoleUI console;
    private LCD lcd;
    
    private Pong(){
        lcd = new LCD();
    }
    
    public static void run(ConsoleUI console) throws InterruptedException {
        console.println("Welcome to Pong!\n");
        (new Pong()).internalRun(console);
    }
    
    private void internalRun(ConsoleUI console_) throws InterruptedException {
        console = console_;
        console.displayButtons(new String[]{"Go!"});
        console.println("In order to play, use your UP and DOWN keys.");
        console.println("Press the button below or the ENTER key when you are ready.");
        console.getNextButtonEnter();
        console.clear();
        console.displayNothing();
        console.setLCD(lcd);
        lcd.fillRect(1, 0, 57, 0, '_');
        lcd.fillRect(1, 16, 57, 16, '_');
        lcd.fillRect(2, 1, 2, 4, '|');
        lcd.fillRect(56, 1, 56, 4, '|');
        internalGame();
    }
    
    // "\uD83C\uDF11" is 🌑
    // "\u25CF" is ●
    private void internalGame() throws InterruptedException {
        int playerOneScore = 0, playerTwoScore = 0;
        Ball b = new Ball(28,8);
        while(playerOneScore < 10 || playerTwoScore < 10) {
            b.render();
            if(b.isOutOfBounds()) {
                System.out.println("X location: " + b.getXLocation() + "\n\n\n\n\n\n\n\n\n\n\n\n\n");
                if(b.getXLocation() == 57) {
                    playerTwoScore++;
                } else if(b.getXLocation() == 1) {
                    playerOneScore++;
                }
                b.reset();
            }
            Thread.sleep(100);
        }
        console.removeLCD();
    }
    
    class Ball {
        private int downVelocity, rightVelocity, xLocation, yLocation;
        private final int xStart, yStart;
        private boolean outOfBounds;
        public Ball(int xL, int yL) {
            xLocation = xL;
            yLocation = yL;
            downVelocity = -1;
            rightVelocity = 1;
            outOfBounds = false;
            xStart = xL;
            yStart = yL;
        }

        public int getRightVelocity() {
            return rightVelocity;
        }

        public int getUpVelocity() {
            return downVelocity;
        }

        public int getXLocation() {
            return xLocation;
        }

        public int getYLocation() {
            return yLocation;
        }
        
        public void render() {
            if(outOfBounds) return;
            if(lcd.charAt(xLocation-1, yLocation) == '|' || lcd.charAt(xLocation+1, yLocation) == '|') {
                rightVelocity *= -1;
            }
            if(lcd.charAt(xLocation, yLocation+1) == '_' || lcd.charAt(xLocation, yLocation-1) == '_') {
                downVelocity *= -1;
            }
            lcd.setChar(yLocation, xLocation, ' ');
            xLocation += rightVelocity;
            System.out.println(xLocation + ", " + yLocation);
            yLocation += downVelocity;
            if(xLocation == 1 || xLocation == 57) {
                if(xLocation == 1) {
                    for(int y = 1;y<16;y++) {
                        lcd.setChar(y, 0, ' ');
                    }
                } else {
                    for(int y = 1;y<16;y++) {
                        lcd.setChar(y, 57, ' ');
                    }
                }
                rightVelocity = 0;
                downVelocity = 0;
                outOfBounds = true;
                return;
            }
            lcd.setChar(yLocation, xLocation, '\u25CF');
        }

        public boolean isOutOfBounds() {
            return outOfBounds;
        }
        
        public void reset() {
            outOfBounds = false;
            xLocation = xStart;
            yLocation = yStart;
        }
    }
}