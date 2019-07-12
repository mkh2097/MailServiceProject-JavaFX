package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.FxmlLoader;
import sample.Main;
import sample.Model.*;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;

import java.io.IOException;
import java.util.Random;

public class FirstSignUpController {
    @FXML
    TextField name;
    @FXML
    TextField lastName;
    @FXML
    TextField userName;
    @FXML
    PasswordField passWord;
    @FXML
    PasswordField confirmedPassWord;
    @FXML
    Label userNameError;
    @FXML
    Label weakPassWordError1;
    @FXML
    Label weakPassWordError2;
    @FXML
    Label equalPassWordError;
    @FXML
    Label emptyFieldError;
    @FXML
    Hyperlink fuzzyUserName;
    @FXML
    Hyperlink formalUserName;
    Connection connection;


    public void goToWelcome(MouseEvent mouseEvent) throws IOException {
        new FxmlLoader().load("View/WelcomeScreen.fxml");
    }

    public void goToComplete(ActionEvent actionEvent) throws IOException {
        userNameError.setVisible(false);
        weakPassWordError1.setVisible(false);
        weakPassWordError2.setVisible(false);
        equalPassWordError.setVisible(false);
        emptyFieldError.setVisible(false);
        boolean duplicatedUser = false;
        boolean equalPassWords = false;
        boolean weakPassWord = true;
        boolean emptyField = false;
        if (name.getText().isEmpty() || lastName.getText().isEmpty() || userName.getText().isEmpty() ||  passWord.getText().isEmpty() || confirmedPassWord.getText().isEmpty())
        {
            emptyField = true;
        }
//        for (User user : AllUsers.getAllUsers()) {
//            if (userName.getText().trim().equalsIgnoreCase(user.getAccountInfo().getUserName())) {
//                duplicatedUser = true;
//            }
//        }
        try {
            AllUsers.readFromFile(userName.getText().trim());
            duplicatedUser = true;

        } catch (ClassNotFoundException | IOException e) {
            //ignore it
        }

        if (passWord.getText().equals(confirmedPassWord.getText())) {
            equalPassWords = true;
        }
        if (PasswordValidation.checkString(passWord.getText())) {
            weakPassWord = false;
        }
        if (emptyField)
        {
            emptyFieldError.setVisible(true);
        }
        else {
            if (duplicatedUser || !EmailValidation.emailValidator(userName.getText())) {
                userNameError.setVisible(true);
            }
            if (!equalPassWords) {
                equalPassWordError.setVisible(true);
            }
            if (weakPassWord) {
                weakPassWordError1.setVisible(true);
                weakPassWordError2.setVisible(true);
            }
            if (!duplicatedUser && equalPassWords && !weakPassWord) {
                User registered = new User(new BasicInfo(name.getText(), lastName.getText()), new AccountInfo(userName.getText(), passWord.getText()));
                try {
                    connection = new Connection(registered);
                    connection.sendRequest(new MessageHandler(MessageType.Register,registered,null,null));
                }catch (Exception e){

                }
                new FxmlLoader().load("View/SecondSignUp.fxml");
            }
        }
    }

    public void userNameGenerator(ActionEvent actionEvent) {
        if(!(name.getText().trim().isEmpty() || lastName.getText().trim().isEmpty())){
            Random randomNumber = new Random();
            int rand_int = randomNumber.nextInt(1000);
            formalUserName.setText(name.getText().toLowerCase().charAt(0)+"."+lastName.getText().toLowerCase().split(" ")[0]+ rand_int);

            String[] nameSplit = name.getText().toLowerCase().split(" ");
            String[] lastNameSplit = lastName.getText().toLowerCase().split(" ");
            StringBuilder theUserName = new StringBuilder();
            for(String i : nameSplit){
                if(i.length() >= 1) {
                    theUserName.append(i.charAt(0));
                }
            }
            char[] symbol = new char[]{'+','-','_','.'};
            int arrayRandom = randomNumber.nextInt(symbol.length-1);
            char theSelectedChar = symbol[arrayRandom];
            theUserName.append(theSelectedChar);
            for (String i : lastNameSplit){
                if(i.length() >= 2) {
                    theUserName.append(i.charAt(0));
                    theUserName.append(i.charAt(1));
                }
                else if(i.length() >=1){
                    theUserName.append(i.charAt(0));
                }
            }
            int rand_int2 = randomNumber.nextInt(10000);
            fuzzyUserName.setText(theUserName.toString()+ rand_int2);
            formalUserName.setVisible(true);
            fuzzyUserName.setVisible(true);
        }
    }

    public void setFuzzyUserName(ActionEvent actionEvent) {
        userName.setText(fuzzyUserName.getText());
    }

    public void setFormalUserName(ActionEvent actionEvent) {
        userName.setText(formalUserName.getText());
    }
}

