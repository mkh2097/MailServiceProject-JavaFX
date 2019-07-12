package sample.Model;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {


    private User sender;
    private User receiver;
    private String subject;
    private String time;
    private String date;
    private String attachment;
    private String content;
    private boolean isSend;
    private boolean isReceived;
    private boolean isImportant;
    private boolean isRead;
    private boolean isDrafted;
    private boolean isDeleted;
    private boolean isPending;



    public Message(User sender, User receiver, String subject, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.time = CurrentDateAndTime.getCurrentTime();
        this.date = CurrentDateAndTime.getCurrentDate();
        this.content = content;
    }

    public Message(User sender, User receiver, String subject, String attachment, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.time = CurrentDateAndTime.getCurrentTime();
        this.date = CurrentDateAndTime.getCurrentDate();
        this.attachment = attachment;
        this.content = content;
        AllMessages.getAllMessages().add(this);
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public User getSender() {
        return sender;
    }

    public User getReciever() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getContent() {
        return content;
    }

    public boolean isSend() {
        return isSend;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isDrafted() {
        return isDrafted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReciever(User reciever) {
        this.receiver = reciever;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setDrafted(boolean drafted) {
        isDrafted = drafted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Message) {
            if (((Message) obj).sender.equals(this.sender) && ((Message) obj).receiver.equals(this.receiver) && ((Message) obj).subject.equals(this.subject) && ((Message) obj).time.equals(this.time) && ((Message) obj).date.equals(this.date) && ((Message) obj).content.equals(this.content)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender,receiver,time,date,content);
    }
}
