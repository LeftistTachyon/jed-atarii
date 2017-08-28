package jedatarii;

public class GamePage {
    private final String name, description;

    public GamePage(String n, String d) {
        name = n;
        description = d;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getFirstButtonLabels() {
        return new String[]{"Back", "Play", ">"};
    }

    public String[] getStandardButtonLabels() {
        return new String[]{"<", "Back", "Play", ">"};
    }

    public String[] getLastButtonLabels() {
        return new String[]{"<", "Back", "Play"};
    }
    
    public String[] getSoloButtonLabels() {
        return new String[]{"Back", "Play"};
    }

    @Override
    public String toString() {
        return "GamePage for " + name;
    }
}
