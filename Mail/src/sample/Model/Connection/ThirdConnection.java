package sample.Model.Connection;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Initialize a connection between server and client
 */
public class ThirdConnection {
    private static final int PORT = 1379;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        new DBThread(server.accept()).start();
    }
}
/***
 * This Class is used to read Object that are in Server
 * It's used for downloading operation in client mode
 */
class DBThread extends Thread {
    private Socket socket;
    private DataInputStream in;
    private OutputStream out;

    public DBThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        String fileName = in.readUTF();
        String DB_ROOT = "./src/sample/Resources/DownloadedFiles/";
        out = new FileOutputStream(DB_ROOT + fileName);
    }

    @Override
    public void run() {
        try {
            int readBytes;
            byte[] buffer = new byte[2048];
            while ((readBytes = in.read(buffer)) > 0) {
                out.write(buffer, 0, readBytes);
                out.flush();
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
