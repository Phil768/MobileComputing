package com.example.coffebean.ui.OrderHistory;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffebean.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryViewModel extends ViewModel
{
    //Creating MutableLiveData List<> of type OrderHistoryItemModel. This will allow us to set the values easily to this list alter on.
    private MutableLiveData<List<OrderHistoryItemModel>> c;

    //Constructor of the class.
    public OrderHistoryViewModel()
    {
        c = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<OrderHistoryItemModel>> getItems(Context context)
    {
        //Creating a new instance of the database, using the context that has been passed as a parameter from the fragment.
        DataBaseOpenHelper db = new DataBaseOpenHelper(context);
        //Creating a new ArrayList of type v, and setting its value to the arrayList returned from the database.
        ArrayList<OrderHistoryItemModel> orderItems = db.getOrderDetailsItem();

        //Setting the values of the MutableLiveDate list with the values of the array list passed as a parameter form the Database.
        c.setValue(orderItems);
        //Returning the MutableLiveData list.
        return c;
    }
}