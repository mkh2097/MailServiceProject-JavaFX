package sample.Controller;

import javafx.scene.control.ListCell;
import sample.Model.Conversation;
import sample.Model.Message;

import java.io.IOException;

public class ConversationListItem extends ListCell<Conversation> {


    @Override
    protected void updateItem(Conversation conversation, boolean empty) {
        super.updateItem(conversation, empty);
        if(conversation != null)
        {
            boolean hasUnreadMessage = false;
            for(Message i : conversation.getMessages()){
                if(!i.isRead()){
                    hasUnreadMessage = true;
                    break;
                }
            }
            if (hasUnreadMessage) {
                setStyle("-fx-background-color: #00BFFF");
                try {
                    setGraphic(new ConversationListItemController(conversation).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                setStyle("-fx-background-color: #E0FFFF");
                try {
                    setGraphic(new ConversationListItemController(conversation).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
