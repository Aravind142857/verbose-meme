import com.sun.source.tree.BreakTree;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    static HashMap<User, HashMap<User, Queue<Message>>> messages = new HashMap<>();
    static HashMap<String, User> users = new HashMap<>();
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(30000);
        loadUsers();
        while (true) {
            nibba(listener);
        }
    }
    public static void loadUsers() throws FileNotFoundException {
        File f = new File("users.csv");
        Scanner scan = new Scanner(f);
        scan.useDelimiter(",");
        User a;
        while (scan.hasNextLine()) {
            String name = scan.next();
            String username = scan.next();
            String password = scan.nextLine();
            a = new User(name, username, password);
            users.put(a.username, a);
        }
    }
    public static User lookup(String username) {
        return users.get(username);
    }
    public static void nibba(ServerSocket s) throws IOException {
        Socket a = s.accept();
        //System.out.println("You have made a connection to port " + s.getLocalPort());
        String returnToClient = "";
        InputStream input = a.getInputStream();
        Scanner reader = new Scanner(new InputStreamReader(input));
        reader.useDelimiter("\n\n");
        String line = reader.next();// reads a line of text
        Scanner scan = new Scanner(line);
        scan.useDelimiter("\n");
        String sendReceive = scan.next();     /** send/receive */
        User sender = lookup(scan.next());      /** sender username */
        User receiver = lookup(scan.next());       /** receiver username */
        if (sendReceive.equals("send")) {
            String msg = scan.next();           /** message */
            //returnToClient += sender.username + receiver.username + msg;
            returnToClient += send(msg, sender, receiver);
        } else if (sendReceive.equals("receive")){
            returnToClient += receive(sender, receiver);
        }
        PrintWriter writer = new PrintWriter(a.getOutputStream(), true);
        writer.println(returnToClient);
        writer.close();
        a.close();
    }

    public static String receive(User sender, User receiver) {  /** sender checks if got messages from receiver */
        String toReturn = "";
        if (messages.containsKey(sender) && messages.get(sender).containsKey(receiver)) {
            int count = 0;
            Queue<Message> messageQueue = messages.get(sender).get(receiver);
            while (!messageQueue.isEmpty()) {
                count++;
                Message temp = messageQueue.poll();
                toReturn += temp.time + ";" + temp.sender.username + ";" + temp.msg + "\n\n";
            }
            toReturn = count + "\n\n" + toReturn;
            if (messages.get(sender).isEmpty()) {
                messages.remove(sender);
            }
        } else {
            toReturn = "0\n\n";
        }
        return toReturn;
    }

    public static String send(String s, User a, User b) {
        Message msg = new Message(s, a, b);
        if (!messages.containsKey(b)) {
            messages.put(b, new HashMap<>());
        }
        if (!messages.get(b).containsKey(a)) {
            messages.get(b).put(a, new ArrayDeque<>());
        }
        messages.get(b).get(a).add(msg);
        return "ok";
    }

}

/** TODO:
 *      Write user class
 *      Write Client
 *      Store messages in an efficient data structure
 *      Obtain Sender and Receiver from Sender
 *      Exception if Sender is empty
 *      Exception if Sender not recognized
 *      Exception if Receiver not recognized
 *
 */