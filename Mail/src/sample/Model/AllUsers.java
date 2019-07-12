package sample.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AllUsers {
    private static final String USERS_FILE_URL = "./scr/main/Resources/users.ser";
    private static List<User> ALL_USERS = new ArrayList<>();
    static User lastUserCreated;
    static User userWhoWantToSignIn;

    public static List<User> getAllUsers() {
        return ALL_USERS;
    }

    public static void setAllUsers(List<User> allUsers) {
        ALL_USERS = allUsers;
    }

    public static void init() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USERS_FILE_URL));
        ALL_USERS = (List<User>) objectInputStream.readObject();
        objectInputStream.close();
    }

    public static User getLastUserCreated() {
        return lastUserCreated;
    }

    public static void setLastUserCreated(User lastUserCreated) {
        AllUsers.lastUserCreated = lastUserCreated;
    }

    public static void writeToFile(User user) {
        FileOutputStream fileOutputStream;
        try {
            File file = new File("./src/sample/Resources/Users/" + user.getAccountInfo().getUserName() + ".ser");
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);


            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User readFromFile(String username) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream;
        User user = null;
            fileInputStream = new FileInputStream("./src/sample/Resources/Users/" + username + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();

        return user;
    }

    public static User getUserWhoWantToSignIn() {
        return userWhoWantToSignIn;
    }

    public static void setUserWhoWantToSignIn(User userWhoWantToSignIn) {
        AllUsers.userWhoWantToSignIn = userWhoWantToSignIn;
    }
}
