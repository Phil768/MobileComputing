package com.example.coffebean.ui.Menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffebean.R;
import com.example.coffebean.databinding.FragmentMenuBinding;
import com.example.coffebean.ui.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment
{
    //Creating the required variables needed by the recyclerview.
    private MenuViewModel menuViewModel;
    private ItemsAdapter adapter;
    private @NonNull FragmentMenuBinding binding;
    private RecyclerView itemsView;
    private List<MenuItemModel> items = new ArrayList<>();

    //The onCreateView executes as soon as the corresponding view is loaded.
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        //Setting the menuViewModel item, to the correct instance of its ViewModel class.
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        //Binding the fragment.
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Setting the recyclerview object created above, to the recyclerview that is present in the fragment.
        itemsView = root.findViewById(R.id.items_list);
        //setting uop the RecyclerView.
        setUpRecyclerView();
        //Fetches the items and inserting them into the recyclerview.
        fetchItems();
        return root;
    }
    //This method destroys the unused parts of the recycler view and leaves only those that are utilised. Unlike a list-view for example, which creates the whole view
    //even if parts of it are unused. They are created and left empty.
    @Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        binding = null;
    }
    //This method gets the items from the menuViewModel method 'getItems()', which returns a list with items from the database. These items are then passed to the updateItemsList()
    //method so the recyclerview is updated dynamically.
    private void fetchItems() 
    {
        //Getting the context of the class.
        Context context = this.getContext();
        //Passing the context to the MenuViewModel so ot can used to create an instance of the database.
        menuViewModel.getItems(context).observe(getViewLifecycleOwner(), this::updateItemsList);
    }
    //Setting up the recyclerView by using the RecyclerView that was set above and the adapter class.
    private void setUpRecyclerView() 
    {
        //Setting the adapter object created above, to the correct Adapter that corresponds with this fragment. Also an ArrayList of the corresponding model us passed as a
        //parameter.
        adapter = new ItemsAdapter(items);
        //Setting the recyclerview adapter to the adapter that has been declared above with an arrayList passed a parameter.
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(new LinearLayoutManager(itemsView.getContext()));
    }
    //Update the RecyclerView dynamically.
    private void updateItemsList(List<MenuItemModel> newItems)
    {
        //Clear the contents of the recyclerview when another fragment is chosen. This will allow the recyclerview to maintain unique items and not create duplicates everytime it is
        //created.
        items.clear();
        //Adding the contents that were received from the database through the fetchItems() method present here and the getItems() method present in the viewModel to the list created above.
        //This list is later passed on to the adapter.
        items.addAll(newItems);
        adapter.notifyDataSetChanged();
    }


}