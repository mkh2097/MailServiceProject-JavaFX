package sample.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AllMessages {
    private static final String MESSAGES_FILE_URL = "src/main/Resources/messages.ser";
    private static List<Message> ALL_MESSAGES = new ArrayList<>();
    private static Message theMessageToView;

    public static void writeToFile(List<Message> messages) {
        FileOutputStream fileOutputStream;
        try {
            File file = new File("./src/sample/Resources/Messages/allmessages.ser");
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(messages);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Message> readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream;
        List<Message> messages = null;
        fileInputStream = new FileInputStream("./src/sample/Resources/Messages/allmessages.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        messages = (List<Message>) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        return messages;
    }

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
