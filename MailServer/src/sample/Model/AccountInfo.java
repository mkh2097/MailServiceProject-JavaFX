package sample.Model;

import java.io.Serializable;

public class AccountInfo implements Info, Serializable {
    private static final long serialVersionUID = 1;

    private String userName;
    private String passWord;
    private String emailAddress;

    public AccountInfo(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        emailAddress = userName + "@fmail.com";
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return userName+"&&"+passWord+"&&"+emailAddress;
    }
}
