package com.example.makepayment.models;

import java.io.Serializable;

public class History implements Serializable {
    public int amount;
    public String date;
    public boolean status;
    public String to;
    public String upiType;
    public String address;
}
