package com.example.coffebean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.coffebean.ui.Cart.CartItemModel;
import com.example.coffebean.ui.Favourites.FavouritesItemModel;
import com.example.coffebean.ui.Menu.MenuItemModel;
import com.example.coffebean.ui.OrderHistory.OrderHistoryItemModel;
import com.example.coffebean.ui.CheckOutItemModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/*
* DataBaseOpenHelper class
* ^^^^^^^^^^^^^^^^^^^^^^^^
*   This class acts as the database of the app. The first time that it is executed the database is created from the constructor amd all the SQL statements from the
* onCreate method are inserted into this database. Most commonly, these SQL statement represent the tables of the database. This database is stored into the files of the
* phone(virtual or physical), and the methods that follow after will interact with it.
*
* NOTE: The database can be accessed through an app, such as SQLite DB Browser. All that needs to be done is download the database from teh files of the phone and import
* ^^^^^ it into the DB Browser.(To download the database:- Device File Explorer -> data -> data -> databases -> right click and 'save as' on the desired database).
*
*   The methods following this will act as DML(Data Manipulation Language). The are the SELECT, ADD and DELETE statements. Each table has its own set of these methods
* so its data could be manipulated more freely.
* */

public class DataBaseOpenHelper extends SQLiteOpenHelper
{
    //Constructor of the class, creating the database(in this case coffeBean.db).
    public DataBaseOpenHelper(@Nullable Context context)
    {
        super(context, "coffeeBean.db", null, 1);
    }

    //Creating the tables through the onCreate method.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Creating the table that will hold all the menu items.
        String createMenuTable =
                "CREATE TABLE Menu (" +
                        "           ID INTEGER," +
                        "            Name Text," +
                        "            Price INTEGER,\n" +
                        "            Description TEXT," +
                        "            Image BLOB" +
                        "        )";
        //Executing the above SQL queries.
        db.execSQL(createMenuTable);

        //Creating the table that will hold all the cart items.
        String createCartTable =
                "CREATE TABLE Favourites (" +
                        "            ID INTEGER," +
                        "            Name TEXT," +
                        "            Size Text," +
                        "            SugarAndCream TEXT," +
                        "            Amount TEXT," +
                        "            Price INTEGER)";
        //Executing the above query.
        db.execSQL(createCartTable);

        //Creating the table that will hold all the favourites items.
        String createFavouritesTable =
                "CREATE TABLE Favourites (" +
                        "            ID INTEGER," +
                        "            Name TEXT," +
                        "            Size Text," +
                        "            SugarAndCream TEXT," +
                        "            Amount TEXT," +
                        "            Price INTEGER)";

        //Executing the above query.
        db.execSQL(createFavouritesTable);

        String createOrdersTable =
                "CREATE TABLE Orders (" +
                        "            ID INTEGER," +
                        "            Name TEXT," +
                        "            Surname TEXT," +
                        "            Phone INTEGER," +
                        "            Destination TEXT)";
        //Executing the above query.
        db.execSQL(createOrdersTable);

        String createOrderDetailsTable =
                "CREATE TABLE Orders (" +
                        "            ID INTEGER," +
                        "            Name TEXT," +
                        "            Surname TEXT," +
                        "            Phone INTEGER," +
                        "            Destination TEXT)";
        //Executing the above query.
        db.execSQL(createOrderDetailsTable);

