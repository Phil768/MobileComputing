package com.example.themetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.themetest.ui.Cart.CartItemModel;
import com.example.themetest.ui.Favourites.FavouritesItemModel;
import com.example.themetest.ui.Menu.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOpenHelper extends SQLiteOpenHelper
{

    public DataBaseOpenHelper(@Nullable Context context)
    {
        super(context, "coffeeBean.db", null, 1);
    }


    //Tables are created here.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /*
        //Creating the table that will hold all the menu items.
        String createTable = "CREATE TABLE Menu(Name VARCHAR(40))";
        //Executing the above SQL queries.
        db.execSQL(createTable);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //Get all menu items from the database.
    public ArrayList<MenuItemModel> getMenuItems()
    {
        ArrayList<MenuItemModel> menuItem = new ArrayList<>();
        String query = "SELECT * FROM Menu";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                String menuItemName = cursor.getString(1);

                MenuItemModel newItem = new MenuItemModel(menuItemName);
                menuItem.add(newItem);
            }while(cursor.moveToNext());
        }
        else
        {
            //nothing happens.
        }
        cursor.close();
        db.close();
        return menuItem;
    }


    //retrieves the name of the selected item from the database.
    public String getItemName(int i)
    {
        String menuItemName = "";
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                menuItemName = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        else
        {
            //nothing happens.
        }
        cursor.close();
        db.close();
        return menuItemName;
    }

    //Retreives the price of the menu item from the databse.
    public int getItemPrice(int i)
    {
        int menuItemPrice = 0;
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                menuItemPrice = cursor.getInt(2);
            }while(cursor.moveToNext());
        }
        else
        {
            //nothing happens.
        }
        cursor.close();
        db.close();
        return menuItemPrice;
    }

    ////////////////////////////////////////////////////////// CART METHODS //////////////////////////////////////////////////////////////////////////

    //Get all menu items from the database.
    public ArrayList<CartItemModel> getCartItems()
    {
        ArrayList<CartItemModel> cartItem = new ArrayList<>();
        String query = "SELECT * FROM Cart";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                String cartItemName = cursor.getString(1);

                CartItemModel newItem = new CartItemModel(cartItemName);
                cartItem.add(newItem);
            }while(cursor.moveToNext());
        }
        else
        {
            //if it fails nothing happens.
        }
        cursor.close();
        db.close();
        return cartItem;
    }

    //Get all cart items from the database.
    public int getNumberOfCartItems()
    {
        ArrayList<CartItemModel> cartItem = new ArrayList<>();
        String query = "SELECT * FROM Cart";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                String cartItemName = cursor.getString(1);

                CartItemModel newItem = new CartItemModel(cartItemName);
                cartItem.add(newItem);
            }while(cursor.moveToNext());
        }
        else
        {
            //if it fails nothing happens.
        }
        cursor.close();
        db.close();
        return cartItem.size();
    }

    //Inserting items to the database (Cart table).
    public void addCartItem(String cart)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", cart);
        db.insert("Cart", null, cv);
    }

    //Methods to delete all the items in the cart.
    public void deleteAllInCart()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Cart");
        db.close();
    }

    ////////////////////////////////////////////////// FAVOURITES METHODS ///////////////////////////////////////////////

    public void addFavouritesItem(int ID, String name, String amount, int price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ID", ID);
        cv.put("Name", name);
        cv.put("Amount", amount);
        cv.put("Price", price);
        db.insert("Favourites", null, cv);
    }

    public void deleteFavouritesItem(FavouritesItemModel fav)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Favourites WHERE ID =" + fav.getID());
        db.close();
    }

    //Deletes all Items from the favourites table(favourites tab).
    public void deleteAllInFavourites()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Favourites");
        db.close();
    }

    public int getNumberOfFavouritesItems()
    {
        ArrayList<FavouritesItemModel> favItem = new ArrayList<>();
        String query = "SELECT * FROM Favourites";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                String favItemName = cursor.getString(1);

                FavouritesItemModel newItem = new FavouritesItemModel(favItemName);
                favItem.add(newItem);
            }while(cursor.moveToNext());
        }
        else
        {
            //if it fails nothing happens.
        }
        cursor.close();
        db.close();
        return favItem.size();
    }

}
