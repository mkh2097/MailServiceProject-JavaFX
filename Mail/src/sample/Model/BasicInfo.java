package sample.Model;

import java.io.Serializable;

public class BasicInfo implements Info, Serializable {
    private static final long serialVersionUID = 1;

    private String name;
    private String lastName;
    private Age birthDay;
    private Gender sex;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Age getBirthDay() {
        return birthDay;
    }

    public Gender getSex() {
        return sex;
    }

    public BasicInfo(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDay(Age birthDay) {
        this.birthDay = birthDay;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName()
    {
        return name+" "+lastName;
    }
    @Override
    public String toString() {
        return name+"&&"+lastName+"&&"+birthDay+"&&"+sex+"&&"+phoneNumber;
    }
}
