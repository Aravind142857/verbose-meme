package UIDesign;
import FileExplorer.ColorPane;
import FileExplorer.Main;
import FileExplorer.MyListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.constant.MethodTypeDesc;

public class Window extends JFrame {
    private final Color LEFT_PANE_COLOR = Color.BLACK;
    private final Color TOP_PANE_COLOR = Color.BLUE;
    private final Color BOTTOM_PANE_BG_COLOR = Color.black;
    private final Color BOTTOM_PANE_FG_COLOR = Color.BLACK;
    public final Color DIRECTORY_COLOR = Color.BLUE;
    public final Color FILE_COLOR = Color.GREEN;
    public final Color HIDDEN_COLOR = Color.BLACK;
    JPanel leftPane;
    JPanel topPane;
    ColorPane bottomPane;
    JTextArea textBox;
    Main explorer;
    public Window(Main main) {
        explorer = main;

        /** Creating Condensed View Panel = Panel 3 */
        leftPane = new JPanel();
        leftPane.setBackground(LEFT_PANE_COLOR);

        /** Creating Expanded Visualizer = Panel 1 */
        topPane = new JPanel();
        topPane.setBackground(TOP_PANE_COLOR);

        /** Creating Terminal view = Panel 2 */
        bottomPane = new ColorPane();
        bottomPane.setBackground(BOTTOM_PANE_BG_COLOR);
        //bottomPane.setForeground(BOTTOMPANEFGCOLOR);
        //bottomPane.setContentType("text/html");
        //bottomPane.setLineWrap(true);
        bottomPane.setEditable(false);
        bottomPane.insert(explorer.getPrompt());


        /** Adding a ScrollPane to bottomPane */
        JScrollPane sp = new JScrollPane(bottomPane);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.getVerticalScrollBar().setBackground(Color.RED);
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GREEN;
            }
        });

        /** Creating Text Box */
        textBox = new JTextArea();
        textBox.setRows(1);
        textBox.addKeyListener(new MyListener() {
            StringBuilder command = new StringBuilder();
            int startPressed = 0;

            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e the event to be processed
             */
            @Override
            public void focusLost(FocusEvent e) {
                /*
                if(textBox.getText().trim().equals("")) {
                    textBox.setText("Enter a command here. Eg. pwd");
                    System.out.println("Focus lost");
                } else {
                     //ignore
                }
                */
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e the event to be processed
             */
            @Override
            public void focusGained(FocusEvent e) {
                /*if(textBox.getText().trim().equals("Enter a command here. Eg. pwd")) {
                    textBox.setText("");
                    System.out.println("Focus gained");
                } else {
                    // ignore
                }
                */
            }
            /**
             * Invoked when a key has been typed.
             * See the class description for {@link KeyEvent} for a definition of
             * a key typed event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyTyped(KeyEvent e) {
                int cursorIndex = textBox.getCaretPosition();
                //System.out.println(1 + " " + cursorIndex + " " + command);

                //System.out.println(cursorIndex);
                if (e.getExtendedKeyCode() != 10 && e.getExtendedKeyCode() != 8) {
                    if (cursorIndex == command.length()) {
                        command.append(e.getKeyChar());
                    } else {
                        command = new StringBuilder(command.substring(0, cursorIndex) + e.getKeyChar() + command.substring(cursorIndex));
                    }
                }
            }

            /**
             * Invoked when a key has been pressed.
             * See the class description for {@link KeyEvent} for a definition of
             * a key pressed event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyPressed(KeyEvent e) {
                startPressed = textBox.getCaretPosition();
            }

            /**
             * Invoked when a key has been released.
             * See the class description for {@link KeyEvent} for a definition of
             * a key released event.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println(2 + " " + textBox.getCaretPosition() + " " + startPressed + " " + command);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {  /** Print the user inputted command to the console and then print the output of that command */
                    //System.out.println("Command entered is: " + command + "...");
                    outCommandToTextArea(command.toString());
                    command = new StringBuilder();
                    textBox.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {  /** Delete last user typed character */
                    //int cursorIndex = textBox.getCaretPosition();
                    if (startPressed > 0) {
                        //command = command.substring(0, startPressed) + command.substring(cursorIndex + 1);
                        command.deleteCharAt(startPressed - 1);
                    } else {
                        //command = "";
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_TAB) { /** Auto-complete */
                    // if multiple possible files then print them, else change the halfcompleted user input file to file.
                    // command.replace(input, file);
                }
            }

        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /**
                                ________________________________________________
                                |X____|____________FILEX_WINDOW_1______________|
                                |     |__-path-__|                             |
                                |     |                                        |
                                |     |          Expanded File Visualizer      |
                                |     |                                        |
                                |     |                                        |
                                |     |________________________________________|
        Condensed File          |     |            Terminal Output             |
            Visualizer          |     |                                        |
                                |     |________________________________________|
                                |     |           Terminal Input               |
                                |_____|________________________________________|
         */

        /** Splitting Panes to match the desired view */
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        sp.setSize(525, 275);
        JSplitPane splitBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, textBox);
        splitBottom.setBorder(null);
        splitBottom.setDividerLocation(270);
        JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, splitBottom);
        split1.setDividerLocation(375);
        split1.setBorder(null);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, split1);
        split2.setDividerLocation(225);
        split2.setBorder(null);
        add(split2);

        /** JFrame Preferences */
        setSize(new Dimension(750, 750));
        setTitle("FileX window 1");

        /** Make JFrame visible */
        setVisible(true);
    }
    /*public void insert(String s) {
        try {
            Document doc = bottomPane.getDocument();
            doc.insertString(doc.getLength(), s, null);
        } catch(BadLocationException exc) {
            exc.printStackTrace();
        }
    }*/
    private void outCommandToTextArea(String command) {
        bottomPane.insert(command + "\n");
        String s = explorer.runCommand(command);
        bottomPane.insert(s + "\n");
        bottomPane.insert(explorer.getPrompt());
    }


    public static void main(String[] args) {
        Main explorer = new Main();
        Window window = new Window(explorer);

    }
}

/**
 * Holding backspace
 * CMD + Backspace
 * CMD + A
 * CTRL + C to stop code
 *
 */
/**
 *
 * At the end of ls -aR return the number of Files and number of directories found.
 * Instead of printing full path of directories, print abbreviated path and make hover over text option on right click to view path
 * Create New Listener: that implements FocusListener and KeyListener and add it to textBox.
 * public void focusLost(FocusEvent e) {
 *         if(txtFirstName.getText().trim().equals(""))
 *            txtFirstName.setText("Enter a command here. Eg. pwd");
 *         else
 *            //do nothing
 *     }
 * public void focusGained(FocusEvent e) {
 *         if(txtFirstName.getText().trim().equals("First name"))
 *            txtFirstName.setText("");
 *         else
 *            //do nothing
 *     }
 */