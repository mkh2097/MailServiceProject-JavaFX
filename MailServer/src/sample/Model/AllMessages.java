package sample.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class AllMessages {
    private static final String MESSAGES_FILE_URL = "src/main/Resources/messages.ser";
    private static List<Message> ALL_MESSAGES = new ArrayList<>();
    private static Message theMessageToView;

    public static Message getTheMessageToView() {
        return theMessageToView;
    }

    public static void setTheMessageToView(Message theMessageToView) {
        AllMessages.theMessageToView = theMessageToView;
    }

    public static void init()
            throws IndexOutOfBoundsException, IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MESSAGES_FILE_URL));
        ALL_MESSAGES = (List<Message>) objectInputStream.readObject();
        objectInputStream.close();
    }

    public static List<Message> getAllMessages() {
        return ALL_MESSAGES;
    }

    public static void setAllMessages(List<Message> allMessages) {
        ALL_MESSAGES = allMessages;
    }
}
