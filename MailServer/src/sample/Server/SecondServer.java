package sample.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Read the objects and files that send from client
 */
public class SecondServer {
    private static final int Port = 2097;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Port);
            new DBThread(serverSocket.accept()).start();
    }
}
class DBThread extends Thread{
    private Socket socket;
    private DataInputStream in;
    private FileOutputStream out;

    public DBThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        String fileName = in.readUTF();
        String DB_ROOT = "./src/sample/Database/";
        out = new FileOutputStream(DB_ROOT + fileName);
    }
    @Override
    public void run(){
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