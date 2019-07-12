package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.FxmlLoader;
import sample.Main;
import sample.Model.AgeValidation;
import sample.Model.AllUsers;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;
import sample.Model.User;

import java.io.IOException;

public class SignInController {
    @FXML
    TextField emailAddress;
    @FXML
    TextField passWord;
    @FXML
    Label accountNotFoundError;
    @FXML
    Label incorrectPassWordError;
    Connection connection;

    public void goToWelcome(MouseEvent mouseEvent) throws IOException {
        new FxmlLoader().load("View/WelcomeScreen.fxml");
    }

    public void goToProfile(ActionEvent actionEvent) throws IOException {
        accountNotFoundError.setVisible(false);
        incorrectPassWordError.setVisible(false);
        boolean accountFounded = false;
        User selected = null;
        String searchUser;
//        for(User user : AllUsers.getAllUsers())
//        {
//            if(user.getAccountInfo().getEmailAddress().equalsIgnoreCase(emailAddress.getText()) || user.getAccountInfo().getUserName().equals(emailAddress.getText())){
//                accountFounded = true;
//                selected = user;
//                break;
//            }
//        }
        try {
            if (emailAddress.getText().contains("@fmail.com")){
                searchUser = emailAddress.getText().substring(0, emailAddress.getText().indexOf("@fmail.com"));
            }
            else {
                searchUser = emailAddress.getText();
            }
            selected = AllUsers.readFromFile(searchUser);
            System.out.println(selected.getBasicInfo().getFullName());
            accountFounded = true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (accountFounded) {
            if (selected.getAccountInfo().getPassWord().equals(passWord.getText())) {
                System.out.println(selected.getBasicInfo().getBirthDay());
                if (AgeValidation.ageChecker(selected.getBasicInfo().getBirthDay().toString())) {
                    System.out.println("hi");
                    selected.setActive(true);
                }
                if (selected.isActive()) {
                    AllUsers.setUserWhoWantToSignIn(selected);
                    try {
                        connection = new Connection(selected);
                        connection.sendRequest(new MessageHandler(MessageType.SignIn,selected,null,null));
                    }catch (Exception e){

                    }

                    new FxmlLoader().load("View/UserProfile.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Account is deactivated");
                    alert.setHeaderText("Can't reach your account");
                    alert.setTitle("Account Access Error");
                    alert.showAndWait();
                }
            } else {
                incorrectPassWordError.setVisible(true);
            }
        } else {
            accountNotFoundError.setVisible(true);
        }
    }
}
