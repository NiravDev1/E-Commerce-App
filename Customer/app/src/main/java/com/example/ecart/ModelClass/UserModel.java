package com.example.ecart.ModelClass;

public class UserModel {
    private  String Email,Password,Name,Phone,Uid;


    public UserModel(String email, String password, String name, String phone, String uid) {
        Email = email;
        Password = password;
        Name = name;
        Phone = phone;
        Uid = uid;
    }

    public UserModel() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Uid='" + Uid + '\'' +
                '}';
    }
}
