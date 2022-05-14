package com.example.coffebean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.coffebean.ui.Favourites.FavouritesItemModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/*
* ScreenTwo:
* ^^^^^^^^^^
* This page is basically the order selection page. The user will be able to access after they have chosen a beverage of they choice from the Menu fragment.
* Afterwards, they will be redirected here. They can choose the size of their drinks, add some extra toppings and also set how many cups of this drink they want.
* They can also choose to add the item in the favorites, saved with the since, add ons and the amount. If this is a routine order, this would make their lives
* much easier. Form here they can also add their selected item to teh cart, for the ultimate checkout.
* */
public class ScreenTwo extends AppCompatActivity
{
    //Creating the objects required for the activity.
    ImageView backArrow, ItemImageView;
    TextView itemName, itemPrice, itemDescription;
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

        //Redirecting the imageview that represents the back arrow.
        backArrow = findViewById(R.id.previousArrowBtn);
        //Handles what happens when the back-arrow is pressed, .i.e taking the user back to the navigationActivity.
        backArrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new intent instance, with context of this page and the page that teh intent will redirect to.
                Intent intent = new Intent(ScreenTwo.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Getting the name of the selected Item through intent.
        getName();

        //Getting the image of the selected Item through intent.
        getImage();

        //Getting the description of the selected Item through intent.
        getDescription();

        //Redirecting the above created button object to the instance of the button found on the screen.
        addButton = (Button) findViewById(R.id.addBtn);
        //Handles what happens when the 'Add to cart' button is pressed.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Declaring the sugar and cream checkbox variable.
                String sugarAndCream = "";
                //Declaring the drink size variable.
                String drinkSize = "";

                //Re-defining the variables to male sure that they are available in the current context and redirecting the objects created above to the instances that are present on screen using findViewById().
                itemName = (TextView) findViewById(R.id.selectedItemName);
                amountTxt = (EditText) findViewById(R.id.amountTxt);
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);

                //Radio buttons(group). Redirecting the objects created above to the instances that are present on screen using findViewById().
                small = (RadioButton) findViewById(R.id.smallRadioBtn);
                medium = (RadioButton) findViewById(R.id.mediumRadiobtn);
                large = (RadioButton) findViewById(R.id.largeRadioBtn);

                //Sugar & Cream checkbox.
                sugarAndCreamchbx = (CheckBox) findViewById(R.id.sugarAndCreamchbx);

                //Checking which of the drink sizes is selected
                if(small.isChecked())
                    drinkSize = "Small";
                else if(medium.isChecked())
                    drinkSize = "Medium";
                else if(large.isChecked())
                    drinkSize = "Large";

                //Checking if the checkbox for Sugar & cream is checked.
                if(sugarAndCreamchbx.isChecked())
                    sugarAndCream = "Yes";
                else
                    sugarAndCream = "No";

