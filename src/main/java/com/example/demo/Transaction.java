package com.example.demo;

import java.sql.Date;
import java.util.Comparator;

public class Transaction {

    // in the case it is a request the sender number belongs to one who send the request
    // and the receiver number the one who received the request and gets to process it
    // The same transaction between 2 ppl uses the same transaction id, so if I send money to a person
    // that transaction has the same id on my end as it has on his even tho it will look different

    private int transitionId;
    private String transactionType;
    private int senderNumber;
    private int receiverNumber;
    private int amount;
    private String date;

    public Transaction(int transitionId, String transactionType, int senderNumber, int receiverNumber, int amount, String date) {
        this.transitionId = transitionId;
        this.transactionType = transactionType;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.amount = amount;
        this.date = date;
    }

    public static Comparator<Transaction> transitionid = (o1, o2) -> {

        int transitionId1 = o1.getTransitionId();
        int transitionId2 = o2.getTransitionId();

        return  transitionId2 - transitionId1;
    };



    public int getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(int transitionId) {
        this.transitionId = transitionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(int senderNumber) {
        this.senderNumber = senderNumber;
    }

    public int getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(int receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
