package com.example.college.Databases;

public class UserHelperClass {

    String fullName,userName,eMail,phoneNumber,passWord,date,gender;

    public UserHelperClass(){}

    public UserHelperClass(String fullName, String userName, String eMail, String phoneNumber, String passWord, String date, String gender) {
        this.fullName = fullName;
        this.userName = userName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.date = date;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
