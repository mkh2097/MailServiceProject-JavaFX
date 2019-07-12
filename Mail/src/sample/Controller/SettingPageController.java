package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.FxmlLoader;
import sample.Model.*;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingPageController {
    @FXML
    AnchorPane anchor;
    @FXML
    ListView<User> blockedUsersList;

Connection connection;
    public void goToProfile(ActionEvent actionEvent) throws IOException {
        anchor.setVisible(false);
        new FxmlLoader().load("View/UserProfile.fxml");
    }

    public void changeFullName(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("FullName Changer");
        dialog.setHeaderText("Change Your Name");
        dialog.setResizable(false);

        Label label1 = new Label("First Name:");
        Label label2 = new Label("Last Name:");
        TextField name = new TextField();
        TextField lastName = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(label2, 1, 2);
        grid.add(name, 2, 1);
        grid.add(lastName, 2, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (name.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fields should not be empty!");
                    alert.showAndWait();
                } else {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setName(name.getText().trim());
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setLastName(lastName.getText().trim());
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "FullName Changed Successfully!");
                    success.showAndWait();
                }
            }
            return null;
        });
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());

    }


    public void changeGender(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Gender Changer");
        dialog.setHeaderText("Change Your Gender");
        dialog.setResizable(false);

        Label label1 = new Label("Select Your Gender:");
        Text maleT = new Text("Male:");
        Text femaleT = new Text("Female:");
        Text nonBinaryT = new Text("nonBinary:");
        RadioButton male = new RadioButton();
        RadioButton female = new RadioButton();
        RadioButton nonBinary = new RadioButton();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(maleT, 1, 2);
        grid.add(femaleT, 1, 3);
        grid.add(nonBinaryT, 1, 4);
        grid.add(male, 2, 2);
        grid.add(female, 2, 3);
        grid.add(nonBinary, 2, 4);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (!(male.isSelected() || female.isSelected() || nonBinary.isSelected())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Select at least one item!");
                    alert.showAndWait();
                } else if (male.isSelected() && !(female.isSelected() || nonBinary.isSelected())) {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setSex(Gender.MALE);
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Gender Changed Successfully!");
                    success.showAndWait();
                } else if (female.isSelected() && !(male.isSelected() || nonBinary.isSelected())) {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setSex(Gender.FEMALE);
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Gender Changed Successfully!");
                    success.showAndWait();
                } else if (nonBinary.isSelected() && !(male.isSelected() || female.isSelected())) {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setSex(Gender.NONBINARY);
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Gender Changed Successfully!");
                    success.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Select just one item!");
                    alert.showAndWait();
                }
            }
            return null;
        });
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());

    }


    public void changeBirthday(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Alert information = new Alert(Alert.AlertType.WARNING, "Be Aware! Changing this field may deactivate your account!");
        information.setTitle("Warning");
        information.setResizable(false);
        information.setHeaderText("Read this carefully!");
        information.showAndWait();
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("BirthDay Changer");
        dialog.setHeaderText("Change Your BirthDay Info");
        dialog.setResizable(false);

        Label label1 = new Label("Year:");
        Label label2 = new Label("Month:");
        Label label3 = new Label("Day:");
        TextField year = new TextField();
        TextField month = new TextField();
        TextField day = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(label2, 1, 2);
        grid.add(label3, 1, 3);
        grid.add(year, 2, 1);
        grid.add(month, 2, 2);
        grid.add(day, 2, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (year.getText().trim().isEmpty() || month.getText().trim().isEmpty() || day.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fields should not be empty!");
                    alert.showAndWait();
                } else {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setBirthDay(new Age(Integer.parseInt(day.getText()), Integer.parseInt(month.getText()), Integer.parseInt(year.getText())));
                    if (!AgeValidation.ageChecker(AllUsers.getUserWhoWantToSignIn().getBasicInfo().getBirthDay().toString())) {
                        Alert deactivation = new Alert(Alert.AlertType.ERROR, "Sorry Your Account Deactivated");
                        deactivation.showAndWait();
                        try {
                            new FxmlLoader().load("View/WelcomeScreen.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Alert success = new Alert(Alert.AlertType.INFORMATION, "BirthDay Changed Successfully!");
                        success.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());

    }

    public void changePhoneNumber(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("PhoneNumber Changer");
        dialog.setHeaderText("Change Your PhoneNumber");
        dialog.setResizable(false);

        Label label1 = new Label("Country Code:");
        Label label2 = new Label("Phone Number");
        TextField countryCode = new TextField();
        TextField phoneNumber = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(label2, 1, 2);
        grid.add(countryCode, 2, 1);
        grid.add(phoneNumber, 2, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (countryCode.getText().trim().isEmpty() || phoneNumber.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fields should not be empty!");
                    alert.showAndWait();
                } else {
                    AllUsers.getUserWhoWantToSignIn().getBasicInfo().setPhoneNumber(countryCode.getText().trim() + phoneNumber.getText().trim());
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "PhoneNumber Changed Successfully!");
                    success.showAndWait();
                }
            }
            return null;
        });
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());

    }

    public void changePassWord(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Password Changer");
        dialog.setHeaderText("Change Your Password");
        dialog.setResizable(false);

        Label label1 = new Label("Current Password:");
        Label label2 = new Label("New Password:");
        Label label3 = new Label("Confirm New Password:");
        PasswordField currentPassword = new PasswordField();
        PasswordField newPassword = new PasswordField();
        PasswordField confirmPassword = new PasswordField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(label2, 1, 2);
        grid.add(label3, 1, 3);
        grid.add(currentPassword, 2, 1);
        grid.add(newPassword, 2, 2);
        grid.add(confirmPassword, 2, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (currentPassword.getText().isEmpty() || newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fields should not be empty!");
                    alert.showAndWait();
                } else {
                    if (!AllUsers.getUserWhoWantToSignIn().getAccountInfo().getPassWord().equals(currentPassword.getText())) {
                        Alert currentPassError = new Alert(Alert.AlertType.ERROR, "Current Password is Wrong!");
                        currentPassError.showAndWait();
                    } else if (!confirmPassword.getText().equals(newPassword.getText())) {
                        Alert currentPassError = new Alert(Alert.AlertType.ERROR, "Passwords not match!");
                        currentPassError.showAndWait();
                    } else if (!PasswordValidation.checkString(newPassword.getText())) {
                        Alert currentPassError = new Alert(Alert.AlertType.ERROR, "Weak Password!");
                        currentPassError.showAndWait();
                    } else {
                        AllUsers.getUserWhoWantToSignIn().getAccountInfo().setPassWord(newPassword.getText());
                        Alert success = new Alert(Alert.AlertType.INFORMATION, "Password Changed Successfully!");
                        success.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());
    }


    public void goToWelcome(ActionEvent actionEvent) throws IOException {
        Alert signOut = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure for leaving your account?");
        signOut.setTitle("SignOut");
        signOut.setHeaderText("SignOut");
        signOut.showAndWait();
        if (signOut.getResult() == ButtonType.OK) {
            new FxmlLoader().load("View/WelcomeScreen.fxml");
        }
        AllMessages.writeToFile(AllMessages.getAllMessages());
        AllConversations.writeToFile(AllConversations.getAllConversations());
    }

    public void changeProfilePic(ActionEvent actionEvent) {
        anchor.setVisible(false);
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Photo Changer");
        dialog.setHeaderText("Change Your Photo");
        dialog.setResizable(false);


        Button changePhoto = new Button("Choose Photo");

        GridPane grid = new GridPane();
        grid.add(changePhoto, 1, 3);

        changePhoto.setOnAction(b -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please Select Image");
            fileChooser.setInitialDirectory(new File("./src/sample/Resources"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
                    , new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
            );
            File selectedFile = fileChooser.showOpenDialog(FxmlLoader.getMainStage());
            if (selectedFile != null) {
                String path = "." + selectedFile.toURI().toString().substring(selectedFile.toURI().toString().indexOf("/src"));
                AllUsers.getUserWhoWantToSignIn().setImageFileName(path);
            }
        });

        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.showAndWait();
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());

    }

    public void manageBlockList(ActionEvent actionEvent) {
        anchor.setVisible(true);
        System.out.println(AllUsers.getUserWhoWantToSignIn().getAccountInfo().getUserName());
        List<User> blockList = new ArrayList<>(AllUsers.getUserWhoWantToSignIn().getBlockList());
        System.out.println(blockList.size());
        blockedUsersList.setItems(FXCollections.observableList(blockList));
        blockedUsersList.setCellFactory(blockedUsersList -> new UserListItem());
        blockedUsersList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                User currentItemSelected = blockedUsersList.getSelectionModel()
                        .getSelectedItem();
                Alert unblock = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure for unblocking "+currentItemSelected.getBasicInfo().getFullName()+" ?");
                unblock.setTitle("SignOut");
                unblock.setHeaderText("SignOut");
                unblock.showAndWait();
                if(unblock.getResult() == ButtonType.OK){
                    AllUsers.getUserWhoWantToSignIn().getBlockList().remove(currentItemSelected);
                    try {
                        connection = new Connection(AllUsers.getUserWhoWantToSignIn());
                        connection.sendRequest(new MessageHandler(MessageType.Unblock,AllUsers.getUserWhoWantToSignIn(),currentItemSelected,null));
                    }catch (Exception e){

                    }
                }
            }
        });
    }
}

