package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.FxmlLoader;
import sample.Model.AllUsers;
import sample.Model.User;

import java.io.IOException;
import java.nio.file.Paths;

public class UserListItemController {
    public User user;

    @FXML
    AnchorPane root;
    @FXML
    Label name;
    @FXML
    ImageView profilePic;

    public UserListItemController(User user) throws IOException {
        this.user = user;
        new FxmlLoader().load("View/UserListItem.fxml", this);
    }

    public AnchorPane init() {
        name.setText(AllUsers.getUserWhoWantToSignIn().getBasicInfo().getFullName());
        profilePic.setImage(new Image(Paths.get(AllUsers.getUserWhoWantToSignIn().getImageFileName()).toUri().toString()));
        return root;
    }
}
