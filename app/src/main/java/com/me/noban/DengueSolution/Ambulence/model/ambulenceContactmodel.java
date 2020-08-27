package com.me.noban.DengueSolution.Ambulence.model;

public class ambulenceContactmodel {

    String Name;
    String location;
    String phone;

    public ambulenceContactmodel(String name, String location, String phone) {
        Name = name;
        this.location = location;
        this.phone = phone;
    }

    public ambulenceContactmodel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
