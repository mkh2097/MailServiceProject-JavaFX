package sample.Model.Connection;

import java.io.IOException;

/***
 * Source : Chat Question
 * NO Usage!
 */
public class ClientMessageHandler {

    public static String handle(MessageHandler message) {
        String respond = "";
        switch (message.getMessageType()) {
            case Connect:
                respond = message.getSender()+" connected";
                break;
            case Disconnect:
                respond = message.getSender()+" disconnected";
                break;
        }
        return respond;
    }
}
