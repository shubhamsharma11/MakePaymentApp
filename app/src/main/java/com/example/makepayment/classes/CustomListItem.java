package com.example.makepayment.classes;

import java.util.Date;

public class CustomListItem {
    public String amount;
    public int imgId;
    public Date date;

    public CustomListItem(String amount, int imgId, Date date) {
        this.amount = amount;
        this.imgId = imgId;
        this.date = date;
    }
}
