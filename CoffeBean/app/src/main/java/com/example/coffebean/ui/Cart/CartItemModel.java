package com.example.coffebean.ui.Cart;

public class CartItemModel
{
    //In the cart, all the data of the item will be displayed. The Name as primary text, and the rest as secondary, offering some description
    //of the item that the user has put in the cart.
    public String Name;
    public String Size;
    public String SugarAndCream;
    public String Amount;
    public int Price;

    //Constructor of the model.
    public CartItemModel(String name, String size, String sugarAndCream, String amount, int price)
    {
        Name = name;
        Size = size;
        SugarAndCream = sugarAndCream;
        Amount = amount;
        Price = price;
    }

    //Data retrieval methods.
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
