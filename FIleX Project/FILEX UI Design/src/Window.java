

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;

public class Window extends JFrame {
    JPanel leftPane;
    JPanel topPane;
    //JPanel bottomPane;
    JTextArea bottomPane;
    JTextArea textBox;
    public Window() {

        /** Creating Condensed View Panel = Panel 3 */
        leftPane = new JPanel();
        leftPane.setBackground(Color.BLACK);
        //leftPane.setSize(200, 750);

        /** Creating Expanded Visualizer = Panel 1 */
        topPane = new JPanel();
        topPane.setBackground(Color.GRAY);
        //topPane.setSize(550, 375);

        /** Creating Terminal view = Panel 2 */
        //bottomPane = new JPanel();
        bottomPane = new JTextArea();
        bottomPane.setBackground(Color.GREEN);
        bottomPane.setEditable(false);
        //bottomPane.setSize(550, 375);

        /** Creating Text Box */
        textBox = new JTextArea();
        textBox.setRows(1);
        textBox.addKeyListener(new KeyListener() {  // Does not work //
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
                    //System.out.println(e.getExtendedKeyCode() + " crsr: " + cursorIndex + " cmd: " + command.length());
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    outCommandToTextArea(command + "\n");
                    command = "";
                    textBox.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    int cursorIndex = textBox.getCaretPosition();
                    if (cursorIndex >= 0) {
                        command = command.substring(0, cursorIndex) + command.substring(cursorIndex + 1);
                    } else {
                        command = "";
                    }
                }
            }

        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /** Splitting Panes to match the desired view */
        JSplitPane splitBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT, bottomPane, textBox);
        //splitBottom.setResizeWeight(0.75);
        splitBottom.setDividerLocation(300);
        JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, splitBottom);
        split1.setDividerLocation(375);
        split1.setBorder(null);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, split1);
        split2.setDividerLocation(225);
        split2.setBorder(null);
        add(split2);
        setSize(new Dimension(750, 750));
        setVisible(true);
    }

    private void outCommandToTextArea(String command) {
        bottomPane.insert(command, bottomPane.getCaretPosition());
    }

    public static void main(String[] args) {
        Window window = new Window(Main);
    }
}

/**
 * Holding backspace
 * CMD + Backspace
 * CMD + A
 *
 */
/**
 * TODO: Go to File Explorer Folder and replace all prints with return String.
 * At the end of ls -aR return the number of Files and number of directories found.
 */