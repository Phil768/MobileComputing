package com.example.coffebean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/*
* The MainActivity
* ^^^^^^^^^^^^^^^^
* This is a fairly simple activity, containing only a button and the textview. This is because it is
* the first page that the user encounters when using the app. Its only role is to redirect them to the
* next pages were they can interact and explore the app's proper features
* */
public class MainActivity extends AppCompatActivity
{
    //Creating the objects required by the page.
    Button getStartedBtn;

    //This method executes when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Redirecting the Button object that was created above, to the button that is present on screen using the 'findViewById()' method.
        getStartedBtn = (Button) findViewById(R.id.getStartedBtn);

        //Creating an OnClickListener which will handle what happens when the button on screen is pressed.
        getStartedBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new Intent object with a context of this activity and the activity that it will Redirect the user to.
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                //Redirect the user to a new activity.
                startActivity(intent);
                //Finishing the intent process.
                finish();
            }
        });
    }
}