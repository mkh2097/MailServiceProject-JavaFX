package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.FxmlLoader;
import sample.Model.AllUsers;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;
import sample.Model.Conversation;
import sample.Model.Message;

import java.io.IOException;

public class ConversationListItemController {

    private Conversation conversation;

    @FXML
    AnchorPane root;
    @FXML
    Label sender;
    @FXML
    Label subject;
    @FXML
    Label text;
    @FXML
    Label date;
    @FXML
    Label time;
    @FXML
    Button starButton;
    @FXML
    Button markAsUnreadButton;
    @FXML
    Button markAsReadButton;

    Connection connection;

    public ConversationListItemController(Conversation conversation) throws IOException {
        this.conversation = conversation;
        new FxmlLoader().load("View/ConversationListItem.fxml", this);
    }

    public AnchorPane init() {
        markAsUnreadButton.setVisible(false);
        markAsReadButton.setVisible(false);
        sender.setText(conversation.getLastMessageAddedToThisConversation().getSender().getBasicInfo().getFullName()+"<->"+conversation.getLastMessageAddedToThisConversation().getReceiver().getBasicInfo().getFullName());
        subject.setText(conversation.getLastMessageAddedToThisConversation().getSubject());
        date.setText(conversation.getLastMessageAddedToThisConversation().getDate());
        time.setText(conversation.getLastMessageAddedToThisConversation().getTime());
        text.setText(conversation.getLastMessageAddedToThisConversation().getContent());
        boolean showReadButton = false;
        for(Message i : conversation.getMessages()){
            if(!i.isRead()){
                showReadButton = true;
            }
        }
        if(showReadButton) {
            markAsReadButton.setVisible(true);
        }else {
            markAsUnreadButton.setVisible(true);
        }
        boolean goldStyle = true;
        for(Message i : conversation.getMessages()){
            if(!i.isImportant()){
                goldStyle = false;
                break;
            }
        }
        if(goldStyle){
            starButton.setStyle("-icon-paint: gold;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        }
        else {
            starButton.setStyle("-icon-paint: white;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        }
        return root;
    }

    public void importantChanger(ActionEvent actionEvent) throws IOException {
        boolean goldStyle = true;
        for(Message i : conversation.getMessages()){
            if(!i.isImportant()){
                goldStyle = false;
                break;
            }
        }
        if (goldStyle) {
            for (Message i : conversation.getMessages()) {
                i.setImportant(false);
                try {
                    System.out.println("commmmmmmmmmmmmmit");
                    connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                    connection.sendRequest(new MessageHandler(MessageType.Unimportant,AllUsers.getUserWhoWantToSignIn(),null,i));
                }catch (Exception e){

                }
            }
            starButton.setStyle("-icon-paint: white;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        } else {
            for (Message i : conversation.getMessages()) {
                i.setImportant(true);
                try {
                    connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                    connection.sendRequest(new MessageHandler(MessageType.Important,AllUsers.getUserWhoWantToSignIn(),null,i));
                }catch (Exception e){

                }
            }
            starButton.setStyle("-icon-paint: gold;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        }
    }

    public void deleteMessage(ActionEvent actionEvent) {
        try {
            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
            connection.sendRequest(new MessageHandler(MessageType.RemoveCONV,AllUsers.getUserWhoWantToSignIn(),null,conversation.getLastMessageAddedToThisConversation()));
        }catch (Exception e){
        }

        for (Message i : conversation.getMessages()) {
            i.setDeleted(true);
        }
    }

    public void markAsRead(ActionEvent actionEvent) {
        markAsReadButton.setVisible(false);
        markAsUnreadButton.setVisible(true);
        for (Message i : conversation.getMessages()) {
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Read,AllUsers.getUserWhoWantToSignIn(),null,i));
            }catch (Exception e){
            }
            i.setRead(true);
        }
    }

    public void markAsUnread(ActionEvent actionEvent) {
        markAsUnreadButton.setVisible(false);
        markAsReadButton.setVisible(true);
        for (Message i : conversation.getMessages()) {
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Unread,AllUsers.getUserWhoWantToSignIn(),null,i));
            }catch (Exception e){
            }
            i.setRead(false);
        }
    }
}
