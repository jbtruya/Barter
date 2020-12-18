package com.example.barter;

import java.io.Serializable;

public class OfferObject implements Serializable {

    private int offerid;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String image_url;

    public OfferObject(int offerid, int listingid, int accountid, int imageid, String productname, String listingdetails, String dateoffered, String offerstatus, String phoneNumber, String firstname, String middlename, String lastname, String userimage, String image_url) {
        this.offerid = offerid;
        this.listingid = listingid;
        this.accountid = accountid;
        this.imageid = imageid;
        this.productname = productname;
        this.listingdetails = listingdetails;
        this.dateoffered = dateoffered;
        this.offerstatus = offerstatus;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.userimage = userimage;
        this.image_url = image_url;
    }

    private int listingid;
    private int accountid;
    private int imageid;
    private String productname;
    private String listingdetails;
    private String dateoffered;
    private String offerstatus;
    private String phoneNumber;
    private String firstname;
    private String middlename;
    private String lastname;
    private String userimage;



    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public int getListingid() {
        return listingid;
    }

    public void setListingid(int listingid) {
        this.listingid = listingid;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
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

    public String getDateoffered() {
        return dateoffered;
    }

    public void setDateoffered(String dateoffered) {
        this.dateoffered = dateoffered;
    }

    public String getOfferstatus() {
        return offerstatus;
    }

    public void setOfferstatus(String offerstatus) {
        this.offerstatus = offerstatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

}
