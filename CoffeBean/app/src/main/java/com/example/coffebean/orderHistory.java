package com.example.coffebean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
* OrderHistory Activity
* ^^^^^^^^^^^^^^^^^^^^^
* In this activity the user is able to view past orders that he has made. The orders appear separately, for example, if in the cart there were 5 items, in this activity 5 items would appear.
* The activity uses a recyclerview to display these order. The user also has the option to clear the history with just a press of a button.
* */

public class orderHistory extends AppCompatActivity
{
    //Creating the required objects by the activity
    ImageView previousBtn;
    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        //Redirecting the imageview that represents the back arrow.
        previousBtn = (ImageView) findViewById(R.id.previousArrowBtn);
        //handles the back arrow(May make it more efficient in the future)
        previousBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new intent instance, with context of this page and the page that the intent will redirect to.
                Intent intent = new Intent(orderHistory.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Redirecting the button created above the teh button 'Clear order history' that is present on screen.
        clearBtn = (Button) findViewById(R.id.clearBtn);
        //Handles what happens when the above button is clicked.
        clearBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new instance of the database.
                DataBaseOpenHelper db = new DataBaseOpenHelper(view.getContext());
                //Deleting all the items from the 'Order' table.
                db.deleteAllInOrderHistory();
                //refreshing the page to make it look more dynamic.
                recreate();
            }
        });
    }
}