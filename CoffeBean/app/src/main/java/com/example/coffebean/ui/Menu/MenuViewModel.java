package com.example.coffebean.ui.Menu;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffebean.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel
{
    //Creating MutableLiveData List<> of type MenuItemModel. This will allow us to set the values easily to this list alter on.
    private MutableLiveData<List<MenuItemModel>> items;

    //Constructor of the class.
    public MenuViewModel()
    {
        items = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<MenuItemModel>> getItems(Context context)
    {
        //Creating a new instance of the database, using the context that has been passed as a parameter from the fragment
        DataBaseOpenHelper db = new DataBaseOpenHelper(context);
        //Creating a new ArrayList of type MenuItemModel, and setting its value to the arrayList returned from the database.
        ArrayList<MenuItemModel> menuItems = db.getMenuItems();

        //Setting the values of the MutableLiveDate list with the values of the array list passed as a parameter form the Database.
        items.setValue(menuItems);
        //Returning the MutableLiveData list.
        return items;
    }
}