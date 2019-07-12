package sample.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.FxmlLoader;
import sample.Model.*;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;
import sample.Model.Connection.SecondConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ComposePageController implements Initializable {
    @FXML
    TextField sender;
    @FXML
    TextField receiver;
    @FXML
    TextField subject;
    @FXML
    TextArea text;
    @FXML
    ImageView icon;
    @FXML
    Text pageName;
    @FXML
    TextField attachedFileName;
    @FXML
    StackPane stackPane;
    @FXML
    ProgressIndicator loading;

    Connection connection;
    boolean fileAttached = false;
    MessageActions messageActionsServer = MessageActions.NONE;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sender.setText(AllUsers.getUserWhoWantToSignIn().getAccountInfo().getEmailAddress());
        sender.setDisable(true);
        attachedFileName.setEditable(false);
        if (EmailReadPageController.getMessageAction() == MessageActions.REPLY) {
            messageActionsServer = MessageActions.REPLY;
            pageName.setText("Reply");
            receiver.setText(AllMessages.getTheMessageToView().getSender().getAccountInfo().getEmailAddress());
            EmailReadPageController.setMessageAction(MessageActions.NONE);

        } else if (EmailReadPageController.getMessageAction() == MessageActions.FORWARD) {
            messageActionsServer = MessageActions.FORWARD;
            pageName.setText("Forward");
            subject.setText(AllMessages.getTheMessageToView().getSubject());
            text.setText("Forwarded From:" + "<" + AllMessages.getTheMessageToView().getSender().getAccountInfo().getEmailAddress() + ">\n" + AllMessages.getTheMessageToView().getContent());
            EmailReadPageController.setMessageAction(MessageActions.NONE);
        }
    }

    public void attachFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Select the file you want to attach");
        fileChooser.setInitialDirectory(new File("./src/sample/Resources"));
        File selectedFile = fileChooser.showOpenDialog(FxmlLoader.getMainStage());
        if (selectedFile != null) {
            fileAttached = true;
            attachedFileName.setText(selectedFile.getPath());
        }
    }

    public void sendMessage(ActionEvent mouseEvent) {
        System.out.println("enter");
        User target = null;
        boolean userFound = false;
        final User FENCHITTER_HOST = new User(new BasicInfo("Fenchitter", "Service"), new AccountInfo("fenchitter", "admin2097A"));
        try {
            if(receiver.getText().trim().contains("@fmail.com")) {
                target = AllUsers.readFromFile(receiver.getText().trim().substring(0, receiver.getText().trim().indexOf("@fmail.com")));
            }else {
                throw new ClassNotFoundException();
            }
            userFound = true;

        } catch (ClassNotFoundException | IOException e) {
            //ignore it
        }
        if (userFound) {
            final User targetUser = target;
            System.out.println("user Found");
            boolean conversationFound = false;
            Message newMessage = null;
            Task<Message> task;
            task = new Task<>() {
                private int loadNumber = 0;
                @Override
                protected Message call() throws Exception {
                    Message newMessage;
                    loadNumber += 1;
                    loading.setProgress(loadNumber);
                    if (fileAttached) {
                        newMessage = new Message(AllUsers.getUserWhoWantToSignIn(), targetUser, subject.getText(), attachedFileName.getText(), text.getText());
                        SecondConnection.sendFile(newMessage);
                        newMessage.setPending(true);
                    } else {
                        newMessage = new Message(AllUsers.getUserWhoWantToSignIn(), targetUser, subject.getText(), text.getText());
                        newMessage.setPending(true);
                    }
                    return newMessage;
                }
            };

           new Thread(task).start();
           stackPane.setVisible(true);

            task.setOnSucceeded(e -> {
                try {
                    if (messageActionsServer == MessageActions.REPLY){
                        try {
                            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                            connection.sendRequest(new MessageHandler(MessageType.Reply,AllUsers.getUserWhoWantToSignIn(),null,task.get()));
                        }catch (Exception t){

                        }
                    }else if(messageActionsServer == MessageActions.FORWARD){
                        try {
                            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                            connection.sendRequest(new MessageHandler(MessageType.Forward,AllUsers.getUserWhoWantToSignIn(),null,task.get()));
                        }catch (Exception t){

                        }
                    }
                    else if (messageActionsServer == MessageActions.NONE){
                        try {
                            connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                            connection.sendRequest(new MessageHandler(MessageType.Send,AllUsers.getUserWhoWantToSignIn(),null,task.get()));
                        }catch (Exception t){

                        }
                    }
                    try {
                        connection  = new Connection(task.get().getReceiver());
                        connection.sendRequest(new MessageHandler(MessageType.Receive,task.get().getReceiver(),null,task.get()));
                    }catch (Exception t){

                    }
//                    if (ta)
//                    connection = new Connection(AllUsers.getUserWhoWantToSignIn());
//                    connection

                initialize(null,null);
                stackPane.setVisible(false);
                Alert succeed = new Alert(Alert.AlertType.INFORMATION,"Email Sent Successful");
                succeed.setTitle("Success");
                succeed.setHeaderText("Success");
                succeed.showAndWait();
                task.get().setPending(false);
                System.out.println("ok");
                    new FxmlLoader().load("View/UserProfile.fxml");
                } catch (IOException | ExecutionException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
            task.setOnFailed(e -> {
                try {
                    initialize(null,null);
                    stackPane.setVisible(false);
                    Alert error = new Alert(Alert.AlertType.ERROR,"Email Failed");
                    error.setTitle("Fail");
                    error.setHeaderText("Fail");
                    error.showAndWait();
                    System.out.println("ok");
                    new FxmlLoader().load("View/ComposePage.fxml");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            try {
                newMessage = task.get();
                newMessage.setPending(false);
                Conversation newConv = new Conversation(AllUsers.getUserWhoWantToSignIn(), target, newMessage);
                Conversation temp = null;
                for (Conversation conversation : AllConversations.getAllConversations()) {
                    if (conversation.equals(newConv)) {
                        conversationFound = true;
                        temp = conversation;
                        break;
                    }
                }
                if (!conversationFound) {
                    System.out.println("conversation creation");
                    AllConversations.getAllConversations().add(newConv);
                    newConv.setFirstMessage(newMessage);
                    newConv.setLastMessageAddedToThisConversation(newMessage);
                } else {
                    System.out.println("added message");
                    temp.getMessages().add(newMessage);
                    temp.setLastMessageAddedToThisConversation(newMessage);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("error");
            boolean conversationFound = false;
            Message errorMessage = new Message(FENCHITTER_HOST, AllUsers.getUserWhoWantToSignIn(), "Something Wrong Happened", "", "This Account that you send message to it not found");
            Conversation errorConv = new Conversation(AllUsers.getUserWhoWantToSignIn(), FENCHITTER_HOST, errorMessage);
            Conversation temp = null;
            for (Conversation conversation : AllConversations.getAllConversations()) {
                if (conversation.equals(errorConv)) {
                    conversationFound = true;
                    temp = conversation;
                    break;
                }
            }
            if (!conversationFound) {
                System.out.println("conversation creation");
                AllConversations.getAllConversations().add(errorConv);
                errorConv.setLastMessageAddedToThisConversation(errorMessage);
                errorConv.setFirstMessage(errorMessage);
            } else {
                System.out.println("added message");
                temp.getMessages().add(errorMessage);
                temp.setLastMessageAddedToThisConversation(errorMessage);
            }
        }
    }

}

