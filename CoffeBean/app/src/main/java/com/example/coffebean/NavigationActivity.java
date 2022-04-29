package com.example.coffebean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.coffebean.databinding.ActivityNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;


/*
* NavigationActivity:
* ^^^^^^^^^^^^^^^^^^
* This is the activity which holds the main content of the app. It allows the user to navigate through the different fragments of the app, which are:
*   - Menu
*   - Favourites
*   - Cart
* In these three fragments the user will perform most of the activities presented by the app.
*
* This activity is also responsible for the Drawer layout. This drawer will allow the user to perform some secondary actions that will make the app
* feel much more dynamic.
* */


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    //Creating the required objects for this activity.
    private ActivityNavigationBinding binding;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Inflating the navigation UI using Java code instead of XML.
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu, R.id.navigation_favourites, R.id.navigation_cart)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //Redirecting the objects created above, to the NavigationDrawer objects that are present on screen.
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        navDrawer = (ImageView) findViewById(R.id.previousArrowBtn);

        //Badges reference: https://material.io/components/bottom-navigation/android#using-bottom-navigation

        //The below will handle the badge display of items in the cart.
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //Creating an instance of the DatabaseOpenHelper.
        DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
        //Storing the value returned from the database into an int variable.
        int badgeNumber = db.getCartItemssize();
        //Creating a badge. this will prove useful when deploying the cart.
        var badge = navView.getOrCreateBadge(R.id.navigation_cart);
        //Setting visibility.
        if(badgeNumber <= 0)
            badge.setVisible(false);
        else
            badge.setVisible(true);
        //Number that will be displayed as a badge.
        badge.setNumber(badgeNumber);

        //The below will handle badge display of items in favourites.
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        int favBadgeNumber = db.getFavouritesItem().size();
        //Creating a badge. this will prove useful when deploying the cart.
        var favBadge = navView.getOrCreateBadge(R.id.navigation_favourites);
        //Setting visibility.
        if(favBadgeNumber <= 0)
            favBadge.setVisible(false);
        else
            favBadge.setVisible(true);
        //Number that will be displayed as a badge.
        favBadge.setNumber(favBadgeNumber);

        //This code represents the drawer layout that can be called upon and retracted.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());

        //This method handles interaction with the navigation view.
        setNavigationViewListener();
    }

    //This method opens the Drawer layout.
    public void openDrawer()//(*)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //Source: https://stackoverflow.com/questions/42297381/onclick-event-in-navigation-drawer (*)
    //This method handles what happens when an option of teh navigationDrawer is selected.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        //Switch statement that represents all the options of the navigationDrawer by id. each case handles what happens when an item is clicked.
        switch (item.getItemId())
        {
            case R.id.clear_cart:
            {
                //Creating a new instance of the BottomNavigationView.
                BottomNavigationView navView = findViewById(R.id.nav_view);
                //Creating a new instance of the database.
                DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
                //Action of the selected option.
                db.deleteAllInCart();
                //Updating the badge.
                int badgeNumber = db.getCartItems().size();
                //Creating a badge. this will prove useful when deploying the cart.
                var badge = navView.getOrCreateBadge(R.id.navigation_cart);
                //Setting visibility.
                if(badgeNumber <= 0)
                    badge.setVisible(false);
                else
                    badge.setVisible(true);
                //Number that will be displayed as a badge.
                badge.setNumber(badgeNumber);
                //Refresh the page.
                recreate();
                break;
            }
            case R.id.clear_favourites:
            {
                //Creating a new instance of the BottomNavigationView.
                BottomNavigationView navView = findViewById(R.id.nav_view);
                //Creating a new instance of the database.
                DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
                //Action of the selected option.
                db.deleteAllInFavourites();
                //Updating the badge.
                int badgeNumber = db.getFavouritesItem().size();
                //Creating a badge. this will prove useful when deploying the cart.
                var badge = navView.getOrCreateBadge(R.id.navigation_favourites);
                //Setting visibility.
                if(badgeNumber <= 0)
                    badge.setVisible(false);
                else
                    badge.setVisible(true);
                //Number that will be displayed as a badge.
                badge.setNumber(badgeNumber);
                //Refresh the page.
                recreate();
                break;
            }
            case R.id.order_historyy:
            {
                //Redirect to a new activity.
                Intent intent = new Intent(NavigationActivity.this, orderHistory.class);
                startActivity(intent);
                break;
            }
            case R.id.suggest:
            {
                //Redirect to a new activity.
                Intent intent = new Intent(NavigationActivity.this, suggestBeverage.class);
                startActivity(intent);
                break;
            }

        }
        //close navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Listener that handles what happens when the navigationView is interacted with.
    private void setNavigationViewListener()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}