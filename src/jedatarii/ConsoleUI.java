package jedatarii;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ConsoleUI extends javax.swing.JFrame {
    
    private String lastButtonName = null;
    private boolean textFieldActivated = false, buttonActivated = false;
    private int x_0, y_0, x_1, y_1, enter, backspace;
    private LCD lcd = null;
    private Market<String> buttonMarket = new Market<>(), textFieldMarket = new Market<>();
    
    /**
     * Creates new form ConsoleUI
     */
    public ConsoleUI() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code <strike>(unless you know what you're doing)</strike>.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        
        contentPane = new DrawingPanel(); 
        scrollPane = new JScrollPane();
        textArea = new JTextArea(Integer.MAX_VALUE, Integer.MAX_VALUE);
        textField = new JTextField();
        buttons = new JButton[4];
        
        contentPane.setFocusable(true);

        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Atarii Console");
        
        textArea.setFont(new Font("Consolas", 0, 13)); // NOI18N
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        textField.setFont(new Font("Consolas", 0, 12)); // NOI18N
        textField.setPreferredSize(new Dimension(430, 25));
        textField.addActionListener(this::textFieldActivated);
        textField.addActionListener((ActionEvent e) -> textFieldActivated = true);

        for(int i = 0;i<buttons.length;i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Consolas", 0, 12)); // NOI18N
            buttons[i].setText("button" + i);
            buttons[i].addActionListener(this::buttonPressed);
            buttons[i].setVisible(false);
        }
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        GroupLayout.SequentialGroup sGroup = layout.createSequentialGroup();
        for(int i = 0;i<buttons.length;i++) {
            sGroup.addComponent(buttons[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            if(i != buttons.length-1) {
                sGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
            } else {
                sGroup.addGap(0, 0, Short.MAX_VALUE);
            }
        }
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addComponent(textField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sGroup))
                .addContainerGap())
        );
        
        GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        for (JButton button:buttons) {
            pGroup.addComponent(button, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pGroup)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>
    
    private void buttonPressed(ActionEvent ae) {
        lastButtonName = ((JButton)(ae.getSource())).getText();
        buttonActivated = true;
    }
    
    private void textFieldActivated(ActionEvent ae) {
        textFieldActivated = true;
        while(textFieldActivated) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Atarii.showException(ex);
            }
        }
        ((JTextField)(ae.getSource())).setText("");
    }
    
    public String getLastButtonPress() {
        return lastButtonName;
    }
    
    public String getNextButtonPress() {
        while(!buttonActivated) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Atarii.showException(ex);
            }
        }
        buttonActivated = false;
        return lastButtonName;
    }
    
    public String getTextFieldText() {
        while (!textFieldActivated) {            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Atarii.showException(ex);
            }
        }
        textFieldActivated = false;
        return textField.getText();
    }
    
    public void println(String s) {
        textArea.setText(textArea.getText()+s+"\n");
    }
    
    public void println() {
        textArea.setText(textArea.getText()+"\n");
    }
    
    public void print(String s) {
        textArea.setText(textArea.getText()+s);
    }
    
    public void clear() {
        textArea.setText("");
    }
    
    public void displayTextField() {
        for(JButton button:buttons) {
            button.setVisible(false);
        }
        textField.setVisible(true);
    }
    
    public void displayButtons(String[] ss) {
        textField.setVisible(false);
        for(int i = 0;i<buttons.length;i++) {
            if(i >= ss.length) buttons[i].setVisible(false);
            else {
                buttons[i].setText(ss[i]);
                buttons[i].setVisible(true);
            }
        }
    }
    
    public void displayNothing() {
        for(JButton button:buttons) {
            button.setVisible(false);
        }
        textField.setVisible(false);
    }
    
    public static ConsoleUI run() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.LookAndFeelInfo[] installedLookAndFeels=UIManager.getInstalledLookAndFeels();
            for (UIManager.LookAndFeelInfo installedLookAndFeel : installedLookAndFeels) {
                if ("Nimbus".equals(installedLookAndFeel.getName())) {
                   UIManager.setLookAndFeel(installedLookAndFeel.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Atarii.showException(ex);
        }
        //</editor-fold>

        /* Create and display the form */
        ConsoleUI consoleUI = new ConsoleUI();
        EventQueue.invokeLater(() -> {
            consoleUI.setResizable(false);
            consoleUI.setVisible(true);
        });
        return consoleUI;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private JButton[] buttons;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTextField textField;
    private DrawingPanel contentPane;
    //</editor-fold>
    
    class DrawingPanel extends JPanel {
        private final HashMap<String, Integer> keyboardCommands;
        {
            keyboardCommands = new HashMap<>();
            for(char c = 'A';c<='Z';c++) {
                keyboardCommands.put("" + c, 0);
            }
            for(char c = '0';c<='9';c++) {
                keyboardCommands.put("" + c, 0);
            }
        }
        private HashMap<String, Integer> keyMap;
        
        private boolean arrowKeyListening = false;
        private final String[] arrowKeyCommands = {
            "UP","DOWN","LEFT","RIGHT",
            "W","A","S","D"
        };
        private Market<Integer>[] arrowKeyMarket = new Market[] {
            new Market(), new Market(), new Market(), new Market()
        };
        private ActionListener arrowKeyAction = (ActionEvent ae) -> {
            String command = (String) ae.getActionCommand();
            if (command.equals(arrowKeyCommands[0])) {
                y_0++;
                if(arrowKeyListening) arrowKeyMarket[1].put(y_0);
            } else if (command.equals(arrowKeyCommands[1])) {
                y_0--;
                if(arrowKeyListening) arrowKeyMarket[1].put(y_0);
            } else if (command.equals(arrowKeyCommands[2])) {
                x_0--;
                if(arrowKeyListening) arrowKeyMarket[0].put(x_0);
            } else if (command.equals(arrowKeyCommands[3])) {
                x_0++;
                if(arrowKeyListening) arrowKeyMarket[0].put(x_0);
            } else if (command.equals(arrowKeyCommands[4])) {
                y_1++;
                if(arrowKeyListening) arrowKeyMarket[3].put(y_1);
            } else if (command.equals(arrowKeyCommands[5])) {
                x_1--;
                if(arrowKeyListening) arrowKeyMarket[2].put(x_1);
            } else if (command.equals(arrowKeyCommands[6])) {
                y_1--;
                if(arrowKeyListening) arrowKeyMarket[3].put(y_1);
            } else if (command.equals(arrowKeyCommands[7])) {
                x_1++;
                if(arrowKeyListening) arrowKeyMarket[2].put(x_1);
            }
            
            repaint();
        };
        
        private boolean enterKeyListening = false;
        private Market<String> enterKeyMarket = new Market<>();
        private ActionListener enterAction = (ActionEvent ae) -> {
            String command = (String) ae.getActionCommand();
            if(command.equals("ENTER")) {
                enter++;
            }
            if(enterKeyListening) enterKeyMarket.put("Go!");
        };
        
        private boolean backspaceListening = false;
        private Market<String> backspaceKeyMarket = new Market<>();
        private ActionListener backspaceAction = (ActionEvent ae) -> {
            String command = (String) ae.getActionCommand();
            if(command.equals("BACK_SPACE")) {
                backspace++;
            }
            if(backspaceListening) backspaceKeyMarket.put("Back");
        };
        
        private boolean keyBoardListening = false;
        private Market<Integer>[] keyBoardMarket = new Market[keyboardCommands.keySet().size()];
        private ActionListener keyBoardAction = (ActionEvent ae) -> {
            String command = (String) ae.getActionCommand();
            if(command.length() == 1) {
                char onlyChar = command.charAt(0);
                if((onlyChar >= 'A' && onlyChar <= 'Z')||(onlyChar >= '0' && onlyChar <= '9')) {
                    keyboardCommands.put(command, keyboardCommands.get(command)+1);
                    if(keyBoardListening) keyBoardMarket[onlyChar-'A'].put(keyboardCommands.get(command));
                }
            }
        };
        
        public DrawingPanel() {
            keyMap = new HashMap<>();
            x_0 = 0;
            y_0 = 0;
            x_1 = 0;
            y_1 = 0;
            enter = 0;
            for(int i = 0;i<keyBoardMarket.length;i++) {
                keyBoardMarket[i] = new Market();
            }

            for(String command:arrowKeyCommands) {
                super.registerKeyboardAction(arrowKeyAction, command, KeyStroke.getKeyStroke(command), JComponent.WHEN_IN_FOCUSED_WINDOW);
            }
            for(String command:keyboardCommands.keySet()) {
                super.registerKeyboardAction(keyBoardAction, command, KeyStroke.getKeyStroke(command), JComponent.WHEN_IN_FOCUSED_WINDOW);
            }
            super.registerKeyboardAction(enterAction, "ENTER", KeyStroke.getKeyStroke("ENTER"), JComponent.WHEN_IN_FOCUSED_WINDOW);
            super.registerKeyboardAction(backspaceAction, "BACK_SPACE", KeyStroke.getKeyStroke("BACK_SPACE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
        }

        public HashMap<String, Integer> getKeyboardCommands() {
            return keyboardCommands;
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("X_1 : " + x_0 + " and Y_1 : " + y_0 + "\tX_2 : " + x_1 + " and Y_2 : " + y_1 + "\tEnter : " + enter + "\tBackspace : " + backspace);
            for(char c = 'A';c<='Z';c++) {
                System.out.print(c + ":" + keyboardCommands.get("" + c) + " ");
            }
            System.out.println();
            for(char c = '0';c<='9';c++) {
                System.out.print(c + ":" + keyboardCommands.get("" + c) + " ");
            }
            System.out.println();
        }
    }
    
    public String getNextEnter() throws InterruptedException {
        int prevEnter = enter;
        while(prevEnter == enter) {
            Thread.sleep(100);
        }
        return "Go!";
    }
    
    public String getNextButtonEnter() throws InterruptedException {
        int prevEnter = enter;
        do {
            if(prevEnter != enter) {
                return "Go!";
            }
            if(buttonActivated) {
                buttonActivated = false;
                return lastButtonName;
            }
            Thread.sleep(100);
        } while(true);
    }
    
    public Direction getNextDirection(int i) throws InterruptedException {
        if(i == 0) {
            int prevX_0 = x_0;
            int prevY_0 = y_0;
            do {
                if(prevX_0 != x_0) {
                    if(prevX_0 < x_0) {
                        return Direction.EAST;
                    } else {
                        return Direction.WEST;
                    }
                } else if(prevY_0 != y_0) {
                    if(prevY_0 < y_0) {
                        return Direction.NORTH;
                    } else {
                        return Direction.SOUTH;
                    }
                }
                Thread.sleep(100);
            } while(true);
        } else if(i == 1) {
            int prevX_1 = x_1;
            int prevY_1 = y_1;
            do {
                if(prevX_1 != x_1) {
                    if(prevX_1 < x_1) {
                        return Direction.EAST;
                    } else {
                        return Direction.WEST;
                    }
                } else if(prevY_1 != y_1) {
                    if(prevY_1 < y_1) {
                        return Direction.NORTH;
                    } else {
                        return Direction.SOUTH;
                    }
                }
                Thread.sleep(100);
            } while(true);
        }
        return null;
    }
    
    public Direction getNextLeftRightEnter() throws InterruptedException {
        int prevX_0 = x_0, prevEnter = enter;
        do {
            if(prevX_0 != x_0) {
                if(prevX_0 < x_0) {
                    return Direction.EAST;
                } else {
                    return Direction.WEST;
                }
            }
            if(prevEnter != enter) return Direction.SOUTH;
            Thread.sleep(100);
        } while(true);
    }
    
    public String getNextGamePageNavigator(int i) throws InterruptedException {
        int prevBack = backspace, prevEnter = enter;
        if(i == 0) {
            int prevX_0 = x_0;
            do {
                if(prevX_0 != x_0) {
                    if(prevX_0 < x_0) {
                        return ">";
                    } else {
                        return "<";
                    }
                }
                if(prevBack != backspace) {
                    return "Back";
                }
                if(prevEnter != enter) {
                    return "Go!";
                }
                if(buttonActivated) {
                    buttonActivated = false;
                    return lastButtonName;
                }
                Thread.sleep(100);
            } while(true);
        } else if(i == 1) {
            int prevX_1 = x_1;
            do {
                if(prevX_1 != x_1) {
                    if(prevX_1 < x_1) {
                        return ">";
                    } else {
                        return "<";
                    }
                }
                if(prevBack != backspace) {
                    return "Back";
                }
                if(prevEnter != enter) {
                    return "Go!";
                }
                if(buttonActivated) {
                    buttonActivated = false;
                    return lastButtonName;
                }
                Thread.sleep(100);
            } while(true);
        }
        return null;
    }
    
    public String getNextKeyboardPress() throws InterruptedException {
        HashMap<String, Integer> copy = new HashMap<>(contentPane.getKeyboardCommands());
        do {
            if(!copy.equals(contentPane.getKeyboardCommands())) {
                for(String s:copy.keySet()) {
                    if(!copy.get(s).equals(contentPane.getKeyboardCommands().get(s))) return s;
                }
            }
            Thread.sleep(10);
        } while(true);
    }

    public void setLCD(LCD lcd1) {
        lcd = lcd1;
        lcd.addActionListener((ActionEvent e) -> {
            clear();
            print(lcd.toString());
        });
    }
    
    public void removeLCD() {
        lcd = null;
    }
}