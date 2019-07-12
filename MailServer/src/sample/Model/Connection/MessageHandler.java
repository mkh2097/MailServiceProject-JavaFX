package sample.Model.Connection;

import sample.Model.Message;
import sample.Model.User;

import java.io.Serializable;

public class MessageHandler implements Serializable
{
    public static final long serialVersionUID = 14L;
    private User sender;
    private User receiver;
    private Message message;
    /**
     * server or client use type of message to do correct work correspond to that
     */
    private final MessageType messageType;

    public MessageHandler(MessageType messageType, User sender, User receiver, Message message) {
        this.messageType = messageType;
        this.receiver = receiver;
        this.message = message;
        this.sender = sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Message getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "MessageHandler{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", messageText='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