                //Storing all the data acquired above into variables.
                String amount = amountTxt.getText().toString();
                String name = itemName.getText().toString();
                int price = Integer.parseInt(itemPrice.getText().toString());
                //Adding the item to the database by passing it to the corresponding method.
                addItem(name,drinkSize, sugarAndCream, amount, price);
            }
        });

        //Getting the price of the item from the database.
        getPrice();

        //Redirecting the toggleButton that represents the Favourites feature.
        favToggleBtn = (ToggleButton) findViewById(R.id.favToggleBtn);
        //Handles what happens when the Favourites toggle button is toggled.
        favToggleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Validation to check if a size is selected before adding to teh favourites.
                if (!small.isChecked() && !medium.isChecked() && !large.isChecked())
                {
                    //Generating a toast in case the user does not select a size before adding it to the favourites.
                    Toast.makeText(ScreenTwo.this, "Make sure that you have selected the appropriate size!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //The below procedures will execute of the above criteria is met.
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                    //Creating an instance of the database.
                    DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);

                    //Storing the size of the favourites table in a variable.
                    int size = db.getFavouritesItem().size();
                    //Declaring the ID variable.
                    int ID = 0;
                    //Declaring the sugar and cream checkbox variable.
                    String sugarAndCream = "";
                    //Declaring the drink size variable.
                    String drinkSize = "";

                    //This stupid piece of code is added since SQLite could not accept an AUTOINCREMENT data type. :/
                    if (size == 0) {
                        ID = 1;
                    } else {
                        ID = (size + 1);
                    }

                    //Re-defining the variables to male sure that they are available in the current context and redirecting the objects created above to the instances that are present on screen using findViewById().
                    itemName = (TextView) findViewById(R.id.selectedItemName);
                    amountTxt = (EditText) findViewById(R.id.amountTxt);
                    itemPrice = (TextView) findViewById(R.id.totalPricetxt);

                    //Radio buttons(group). Redirecting the objects created above to the instances that are present on screen using findViewById().
                    small = (RadioButton) findViewById(R.id.smallRadioBtn);
                    medium = (RadioButton) findViewById(R.id.mediumRadiobtn);
                    large = (RadioButton) findViewById(R.id.largeRadioBtn);

                    //Sugar & Cream checkbox.
                    sugarAndCreamchbx = (CheckBox) findViewById(R.id.sugarAndCreamchbx);

                    //Checking which of the drink sizes is selected
                    if(small.isChecked())
                        drinkSize = "Small";
                    else if(medium.isChecked())
                        drinkSize = "Medium";
                    else if(large.isChecked())
                        drinkSize = "Large";

                    //Checking if the checkbox for Sugar & cream is checked.
                    if(sugarAndCreamchbx.isChecked())
                        sugarAndCream = "Yes";
                    else
                        sugarAndCream = "No";

                    //storing all data items into variables.
                    String name = itemName.getText().toString();
                    String amount = amountTxt.getText().toString();
                    int price = Integer.parseInt(String.valueOf(itemPrice.getText()));

                    //Creating a FavouritesItemModel object using the above acquired variable.
                    FavouritesItemModel fav = new FavouritesItemModel(ID, name, drinkSize, sugarAndCream, amount, price);

                    if (favToggleBtn.isChecked()) {
                        //Adding the items to the table.
                        addFavItem(fav.getID(), fav.getName(), fav.getSize(), fav.getSugarAndCream(), fav.getAmount(), fav.getPrice());
                        //Displaying successful message.
                        Toast.makeText(ScreenTwo.this, "Item has been added to favourites :D", Toast.LENGTH_SHORT).show();
                        //Making the icon appear filled.
                        favToggleBtn.setChecked(true);

                    } else {
                        //Delete the item if the favourites icon is de-selected.
                        deleteFavouritesItem(fav);
                        //Displaying message.
                        Toast.makeText(ScreenTwo.this, "Item has been removed from favourites :(", Toast.LENGTH_SHORT).show();
                        //Making the icon appear hollowed.
                        favToggleBtn.setChecked(false);
                    }

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

    //Getting the name of the selected item and displaying it in this activity.
    public void getName()
    {
        //Creating a new bundle instance and getting the intent through it. The 'getExtras()' method is also used to receive the intent passed from the fragment through the 'putExtra()'.
        Bundle name = getIntent().getExtras();
        //Storing the name received through the intent into a variable.
        String menuItemName = name.getString("Name");

        //Getting the desired textview by id.
        itemName = (TextView) findViewById(R.id.selectedItemName);
        //Setting the text of the above textview with the intent variable received.
        itemName.setText(menuItemName);
    }

    //Getting the image of the selected item and displaying it in this activity.
    public void getImage()
    {
        //Creating a new intent instance that will be able to receive by the 'getIntent()' method is also used to receive the intent passed from the fragment.
        Intent intent = getIntent();
        //Storing the image received from the fragment into a byte[] array.
        byte[] image = intent.getByteArrayExtra("ByteImage");

        //Converting the byte[] array to a byte input string. This is done to make it easier for the Bitmap to display the image
        InputStream stream = new ByteArrayInputStream(image);

        //converting the byte stream to a bitmap in order to be able to display it properly.
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        //Getting the desired ImageView by id.
        ItemImageView = (ImageView) findViewById(R.id.itemImageView);
        //Setting the image of the above ImageView with the intent variable received.
        ItemImageView.setImageBitmap(bitmap);
    }

    //Getting the description of the selected item and displaying it in this activity.
    public void getDescription()
    {
        //Creating a new bundle instance and getting the intent through it. The 'getExtras()' method is also used to receive the intent passed from the fragment through the 'putExtra()'.
        Bundle description = getIntent().getExtras();
        //Storing the description received through the intent into a variable.
        String menuDescription = description.getString("Description");

        //Getting the desired textview by id.
        itemDescription = (TextView) findViewById(R.id.itemDescriptiontxt);
        //Setting the text of the above textview with the intent variable received.
        itemDescription.setText(menuDescription);
    }

    //Handles what happens when the 'Add to cart' button is pressed.
    public void addItem(String name, String size, String creamAndSugar, String amount, int price)
    {
        //Radio buttons(group). Redirecting the objects created above to the instances that are present on screen using findViewById().
        small = (RadioButton) findViewById(R.id.smallRadioBtn);
        medium = (RadioButton) findViewById(R.id.mediumRadiobtn);
        large = (RadioButton) findViewById(R.id.largeRadioBtn);
        //Amount TextView. Redirecting the objects created above to the instances that are present on screen using findViewById().
        amountTxt = (EditText) findViewById(R.id.amountTxt);

        //Validation to check if the item is suitable to be inserted into the databse.
        if(!small.isChecked() && !medium.isChecked() && !large.isChecked())
        {
            //Generating a toast to tell the users what they require.
            Toast.makeText(ScreenTwo.this, "Make sure that you have selected the appropriate size!!", Toast.LENGTH_SHORT).show();
        }
        else if(amountTxt.getText().toString().equals("0"))
        {
            //Generating a toast to tell the users what they require.
            Toast.makeText(ScreenTwo.this, "Make sure to enter the number of cups needed!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Creating a nwe instance of the database.
            DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
            //Adding the data to the database.
            db.addCartItem(name, size, creamAndSugar, amount, price);
            //Generating a toast to tell the users that the items have been added.
            Toast.makeText(ScreenTwo.this, "Added to cart :D", Toast.LENGTH_SHORT).show();
        }
    }

    //Handles what happens if the 'Favourites' icon is toggled.
    public void addFavItem(int ID, String name, String size, String creamAndSugar, String amount, int price)
    {
        //Creating a new database instance.
        DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
        //Adding the item to teh favourites table.
        db.addFavouritesItem(ID, name, size, creamAndSugar, amount, price);
    }

    //Handles what happens if the 'Favourites' icon is pressed when it has already been toggled.
    public void deleteFavouritesItem(FavouritesItemModel fav)
    {
        //Creating a new database instance.
        DataBaseOpenHelper db = new DataBaseOpenHelper(ScreenTwo.this);
        //Deleting the item from the table.
        db.deleteFavouritesItem(fav);
    }

    //This method handles the price summation throughout the various functions that could affect it.
    public void getPrice()
    {
        //Radio buttons(group). Redirecting the objects created above to the instances that are present on screen using findViewById().
        small = (RadioButton) findViewById(R.id.smallRadioBtn);
        medium = (RadioButton) findViewById(R.id.mediumRadiobtn);
        large = (RadioButton) findViewById(R.id.largeRadioBtn);

        //Getting the id of the button
        setBtn = (Button) findViewById(R.id.setBtn);


       // Bundle price = getIntent().getExtras();
       // final int[] menuItemPrice = {price.getInt("Price")};

        //Getting the Item price textview by findViewById().
        itemPrice = (TextView) findViewById(R.id.totalPricetxt);
        //The value is initially set to 0.
        itemPrice.setText("0");

        //Handles what happens if the 'small' option from teh radio group is chosen.
        small.setOnClickListener(new View.OnClickListener()
        {
            //When the radio button is clicked.
            @Override
            public void onClick(View view)
            {
                //Creating a new bundle instance and getting the intent through it. The 'getExtras()' method is also used to receive the intent passed from the fragment through the 'putExtra()'.
                //This is done to acquire the initial price of the beverage.
                Bundle price = getIntent().getExtras();
                //Storing the price value into a variable.
                int menuItemPrice = price.getInt("Price");

                //Getting the ItemPrice textview with findViewById().
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                //Converting the price that has been acquired through intent, from int -> to String.
                String finalPrice = String.valueOf(menuItemPrice);
                //Setting the value of the text to the item price.
                itemPrice.setText(finalPrice);
            }
        });

        //Handles what happens if the 'medium' option from teh radio group is chosen.
        medium.setOnClickListener(new View.OnClickListener()
        {
            //When the radio button is clicked.
            @Override
            public void onClick(View view)
            {
                //Creating a new bundle instance and getting the intent through it. The 'getExtras()' method is also used to receive the intent passed from the fragment through the 'putExtra()'.
                //This is done to acquire the initial price of the beverage.
                Bundle price = getIntent().getExtras();
                //Storing the price value into a variable.
                int menuItemPrice = price.getInt("Price");
                //Adding a 1 to the initial price since this is a medium size cup.
                menuItemPrice = (menuItemPrice + 1);

                //Getting the ItemPrice textview with findViewById().
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                //Converting the price that has been acquired through intent, from int -> to String.
                String finalPrice = String.valueOf(menuItemPrice);
                //Setting the value of the text to the item price.
                itemPrice.setText(finalPrice);
            }
        });

        //Handles what happens if the 'large' option from teh radio group is chosen.
        large.setOnClickListener(new View.OnClickListener()
        {
            //When the radio button is clicked.
            @Override
            public void onClick(View view)
            {
                //Creating a new bundle instance and getting the intent through it. The 'getExtras()' method is also used to receive the intent passed from the fragment through the 'putExtra()'.
                //This is done to acquire the initial price of the beverage.
                Bundle price = getIntent().getExtras();
                //Storing the price value into a variable.
                int menuItemPrice = price.getInt("Price");
                //Adding a 2 to the initial price since this is a medium size cup.
                menuItemPrice = (menuItemPrice + 2);

                //Getting the ItemPrice textview with findViewById().
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                //Converting the price that has been acquired through intent, from int -> to String.
                String finalPrice = String.valueOf(menuItemPrice);
                //Setting the value of the text to the item price.
                itemPrice.setText(finalPrice);
            }
        });

        //Getting the id of the checkbox.
        sugarAndCreamchbx = (CheckBox) findViewById(R.id.sugarAndCreamchbx);
        //Handles the price if the checkbox is checked or not.
        sugarAndCreamchbx.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(sugarAndCreamchbx.isChecked())
                {
                    //Getting the ItemPrice textview with findViewById().
                    itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                    //Getting the price that is currently present in the price textview and converting ot to an int variable.
                    int finalPrice = Integer.parseInt(itemPrice.getText().toString());

                    //Increasing the price by 1, since an extra add-on has been added.
                    finalPrice = (finalPrice + 1);
                    //Setting the final price with the new updated price.
                    itemPrice.setText(String.valueOf(finalPrice));
                }
                else if(!sugarAndCreamchbx.isChecked())
                {
                    //Getting the ItemPrice textview with findViewById().
                    itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                    //Getting the price that is currently present in the price textview and converting ot to an int variable.
                    int finalPrice = Integer.parseInt(itemPrice.getText().toString());

                    //Decreasing the price by 1, since an extra add-on has been added.
                    finalPrice = (finalPrice - 1);
                    //Setting the final price with the new updated price.
                    itemPrice.setText(String.valueOf(finalPrice));
                }

            }
        });

        //OnClickListener that handles what happens when the 'Set' button for the amount is pressed.
        setBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Getting the ItemPrice textview and amountTxt EditText with findViewById().
                itemPrice = (TextView) findViewById(R.id.totalPricetxt);
                amountTxt = (EditText) findViewById(R.id.amountTxt);

                //Getting the current price and converting it into an int variable.
                final int subTotal = Integer.parseInt(itemPrice.getText().toString());
                //Getting the amount chosen by the user and storing it into an int variable.
                int amount = Integer.parseInt(amountTxt.getText().toString());

                //Creating a new total price by multiplying the current price by the amount chosen by the user.
                int total = (subTotal * amount);
                //Setting the new price
                itemPrice.setText(String.valueOf(total));

                //Validation
                if(!amountTxt.getText().toString().equals("0") || itemPrice.getText().toString().equals("0"))
                {
                    //Hiding the button once it is pressed.
                    setBtn.setVisibility(view.GONE);
                    //Source: https://stackoverflow.com/questions/9470171/edittext-non-editable
                    //Making the EditText uneditable since the amount has already been chosen.
                    amountTxt.setEnabled(false);
                }
            }
        });
    }
}