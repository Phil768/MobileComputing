package com.example.coffebean.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffebean.DataBaseOpenHelper;
import com.example.coffebean.R;
import com.example.coffebean.ScreenTwo;
import com.example.coffebean.ui.Menu.MenuItemModel;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{
    //Creating a list of type MenuItemModel.
    private List<MenuItemModel> items;

    //Constructor of the class.
    public ItemsAdapter(List<MenuItemModel> items)
    {
        this.items = items;
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
        View itemView = inflater.inflate(R.layout.layout_card, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //Getting the item at the desired position from the recyclerview.
        MenuItemModel item = items.get(position);
        //Creating a new textview that its value is passed through the ViewHolder.
        TextView primaryTextView = holder.primaryTextView;

        //The below parameter for the setText was cast to a string via a toString() method. However this was not working since we do not want a variable
        //of type sting, we want it of MenuItemModel. So instead we call the Name attribute of the object in the list via the .Name command.
        primaryTextView.setText(item.Name);
    }

    //Getting the size of the recyclerview.
    @Override
    public int getItemCount()
    {
        return items.size();
    }

    //This ViewHolder will allow us to access any item from the recyclerview without out it looking it up each time. This is what allows us to interact
    //with each item in the recyclerview without using the 'finViewById()' method.
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Creating the required objects that are present in each card.
        public ImageView iconImageView;
        public TextView primaryTextView;

        //Constructor of this ViewHolder sub-class.
        public ViewHolder(final View itemView)
        {
            super(itemView);
            //Setting the objects created above to the components that we have on screen.
            iconImageView = (ImageView) itemView.findViewById(R.id.mtrl_list_item_icon);
            primaryTextView = (TextView) itemView.findViewById(R.id.item_primary_text);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                //Handles what happens when an item of the RecyclerView is pressed.
                @Override
                public void onClick(View v) {
                    itemOnClick(v);
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
            //Storing the returned name in a string.
            String menuItemName = db.getItemName(i);
            //Storing the image retrieved from the database into a bitmap variable.
            Bitmap bitmap = db.getMenuImage(i);
            //Getting description of item from the database.
            String description = db.getMenuDescription(i);
            //Creating a new intent instance.
            Intent intent = new Intent(v.getContext(), ScreenTwo.class);
            //Passing data to the Activity.
            intent.putExtra("Name", menuItemName);
            //Getting the item price.
            int menuItemPrice = db.getItemPrice(i);
            //Passing data to the activity.
            intent.putExtra("Price", menuItemPrice);
            //Passing image data to the activity.
            intent.putExtra("BitmapImage", bitmap);
            //Passing description data to the activity.
            intent.putExtra("Description", description);
            //redirecting to a new activity.
            v.getContext().startActivity(intent);
        }
    }
}
