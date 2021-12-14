package com.example.demo;

public class Request {


    private int transactionId;
    private int senderOfRequests;
    private int receiverOfRequests;
    private  String dateTime;
    private int amount;


    public Request(int transactionId, int senderOfRequests, int receiverOfRequests, int amount, String dateTime) {
        this.transactionId = transactionId;
        this.senderOfRequests = senderOfRequests;
        this.receiverOfRequests = receiverOfRequests;
        this.amount = amount;
        this.dateTime = dateTime;

    }


    public int getTransactionId() {return transactionId;}
    public void setTransactionId(int transactionId) {this.transactionId = transactionId;}

    public int getSenderOfRequests() {return senderOfRequests;}
    public void setSenderOfRequests(int senderOfRequests) {this.senderOfRequests = senderOfRequests;}

    public int getReceiverOfRequests() {return receiverOfRequests;}
    public void setReceiverOfRequests(int receiverOfRequests) {this.receiverOfRequests = receiverOfRequests;}

    public int getAmount() {return amount;}
    public void setAmount(int amount) {this.amount = amount;}

    public String getDateTime() {return dateTime;}
    public void setDateTime(String dateTime) {this.dateTime = dateTime;}


}
