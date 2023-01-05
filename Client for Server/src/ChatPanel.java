import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class ChatPanel extends JSplitPane {
    Socket socket = new Socket("localhost", 30000);
    String username;
    /**public static JFrame window;*/
    public ChatPanel(String s, String l) throws IOException {
        super(JSplitPane.HORIZONTAL_SPLIT);
        username = s;
        Font font = new Font("Courier", Font.BOLD,12);
        JButton left = new JButton(s);
        left.setFont(font);
        left.setBorderPainted(false);
        left.setOpaque(true);
        left.setBackground(Color.darkGray);
        left.setForeground(Color.white);
        left.setMargin(new Insets(50,50,50,50));
        left.addActionListener(e -> {
            /** TODO: Clear screen and open chat of user: s */
            ChatWindow frame = new ChatWindow();

            try {
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("receive\n" + "test3\n" + username + "\n");
                Scanner scan = new Scanner(in);
                scan.useDelimiter("\n\n");
                String result = "";
                result += "You have " + scan.next() + " new messages: \n";
                while (scan.hasNext()) {
                    result += scan.next();
                }
                //((JTextPane)frame.getContentPane()).setText(result);
                System.out.println(result);
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
        setLeftComponent(left);

        JButton right = new JButton(l);
        right.setFont(font);
        right.setBorderPainted(false);
        right.setOpaque(true);
        right.setBackground(Color.lightGray);
        right.setForeground(Color.BLACK);
        right.setMargin(new Insets(50,50,50,500));
        right.addActionListener(e -> {
            /** TODO: Clear screen and open chat of user: s */
            ChatWindow frame = new ChatWindow();

            try {
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("receive\n" + "test3\n" + username + "\n");
                Scanner scan = new Scanner(in);
                scan.useDelimiter("\n\n");
                String result = "";
                result += "You have " + scan.next() + " new messages: \n";
                while (scan.hasNext()) {
                    result += scan.next();
                }
                ((JTextPane)frame.getContentPane()).setText(result);
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
        setRightComponent(right);

        this.setDividerSize(1);

        this.setBackground(Color.red);
    }

}
