package com.example.themetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.themetest.ui.Cart.CartItemModel;
import com.example.themetest.ui.Favourites.FavouritesItemModel;
import com.example.themetest.ui.Menu.MenuFragment;

import java.util.Random;

public class ScreenTwo extends AppCompatActivity
{
    ImageView backArrow;
    TextView itemName, itemPrice;
    Button addButton, setBtn;
    RadioButton small, medium, large;
    CheckBox sugarAndCreamchbx;
    EditText amountTxt;
    ToggleButton favToggleBtn;

    //Creating a new bundle instance
    public static Bundle bundle=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_two);

        //handles the back arrow(May make it more efficient in the future)
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ScreenTwo.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Getting the name of the selected Item through intent.
        getName();

        //Creating and instance of the button found on the screen.
        addButton = (Button) findViewById(R.id.addBtn);
        //Add item to database.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Adding the
                String name = itemName.getText().toString();
                addItem(name);
            }
        });

        //Getting the price of the item from the database.
        getPrice();
/*
        //Handling what happens when the favourite button (heart) is clicked.
        favImgBtn = (ImageButton) findViewById(R.id.favImgBtn);
        favImgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(favImgBtn.isPressed())
                    favImgBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                else
                    favImgBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });

 */

        favToggleBtn = (ToggleButton) findViewById(R.id.favToggleBtn);
        /*
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean("tgpref", false);
        favToggleBtn.setChecked(tgpref);

         */

        favToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("tgpref", favToggleBtn.isChecked());
                editor.commit();
                 */

                //Creating an instance of the database.
                DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);

                //Storing the size of the favourites table in a variable.
                int size = db.getNumberOfFavouritesItems();
                //Declaring the ID variable.
                int ID = 0;

                //This stupid piece of code is added since SQLite could not accept an AUTOINCREMENT data type. :/
                if(size == 0)
                {
                    ID = 1;
                }
                else
                {
                    ID = (size + 1);
                }

                //Re-defining the variables to male sure that they are available in the current context.
                itemName = (TextView) findViewById(R.id.selectedItemName);
                amountTxt = (EditText) findViewById(R.id.amountTxt);
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);

                //storing all data items into variables.
                String name = itemName.getText().toString();
                String amount = amountTxt.getText().toString();
                int price = Integer.parseInt(String.valueOf(itemPrice.getText()));

                FavouritesItemModel fav = new FavouritesItemModel(ID, name, amount, price);

                if(favToggleBtn.isChecked())
                {
                    //Adding the items to the table.
                    addFavItem(fav.getID(), fav.getName(), fav.getAmount(), fav.getPrice());
                    //Displaying successful message.
                    Toast.makeText(ScreenTwo.this, "Item has been added to favourites :D", Toast.LENGTH_SHORT).show();
                    favToggleBtn.setChecked(true);

                }
                else
                {
                    //Delete the item if the favourites icon is de-selected.
                    deleteFavouritesItem(fav);
                    Toast.makeText(ScreenTwo.this, "Item has been removed from favourites :(", Toast.LENGTH_SHORT).show();
                    favToggleBtn.setChecked(false);
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        favToggleBtn = (ToggleButton) findViewById(R.id.favToggleBtn);
        outState.putBoolean("ToggleButtonState", favToggleBtn.isChecked());
        super.onSaveInstanceState(outState);
    }

    public void getName()
    {
        Bundle name = getIntent().getExtras();
        String menuItemName = name.getString("Name");

        itemName = (TextView) findViewById(R.id.selectedItemName);
        itemName.setText(menuItemName);
    }

    public void addItem(String name)
    {
        DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
        db.addCartItem(name);
    }

    public void addFavItem(int ID, String name, String amount, int price)
    {
        DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
        db.addFavouritesItem(ID, name, amount, price);
    }

    public void deleteFavouritesItem(FavouritesItemModel fav)
    {
        DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
        db.deleteFavouritesItem(fav);
    }

    public void getPrice()
    {
        //Handling radio buttons for the sizes.
        small = (RadioButton) findViewById(R.id.smallRadioBtn);
        medium = (RadioButton) findViewById(R.id.mediumRadiobtn);
        large = (RadioButton) findViewById(R.id.largeRadioBtn);

        //Getting the id of the button
        setBtn = (Button) findViewById(R.id.setBtn);

        //Getting the id of the checkbox.
        sugarAndCreamchbx = (CheckBox) findViewById(R.id.sugarAndCreamchbx);

        Bundle price = getIntent().getExtras();
        final int[] menuItemPrice = {price.getInt("Price")};

        itemPrice = (TextView) findViewById(R.id.totalPricetxt);
        itemPrice.setText("0");

        small.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle price = getIntent().getExtras();
                int menuItemPrice = price.getInt("Price");

                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                String finalPrice = String.valueOf(menuItemPrice);
                itemPrice.setText(finalPrice);
            }
        });

        medium.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle price = getIntent().getExtras();
                int menuItemPrice = price.getInt("Price");
                menuItemPrice = (menuItemPrice + 1);

                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                String finalPrice = String.valueOf(menuItemPrice);
                itemPrice.setText(finalPrice);
            }
        });

        large.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle price = getIntent().getExtras();
                int menuItemPrice = price.getInt("Price");
                menuItemPrice = (menuItemPrice + 2);

                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                String finalPrice = String.valueOf(menuItemPrice);
                itemPrice.setText(finalPrice);

            }
        });

        sugarAndCreamchbx.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(sugarAndCreamchbx.isChecked())
                {
                    itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                    int finalPrice = Integer.parseInt(itemPrice.getText().toString());

                    finalPrice = (finalPrice + 1);
                    itemPrice.setText(String.valueOf(finalPrice));
                }
                else if(!sugarAndCreamchbx.isChecked())
                {
                    itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                    int finalPrice = Integer.parseInt(itemPrice.getText().toString());

                    finalPrice = (finalPrice - 1);
                    itemPrice.setText(String.valueOf(finalPrice));
                }

            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                amountTxt = (EditText) findViewById(R.id.amountTxt);
                final int subTotal = Integer.parseInt(itemPrice.getText().toString());
                int amount = Integer.parseInt(amountTxt.getText().toString());

                int total = (subTotal * amount);
                itemPrice.setText(String.valueOf(total));

                if(amountTxt.getText().toString() != null || amountTxt.getText().toString() != "0")
                    setBtn.setVisibility(view.GONE);
            }
        });


    }
}