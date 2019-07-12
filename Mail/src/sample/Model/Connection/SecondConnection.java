package sample.Model.Connection;

import sample.Model.Message;

import java.io.*;
import java.net.Socket;

/***
 *Second Connection is used for sending File to Server
 */
public class SecondConnection {
    public static final int Port = 2097;
    public static String IPAddress = "localhost";
    public static Socket socket;
    private static InputStream in;
    private static DataOutputStream out;

    public static void setIPAddress(String IPAddress) {
        SecondConnection.IPAddress = IPAddress;
    }

    public static void sendFile(Message message) {
        new Thread(() -> {
            try {
                connect();
                writeData(message);
                disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void writeData(Message message) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        in = new FileInputStream(message.getAttachment());
        File file = new File(message.getAttachment());
        out.writeUTF(file.getName());
        int readBytes;
        byte[] buffer = new byte[2048];
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
            out.flush();
        }

    }

    public static void connect() {
        try {
            socket = new Socket(IPAddress, Port);
        }catch (IOException e){

        }
    }

    private static void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}

