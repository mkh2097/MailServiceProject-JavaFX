package sample.Model;

import java.util.ArrayList;
import java.util.List;

public class AllConversations {
    private static List<Conversation> ALL_CONVESATIONS = new ArrayList<>();
    private static Conversation theConversationToView;

    public static Conversation getTheConversationToView() {
        return theConversationToView;
    }

    public static void setTheConversationToView(Conversation theConversationToView) {
        AllConversations.theConversationToView = theConversationToView;
    }

    public static List<Conversation> getAllConvesations() {
        return ALL_CONVESATIONS;
    }

    public static void setAllConvesations(List<Conversation> allConvesations) {
        ALL_CONVESATIONS = allConvesations;
    }
}

