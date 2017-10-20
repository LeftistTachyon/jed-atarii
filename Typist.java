package jedatarii;

public class Typist {
    private ConsoleUI console;
    
    private Typist(){}
    
    public static void run(ConsoleUI console) throws InterruptedException {
        console.displayNothing();
        for(int i = 0;i<10;i++) {
            console.println(console.getNextKeyboardPress());
        }
    }
}
