package com.me.noban.DengueSolution.model;

public class apoint_model {
    String name;
    String number;
    String doctorId;

    public apoint_model() {
    }

    public apoint_model(String name, String number, String doctorId) {
        this.name = name;
        this.number = number;
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
