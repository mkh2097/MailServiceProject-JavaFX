package sample.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AllConversations {
    private static List<Conversation> ALL_CONVERSATIONS = new ArrayList<>();
    private static Conversation theConversationToView;

    public static void writeToFile(List<Conversation> conversations) {
        FileOutputStream fileOutputStream;
        try {
            File file = new File("./src/sample/Resources/Conversations/allconversations.ser");
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(conversations);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Conversation> readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream;
        List<Conversation> conversations = null;
        fileInputStream = new FileInputStream("./src/sample/Resources/Conversations/allconversations.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        conversations = (List<Conversation>) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        return conversations;
    }

    public static Conversation getTheConversationToView() {
        return theConversationToView;
    }

    public static void setTheConversationToView(Conversation theConversationToView) {
        AllConversations.theConversationToView = theConversationToView;
    }

    public static List<Conversation> getAllConversations() {
        return ALL_CONVERSATIONS;
    }

    public static void setAllConversations(List<Conversation> allConversations) {
        ALL_CONVERSATIONS = allConversations;
    }
}

