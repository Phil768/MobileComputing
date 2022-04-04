package com.example.themetest.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themetest.DataBaseOpenHelper;
import com.example.themetest.R;
import com.example.themetest.ScreenTwo;
import com.example.themetest.ui.Menu.MenuFragment;
import com.example.themetest.ui.Menu.MenuItemModel;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{
    private List<MenuItemModel> items;

    public ItemsAdapter(List<MenuItemModel> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_card, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MenuItemModel item = items.get(position);
        TextView primaryTextView = holder.primaryTextView;

        //The below parameter for the setText was cast to a string via a toString() method. However this was not working since we do not want a variable
        //of type sting, we want it of MenuItemModel. So instead we call the Name attribute of the object in the list via the .Name command.
        primaryTextView.setText(item.Name);

    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iconImageView;
        public TextView primaryTextView;

        public ViewHolder(final View itemView)
        {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.mtrl_list_item_icon);
            primaryTextView = (TextView) itemView.findViewById(R.id.item_primary_text);
            itemView.setOnClickListener(new View.OnClickListener()
            {
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
            //Creating a new intent instance.
            Intent intent = new Intent(v.getContext(), ScreenTwo.class);
            //Passing data to the Activity.
            intent.putExtra("Name", menuItemName);
            //Getting the item price.
            int menuItemPrice = db.getItemPrice(i);
            //Passing data to the activity.
            intent.putExtra("Price", menuItemPrice);
            //redirecting to a new activity.
            v.getContext().startActivity(intent);
        }
    }
}
