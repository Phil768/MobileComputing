package com.example.coffebean.ui.Cart;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffebean.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel
{
    //Creating MutableLiveData List<> of type CartItemModel. This will allow us to set the values easily to this list alter on.
    private MutableLiveData<List<CartItemModel>> c;

    //Constructor of the class.
    public CartViewModel()
    {
        c = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<CartItemModel>> getItems(Context context)
    {
        //Creating a new instance of the database, using the context that has been passed as a parameter from the fragment.
        DataBaseOpenHelper db = new DataBaseOpenHelper(context);
        //Creating a new ArrayList of type CartItemModel, and setting its value to the arrayList returned from the database.
        ArrayList<CartItemModel> cartItems = db.getCartItems();

        //Setting the values of the MutableLiveDate list with the values of the array list passed as a parameter form the Database.
        c.setValue(cartItems);
        //Returning the MutableLiveData list.
        return c;
    }
}