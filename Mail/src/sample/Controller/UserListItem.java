package sample.Controller;

import javafx.scene.control.ListCell;
import sample.Model.Message;
import sample.Model.User;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class UserListItem extends ListCell<User> {
    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);

                setStyle("-fx-background-color: #03ab4f");
                try {
                    setGraphic(new UserListItemController(user).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


}
