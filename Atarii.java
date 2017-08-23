package jedatarii;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Atarii {
    public static void main(String[] args) {
        final GamePage[] singlePlayer = new GamePage[]{
            new GamePage("Maze", "Generates a random maze that you can solve. Try solving it!\n"
                    + "Comes with easy, medium, and hard modes."), 
            new GamePage("Connect 4", "Play against a computer! It\'s quite hard, though.\n"
                    + "Implemented an AI that plays perfectly."), 
            new GamePage("Wild West Showdown", "Go in a shootoff against a computer! With a delay, of course.\n"
                    + "You can choose a varying degree of difficulty.")
        };
        final GamePage[] dualPlayer = new GamePage[]{
            new GamePage("Connect 4", "Play Connect 4 with a friend.")
        };
        String jedAtarii = "**************\n* JED ATARII *\n**************\n";
        try {
            ConsoleUI console = ConsoleUI.run();
            
            boolean _continue = true, backwards = true;
            do {
                if(backwards) {
                    console.clear();
                    console.println(jedAtarii);
                }
                console.print("What Player Mode would you like?");
                console.displayButtons(new String[]{"Single Player", "2-Player", "Exit Atarii"});
                String reply = console.getNextButtonPress();
                console.clear();
                console.println(jedAtarii);
                String game;
                if(null != reply) switch (reply) {
                    case "Single Player":
                        console.displayButtons(new String[]{"Maze", "Connect 4", "Wild West Showdown", "Back"});
                        console.println("What game do you want to play? (Press the button)");
                        game = console.getNextButtonPress();
                        switch (game) {
                            case "Maze":
                                console.clear();
                                Maze.run(console);
                                backwards = false;
                                break;
                            case "Connect 4":
                                console.clear();
                                Connect4.run(console, 1);
                                backwards = false;
                                break;
                            case "Wild West Showdown":
                                console.clear();
                                WildWestShowdown.run(console);
                                backwards = false;
                                break;
                            case "Back":
                                backwards = true;
                                break;
                        }
                        break;
                    case "2-Player":
                        console.displayButtons(new String[]{"Connect 4", "Back"});
                        console.println("What game do you want to play? (Press the button)");
                        game = console.getNextButtonPress();
                        switch(game) {
                            case "Connect 4":
                                console.clear();
                                Connect4.run(console, 2);
                                backwards = false;
                                break;
                            case "Back":
                                backwards = true;
                                break;
                        }
                        break;
                    case "Exit Atarii":
                        _continue = false;
                        console.dispose();
                        break;
                }
            } while (_continue);
        } catch (Exception ex) {
            showException(ex);
        }
    }
    
    public static void showException(Exception ex) {
        System.out.println(ex.getMessage());
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "An error occured:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        frame.dispose();
        System.exit(0);
    }
}