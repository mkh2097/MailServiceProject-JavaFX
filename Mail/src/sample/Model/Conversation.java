package sample.Model;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {

    private List<Message> messages = new ArrayList<>();
    private  Message firstMessage;
    private  User from;
    private  User to;

    public Conversation(User from, User to,Message message) {
        this.from = from;
        this.to = to;
        messages.add(message);
    }


    private Message LastMessageAddedToThisConversation = null;
    private boolean isImportant;
    private boolean isDeleted;
    private boolean isRead;

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isRead() {
        return isRead;
    }

    public Message getLastMessageAddedToThisConversation() {
        return LastMessageAddedToThisConversation;
    }

    public void setLastMessageAddedToThisConversation(Message lastMessageAddedToThisConversation) {
        LastMessageAddedToThisConversation = lastMessageAddedToThisConversation;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(Message firstMessage) {
        this.firstMessage = firstMessage;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
    }


    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Conversation) {
            if (((Conversation) obj).getFrom().equals(this.getFrom())&&((Conversation) obj).getTo().equals(this.getTo())) {
                return true;
            }
            if(((Conversation) obj).getFrom().equals(this.getTo())&&((Conversation) obj).getTo().equals(this.getFrom())){
                return true;

            }

        }
        return false;
    }
}
