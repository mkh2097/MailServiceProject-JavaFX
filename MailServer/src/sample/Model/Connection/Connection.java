package sample.Model.Connection;

import sample.Model.AllUsers;
import sample.Model.Message;
import sample.Model.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection
{
    private User currentUser;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static final int Port = 2374;
    private static final String IPAddress = "localhost";

    public Connection(User currentUsername) {
        this.currentUser = currentUsername;
        try
        {
            client = new Socket(IPAddress,Port);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            sendRequest(new MessageHandler(MessageType.Disconnect, currentUser, null, null));
        } catch (IOException e)
        {
            throw new ServerConnectionException();
        }
    }

    public void disconnect() throws IOException {
        sendRequest(new MessageHandler(MessageType.Disconnect, currentUser, null, null));
        in.close();;
        out.close();
        client.close();
    }

    public void initializeServices() {
        Thread listenerThread = new Thread(new ListenerService(this), "Listener Thread");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void sendRequest(MessageHandler request) {
        try
        {
            out.writeObject(request);
        } catch (IOException e)
        {
            throw new ServerConnectionException();
        }
    }

    public void sendText(Message textMessage, User receiver) {
        sendRequest(new MessageHandler(MessageType.Send, currentUser, receiver, textMessage));
    }

    public void getRespond() {
        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./src/sample//Resources/users.ser"));
            objectOutputStream.writeObject(AllUsers.getAllUsers());
            objectOutputStream.close();

        } catch (IOException e)
        {
            throw new ServerConnectionException();
        }
    }
}

