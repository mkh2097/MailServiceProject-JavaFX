package sample.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sample.FxmlLoader;
import sample.Model.*;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;
import sample.Model.Connection.ThirdConnection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class EmailReadPageController implements Initializable {
    @FXML
    Label senderInfo;
    @FXML
    Label receiverInfo;
    @FXML
    Label subject;
    @FXML
    ImageView senderProfilePic;
    @FXML
    TextArea text;
    @FXML
    Button starButton;
    @FXML
    Label attachedFileName;
    @FXML
    Button attachmentButton;
    @FXML
    StackPane stackPane;
    @FXML
    ProgressIndicator loading;


    private static MessageActions messageAction;

    Connection connection;

    public static MessageActions getMessageAction() {
        return messageAction;
    }

    public void goToWelcome(ActionEvent actionEvent) throws IOException {
        Alert signOut = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure for leaving your account?");
        signOut.setTitle("SignOut");
        signOut.setHeaderText("SignOut");
        signOut.showAndWait();
        if (signOut.getResult() == ButtonType.OK) {
            new FxmlLoader().load("View/WelcomeScreen.fxml");
        }
    }

    public void goToProfile(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/UserProfile.fxml");
    }
    public void importantChanger(ActionEvent actionEvent) {
        if (AllMessages.getTheMessageToView().isImportant()) {
            AllMessages.getTheMessageToView().setImportant(false);
            starButton.setStyle("-icon-paint: white;\n"
                    + "-fx-background-color: -icon-paint;\n"
                    + "-fx-border-style: solid;\n"
                    + "-fx-min-height: 20;\n"
                    + "-fx-max-height: 20;\n"
                    + "-fx-min-width: 20;\n"
                    + "-fx-max-width: 20;\n"
                    + "-fx-shape:  \"M 0.000 5.000L 5.878 8.090L 4.755 1.545L 9.511 -3.090L 2.939 -4.045L 0.000 -10.000L -2.939 -4.045L -9.511 -3.090L -4.755 1.545L -5.878 8.090L 0.000 5.000\";\n");
        } else {
            AllMessages.getTheMessageToView().setImportant(true);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attachmentButton.setDisable(true);
            senderInfo.setText(AllMessages.getTheMessageToView().getSender().getBasicInfo().getFullName()+"<"+AllMessages.getTheMessageToView().getSender().getAccountInfo().getEmailAddress()+">");
            receiverInfo.setText(AllMessages.getTheMessageToView().getReceiver().getBasicInfo().getFullName()+"<"+AllMessages.getTheMessageToView().getReceiver().getAccountInfo().getEmailAddress()+">");
            subject.setText(AllMessages.getTheMessageToView().getSubject());
            text.setText(AllMessages.getTheMessageToView().getContent());
            if(AllMessages.getTheMessageToView().getAttachment() != null) {
                attachedFileName.setText(AllMessages.getTheMessageToView().getAttachment().substring(AllMessages.getTheMessageToView().getAttachment().lastIndexOf('\\')+1));
                attachmentButton.setDisable(false);
            }
        senderProfilePic.setImage(new Image(Paths.get(AllMessages.getTheMessageToView().getSender().getImageFileName()).toUri().toString()));
        if(AllMessages.getTheMessageToView().isImportant()){
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Important, AllUsers.getUserWhoWantToSignIn(), null, AllMessages.getTheMessageToView()));
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
        else {
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Unimportant, AllUsers.getUserWhoWantToSignIn(), null, AllMessages.getTheMessageToView()));
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
        }

        text.setEditable(false);
    }

    public void deleteMessage(ActionEvent actionEvent) {
        AllMessages.getTheMessageToView().setDeleted(true);
    }

    public void replyMessage(ActionEvent actionEvent) throws IOException {
        messageAction = MessageActions.REPLY;
        new FxmlLoader().load("View/ComposePage.fxml");
    }

    public static void setMessageAction(MessageActions messageAction) {
        EmailReadPageController.messageAction = messageAction;
    }

    public void forwardMessage(ActionEvent actionEvent) throws IOException {
        messageAction = MessageActions.FORWARD;
        new FxmlLoader().load("View/ComposePage.fxml");
    }

    public void blockMessage(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure for blocking "+AllMessages.getTheMessageToView().getSender().getBasicInfo().getFullName()+" ?");
        alert.setTitle("Block Confirmation");
        alert.setHeaderText("Block!");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            try {
                connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                connection.sendRequest(new MessageHandler(MessageType.Block,AllUsers.getUserWhoWantToSignIn(),AllMessages.getTheMessageToView().getSender(),null));
            }catch (Exception e){
            }
            AllUsers.getUserWhoWantToSignIn().getBlockList().add(AllMessages.getTheMessageToView().getSender());
            Alert blocked = new Alert(Alert.AlertType.INFORMATION,AllMessages.getTheMessageToView().getSender().getBasicInfo().getFullName()+" blocked Successfully!");
            blocked.setTitle("Blocked Successfully");
            blocked.setHeaderText("Blocked!");
            blocked.showAndWait();
        }

    }

    public void viewAttachment(ActionEvent actionEvent) {
        Task task = null;
        String DB_ROOT = "./src/sample/Resources/DownloadedFiles/";
        File file = new File(DB_ROOT+AllMessages.getTheMessageToView().getAttachment().substring(AllMessages.getTheMessageToView().getAttachment().lastIndexOf("\\")+1));
        if (!file.exists()) {
            task = new Task() {
                private int loadNumber = 0;
                @Override
                protected String call() throws Exception {
                    loadNumber += 1;
                    loading.setProgress(loadNumber);
                    ThirdConnection.main(new String[10]);
                    return null;
                }
            };
            new Thread(task).start();
            stackPane.setVisible(true);
            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
            connection.sendRequest(new MessageHandler(MessageType.RequestFile, AllUsers.getUserWhoWantToSignIn(), null, AllMessages.getTheMessageToView()));
        }
        if (task != null)
        {
            task.setOnSucceeded(e -> {
                stackPane.setVisible(false);
                Alert succeed = new Alert(Alert.AlertType.INFORMATION,"Attachment Downloaded Successfully");
                succeed.setTitle("Success");
                succeed.setHeaderText("Success");
                succeed.showAndWait();
            });
        }
        else {
            stackPane.setVisible(false);
            Alert succeed = new Alert(Alert.AlertType.INFORMATION,"Attachment has been downloaded!");
            succeed.setTitle("Success");
            succeed.setHeaderText("Success");
            succeed.showAndWait();
        }
    }

}
