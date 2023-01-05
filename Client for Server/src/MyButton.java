import javax.swing.*;

public class MyButton extends JButton {
    public String username;
    public MyButton(String text) {
        super(text);
        username = text;
    }
    public MyButton(String text, String usr) {
        super(text);
        username = usr;
    }
}
