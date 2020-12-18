package com.example.barter;

import java.io.Serializable;

public class TransactionObject implements Serializable {

    private int transactionid;

    public String getProductname2() {
        return productname2;
    }

    public void setProductname2(String productname2) {
        this.productname2 = productname2;
    }

    private String productname2;

    public TransactionObject(int transactionid, int listingid, int offerid, String transactionDate, String productname,String productname2) {
        this.transactionid = transactionid;
        this.listingid = listingid;
        this.offerid = offerid;
        this.transactionDate = transactionDate;
        this.productname = productname;
        this.productname2 = productname2;
    }

    private int listingid;
    private int offerid;
    private String transactionDate;
    private String productname;
    private String listingdetails;

    public int getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(int transactionid) {
        this.transactionid = transactionid;
    }

    public int getListingid() {
        return listingid;
    }

    public void setListingid(int listingid) {
        this.listingid = listingid;
    }

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getListingdetails() {
        return listingdetails;
    }

    public void setListingdetails(String listingdetails) {
        this.listingdetails = listingdetails;
    }

}
