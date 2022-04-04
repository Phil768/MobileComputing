package com.example.themetest.ui.Menu;

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
import com.example.themetest.ui.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment
{
    private MenuViewModel menuViewModel;
    private ItemsAdapter adapter;
    private @NonNull FragmentMenuBinding binding;
    private RecyclerView itemsView;
    private List<MenuItemModel> items = new ArrayList<>();
    
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //This is the recycler view.
        itemsView = root.findViewById(R.id.items_list);
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
        ArrayList<MenuItemModel> menuItems = db.getMenuItems();

        menuViewModel.getItems(menuItems).observe(getViewLifecycleOwner(), this::updateItemsList);
    }
    private void setUpRecyclerView() 
    {
        adapter = new ItemsAdapter(items);
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(new LinearLayoutManager(itemsView.getContext()));
    }
    private void updateItemsList(List<MenuItemModel> newItems)
    {
        items.addAll(newItems);
        adapter.notifyDataSetChanged();
    }


}