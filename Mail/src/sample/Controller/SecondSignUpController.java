package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sample.FxmlLoader;
import sample.Model.*;

import java.io.IOException;

public class SecondSignUpController {

    @FXML
    TextField day;
    @FXML
    TextField month;
    @FXML
    TextField year;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    RadioButton nonBinary;
    @FXML
    TextField countryCode;
    @FXML
    TextField phoneNumber;
    @FXML
    Label emptyFieldError;
    @FXML
    Label ageError;
    @FXML
    Button ageErrorButton;

    static int userFinder = 0;
    public void goToPhoto(ActionEvent actionEvent) throws IOException {
        emptyFieldError.setVisible(false);
        ageError.setVisible(false);
        male.setSelected(false);
        female.setSelected(false);
        nonBinary.setSelected(false);

        if(day.getText().isEmpty() || month.getText().isEmpty() || year.getText().isEmpty() || countryCode.getText().isEmpty() || phoneNumber.getText().isEmpty())
        {
            emptyFieldError.setVisible(true);
        }
        else {
            AllUsers.getLastUserCreated().getBasicInfo().setBirthDay(new Age(Integer.parseInt(day.getText()),Integer.parseInt(month.getText()),Integer.parseInt(year.getText())));
            AllUsers.getLastUserCreated().getBasicInfo().setPhoneNumber("+"+countryCode.getText()+phoneNumber.getText());
            if (!AgeValidation.ageChecker(day.getText() + "/" + month.getText() + "/" + year.getText())) {
                AllUsers.getLastUserCreated().setActive(false);
                ageError.setVisible(true);
                ageErrorButton.setVisible(true);
            }
            else {
                AllUsers.getLastUserCreated().setActive(true);
//                System.out.println(User.getLastUserCreated().getAccountInfo());
//                System.out.println(User.getLastUserCreated().getBasicInfo());
                new FxmlLoader().load("View/AddPhoto.fxml");
            }
        }
    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/WelcomeScreen.fxml");
    }

    public void maleSelected(ActionEvent actionEvent) {
        AllUsers.getLastUserCreated().getBasicInfo().setSex(Gender.MALE);
        female.setSelected(false);
        nonBinary.setSelected(false);
    }

    public void femaleSelected(ActionEvent actionEvent) {
        AllUsers.getLastUserCreated().getBasicInfo().setSex(Gender.FEMALE);
        male.setSelected(false);
        nonBinary.setSelected(false);
    }

    public void nonBinarySelected(ActionEvent actionEvent) {
        AllUsers.getLastUserCreated().getBasicInfo().setSex(Gender.NONBINARY);
        male.setSelected(false);
        female.setSelected(false);
    }
}
