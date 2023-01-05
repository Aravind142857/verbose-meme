import java.text.SimpleDateFormat;

import java.util.Comparator;
import java.util.Date;

public class Message {
    String msg;
    String time;
    User sender;
    User recipient;

    public Message(String m, User s, User r) {
        msg = m;
        time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        sender = s;
        recipient = r;
    }

}