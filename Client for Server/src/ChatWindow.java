import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JFrame {
    public ChatWindow() {
        super();
        this.setSize(800, 1000);
        JTextPane messages = new JTextPane();
        messages.setOpaque(true);
        messages.setBackground(Color.YELLOW);
        this.getContentPane().add(messages);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
