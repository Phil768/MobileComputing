package com.example.themetest.ui.Menu;

public class MenuItemModel
{
    //Currently only the name is present since this is a test.
    public String Name;

    //Constructor.
    public MenuItemModel(String name)
    {
        this.Name = name;
    }

    //Data retrieval methods.
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
