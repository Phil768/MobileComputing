package com.example.themetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    Button getStartedBtn;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStartedBtn = (Button) findViewById(R.id.getStartedBtn);

        timer = new Timer();
        getStartedBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                timer.schedule(new TimerTask()
                {
                    public void run()
                    {
                        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },2000);
            }
        });
    }
}