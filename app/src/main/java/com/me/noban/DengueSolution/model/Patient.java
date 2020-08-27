package com.me.noban.DengueSolution.model;

public class Patient {

    private String uniqueId;
    private String name;
    private String address;
    private String age;
    private String gender;
    private String bloodGroup;
    private String mobile;
    private String email;
    private String password;

    public Patient() {
    }

    public Patient(String uniqueId, String name, String address, String age, String gender, String bloodGroup, String mobile, String email, String password) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
