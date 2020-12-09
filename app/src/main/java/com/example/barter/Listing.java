package com.example.barter;

import java.io.Serializable;

public class Listing implements Serializable {

    private String imageURL;
    private String product_name;
    private String listing_details;
    private int accountid;

    public String getGoBackTo() {
        return goBackTo;
    }

    public void setGoBackTo(String goBackTo) {
        this.goBackTo = goBackTo;
    }

    private String goBackTo;

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    private String userimage;

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

    public String getDatelisted() {
        return datelisted;
    }

    public void setDatelisted(String datelisted) {
        this.datelisted = datelisted;
    }

    private String firstname;
    private String middlename;
    private String lastname;
    private String datelisted;

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    private int listing_id;
    private int image_id;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getListing_details() {
        return listing_details;
    }

    public void setListing_details(String listing_details) {
        this.listing_details = listing_details;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public Listing(String imageURL, String product_name, String listing_details, int accountid, int listing_id, int image_id,
                   String firstname, String middlename, String lastname, String datelisted) {
        this.imageURL = imageURL;
        this.product_name = product_name;
        this.listing_details = listing_details;
        this.accountid = accountid;
        this.listing_id = listing_id;
        this.image_id = image_id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.datelisted = datelisted;
    }
    public Listing(String imageURL, String product_name, String listing_details, int accountid, int listing_id, int image_id,
                   String firstname, String middlename, String lastname, String datelisted, String userimage) {
        this.imageURL = imageURL;
        this.product_name = product_name;
        this.listing_details = listing_details;
        this.accountid = accountid;
        this.listing_id = listing_id;
        this.image_id = image_id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.datelisted = datelisted;
        this.userimage = userimage;
    }
    public Listing(String imageURL, String product_name, String listing_details, int accountid, int listing_id, int image_id,
                   String firstname, String middlename, String lastname, String datelisted, String userimage, String goBackTo) {
        this.imageURL = imageURL;
        this.product_name = product_name;
        this.listing_details = listing_details;
        this.accountid = accountid;
        this.listing_id = listing_id;
        this.image_id = image_id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.datelisted = datelisted;
        this.userimage = userimage;
        this.goBackTo = goBackTo;
    }
}
