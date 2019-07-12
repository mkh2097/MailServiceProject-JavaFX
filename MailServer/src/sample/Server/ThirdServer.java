package sample.Server;

import sample.Model.Message;

import java.io.*;
import java.net.Socket;

/***
 * write data to server's output so we can use it in client
 */
public class ThirdServer {
    public static final int Port = 1379;
    public static final String IPAddress = "localhost";
    public static Socket socket;
    private static InputStream in;
    private static DataOutputStream out;
    private static String DB_ROOT = "./src/sample/Database/";

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
        in = new FileInputStream(DB_ROOT+message.getAttachment().substring(message.getAttachment().lastIndexOf('\\')+1));
//        System.err.println(DB_ROOT+message.getAttachment().substring(message.getAttachment().lastIndexOf('\\')+1));
        File file = new File(DB_ROOT+message.getAttachment().substring(message.getAttachment().lastIndexOf('\\')+1));
//        System.out.println(file.getName());
        out.writeUTF(file.getName());
        int readBytes;
        byte[] buffer = new byte[2048];
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
            out.flush();
        }

    }

    public static void connect() throws IOException {
        socket = new Socket(IPAddress, Port);
    }

    private static void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}

