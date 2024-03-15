package com.example.practise.Model;


public class User {

    private int userId;
    private int oweAmount;

    public User(int userId, int oweAmount){
        this.userId=userId;
        this.oweAmount=oweAmount;
    }

    // Getter method for userId
    public int getUserId() {
        return userId;
    }

    // Setter method for userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter method for oweAmount
    public int getOweAmount() {
        return oweAmount;
    }

    // Setter method for oweAmount
    public void setOweAmount(int oweAmount) {
        this.oweAmount = oweAmount;
    }
}
    