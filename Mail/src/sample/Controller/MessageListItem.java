package sample.Controller;

import javafx.scene.control.ListCell;
import sample.Model.Message;

import java.io.IOException;

public class MessageListItem extends ListCell<Message> {


    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        if(message != null)
        {
            if (!message.isRead()) {
                setStyle("-fx-background-color: #00BFFF");
                try {
                    setGraphic(new MessageListItemController(message).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                setStyle("-fx-background-color: #E0FFFF");
                try {
                    setGraphic(new MessageListItemController(message).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
