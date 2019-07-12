package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.FxmlLoader;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserProfileController implements Initializable {
    @FXML
    Label fullName;
    @FXML
    ImageView profilePicture;
    @FXML
    ListView<Conversation> conversationListView;
    @FXML
    ListView<Message> messageListView;
    @FXML
    MenuButton searchType;
    @FXML
    TextField searchField;
    @FXML
    Button searchButton;
    @FXML
    Button showUnreadButton;
//    {
//        fullName.setText(User.getLastUserCreated().getBasicInfo().getFullName());
//    }
//    {
//        nameID.setText(User.getUserWhoWantToSignIn().getBasicInfo().getName()+" "+User.getUserWhoWantToSignIn().getBasicInfo().getLastName());

    //    }
    private int searchNumber = 0;
    private SelectedField selectedField = SelectedField.INBOX;

    public void goToWelcome(ActionEvent actionEvent) throws IOException {
        Alert signOut = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure for leaving your account?");
        signOut.setTitle("SignOut");
        signOut.setHeaderText("SignOut");
        signOut.showAndWait();
        if (signOut.getResult() == ButtonType.OK) {
            new FxmlLoader().load("View/WelcomeScreen.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUnreadButton.setVisible(false);
        messageListView.setVisible(false);
        conversationListView.setVisible(true);
//            showInbox(null);
//        ArrayList<Message> messagesToShowList1 = new ArrayList<>();
//        User ME = new User(new BasicInfo("Mohammad", "Khoddam"), new AccountInfo("mkh2098", "mkh2097A"));
//        User YOU = new User(new BasicInfo("Mohammad", "Khoddam"), new AccountInfo("mkh2099", "mkh2097A"));
//        AllUsers.setUserWhoWantToSignIn(ME);
//        AllUsers.setLastUserCreated(ME);
//        Message a = new Message(ME, YOU, "hi", "dgg", "djhsdhsdkh");
//        Message c = new Message(YOU, ME, "hi", "dgg", "djhsdhsdkh");
//        a.setRead(false);
//        messagesToShowList1.add(a);
//        messagesToShowList1.add(c);
//        messagesToShowList1.addAll(AllMessages.getAllMessages());
//        messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
//        messageListView.setCellFactory(messagesListView -> new MessageListItem());

//        ArrayList<Conversation> conversationArrayList = new ArrayList<>();
//        Conversation b = new Conversation(new Pair<>(ME,YOU),a);
//        b.setMessages(messagesToShowList1);
//        conversationArrayList.add(b);
//        b.setLastMessageAddedToThisConversation(a);

//        ArrayList<Conversation> conversationArrayList = new ArrayList<>();
//        for (Conversation i :AllConversations.getAllConversations()){
//            if(i.getTo().equals(AllUsers.getUserWhoWantToSignIn()) || i.getFrom().equals(AllUsers.getUserWhoWantToSignIn())){
//                if(i.getFirstMessage().getSender().equals(AllUsers.getUserWhoWantToSignIn())){
//                    conversationArrayList.add(i);
//                }
//            }
//        }
//
//        conversationListView.setItems(FXCollections.observableList(AllConversations.getAllConversations()));
//        conversationListView.setCellFactory(conversationListView -> new ConversationListItem());

        fullName.setText(AllUsers.getUserWhoWantToSignIn().getBasicInfo().getFullName());
        profilePicture.setImage(new Image(Paths.get(AllUsers.getUserWhoWantToSignIn().getImageFileName()).toUri().toString()));

        messageListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Message currentItemSelected = messageListView.getSelectionModel()
                        .getSelectedItem();
                currentItemSelected.setRead(true);
                AllMessages.setTheMessageToView(currentItemSelected);
                try {
                    new FxmlLoader().load("View/EmailReadPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //use this to do whatever you want to. Open Link etc.
            }
        });


        conversationListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Conversation currentItemSelected = conversationListView.getSelectionModel()
                        .getSelectedItem();
//                currentItemSelected.setRead(true);
                AllConversations.setTheConversationToView(currentItemSelected);
                ArrayList<Message> messagesToShowList = new ArrayList<>();
                for(Message i : currentItemSelected.getMessages()){
                    if (!i.isDeleted()){
                        messagesToShowList.add(i);
                    }
                }

//                for (Message i : AllMessages.getAllMessages()) {
//                    if (currentItemSelected.getMessages().contains(i)) {
//                        if (!i.isDeleted()) {
//                            messagesToShowList.add(i);
//                        }
//                    }
//                }
                messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
                messageListView.setCellFactory(messagesListView -> new MessageListItem());
                conversationListView.setVisible(false);
                messageListView.setVisible(true);
            }
        });
    }

    public void goToSetting(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/SettingPage.fxml");
    }

    public void composeMessage(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/ComposePage.fxml");
    }

    public void usernameSelected(ActionEvent actionEvent) {
        searchNumber = 0;
        searchType.setText("Username");
    }

    public void subjectSelected(ActionEvent actionEvent) {
        searchNumber = 1;
        searchType.setText("Subject");
    }

    public void searchClicked(ActionEvent actionEvent) {
        if (searchNumber == 0) {
            ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getSender().getAccountInfo().getUserName().startsWith(searchField.getText())&& !a.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(a.getSender())).collect(Collectors.toCollection(ArrayList::new));
            messageListView.setVisible(true);
            conversationListView.setVisible(false);
            Collections.reverse(messagesToShowList);
            messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
            messageListView.setCellFactory(messagesListView -> new MessageListItem());
        } else if (searchNumber == 1) {
            messageListView.setVisible(true);
            conversationListView.setVisible(false);
            ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getSubject().startsWith(searchField.getText()) && !a.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(a.getSender())).collect(Collectors.toCollection(ArrayList::new));
            Collections.reverse(messagesToShowList);
            messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
            messageListView.setCellFactory(messagesListView -> new MessageListItem());
        }
    }

    public void showInbox(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(true);

        selectedField = SelectedField.INBOX;
        messageListView.setVisible(false);
        conversationListView.setVisible(true);
        ArrayList<Conversation> conversationArrayList = new ArrayList<>();
        for (Conversation i : AllConversations.getAllConversations()) {
            if (i.getTo().equals(AllUsers.getUserWhoWantToSignIn()) || i.getFrom().equals(AllUsers.getUserWhoWantToSignIn())) {
                if (!i.getFirstMessage().getSender().equals(AllUsers.getUserWhoWantToSignIn())) {
                    conversationArrayList.add(i);
                }
            }
        }
        for (int i = conversationArrayList.size()-1;i>=0;i--) {
            boolean delete = true;
            for (Message j : conversationArrayList.get(i).getMessages()) {
                if (!j.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(j.getSender())) {
                    delete = false;
                    break;
                }
            }
            if(delete){
                conversationArrayList.remove(i);
            }
        }
        Collections.reverse(conversationArrayList);
        conversationListView.setItems(FXCollections.observableList(conversationArrayList));
        conversationListView.setCellFactory(conversationListView -> new ConversationListItem());
    }

    public void showImportant(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(false);
        selectedField = SelectedField.IMPORTANT;
        messageListView.setVisible(true);
        conversationListView.setVisible(false);
        ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getReceiver().getAccountInfo().getUserName().equals(AllUsers.getUserWhoWantToSignIn().getAccountInfo().getUserName()) && a.isImportant() && !a.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(a.getSender())).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(messagesToShowList);
        messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
        messageListView.setCellFactory(messagesListView -> new MessageListItem());
    }

    public void showSent(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(false);
        selectedField = SelectedField.SENT;
        messageListView.setVisible(false);
        conversationListView.setVisible(true);
        ArrayList<Conversation> conversationArrayList = new ArrayList<>();
        for (Conversation i : AllConversations.getAllConversations()) {
            if (i.getTo().equals(AllUsers.getUserWhoWantToSignIn()) || i.getFrom().equals(AllUsers.getUserWhoWantToSignIn())) {
                if (i.getFirstMessage().getSender().equals(AllUsers.getUserWhoWantToSignIn())) {
                    conversationArrayList.add(i);
                }
            }
        }
        Collections.reverse(conversationArrayList);
        conversationListView.setItems(FXCollections.observableList(conversationArrayList));
        conversationListView.setCellFactory(conversationListView -> new ConversationListItem());
    }

    public void showOutbox(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(false);
        selectedField = SelectedField.OUTBOX;
        ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getSender().getAccountInfo().getUserName().equals(searchField.getText()) && a.isPending() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(a.getSender())).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(messagesToShowList);
        messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
        messageListView.setCellFactory(messagesListView -> new MessageListItem());
    }

    public void showDrafts(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(false);
        selectedField = SelectedField.DRAFTS;
//        ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getSender().getAccountInfo().getUserName().startsWith(searchField.getText())).collect(Collectors.toCollection(ArrayList::new));
//        messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
//        messageListView.setCellFactory(messagesListView -> new MessageListItem());
    }

    public void showTrash(MouseEvent mouseEvent) {
        showUnreadButton.setVisible(false);
        selectedField = SelectedField.TRASH;
        messageListView.setVisible(true);
        conversationListView.setVisible(false);
        ArrayList<Message> messagesToShowList = AllMessages.getAllMessages().stream().filter(a -> a.getReceiver().getAccountInfo().getUserName().equals(AllUsers.getUserWhoWantToSignIn().getAccountInfo().getUserName()) && a.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(a.getSender())).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(messagesToShowList);
        messageListView.setItems(FXCollections.observableArrayList(messagesToShowList));
        messageListView.setCellFactory(messagesListView -> new MessageListItem());
    }

    public void refresh(ActionEvent actionEvent) {
        switch (selectedField){
            case INBOX:
                showInbox(null);
                break;
            case IMPORTANT:
                showImportant(null);
                break;
            case SENT:
                showSent(null);
                break;
            case OUTBOX:
                showOutbox(null);
                break;
            case DRAFTS:
                showDrafts(null);
                break;
            case TRASH:
                showTrash(null);
                break;
        }
    }

    public void showUnreadFirst(ActionEvent actionEvent) {
        messageListView.setVisible(false);
        conversationListView.setVisible(true);
        ArrayList<Conversation> conversationArrayList = new ArrayList<>();
        for (Conversation i : AllConversations.getAllConversations()) {
            if (i.getTo().equals(AllUsers.getUserWhoWantToSignIn()) || i.getFrom().equals(AllUsers.getUserWhoWantToSignIn())) {
                if (!i.getFirstMessage().getSender().equals(AllUsers.getUserWhoWantToSignIn())) {
                    conversationArrayList.add(i);
                }
            }
        }
        for (int i = conversationArrayList.size()-1;i>=0;i--) {
            boolean delete = true;
            for (Message j : conversationArrayList.get(i).getMessages()) {
                if (!j.isDeleted() && !AllUsers.getUserWhoWantToSignIn().getBlockList().contains(j.getSender())) {
                    delete = false;
                    break;
                }
            }
            if(delete){
                conversationArrayList.remove(i);
            }
        }
        ArrayList<Conversation> hasUnreadMessage = new ArrayList<>();
        ArrayList<Conversation> noUnreadMessage = new ArrayList<>();
        for (Conversation i : conversationArrayList){
            boolean hasUnreadMsg = false;
            for(Message j : i.getMessages()){
                if(!j.isRead()){
                    hasUnreadMsg = true;
                    break;
                }
            }
            if(hasUnreadMsg){
                hasUnreadMessage.add(i);
            }
            else {
                noUnreadMessage.add(i);
            }
        }
        hasUnreadMessage.addAll(noUnreadMessage);
        Collections.reverse(hasUnreadMessage);
        conversationListView.setItems(FXCollections.observableList(hasUnreadMessage));
        conversationListView.setCellFactory(conversationListView -> new ConversationListItem());
    }
}
