package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Model.*;
import sample.Model.Connection.Connection;
import sample.Model.Connection.MessageHandler;
import sample.Model.Connection.MessageType;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static Connection connection;
    private static User user = new User(null,null);

    public static Connection getConnection() {
        return connection;
    }

    public static User getUser() {
        return user;
    }

    public static void setConnection(Connection connection) {
        Main.connection = connection;
    }

    public static void setUser(User user) {
        Main.user = user;
    }

    @Override
    public void init() {
        try {
            AllMessages.setAllMessages(AllMessages.readFromFile());
            AllConversations.setAllConversations(AllConversations.readFromFile());
        }catch (Exception e){

        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //init stage using FXMLLoader
        FxmlLoader.setMainStage(primaryStage);
        new FxmlLoader().load("View/WelcomeScreen.fxml");
    }

    @Override
    public void stop() {
        AllMessages.writeToFile(AllMessages.getAllMessages());
        AllConversations.writeToFile(AllConversations.getAllConversations());

    }

    public static void main(String[] args) {
//        List<User> userList = new ArrayList<>();
//        User ME = new User(new BasicInfo("Mohammad","Khoddam"),new AccountInfo("mkh2097","mkh2097A"));
//        userList.add(ME);
//        AllUsers.setUserWhoWantToSignIn(ME);
//        userList.add(new User(new BasicInfo("Hamed","KH"),new AccountInfo("hamed2097","mkh2097A")));
//        userList.add(new User(new BasicInfo("Matin","ZI"),new AccountInfo("matin2097","mkh2097A")));
//        userList.add(new User(new BasicInfo("Khar","hoooo"),new AccountInfo("khar2097","mkh2097A")));
//        List<Message> messages = new ArrayList<>();
//        messages.add(new Message(AllUsers.getAllUsers().get(0), AllUsers.getAllUsers().get(2),"salam", "This Should not be Shown", "Go and F*** yourself"));
//        messages.add(new Message(AllUsers.getAllUsers().get(0), AllUsers.getAllUsers().get(1),"ih", "Hala FIFA", "Lanat b PES!!!"));
//        messages.add(new Message(AllUsers.getAllUsers().get(1), AllUsers.getAllUsers().get(0),"hola", "Save Message", "I want to save this message"));
//        messages.add(new Message(AllUsers.getAllUsers().get(2), AllUsers.getAllUsers().get(0),"hoooo", "Alan", "Darim mirim k ronaldo ro pack kninm."));
//        messages.add(new Message(AllUsers.getAllUsers().get(3), AllUsers.getAllUsers().get(0), "tokmi", "Test", "This is just for testing."));
        launch(args);
    }
}
