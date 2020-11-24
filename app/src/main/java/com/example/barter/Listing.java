package com.example.barter;

public class Listing {

    private String imageURL;
    private String product_name;
    private String listing_details;
    private int accountid;

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

    public Listing(String imageURL, String product_name, String listing_details, int accountid, int listing_id, int image_id) {
        this.imageURL = imageURL;
        this.product_name = product_name;
        this.listing_details = listing_details;
        this.accountid = accountid;
        this.listing_id = listing_id;
        this.image_id = image_id;
    }
}
