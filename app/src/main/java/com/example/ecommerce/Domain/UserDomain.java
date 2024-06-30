package com.example.ecommerce.Domain;

public class UserDomain {
    public String uid;
    public String email;
    public String userName;
    public String phone;
    public String address;
    public String nation;

    public UserDomain() {
    }

    public UserDomain(String uid, String email, String userName, String phone, String address, String nation) {
        this.uid = uid;
        this.email = email;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.nation = nation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
