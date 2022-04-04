package com.example.themetest.ui.Favourites;

public class FavouritesItemModel
{
    public int ID;
    public String Name;
    public String Amount;
    public int Price;

    public FavouritesItemModel(String name)
    {
        Name = name;
    }
    public FavouritesItemModel(int ID, String name, String amount, int price)
    {
        this.ID = ID;
        Name = name;
        Amount = amount;
        Price = price;
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
}
