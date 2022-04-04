package com.example.themetest.ui.Cart;

public class CartItemModel
{
    public String Name;

    //Constructor.
    public CartItemModel(String name)
    {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
