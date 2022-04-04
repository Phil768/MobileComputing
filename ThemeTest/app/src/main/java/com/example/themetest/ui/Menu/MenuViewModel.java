package com.example.themetest.ui.Menu;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themetest.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel
{
    private MutableLiveData<List<MenuItemModel>> items;
    public MenuViewModel()
    {
        items = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<MenuItemModel>> getItems(ArrayList<MenuItemModel> menu)
    {
        ArrayList<MenuItemModel> menuItems = menu;

        items.setValue(menuItems);
        return items;
    }
}