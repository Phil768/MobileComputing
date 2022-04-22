package com.example.coffebean.ui.Favourites;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffebean.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FavouritesViewModel extends ViewModel
{
    //Creating MutableLiveData List<> of type FavouritesItemModel. This will allow us to set the values easily to this list alter on.
    private MutableLiveData<List<FavouritesItemModel>> fav;

    //Constructor of the class.
    public FavouritesViewModel()
    {
        fav = new MutableLiveData<>();
    }

    @SuppressLint("NewApi")
    public MutableLiveData<List<FavouritesItemModel>> getItems(Context context)
    {
        //Creating a new instance of the database, using the context that has been passed as a parameter from the fragment.
        DataBaseOpenHelper db = new DataBaseOpenHelper(context);
        //Creating a new ArrayList of type FavouritesItemModel, and setting its value to the arrayList returned from the database.
        ArrayList<FavouritesItemModel> favourites = db.getFavouritesItem();

        //Setting the values of the MutableLiveDate list with the values of the array list passed as a parameter from the Database.
        fav.setValue(favourites);
        //Returning the MutableLiveData list.
        return fav;
    }

}