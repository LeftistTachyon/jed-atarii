package jedatarii;

public class Pong {
    private ConsoleUI console;
    private LCD lcd;
    
    {
        lcd = new LCD();
    }
    
    private Pong(){}
    
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
        System.out.println("Line 25");
        console.setLCD(lcd);
        System.out.println("Line 27");
        internalGame();
    }
    
    private void internalGame() {
        
    }
}