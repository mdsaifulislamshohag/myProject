package com.me.noban.DengueSolution.model;

public class Doctor {
    private String userId;
    private String name;
    private String speciality;
    private String gender;
    private String title;
    private String degree;
    private String mobile;
    private String email;
    private String password;
    private String bmdc;


    public Doctor() {
    }

    public Doctor(String userId, String name, String speciality, String gender, String title,
                  String degree, String mobile, String email, String password, String bmdc) {
        this.userId = userId;
        this.name = name;
        this.speciality = speciality;
        this.gender = gender;
        this.title = title;
        this.degree = degree;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.bmdc = bmdc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getBmdc() {
        return bmdc;
    }

    public void setBmdc(String bmdc) {
        this.bmdc = bmdc;
    }
}
