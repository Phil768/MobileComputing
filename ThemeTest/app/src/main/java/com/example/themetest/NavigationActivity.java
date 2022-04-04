package com.example.themetest;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.themetest.databinding.ActivityNavigationBinding;
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

import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationView;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private ActivityNavigationBinding binding;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView navDrawer;
    NavigationView clearCart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        navDrawer = (ImageView) findViewById(R.id.backArrow);

        //The below will handle the badge display of items in the cart.
        //Creating an instance of the DatabaseOpenHelper.
        DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
        //Storing the value returned from the database into an int variable.
        int badgeNumber = db.getNumberOfCartItems();
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
        int favBadgeNumber = db.getNumberOfFavouritesItems();
        //Creating a badge. this will prove useful when deploying the cart.
        var favBadge = navView.getOrCreateBadge(R.id.navigation_favourites);
        //Setting visibility.
        if(favBadgeNumber <= 0)
            favBadge.setVisible(false);
        else
            favBadge.setVisible(true);
        //Number that will be displayed as a badge.
        favBadge.setNumber(favBadgeNumber);

        //This code represents the toolbar that can be called upon and retracted.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());

        //This method handles interaction with the navigation view.
        setNavigationViewListener();
    }

    public void openDrawer()
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //Source: https://stackoverflow.com/questions/42297381/onclick-event-in-navigation-drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.clear_cart:
            {
                BottomNavigationView navView = findViewById(R.id.nav_view);
                DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
                db.deleteAllInCart();
                int badgeNumber = db.getNumberOfCartItems();
                //Creating a badge. this will prove useful when deploying the cart.
                var badge = navView.getOrCreateBadge(R.id.navigation_cart);
                //Setting visibility.
                if(badgeNumber <= 0)
                    badge.setVisible(false);
                else
                    badge.setVisible(true);
                //Number that will be displayed as a badge.
                badge.setNumber(badgeNumber);
                break;
            }
            case R.id.clear_favourites:
            {
                BottomNavigationView navView = findViewById(R.id.nav_view);
                DataBaseOpenHelper db = new DataBaseOpenHelper(NavigationActivity.this);
                db.deleteAllInFavourites();
                int badgeNumber = db.getNumberOfFavouritesItems();
                //Creating a badge. this will prove useful when deploying the cart.
                var badge = navView.getOrCreateBadge(R.id.navigation_favourites);
                //Setting visibility.
                if(badgeNumber <= 0)
                    badge.setVisible(false);
                else
                    badge.setVisible(true);
                //Number that will be displayed as a badge.
                badge.setNumber(badgeNumber);
                break;
            }
        }
        //close navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}