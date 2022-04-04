package com.example.themetest.ui.Cart;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themetest.DataBaseOpenHelper;
import com.example.themetest.ui.Menu.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel
{
    private MutableLiveData<List<CartItemModel>> c;
    public CartViewModel()
    {
        c = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<CartItemModel>> getItems(ArrayList<CartItemModel> cart)
    {
        ArrayList<CartItemModel> cartItems = cart;

        c.setValue(cartItems);
        return c;
    }
}