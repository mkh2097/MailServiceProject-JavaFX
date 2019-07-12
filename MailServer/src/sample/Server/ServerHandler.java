package sample.Server;

import sample.Model.CurrentDateAndTime;
import sample.Model.User;
import sample.Model.Connection.MessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/***
 * handle all users' actions
 */
public class ServerHandler {
    private static List<User> usersList = new ArrayList<User>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private static Semaphore semaphore = new Semaphore(1);
    ServerHandler(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    void handle(MessageHandler handler) throws IOException, InterruptedException {
        final String DB_ROOT = ".\\src\\sample\\Database\\";
        switch (handler.getMessageType()) {
            case Connect:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " connect\n" +"time: "+ CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case SignIn:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " signin\n" +"time: "+ CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Register:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " register "+handler.getSender().getImageFileName()+"\ntime: "+ CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Receive:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" receive");
                System.out.println("message: "+handler.getMessage().getSender().getAccountInfo().getUserName()+" "+handler.getMessage().getAttachment());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Send:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" send");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getAttachment()+" to "+handler.getMessage().getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Reply:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" reply");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getAttachment()+" to "+handler.getMessage().getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Forward:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" forward");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getAttachment()+" from "+handler.getMessage().getSender().getAccountInfo().getUserName()+" to "+handler.getMessage().getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Read:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " mark");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getSender().getAccountInfo().getUserName()+" as "+"read");
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Unread:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " mark");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getSender().getAccountInfo().getUserName()+" as "+"unread");
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Important:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " mark");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getSender().getAccountInfo().getUserName()+" as "+"important");
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Unimportant:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+ " mark");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getSender().getAccountInfo().getUserName()+" as "+"unimportant");
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Block:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" block "+handler.getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case Unblock:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" unblock "+handler.getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case RemoveMSG:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" removemsg");
                System.out.println("message: "+handler.getMessage().getSubject()+" "+handler.getMessage().getSender().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case RemoveCONV:
                semaphore.acquire();
                System.out.println(handler.getSender().getAccountInfo().getUserName()+" removeconv "+handler.getMessage().getReceiver().getAccountInfo().getUserName());
                System.out.println("time: "+CurrentDateAndTime.getCurrentDate()+" "+CurrentDateAndTime.getCurrentTime());
                semaphore.release();
                break;
            case SignOut:
                semaphore.acquire();
                System.out.println(handler.getSender()+" signout");
                semaphore.release();
                break;
            case Disconnect:
                semaphore.acquire();
                System.out.println("disconnect");
                semaphore.release();
                break;
            case RequestFile:
                semaphore.acquire();
                ThirdServer.sendFile(handler.getMessage());
                semaphore.release();
                break;

        }
    }
    public void close() throws IOException {
        outputStream.close();
    }
}
