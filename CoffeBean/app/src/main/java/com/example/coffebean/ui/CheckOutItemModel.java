package com.example.coffebean.ui;

public class CheckOutItemModel
{
    public int ID;
    public String Name;
    public String Surname;
    public int Phone;
    public String Destination;

    public CheckOutItemModel(int ID, String name, String surname, int phone, String destination)
    {
        this.ID = ID;
        Name = name;
        Surname = surname;
        Phone = phone;
        Destination = destination;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }
}
