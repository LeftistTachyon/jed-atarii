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
    }
    
    class LCD {
        private char[][] lcdScreen = new char[59][17];
        
        public LCD() {}
        
        public void setChar(int x, int y, char c) {
            lcdScreen[x][y] = c;
        }
    }
}