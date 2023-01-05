import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.Scanner;

public class GUI {
    public static String user;
    public GUI(String name) {
        user = name;
    }
    public static String getMessaged() throws FileNotFoundException {
        String msgd = "";
        Scanner read = new Scanner(new File("messaged.txt"));
        read.useDelimiter("\n");
        while (read.hasNext()) {
            msgd += read.next() + "\n";
        }
        return msgd;
    }
    public static void main(String[] args) throws Exception{
        Socket clientSocket = new Socket("localhost",30000);
        GUI a = new GUI("chid");
        //String[] users = getMessaged().split("\n");
        JFrame window = new JFrame();
        //JPanel home = new JPanel();
        //home.setBorder(BorderFactory.createEtchedBorder(0));

        //home.setBackground(Color.GREEN);
        window.setSize(800, 1000);

        /** Menu bar */
        JPanel menuBar = new JPanel(new BorderLayout());
        menuBar.add(new JLabel(GUI.user), BorderLayout.PAGE_START);
        menuBar.add(new JButton("New Chat"), BorderLayout.LINE_START);
        menuBar.add(new JButton("Search"), BorderLayout.CENTER);
        menuBar.add(new JButton("Settings"), BorderLayout.LINE_END);

        /** Create and add chat pane with scrollber */
        JPanel chatPane = new JPanel();
        chatPane.setLayout(new BoxLayout(chatPane, 1));
        Scanner scan = new Scanner(getMessaged());
        scan.useDelimiter("\n");
        /**ChatPanel.window = window;*/
        while (scan.hasNext()) {
            chatPane.add(new ChatPanel(scan.next(), "Insert last message here"));
        }

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuBar, chatPane);

        JScrollPane homeScroll = new JScrollPane(split);
        homeScroll.setBackground(Color.RED);
        window.getContentPane().add(homeScroll);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
    }

}
/** TODO:
 *          Figure out a way to have a ChatPanel contain both the user's name and the last message sent in that chat with both linking to the same chat if clicked
 */