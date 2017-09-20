package jedatarii;

public class Pong {
    private ConsoleUI console;
    
    private Pong(){}
    
    public static void run(ConsoleUI console) {
        console.println("Welcome to Pong!\n");
        (new Pong()).internalRun(console);
    }
    
    public void internalRun(ConsoleUI console) {
        this.console = console;
        console.println("In order to play, use your UP and DOWN keys.");
        LCD lcd = new LCD();
        for(int i = 0;i<50;i++) {
            int rand1 = (int) Math.round(Math.random()*17);
            int rand2 = (int) Math.round(Math.random()*59);
            int rand3 = (int) Math.round(Math.random()*25)+65;
            lcd.setChar(rand1, rand2, (char) rand3);
        }
        lcd.fillRect(1, 1, 1, 5, 'x');
        System.out.println(lcd.toString());
    }
    
    class LCD {
        private char[][] lcdScreen = new char[17][59];
        
        public LCD() {
            for(char[] chars:lcdScreen) {
                for(char c:chars) {
                    c = ' ';
                }
            }
        }
        
        public void setChar(int y, int x, char c) {
            lcdScreen[y][x] = c;
        }
        
        public void fillRect(int x1, int y1, int x2, int y2, char c) {
            int yCeil = Math.abs(y1-y2);
            int xCeil = Math.abs(x1-x2);
            for(int y = 0;y<yCeil;y++) {
                for(int x = 0;x<xCeil;x++) {
                    lcdScreen[y][x] = c;
                }
            }
        }
        
        @Override
        public String toString() {
            String output = "";
            for(char[] chars:lcdScreen) {
                for(char c:chars) {
                    output += c;
                }
                output += "\n";
            }
            return output;
        }
    }
}