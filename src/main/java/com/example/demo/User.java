package com.example.demo;

public class User {

    private int phoneNumber;
    private String username;
    private String password;
    private int balance;


    public User(int phoneNumber, String username, String password, int balance) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }





}
