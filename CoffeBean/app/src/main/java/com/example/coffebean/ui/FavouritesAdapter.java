package com.example.coffebean.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffebean.DataBaseOpenHelper;
import com.example.coffebean.R;
import com.example.coffebean.ui.Favourites.FavouritesItemModel;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder>
{
    //Creating a list of type FavouritesItemModel.
    private List<FavouritesItemModel> favItems;

    //Constructor of the class.
    public FavouritesAdapter(List<FavouritesItemModel> fav)
    {
        this.favItems = fav;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Getting the context of the current class.
        Context context = parent.getContext();
        //The inflater allows us to create the UI without XML, just code. In this case we are using Java to inflate the layout of the recyclerview.
        LayoutInflater inflater = LayoutInflater.from(context);
        //Setting the view to the card (layout object) that corresponds to the MenuItems.
        View itemView = inflater.inflate(R.layout.layout_favourites_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //Getting the item at the desired position from the recyclerview.
        FavouritesItemModel item = favItems.get(position);
        //Creating a new textviews who's value is passed through the ViewHolder.
        TextView primaryTextView = holder.pTextView;
        TextView secondaryTextView = holder.sTextView;

        //Specific String to be displayed as a subtext.
        String secondary = "Amount: " + item.Amount + "    Size: " + item.Size + "    Sugar & Cream: " + item.SugarAndCream + "    Price: " + "$" +item.Price;
        //The below parameter for the setText was cast to a string via a toString() method. However this was not working since we do not want a variable
        //of type sting, we want it of MenuItemModel. So instead we call the Name attribute of the object in the list via the .Name command.
        primaryTextView.setText(item.Name);
        secondaryTextView.setText(secondary);
    }

    //Getting the size of the recyclerview.
    @Override
    public int getItemCount() {
        return favItems.size();
    }

    //This ViewHolder will allow us to access any item from the recyclerview without out it looking it up each time. This is what allows us to interact
    //with each item in the recyclerview without using the 'finViewById()' method.
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Creating the required objects that are present in each card.
        public ImageView ImageView;
        public TextView pTextView, sTextView;

        //Constructor of this ViewHolder sub-class.
        public ViewHolder(final View itemView)
        {
            super(itemView);
            //Setting the objects created above to the components that we have on screen.
            ImageView = (ImageView) itemView.findViewById(R.id.favourites_list_item_icon);
            pTextView = (TextView) itemView.findViewById(R.id.favourites_primary_text);
            sTextView = (TextView) itemView.findViewById(R.id.favourites_secondary_text);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                //Handles what happens when an item of the RecyclerView is pressed.
                @Override
                public void onClick(View view)
                {
                    //What happens when the recycler view is clicked.
                    itemOnClick(view);
                    //Attempting to refresh the page if teh recycler view is clicked. (Main aim of this is to update the badge)
                    //FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    //manager.beginTransaction().detach(favourites).attach(favourites).commit();
                }
            });
        }

        //When one of the cards in the recyclerview is clicked!!
        public void itemOnClick(View v)
        {
            //Creating an instance of the database.
            DataBaseOpenHelper db = new DataBaseOpenHelper(v.getContext());
            //Getting the position of the item that has been clicked. The '+1' is added since, like an array, it starts counting at 0 and not 1.
            int i = (getAdapterPosition() + 1);
            //Storing the item retrieved at the position in a list element.
            FavouritesItemModel favItem = db.getFavouriteAtPosition(i);
            //Adding the above element to the database.
            db.addCartItem(favItem.Name, favItem.Size, favItem.SugarAndCream, favItem.Amount, favItem.Price);
            //Generating a simple toast to indulge the user that the app is working correctly.
            Toast.makeText(v.getContext(), "Added to cart !!", Toast.LENGTH_SHORT).show();

        }


    }
}