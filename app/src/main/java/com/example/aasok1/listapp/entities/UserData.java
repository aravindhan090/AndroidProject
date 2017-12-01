package com.example.aasok1.listapp.entities;

import java.io.Serializable;

public class UserData implements Serializable {

    private String userName;
    private String userFullName;
    private String userPassword;
    private String userEmail;
    private String userMobile;

    public UserData(String name, String fullName,String password, String email,String mobile){
        this.userName=name;
        this.userFullName=fullName;
        this.userPassword=password;
        this.userEmail=email;
        this.userMobile=mobile;
    }

    public UserData(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
