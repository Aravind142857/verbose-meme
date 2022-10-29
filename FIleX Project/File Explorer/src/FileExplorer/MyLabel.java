package FileExplorer;

import org.junit.internal.runners.statements.FailOnTimeout;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    public MyLabel(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 16));
        if (text.lastIndexOf(".") == -1) {      /** If File is a Directory */
            setForeground(new Color(134, 11, 255));
        }
    }

}
