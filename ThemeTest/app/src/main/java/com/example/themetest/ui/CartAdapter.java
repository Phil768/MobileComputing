package com.example.themetest.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themetest.DataBaseOpenHelper;
import com.example.themetest.R;
import com.example.themetest.ScreenTwo;
import com.example.themetest.ui.Cart.CartItemModel;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
{
    private List<CartItemModel> cartItems;

    public CartAdapter(List<CartItemModel> cart)
    {
        this.cartItems = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_cart_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        CartItemModel item = cartItems.get(position);
        TextView primaryTextView = holder.pTextView;

        //The below parameter for the setText was cast to a string via a toString() method. However this was not working since we do not want a variable
        //of type sting, we want it of MenuItemModel. So instead we call the Name attribute of the object in the list via the .Name command.
        primaryTextView.setText(item.Name);

    }

    @Override
    public int getItemCount()
    {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ImageView;
        public TextView pTextView;

        public ViewHolder(final View itemView)
        {
            super(itemView);
            ImageView = (ImageView) itemView.findViewById(R.id.cart_list_item_icon);
            pTextView = (TextView) itemView.findViewById(R.id.cart_primary_text);
        }

        //When one of the cards in the recyclerview is clicked!!
        public void itemOnClick(View v)
        {
            //empty for now
        }
    }
}
