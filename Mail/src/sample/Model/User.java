package sample.Model;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {

    private static final long serialVersionUID = 1;

    private String imageFileName;
    private BasicInfo basicInfo;
    private AccountInfo accountInfo;
    private Set<User> blockList = new HashSet<>();

    private boolean isActive = false;

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User(BasicInfo basicInfo, AccountInfo accountInfo) {
        this.basicInfo = basicInfo;
        this.accountInfo = accountInfo;
        AllUsers.getAllUsers().add(this);
        this.imageFileName = "./src/sample/Resources/img_299586.png";
        AllUsers.setLastUserCreated(this);
    }



    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof User){
            return ((User) obj).getAccountInfo().getUserName().equals(this.getAccountInfo().getUserName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAccountInfo().getUserName());
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public String getImageFileName() {
        return imageFileName;
    }

//    public String getImageUrl() {
//        return IMAGE_URL_ROOT + imageFileName;
//    }


    public Set<User> getBlockList() {
        return blockList;
    }

    public void setBlockList(Set<User> blockList) {
        this.blockList = blockList;
    }

    @Override
    public String toString() {
        return basicInfo + "   "+accountInfo;
    }
}
