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
        console.setLCD(lcd);
        internalGame();
    }
    
    // "\uD83C\uDF11" is 🌑
    private void internalGame() {
        lcd.fillRect(1, 0, 57, 0, '_');
        lcd.fillRect(1, 16, 57, 16, '_');
        lcd.fillRect(1, 1, 1, 4, '|');
        lcd.fillRect(57, 1, 57, 4, '|');
        //
    }
}