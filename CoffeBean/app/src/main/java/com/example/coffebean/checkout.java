package com.example.coffebean;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.coffebean.ui.*;
import com.example.coffebean.ui.Cart.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/*
* Checkout Activity
* ^^^^^^^^^^^^^^^^^
* In this activity the user is able to complete his experience of using this app. There are certain data fields that allow the users to enter their data,
* such as name, surname and destination of where they would like their order to be delivered. When the user presses the fields are cleared, the cart items
* are also cleared, and all teh cart items are added to a table called 'Orders'.
* */


public class checkout extends AppCompatActivity
{
    //Creating teh required objects of the class.
    ImageView backArrowBtn;
    EditText nameTxt, surnameTxt, phoneTxt, destinationTxt;
    Button orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Creating an instance of the 'ORDER' button found on screen and calling it using the 'findViewById()' method.
        orderBtn = (Button) findViewById(R.id.orderBtn);

        //Creating an instance of all the inputs that are found on screen.
        nameTxt = (EditText) findViewById(R.id.suggestionName);
        surnameTxt = (EditText) findViewById(R.id.surnameTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneTxt);
        destinationTxt = (EditText) findViewById(R.id.suggestionDesc);


        //Instance of the back arrow in the header and what happens if it is clicked.
        backArrowBtn = (ImageView) findViewById(R.id.previousArrowBtn);
        backArrowBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new intent instance with context of this class and the class that the intent will redirect to.
                Intent intent = new Intent(checkout.this, NavigationActivity.class);
                //Starting teh new activity.
                startActivity(intent);
            }
        });

        //Managing what happens when the order button is clicked.
        orderBtn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view)
            {
                //Validation
                if(nameTxt.getText().toString().equals("") || surnameTxt.getText().toString().equals("") || phoneTxt.getText().toString().equals("") || destinationTxt.getText().toString().equals(""))
                {
                    //Display simple message indicating that the input is not correct.
                    Toast.makeText(checkout.this, "Make sure that you have filled all the fields!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Creating an ID instance.
                    int ID = 0;
                    //Storing the name entered by the user into a String variable.
                    String name = nameTxt.getText().toString();
                    //Storing the surname entered by the user into a String variable.
                    String surname = surnameTxt.getText().toString();
                    //Storing the phone entered by the user into an int variable.
                    int phone = Integer.parseInt((phoneTxt.getText().toString()));
                    //Storing the destination entered by the user into a String variable.
                    String destination = destinationTxt.getText().toString();

                    //Creating a new instance of the database.
                    DataBaseOpenHelper db = new DataBaseOpenHelper(checkout.this);
                    //Creating a new date instance.
                    LocalDate d = LocalDate.now();
                    //Converting the above date into a string.
                    String date = String.valueOf(d);
                    //Getting the total price from teh database and storing it into a variable
                    String totalPrice = String.valueOf(db.getTotalPrice());
                    //Create a new List that will hold all the cart items (in order to get the size).
                    List<CartItemModel> items = db.getCartItems();

                    //Declaring the size of the Order table to determine the ID.
                    int size = 0;
                    //Removing all data items from the cart since the order has been made.
                    db.deleteAllInCart();

                    size = db.getOrderItems().size();
                    if(size == 0)
                    {
                        ID = 1;
                    }
                    else
                    {
                        ID = (size + 1);
                    }

                    //Creating an object with all the data values from the screen.
                    CheckOutItemModel order = new CheckOutItemModel(ID, name, surname, phone, destination);

                    //Passing all the data to the DataBaseOpenHelper and adding it to the table.
                    db.addOrderItem(order.getID(), order.getName(), order.getSurname(), order.getPhone(), order.getDestination());

                    //Storing all the items of the cart into the orders table.
                    for(CartItemModel item: items)
                    {
                        db.addOrderDetailsItem(ID, item.Name, totalPrice, date);
                    }
                    //Ensuring the user that the record has been added.
                    Toast.makeText(checkout.this, "Order received !!", Toast.LENGTH_SHORT).show();

                    //Clearing the data entered since the order has been made.
                    nameTxt.setText("");
                    surnameTxt.setText("");
                    phoneTxt.setText("");
                    destinationTxt.setText("");
                }
            }
        });
    }
}