package com.example.themetest.ui.Cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themetest.DataBaseOpenHelper;
import com.example.themetest.R;
import com.example.themetest.ScreenTwo;
import com.example.themetest.databinding.FragmentMenuBinding;
import com.example.themetest.ui.CartAdapter;
import com.example.themetest.ui.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment
{
    private CartViewModel cartViewModel;
    private CartAdapter adapter;
    private FragmentMenuBinding binding;
    private RecyclerView CartView;
    private List<CartItemModel> cartItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //This is the recycler view.
        CartView = root.findViewById(R.id.cart_list);
        setUpRecyclerView();
        fetchItems();
        return root;
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
    private void fetchItems()
    {
        DataBaseOpenHelper db = new DataBaseOpenHelper(requireContext());
        ArrayList<CartItemModel> cart = db.getCartItems();

        cartViewModel.getItems(cart).observe(getViewLifecycleOwner(), this::updateItemsList);
    }
    private void setUpRecyclerView()
    {
        adapter = new CartAdapter(cartItems);
        CartView.setAdapter(adapter);
        CartView.setLayoutManager(new LinearLayoutManager(CartView.getContext()));
    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateItemsList(List<CartItemModel> newItems)
    {
        cartItems.addAll(newItems);
        adapter.notifyDataSetChanged();
    }


}