package jedatarii;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LCD {
    private char[][] lcdScreen = new char[17][58];
    private ArrayList<ActionListener> listeners;

    public LCD() {
        listeners = new ArrayList<>();
        for(char[] chars:lcdScreen) {
            for(char c:chars) {
                c = ' ';
            }
        }
    }

    public void setChar(int y, int x, char c) {
        lcdScreen[y][x] = c;
        fireActionPerformed();
    }

    public void fillRect(int x1, int y1, int x2, int y2, char c) {
        int bigX, smallX;
        if(x1>x2) {
            bigX = x1;
            smallX = x2;
        } else {
            bigX = x2;
            smallX = x1;
        }
        int bigY, smallY;
        if(y1>y2) {
            bigY = y1;
            smallY = y2;
        } else {
            bigY = y2;
            smallY = y1;
        }
        for(int y = smallY;y<bigY+1;y++) {
            for(int x = smallX;x<bigX+1;x++) {
                lcdScreen[y][x] = c;
            }
        }
        fireActionPerformed();
    }
    
    public void clearRect(int x1, int y1, int x2, int y2) {
        int bigX, smallX;
        if(x1>x2) {
            bigX = x1;
            smallX = x2;
        } else {
            bigX = x2;
            smallX = x1;
        }
        int bigY, smallY;
        if(y1>y2) {
            bigY = y1;
            smallY = y2;
        } else {
            bigY = y2;
            smallY = y1;
        }
        for(int y = smallY;y<bigY+1;y++) {
            for(int x = smallX;x<bigX+1;x++) {
                lcdScreen[y][x] = ' ';
            }
        }
        fireActionPerformed();
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
    
    public void addActionListener(ActionListener e) {
        listeners.add(e);
    }
    
    protected void fireActionPerformed() {
        ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "text changed");
        listeners.forEach((al) -> al.actionPerformed(e));
    }
}