package com.example.barter;

import java.io.Serializable;

public class User implements Serializable {

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    private int accountid;
    private String fname;
    private String mname;
    private String lname;
    private String dob;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(){

    }

    public User(String fname, String mname, String lname, String dob, String address) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.dob = dob;
        this.address = address;
    }
}
