package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sample.FxmlLoader;
import sample.Model.AllUsers;
import java.io.File;
import java.io.IOException;


public class AddPhotoController {
    @FXML
    ImageView imageViewer;
    @FXML
    Button finalButton;
    @FXML
    Button changePhoto;


    public void goToProfile(ActionEvent actionEvent) throws IOException {
        AllUsers.getLastUserCreated().setImageFileName("./src/sample/Resources/img_299586.png");
        AllUsers.setUserWhoWantToSignIn(AllUsers.getLastUserCreated());
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());
        new FxmlLoader().load("View/UserProfile.fxml");

    }

    public void addPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Select Image");
        fileChooser.setInitialDirectory(new File("./src/sample/Resources"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
                , new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(FxmlLoader.getMainStage());
        if (selectedFile != null) {
            finalButton.setVisible(true);
            changePhoto.setVisible(true);
            String path = "." + selectedFile.toURI().toString().substring(selectedFile.toURI().toString().indexOf("/src"));
            imageViewer.setImage(new Image(selectedFile.toURI().toString()));
            AllUsers.getLastUserCreated().setImageFileName(path);
        }
    }

    public void goToProfileWithPicture(ActionEvent actionEvent) throws IOException {
        AllUsers.setUserWhoWantToSignIn(AllUsers.getLastUserCreated());
        AllUsers.writeToFile(AllUsers.getUserWhoWantToSignIn());
        new FxmlLoader().load("View/UserProfile.fxml");
    }
}
