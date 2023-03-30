package com.example.ecart.ModelClass;

public class Address {
    private String  AddressId,Name,PhoneNo,Pincode,Locality,Address,City,State,Landmark;

    public Address(String addressId, String name, String phoneNo, String pincode, String locality, String address, String city, String state, String landmark) {
        AddressId = addressId;
        Name = name;
        PhoneNo = phoneNo;
        Pincode = pincode;
        Locality = locality;
        Address = address;
        City = city;
        State = state;
        Landmark = landmark;
    }

    public Address() {
    }

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }
}
