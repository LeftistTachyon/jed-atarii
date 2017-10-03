package jedatarii;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Atarii {
    public static void main(String[] args) {
        final String errorFile = "JedAtariiErrors.err";
        final WriterFile wf = new WriterFile(errorFile);
        final GamePage[] singlePlayer = new GamePage[]{
            new GamePage("Maze", "Generates a random maze that you can solve. Try solving it!\n"
                    + "Comes with easy, medium, and hard modes.\n"), 
            new GamePage("Connect 4", "Play against a computer! It\'s quite hard, though.\n"
                    + "Implemented an AI that plays perfectly."), 
            new GamePage("Wild West Showdown", "Go in a shootoff against a computer! With a delay, of course\n"
                    + "You can choose a varying degree of difficulty."),
            new GamePage("Pong", "Play Pong against a computer.\n"
                    + "Implemented a flawed CPU."), 
            new GamePage("Typist", "Type as fast as you can!\n"
                    + "A typing game that records your typing speed.")
        };
        final GamePage[] dualPlayer = new GamePage[]{
            new GamePage("Connect 4", "Play Connect 4 with a friend.")
        };
        String jedAtarii = "**************\n* JED ATARII *\n**************\n";
        try {
            if(!wf.fileExists()) wf.createFile();
            ConsoleUI console = ConsoleUI.run();
            boolean _continue = true, backwards = true, justFinishedGame = false;
            do {
                if(backwards) {
                    console.clear();
                    console.println(jedAtarii);
                }
                if(justFinishedGame) {
                    console.displayNothing();
                    Thread.sleep(3000);
                    justFinishedGame = false;
                    console.clear();
                    console.println(jedAtarii);
                }
                console.print("What Player Mode would you like?");
                console.displayButtons(new String[]{"Single Player", "2-Player", "Exit Atarii"});
                String reply = console.getNextButtonPress();
                console.clear();
                String chosen;
                int page = 0;
                if(null != reply) switch (reply) {
                    case "Single Player":
                        do {
                            console.clear();
                            console.println(jedAtarii);
                            console.println(singlePlayer[page].getName().toUpperCase()+"\n");
                            console.println(singlePlayer[page].getDescription());
                            if(singlePlayer.length == 1) {
                                console.displayButtons(singlePlayer[page].getSoloButtonLabels());
                            } else if(page == 0) {
                                console.displayButtons(singlePlayer[page].getFirstButtonLabels());
                            } else if(page == singlePlayer.length-1) {
                                console.displayButtons(singlePlayer[page].getLastButtonLabels());
                            } else {
                                console.displayButtons(singlePlayer[page].getStandardButtonLabels());
                            }
                            chosen = console.getNextGamePageNavigator(0);
                            switch (chosen) {
                                case "Go!":
                                case "Play":
                                    switch (singlePlayer[page].getName()) {
                                        case "Maze":
                                            console.clear();
                                            Maze.run(console);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                        case "Connect 4":
                                            console.clear();
                                            Connect4.run(console, 1);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                        case "Wild West Showdown":
                                            console.clear();
                                            WildWestShowdown.run(console);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                        case "Pong":
                                            console.clear();
                                            Pong.run(console);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                        case "Typist":
                                            console.clear();
                                            Typist.run(console);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                    }
                                    break;
                                case "Back":
                                    backwards = true;
                                    break;
                                case ">":
                                    backwards = false;
                                    if(page != singlePlayer.length-1) page++;
                                    break;
                                case "<":
                                    backwards = false;
                                    if(page != 0) page--;
                                    break;
                            }
                            if(backwards || justFinishedGame) break;
                        } while(!chosen.equals(singlePlayer[page].getName()));
                        break;
                    case "2-Player":
                        do {
                            console.clear();
                            console.println(jedAtarii);
                            console.println(dualPlayer[page].getName().toUpperCase()+"\n");
                            console.println(dualPlayer[page].getDescription());
                            if(dualPlayer.length == 1) {
                                console.displayButtons(dualPlayer[page].getSoloButtonLabels());
                            } else if(page == 0) {
                                console.displayButtons(dualPlayer[page].getFirstButtonLabels());
                            } else if(page == dualPlayer.length-1) {
                                console.displayButtons(dualPlayer[page].getLastButtonLabels());
                            } else {
                                console.displayButtons(dualPlayer[page].getStandardButtonLabels());
                            }
                            chosen = console.getNextGamePageNavigator(0);
                            switch (chosen) {
                                case "Go!":
                                case "Play":
                                    switch (dualPlayer[page].getName()) {
                                        case "Connect 4":
                                            console.clear();
                                            Connect4.run(console, 2);
                                            justFinishedGame = true;
                                            backwards = false;
                                            break;
                                    }
                                    break;
                                case "Back":
                                    backwards = true;
                                    break;
                                case ">":
                                    backwards = false;
                                    if(page != singlePlayer.length-1) page++;
                                    break;
                                case "<":
                                    backwards = false;
                                    if(page != 0) page--;
                                    break;
                            }
                        if(backwards || justFinishedGame) break;
                        } while(!chosen.equals(dualPlayer[page].getName()));
                        break;
                    case "Exit Atarii":
                        _continue = false;
                        console.displayTextField();
                        Thread.sleep(3000);
                        console.dispose();
                        break;
                }
            } while (_continue);
        } catch (Exception ex) {
            try {
                saveException(wf, ex);
            } catch (IOException io) {
                showException(io);
            } finally {
                System.out.println(ex.toString());
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for(StackTraceElement ste:stackTrace) {
                    System.out.println("\t" + ste.toString());
                }
            }
            showException(ex);
        }
    }
    
    private static void saveException(WriterFile wf, Exception e) throws IOException {
        ArrayList<String> toWrite = new ArrayList<>();
        String[] readFileLines = wf.readFileLines();
        toWrite.addAll(Arrays.asList(readFileLines));
        int exceptions = 0;
        for(String s:readFileLines) {
            if(s.contains("EXCEPTION")) {
                int fromIndex = 0;
                while(s.indexOf("EXCEPTION", fromIndex) != -1)
                    exceptions++;
            }
        }
        toWrite.add("EXCEPTION " + (exceptions+1));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a - MM/dd/YYYY");
        String formattedTime = dtf.format(now);
        toWrite.add(formattedTime);
        toWrite.add(e.toString());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for(StackTraceElement ste:stackTrace) {
            toWrite.add("\t" + ste.toString());
        }
        wf.loopWriteToFile(toWrite);
    }
    
    public static void showException(Exception ex) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "An error occured:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        frame.dispose();
        System.exit(0);
    }
}