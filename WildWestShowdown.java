package jedatarii;

public class WildWestShowdown {
    private ConsoleUI console;
    private final Person[] opponents = new Person[]{
        new Person(
                spaces(29) + "Joe" + "\n" +
                "\n" + 
                spaces(30) +  "O" + "\n" +
                spaces(29) + "/|\\\n" + 
                spaces(29) + "/ \\\n" + 
                "\n" + 
                spaces(21) + "Your average Joe." + 
                "\n" + 
                spaces(13) + "Average reaction time: 1.2 seconds"
        , "Joe", 1.2)
    };
    
    private WildWestShowdown(){}
    
    public static void run(ConsoleUI c) throws InterruptedException {
        c.displayButtons(new String[]{"Go!"});
        c.println("INSTRUCTIONS:\n");
        c.println("When you see the words \"FIRE!\" appear, click on the button below as fast as possible.\n"
                + "If you click before \"FIRE!\" appears, your shot will be forfeited.\n"
                + "Your goal is to shoot before your opponent does! Good luck!\n");
        c.println("Click the button below when you\'re ready.");
        c.getNextButtonPress();
        (new WildWestShowdown()).internalRun(c);
    }
    
    public void internalRun(ConsoleUI c) throws InterruptedException {
        console = c;
        console.displayButtons(new String[]{"<", "Fight!", ">"});
        int current = 0;
        Person activePerson = null;
        do {
            console.clear();
            console.println("Who do you want to fight?\n");
            console.println(opponents[current].description);
            String feedback = console.getNextButtonPress();
            switch(feedback) {
                case "<":
                    if(current != 0) current--;
                    break;
                case ">":
                    if(current != opponents.length-1) current++;
                    break;
                case "Fight!":
                    activePerson = opponents[current];
                    break;
            }
        } while(activePerson == null);
        fight(activePerson);
    }
    
    private void fight(Person activePerson) throws InterruptedException {
        int winning = 0;
        for(int i = 1;i<6;i++) {
            console.displayButtons(new String[]{"FIRE!"});
            console.clear();
            double toBeReaction = activePerson.averageReaction * ((Math.random()*0.2) + 1);
            console.print(spaces(26) + "Round " + i + "/5\n" +
                          spaces(16) + "____   ____  ___  ____   _  _\n" +
                          spaces(16) + "|| \\\\ ||    // \\\\ || \\\\  \\\\//\n" +
                          spaces(16) + "||_// ||==  ||=|| ||  ))  )/ \n" +
                          spaces(16) + "|| \\\\ ||___ || || ||_//  //  ");
            Thread.sleep(1000);
            console.clear();
            console.print(spaces(26) + "Round " + i + "/5");
            Thread.sleep((long) (Math.random()*10000));
            boolean earlyFire = "FIRE!".equals(console.getLastButtonPress());
            console.print(spaces(26) + "Round " + i + "/5\n" +
                          spaces(19) + " ____ __ ____   ____ __\n" +
                          spaces(19) + "||    || || \\\\ ||    ||\n" +
                          spaces(19) + "||==  || ||_// ||==  ||\n" +
                          spaces(19) + "||    || || \\\\ ||___ ..");
            long total;
            if(earlyFire) {
                total = Long.MAX_VALUE;
                Thread.sleep((long) (toBeReaction*1000));
            } else {
                long start = System.nanoTime();
                console.getNextButtonPress();
                long end = System.nanoTime();
                total = end - start;
            }
            double totalSeconds = total/1000000000.0;
            console.displayButtons(new String[]{"Continue"});
            console.clear();
            if(totalSeconds < toBeReaction) {
                console.println(spaces(26) + "Round " + i + "/5\n" +
                                spaces(26) + "You Win!\n");
                winning++;
            } else if(totalSeconds > toBeReaction){
                console.println(spaces(26) + "Round " + i + "/5\n" +
                                spaces(26) + "You Lose!\n");
                winning--;
            } else {
               console.println(spaces(26) + "Round " + i + "/5\n" +
                               spaces(25) + "IT'S A TIE!\n" + 
                               spaces(15) + "YOU HAVE DONE THE IMPOSSIBLE!!!\n");
            }
            console.println(spaces(27) + "Speeds:\n" + 
                            spaces(22) + "You: " + roundThousandths(totalSeconds) + " seconds\n" + 
                            spaces(22) + activePerson.name + ": " + roundThousandths(toBeReaction) + " seconds");
            console.getNextButtonPress();
        }
        if(winning > 0) {
            console.println(spaces(8) + " _  _   ___   __ __    __    __ __ __  __ __\n" +
                            spaces(8) + " \\\\//  // \\\\  || ||    ||    || || ||\\ || ||\n" +
                            spaces(8) + "  )/  ((   )) || ||    \\\\ /\\ // || ||\\\\|| ||\n" +
                            spaces(8) + " //    \\\\_//  \\\\_//     \\V/\\V/  || || \\|| ..\n");
        } else if(winning < 0) {
            console.println(spaces(2) + " _  _   ___   __ __    __      ___    __   ____         \n" +
                            spaces(2) + " \\\\//  // \\\\  || ||    ||     // \\\\  (( \\ ||            \n" +
                            spaces(2) + "  )/  ((   )) || ||    ||    ((   ))  \\\\  ||==          \n" +
                            spaces(2) + " //    \\\\_//  \\\\_//    ||__|  \\\\_//  \\_)) ||___ || || ||\n");
        } else {
            console.println(spaces(8) + " _  _   ___   __ __    ______ __  ____ ____  \n" +
                            spaces(8) + " \\\\//  // \\\\  || ||    | || | || ||    || \\\\ \n" +
                            spaces(8) + "  )/  ((   )) || ||      ||   || ||==  ||  ))\n" +
                            spaces(8) + " //    \\\\_//  \\\\_//      ||   || ||___ ||_// \n");
        }
    }
    
    private double roundThousandths(double d) {
        int thousandTimes = (int) Math.round(d*1000);
        return thousandTimes/1000.0;
    }
    
    private String spaces(int spaces) {
        String output = "";
        for(int i = 0;i<spaces;i++) {
            output += " ";
        }
        return output;
    }
    
    class Person {
        private String description, name;
        private double averageReaction;
        
        public Person(String description, String name, double averageReaction) {
            this.description = description;
            this.name = name;
            this.averageReaction = averageReaction;
        }
        /*
        public String getDescription() {
            return description;
        }

        public double getAverageReaction() {
            return averageReaction;
        }*/
    }
}
