package com.example.coffebean.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffebean.R;
import com.example.coffebean.ui.OrderHistory.OrderHistoryItemModel;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>
{
    //Creating a list of type OrderHistoryItemModel.
    private List<OrderHistoryItemModel> orderItems;

    //Constructor of the class.
    public OrderHistoryAdapter(List<OrderHistoryItemModel> order)
    {
        this.orderItems = order;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Getting the context of the current class.
        Context context = parent.getContext();
        //The inflater allows us to create the UI without XML, just code. In this case we are using Java to inflate the layout of the recyclerview.
        LayoutInflater inflater = LayoutInflater.from(context);
        //Setting the view to the card (layout object) that corresponds to the MenuItems.
        View itemView = inflater.inflate(R.layout.layout_order_card, parent, false);
        return new OrderHistoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position)
    {
        //Getting the item at the desired position from the recyclerview.
        OrderHistoryItemModel item = orderItems.get(position);
        //Creating a new textview that its value is passed through the ViewHolder.
        TextView primaryTextView = holder.pTextView;
        TextView secondaryTextView = holder.sTextView;

        //The below parameter for the setText was cast to a string via a toString() method. However this was not working since we do not want a variable
        //of type sting, we want it of MenuItemModel. So instead we call the Name attribute of the object in the list via the .Name command.
        primaryTextView.setText(item.getItem());

        //Specific String to be displayed as a subtext.
        String secondary = "Total price of order: $"+ item.getPrice() + "        Date of order: " + item.getDate();
        secondaryTextView.setText(secondary);

    }

    //Getting the size of the recyclerview.
    @Override
    public int getItemCount()
    {
        return orderItems.size();
    }

    //This ViewHolder will allow us to access any item from the recyclerview without out it looking it up each time. This is what allows us to interact
    //with each item in the recyclerview without using the 'finViewById()' method.
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Creating the required objects that are present in each card.
        public android.widget.ImageView ImageView;
        public TextView pTextView, sTextView;

        //Constructor of this ViewHolder sub-class.
        public ViewHolder(final View itemView)
        {
            super(itemView);
            ImageView = (ImageView) itemView.findViewById(R.id.order_list_item_icon);
            pTextView = (TextView) itemView.findViewById(R.id.order_primary_text);
            sTextView = (TextView) itemView.findViewById(R.id.order_secondary_text);
        }

        //Since we have no interaction with the recyclerview of the OrderHistory, this method can be left empty.
        public void itemOnClick(View v)
        {
            //empty for now
        }
    }
}
