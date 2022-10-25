package UIDesign;
import FileExplorer.Main;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame {
    private final Color LEFTPANECOLOR = Color.BLACK;
    private final Color TOPPANECOLOR = Color.BLUE;
    private final Color BOTTOMPANECOLOR = Color.ORANGE;
    JPanel leftPane;
    JPanel topPane;
    JTextArea bottomPane;
    JTextArea textBox;
    Main explorer;
    public Window(Main main) {
        explorer = main;

        /** Creating Condensed View Panel = Panel 3 */
        leftPane = new JPanel();
        leftPane.setBackground(LEFTPANECOLOR);

        /** Creating Expanded Visualizer = Panel 1 */
        topPane = new JPanel();
        topPane.setBackground(TOPPANECOLOR);

        /** Creating Terminal view = Panel 2 */
        bottomPane = new JTextArea();
        bottomPane.setBackground(BOTTOMPANECOLOR);
        bottomPane.setLineWrap(true);
        bottomPane.setEditable(false);
        bottomPane.insert(explorer.prompt, bottomPane.getCaretPosition());


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
        textBox.addKeyListener(new KeyListener() {
            String command = "";
            int startPressed = 0;
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
                if (e.getExtendedKeyCode() != 10 && e.getExtendedKeyCode() != 8) {
                    if (cursorIndex == command.length()) {
                        command += e.getKeyChar();
                    } else {
                        command = command.substring(0, cursorIndex) + e.getKeyChar() + command.substring(cursorIndex);
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {  /** Print the user inputted command to the console and then print the output of that command */
                    outCommandToTextArea(command);
                    command = "";
                    textBox.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {  /** Delete last user typed character */
                    int cursorIndex = textBox.getCaretPosition();
                    if (cursorIndex >= 0) {
                        command = command.substring(0, cursorIndex) + command.substring(cursorIndex + 1);
                    } else {
                        command = "";
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
                                |     |                                        |
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

    private void outCommandToTextArea(String command) {
        bottomPane.insert(command + "\n", bottomPane.getText().length());
        String s = explorer.runCommand(command);
        bottomPane.insert(s + "\n", bottomPane.getText().length());
        bottomPane.insert(explorer.prompt, bottomPane.getText().length());
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
 */