package sample.Model.Connection;


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
