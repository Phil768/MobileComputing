package com.example.coffebean.ui.Favourites;

public class FavouritesItemModel
{
    //In the favourites, all the data of the item will be displayed. The Name as primary text, and the rest as secondary, offering some description
    //of the item that the user has put in the favourites.
    public int ID;
    public String Name;
    public String Size;
    public String SugarAndCream;
    public String Amount;
    public int Price;

    //Constructor of the model.
    public FavouritesItemModel(int ID, String name, String size, String sugarAndCream, String amount, int price)
    {
        this.ID = ID;
        Name = name;
        Size = size;
        SugarAndCream = sugarAndCream;
        Amount = amount;
        Price = price;
    }

    //Data retrieval methods.
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

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getSugarAndCream() {
        return SugarAndCream;
    }

    public void setSugarAndCream(String sugarAndCream) {
        SugarAndCream = sugarAndCream;
    }
}