        String createSuggestionsTable =
                "CREATE TABLE Suggestions (" +
                        "            Name TEXT," +
                        "            Description TEXT," +
                        "            Image BLOB)";
        //Executing the above query.
        db.execSQL(createSuggestionsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //Get all menu items from the database.
    public ArrayList<MenuItemModel> getMenuItems()
    {
        //New arrayList of type MenuItemModel.
        ArrayList<MenuItemModel> menuItem = new ArrayList<>();
        //SQL query.
        String query = "SELECT * FROM Menu";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                String menuItemName = cursor.getString(1);

                //Creating a new MenuItemModel object and passing the data acquired through the cursor and the SQL statement through the constructor so it becomes the data of the object.
                MenuItemModel newItem = new MenuItemModel(menuItemName);
                //Adding the newly created object to the ArrayList created above.
                menuItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return menuItem;
    }


    //Retrieves the name of the selected item from the database. Much like the above but the selection depends on the ID of the selected item from the RecyclerView.
    public String getItemName(int i)
    {
        //Creating an empty instance of the item that is going to be retrieved.
        String menuItemName = "";
        //SQL query.
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                menuItemName = cursor.getString(1);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the menuItemName in the form of a single String, since this is only targeting the name.
        return menuItemName;
    }

    //Retrieves the price of the menu item from the database.
    public int getItemPrice(int i)
    {
        //Creating an empty instance of the item that is going to be retrieved.
        int menuItemPrice = 0;
        //SQL query.
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 2 and storing it into an int variable.
                menuItemPrice = cursor.getInt(2);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the menuItemPrice in the form of a single int, since this is only targeting the price.
        return menuItemPrice;
    }

    //Getting the menu description from the database.
    public String getMenuDescription(int i)
    {
        //Creating an empty instance of the item that is going to be retrieved.
        String description = "";
        //SQL query.
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do {
                //Getting the cursor object, of type int at position 3 and storing it into a String variable.
                description = cursor.getString(3);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //do nothing if it fails.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the menuItemPrice in the form of a single String, since this is only targeting the description.
        return description;
    }

    //Getting the menu image from the database.
    public Bitmap getMenuImage(int i)
    {
        //SQL query.
        String query = "SELECT * FROM Menu WHERE ID =" + i;

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            do {
                //Getting the cursor object, of type int at position 4 and storing it into a byte[] array, since this is an image.
                byte[] imgByte = cursor.getBlob(4);
                //Decode the ByteArray and return the image in the form of a BitMap.
                return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //do nothing if it fails.
        }
        //The targeted object has already been returned, so here we return null.
        return null;
    }

    ////////////////////////////////////////////////////////// CART METHODS //////////////////////////////////////////////////////////////////////////
//Get all cart items from the database.
    public ArrayList<CartItemModel> getCartItems()
    {
        //New arrayList of type CartItemModel.
        ArrayList<CartItemModel> cartItem = new ArrayList<>();
        //SQL query
        String query = "SELECT * FROM Cart";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do
            {
                //Getting the cursor object, of type string at position 0 and storing it into a String variable.
                String cartItemName = cursor.getString(0);
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                String cartItemSize = cursor.getString(1);
                //Getting the cursor object, of type string at position 2 and storing it into a String variable.
                String cartCreamAndSugar = cursor.getString(2);
                //Getting the cursor object, of type string at position 3 and storing it into a String variable.
                String cartAmount = cursor.getString(3);
                //Getting the cursor object, of type int at position 4 and storing it into an int variable.
                int cartPrice = cursor.getInt(4);

                //Creating a new object of type CartItemModel and and passing the above variables(database values) through the constructor of the class.
                CartItemModel newItem = new CartItemModel(cartItemName, cartItemSize, cartCreamAndSugar, cartAmount, cartPrice);
                //Adding the newly created object to the ArrayList created above.
                cartItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return cartItem;
    }

    //Get all cart items from the database.
    public int getCartItemssize()
    {
        //New arrayList of type CartItemModel.
        ArrayList<CartItemModel> cartItem = new ArrayList<>();
        //SQL query
        String query = "SELECT * FROM Cart";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do
            {
                //Getting the cursor object, of type string at position 0 and storing it into a String variable.
                String cartItemName = cursor.getString(0);
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                String cartItemSize = cursor.getString(1);
                //Getting the cursor object, of type string at position 2 and storing it into a String variable.
                String cartCreamAndSugar = cursor.getString(2);
                //Getting the cursor object, of type string at position 3 and storing it into a String variable.
                String cartAmount = cursor.getString(3);
                //Getting the cursor object, of type int at position 4 and storing it into an int variable.
                int cartPrice = cursor.getInt(4);

                //Creating a new object of type CartItemModel and and passing the above variables(database values) through the constructor of the class.
                CartItemModel newItem = new CartItemModel(cartItemName, cartItemSize, cartCreamAndSugar, cartAmount, cartPrice);
                //Adding the newly created object to the ArrayList created above.
                cartItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return cartItem.size();
    }


    //Inserting items to the database (Cart table).
    public void addCartItem(String name, String size, String creamAndSugar, String amount, int price)
    {
        //Getting an instance of the SQLite database. Since we are writing to the database by inserting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating a new content values object which allows us to insert items into the database.
        ContentValues cv = new ContentValues();

        //Inserting the items into the database, corresponding to their column name.
        cv.put("Name", name);
        cv.put("Size", size);
        cv.put("SugarAndCream", creamAndSugar);
        cv.put("Amount", amount);
        cv.put("Price", price);
        //Inserting the above into the database 'Cart'. The whole ContentValues object is inserted, which contains all the required data.
        db.insert("Cart", null, cv);
    }

    //Methods to delete all the items in the cart.
    public void deleteAllInCart()
    {
        //Getting an instance of the SQLite database. Since we are deleting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Execute the SQL query.
        db.execSQL("DELETE FROM Cart");
        //Close the database.
        db.close();
    }

    ////////////////////////////////////////////////// FAVOURITES METHODS ///////////////////////////////////////////////

    //Inserting items to the database (Favourites table).
    public void addFavouritesItem(int ID, String name, String size, String creamAndSugar, String amount, int price)
    {
        //Getting an instance of the SQLite database. Since we are writing to the database by inserting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating a new content values object which allows us to insert items into the database.
        ContentValues cv = new ContentValues();

        //Inserting the items into the database, corresponding to their column name.
        cv.put("ID", ID);
        cv.put("Name", name);
        cv.put("Size", size);
        cv.put("SugarAndCream", creamAndSugar);
        cv.put("Amount", amount);
        cv.put("Price", price);
        //Inserting the above into the database 'Favourites'. The whole ContentValues object is inserted, which contains all the required data.
        db.insert("Favourites", null, cv);
    }

    public void deleteFavouritesItem(FavouritesItemModel fav)
    {
        //Getting an instance of the SQLite database. Since we are deleting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Execute the SQL query.
        db.execSQL("DELETE FROM Favourites WHERE ID =" + fav.getID());
        //Close the database.
        db.close();
    }

    //Deletes all Items from the favourites table(favourites tab).
    public void deleteAllInFavourites()
    {
        //Getting an instance of the SQLite database. Since we are deleting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Execute the SQL query.
        db.execSQL("DELETE FROM Favourites");
        //Close the database.
        db.close();
    }

    //Get all favourite items from the database.
    public ArrayList<FavouritesItemModel> getFavouritesItem()
    {
        //New arrayList of type FavouritesItemModel.
        ArrayList<FavouritesItemModel> favItem = new ArrayList<>();
        //SQL query.
        String query = "SELECT * FROM Favourites";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int favID = cursor.getInt(0);
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                String favItemName = cursor.getString(1);
                //Getting the cursor object, of type string at position 2 and storing it into a String variable.
                String favItemSize = cursor.getString(2);
                //Getting the cursor object, of type string at position 3 and storing it into a String variable.
                String favCreamAndSugar = cursor.getString(3);
                //Getting the cursor object, of type string at position 4 and storing it into a String variable.
                String favAmount = cursor.getString(4);
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int favPrice = cursor.getInt(5);

                //Creating a new object of type FavouritesItemModel and and passing the above variables(database values) through the constructor of the class.
                FavouritesItemModel newItem = new FavouritesItemModel(favID, favItemName, favItemSize, favCreamAndSugar, favAmount, favPrice);
                //Adding the newly created object to teh above created ArrayList.
                favItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return favItem;
    }

    public FavouritesItemModel getFavouriteAtPosition(int i)
    {
        //SQL query.
        String query = "SELECT * FROM Favourites WHERE ID =" + i;

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int favID = cursor.getInt(0);
                //Getting the cursor object, of type string at position 1 and storing it into a String variable.
                String favItemName = cursor.getString(1);
                //Getting the cursor object, of type string at position 2 and storing it into a String variable.
                String favItemSize = cursor.getString(2);
                //Getting the cursor object, of type string at position 3 and storing it into a String variable.
                String favCreamAndSugar = cursor.getString(3);
                //Getting the cursor object, of type string at position 4 and storing it into a String variable.
                String favAmount = cursor.getString(4);
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int favPrice = cursor.getInt(5);

                //Creating a new object of type FavouritesItemModel and and passing the above variables(database values) through the constructor of the class.
                FavouritesItemModel newItem = new FavouritesItemModel(favID, favItemName, favItemSize, favCreamAndSugar, favAmount, favPrice);

                //Returning the above created object since the method's purpose is to return a single object.
                return newItem;
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //Since the desired item has already been returned, here we return nothing.
        return null;
    }
    //////////////////////////////////////////////////// CHECKOUT METHODS ////////////////////////////////////////////////

    //Get all checkout items from the database.
    public ArrayList<CheckOutItemModel> getOrderItems()
    {
        //New arrayList of type CheckOutItemModel.
        ArrayList<CheckOutItemModel> OrderItem = new ArrayList<>();
        //SQL query
        String query = "SELECT * FROM Orders";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int OrderID = cursor.getInt(0);
                //Getting the cursor object, of type String at position 1 and storing it into an String variable.
                String OrderItemName = cursor.getString(1);
                //Getting the cursor object, of type String at position 2 and storing it into an String variable.
                String OrderItemSurname = cursor.getString(2);
                //Getting the cursor object, of type int at position 3 and storing it into an int variable.
                int OrderPhone = cursor.getInt(3);
                //Getting the cursor object, of type String at position 4 and storing it into an String variable.
                String OrderDestination = cursor.getString(4);

                //Creating a new object of type CheckOutItemModel and and passing the above variables(database values) through the constructor of the class.
                CheckOutItemModel newItem = new CheckOutItemModel(OrderID, OrderItemName, OrderItemSurname, OrderPhone, OrderDestination);
                //Adding the newly created object to teh above created ArrayList.
                OrderItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return OrderItem;
    }

    //Get all order items from the database.
    public int getTotalPrice()
    {
        //Creating an initial price variable, set to 0.
        int price = 0;
        //SQL query.
        String query = "SELECT SUM(Price) FROM Cart";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                price = cursor.getInt(0);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the price in the form of a single int, since this is only targeting the price.
        return price;
    }

    //Adding a new item to the 'Orders' table.
    public void addOrderItem(int ID, String name, String surname, int phone, String destination)
    {
        //Getting an instance of the SQLite database. Since we are writing to the database by inserting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating a new content values object which allows us to insert items into the database.
        ContentValues cv = new ContentValues();

        //Inserting the items into the database, corresponding to their column name.
        cv.put("ID", ID);
        cv.put("Name", name);
        cv.put("Surname", surname);
        cv.put("Phone", phone);
        cv.put("Destination", destination);
        //Inserting the above into the database 'Orders'. The whole ContentValues object is inserted, which contains all the required data.
        db.insert("Orders", null, cv);
    }


    ////////////////////////////////////////////////// ORDER HISTORY METHODS ////////////////////////////////////////////////////////////

    //Adding an order history item to the database.
    public void addOrderDetailsItem(int ID, String item, String price, String date)
    {
        //Getting an instance of the SQLite database. Since we are writing to the database by inserting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating a new content values object which allows us to insert items into the database.
        ContentValues cv = new ContentValues();

        //Inserting the items into the database, corresponding to their column name.
        cv.put("ID", ID);
        cv.put("Item", item);
        cv.put("Price", price);
        cv.put("DateOfPurchase", date);
        //Inserting the above into the database 'OrderDetails'. The whole ContentValues object is inserted, which contains all the required data.
        db.insert("OrderDetails", null, cv);
    }

    //Getting all the items from the OrderDetails table.
    public ArrayList<OrderHistoryItemModel> getOrderDetailsItem()
    {
        //Creating a new ArrayList of type OrderHistoryItemModel.
        ArrayList<OrderHistoryItemModel> orderItem = new ArrayList<>();
        //SQL query.
        String query = "SELECT * FROM OrderDetails";

        //Getting an instance of the SQLite database. Since we are only going to view data, we get a ReadableDatabase.
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor object and making it a reference of the query that has beam created above.
        Cursor cursor = db.rawQuery(query, null);

        //If the cursor, which contains the data from the executed SQL query, has anything to view in teh first position (basically saying '(if != null)').
        if(cursor.moveToFirst())
        {
            //The do while loop will keep iterating until there are not other items.
            do{
                //Getting the cursor object, of type int at position 0 and storing it into an int variable.
                int orderID = cursor.getInt(0);
                //Getting the cursor object, of type String at position 1 and storing it into an String variable.
                String orderItemName = cursor.getString(1);
                //Getting the cursor object, of type String at position 2 and storing it into an String variable.
                String orderPrice = cursor.getString(2);
                //Getting the cursor object, of type String at position 3 and storing it into an String variable.
                String orderDate = cursor.getString(3);

                //Creating a new object of type OrderHistoryItemModel and and passing the above variables(database values) through the constructor of the class.
                OrderHistoryItemModel newItem = new OrderHistoryItemModel(orderID, orderItemName, orderPrice, orderDate);
                //Adding the newly created object to teh above created ArrayList.
                orderItem.add(newItem);
            }while(cursor.moveToNext());//Continues while the cursor is able to move to the next position.
        }
        else
        {
            //if it fails nothing happens.
        }
        //Closing the cursor.
        cursor.close();
        //Closing the database instance.
        db.close();
        //returning the ArrayList, filled with the items from the database(empty or not).
        return orderItem;
    }

    //Deletes all Items from the favourites table(favourites tab).
    public void deleteAllInOrderHistory()
    {
        //Getting an instance of the SQLite database. Since we are deleting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Executes SQL query.
        db.execSQL("DELETE FROM OrderDetails");
        //Closing the database
        db.close();
    }
////////////////////////////////////////////////////////// SUGGESTION METHODS /////////////////////////////////////////////////////////////

    //Adding an order history item to the database.
    public void addSuggestion(String name, String description, Bitmap image)
    {
        //Getting an instance of the SQLite database. Since we are writing to the database by inserting data, we get a WritableDatabase.
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating a new content values object which allows us to insert items into the database.
        ContentValues cv = new ContentValues();

        //Converting the image of type Bitmap, to a byte[] array format. This makes it compatible with a column of type BLOB.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        //Inserting the items into the database, corresponding to their column name.
        cv.put("Name", name);
        cv.put("Description", description);
        cv.put("Image", bArray);
        //Inserting the above into the database 'Suggestions'. The whole ContentValues object is inserted, which contains all the required data.
        db.insert("Suggestions", null, cv);
    }

}
