package adventuregame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class runs the GUI and acts an interface 
 * between the back end and the front end
 * Contains the private classes Painter and KeyBoard.
 * @author andy
 *
 */
public class GUIDriver {
    private JFrame frame;
    private JLabel keyLabel = new JLabel("Welcome");
    private KeyBoard keyboard;
    private Painter painter;
    private GameDriver gameDriver;

    /**
     * This is the main class that houses the
     * KeyListener and painter.
     * 
     * How it works is that the main components are
     * the frame, the keyboard, and the painter
     * 
     * The frame is the window in which the application is launched in
     * the keyboard reads in the inputs from the command line
     * at the bottom of the screen
     * 
     * and the most important part is the Painter, which will read the state of
     * the program
     * and then paint on the frame how the program will look.
     * Each component has to work in its own object.
     */
    public GUIDriver() {
        gameDriver = new GameDriver();
        frame = new JFrame();
        keyboard = new KeyBoard();
        painter = new Painter("foo");

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);

        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        frame.add(keyLabel, BorderLayout.NORTH);

        frame.add(keyboard.keyText, BorderLayout.SOUTH);

        frame.add(painter);

        frame.setVisible(true);
    }


    /**
     * This method is the method that currently changes the state of the
     * painter. Very important.
     * 
     * @param in the string to set to the label
     */
    public void setLabelText(String in) {
        keyLabel.setText(in);
        painter.repaint();

    }
    
    /**
     * Method called by the keyboard listener
     * when the client presses a directional key
     * @param dir Direction enum based on the keyboard key pressed
     */
    private void movePlayerOnMap(Direction dir) { 
        if (gameDriver.getPlayer().move(dir)) { 
            keyLabel.setText(""); // Clears the there's something in da way
            painter.repaint();
        } else { 
            // collision check occured, check the surroundings
            // and print the interactions!
            keyLabel.setText("There's something in your way");
        }
        
    }
    
    private void processInputText(String in) { 
        if (in.equals("y") || in.equals("Y")) { 
            gameDriver.toggleYesNo(true);
        }
    }


    public static void main(String[] args) {
        GUIDriver instance = new GUIDriver();
    }
    
//------------------------KeyBoard---------------------------------------\\

    /**
     * This class implements the keylistener
     * and makes a keyText field for input
     * 
     * @author andy
     *
     */
    private class KeyBoard implements KeyListener {
        private JTextField keyText;

        public KeyBoard() {
            keyText = new JTextField(80);
            keyText.addKeyListener(this);
        }


        /**
         * When a button is pressed it's sent to the this method
         * The label in the main class is then changed
         * to whatever the value of the text field is
         * whenever the user presses enter.
         * The string the user typed is also sent back to the main
         * method.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_ENTER) {
                processInputText(keyText.getText());
                keyText.setText("");
            }
            

        }


        @Override
        public void keyTyped(KeyEvent e) {
            //if (keyText.getText().equals("")) {
                
            //}

        }


        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    movePlayerOnMap(Direction.UP);
                    break;
                case KeyEvent.VK_RIGHT:
                    movePlayerOnMap(Direction.RIGHT);                    
                    break;
                case KeyEvent.VK_DOWN:
                    movePlayerOnMap(Direction.DOWN);                    
                    break;
                case KeyEvent.VK_LEFT:
                    movePlayerOnMap(Direction.LEFT);                    
                    break;

            }
        }

    }


  //--------------------------Painter----------------------------------------\\
    
    /**
     * A class for painting on the canvas
     * 
     * The class will eventually work as follows:
     * There will be fields for each of the data attributes of the program:
     * Map, inventory, health, etc
     * These will be represented by strings.
     * The strings will be modified, and everytime the player makes an action
     * the corresponding strings will change and then the program will repaint.
     * 
     */
    private class Painter extends JPanel {

        private static final long serialVersionUID = -8643343047731761745L;
        //public String useString = "";
        //public char[][] charGrid;
        public String[] mapStrings;
        
        
        public Painter(String in) {
            //useString = in;
            mapStrings = new String[5];
            makeMapStrings();
        }


        /**
         * This method is the one in which the program uses to re paint
         * This is called by painter.repaint in the GUI runner class
         * It can read the state of the Painter's fields
         * and then paint using them
         * 
         * @param g
         *            is graphic in which the method will paint on.
         */
        @Override
        public void paintComponent(Graphics g) {
            makeMapStrings();
            super.paintComponent(g);
            
            g.drawString(mapStrings[0], 100, 100);
            g.drawString(mapStrings[1], 100, 110);
            g.drawString(mapStrings[2], 100, 120);
            g.drawString(mapStrings[3], 100, 130);
            g.drawString(mapStrings[4], 100, 140);
        }
        
        /**
         * makes the strings for use for drawing the strings for the map.
         */
        private void makeMapStrings() {
            gameDriver.refresh();
            char printedChar;
            for (int i = 0; i < gameDriver.getCharGrid()[0].length; i++) {
                mapStrings[i] = "";
                for (int j = 0; j < gameDriver.getCharGrid()[0].length; j++) {
                    printedChar = gameDriver.getCharGrid()[i][j];
                    if (printedChar == 0) {
                        printedChar = '.';
                    }
                    
                    mapStrings[i] += printedChar + " ";
                }
            }
        }

    }
    

}
