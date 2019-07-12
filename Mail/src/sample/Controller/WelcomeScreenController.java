package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.FxmlLoader;
import sample.Model.AllUsers;
import sample.Model.Connection.Connection;
import sample.Model.Connection.SecondConnection;
import sample.Model.User;

import java.io.IOException;

public class WelcomeScreenController {

    public void signIn(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/SignIn.fxml");
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        new FxmlLoader().load("View/FirstSignUp.fxml");
    }

    public void selectServer(ActionEvent actionEvent) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Server");
        dialog.setHeaderText("Server");
        dialog.setResizable(false);

        Label label1 = new Label("IP Address:");
        TextField ip = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(ip, 2, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Confirm Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (ip.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fields should not be empty!");
                    alert.showAndWait();
                } else {
                    Connection.setIPAddress(ip.getText().trim());
                    SecondConnection.setIPAddress(ip.getText().trim());
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "IP sets successfully to "+ip.getText().trim());
                    success.showAndWait();
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
}
