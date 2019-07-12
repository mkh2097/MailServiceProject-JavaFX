package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.FxmlLoader;
import sample.Model.AllUsers;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;
import sample.Model.Message;

import java.io.IOException;

public class MessageListItemController {

    private Message message;

    @FXML
    AnchorPane root;
    @FXML
    Label sender;
    @FXML
    Label receiver;
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


    public MessageListItemController(Message message) throws IOException {
        this.message = message;
        new FxmlLoader().load("View/MessageListItem.fxml", this);
    }

    public AnchorPane init() {
        markAsUnreadButton.setVisible(false);
        markAsReadButton.setVisible(false);
        sender.setText(message.getSender().getBasicInfo().getFullName());
        receiver.setText(message.getReceiver().getBasicInfo().getFullName());
        subject.setText(message.getSubject());
        date.setText(message.getDate());
        time.setText(message.getTime());
        text.setText(message.getContent());
        if (message.isRead()) {
            markAsUnreadButton.setVisible(true);
        } else {
            markAsReadButton.setVisible(true);
        }
        boolean goldStyle = true;
        if (!message.isImportant()) {
            goldStyle = false;
        }
        if (goldStyle) {
            starButton.setStyle("-icon-paint: gold;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        } else {
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

    public void importantChanger(ActionEvent event) throws IOException {
        if (message.isImportant()) {
            message.setImportant(false);
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Unimportant, AllUsers.getUserWhoWantToSignIn(), null, message));
            } catch (Exception e) {

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
            message.setImportant(true);
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Important, AllUsers.getUserWhoWantToSignIn(), null, message));
            } catch (Exception e) {

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
        if (message.isDeleted()){
            message.setDeleted(false);
        }
        else {
            message.setDeleted(true);
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.RemoveMSG, AllUsers.getUserWhoWantToSignIn(), null, message));
            } catch (Exception e) {

            }
        }
    }

    public void markAsRead(ActionEvent actionEvent) {
        markAsReadButton.setVisible(false);
        markAsUnreadButton.setVisible(true);
        message.setRead(true);
        try {
            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
            connection.sendRequest(new MessageHandler(MessageType.Read, AllUsers.getUserWhoWantToSignIn(), null, message));
        } catch (Exception e) {

        }

    }

    public void markAsUnread(ActionEvent actionEvent) {
        markAsUnreadButton.setVisible(false);
        markAsReadButton.setVisible(true);
        message.setRead(false);
        try {
            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
            connection.sendRequest(new MessageHandler(MessageType.Unread, AllUsers.getUserWhoWantToSignIn(), null, message));
        } catch (Exception e) {

        }

    }
}
