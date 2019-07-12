package sample.Server;

import sample.Model.AllUsers;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * The Server for connecting all users and moderating them
 */
public class Server implements Runnable {
    public static final int requestPort = 2374;
    public static final String serverIP = "localhost";
    private static ServerSocket requestServerSocket;
    public static final String USERS_FILE_URL = "./src/sample/resources/users.ser";


    public static void main(String[] args) {
        Server.start();
    }

    public static void start() {
        try {

            requestServerSocket = new ServerSocket(requestPort);
            Thread serverThread = new Thread(new Server(), "Server Thread");
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!requestServerSocket.isClosed()) {
            try {
                new Thread(new ServerRunner(requestServerSocket.accept()), "Server Runner").start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/***
 * The server for tracking users' actions
 */
class ServerRunner implements Runnable {
    private Socket serverSocket;
    private ServerHandler serverHandler;

    public ServerRunner(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        MessageHandler clientRequest = null;
        try {
            serverHandler = new ServerHandler(serverSocket,
                    new ObjectInputStream(serverSocket.getInputStream()),
                    new ObjectOutputStream(serverSocket.getOutputStream()));

            while (clientRequest == null || clientRequest.getMessageType() != MessageType.Disconnect) {
                clientRequest = (MessageHandler) serverHandler.getInputStream().readObject();
                serverHandler.handle(clientRequest);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
//            e.printStackTrace();
        } finally {
            userDisconnect();
        }
    }

    private void userDisconnect() {
        try {
            serverHandler.getOutputStream().close();
            serverHandler.getInputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

