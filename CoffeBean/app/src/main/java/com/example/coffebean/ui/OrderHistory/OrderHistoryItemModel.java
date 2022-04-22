package com.example.coffebean.ui.OrderHistory;

public class OrderHistoryItemModel
{
    //In the order history, all the data of the item will be displayed. The Name as primary text, and the rest as secondary, offering some description
    //of the items that have been previously ordered.
    public int ID;
    public String Item;
    public String price;
    public String date;

    //Constructor of the model.
    public OrderHistoryItemModel(int ID, String item, String price, String date)
    {
        this.ID = ID;
        Item = item;
        this.price = price;
        this.date = date;
    }

    //Data retrieval methods.
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
