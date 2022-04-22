package com.example.coffebean.ui.OrderHistory;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffebean.R;
import com.example.coffebean.databinding.FragmentCartBinding;
import com.example.coffebean.databinding.OrderHistoryFragmentBinding;
import com.example.coffebean.ui.OrderHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment
{
    //Creating the required variables needed by the recyclerview.
    private OrderHistoryViewModel mViewModel;
    private OrderHistoryAdapter adapter;
    private OrderHistoryFragmentBinding binding;
    private RecyclerView orderView;
    private List<OrderHistoryItemModel> orderItems = new ArrayList<>();

    //The onCreateView executes as soon as the corresponding view is loaded.
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Setting the mViewModel item, to the correct instance of its ViewModel class.
        mViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        //Binding the fragment.
        binding = OrderHistoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Setting the recyclerview object created above, to the recyclerview that is present in the fragment.
        orderView = root.findViewById(R.id.orders_list);
        //setting up the RecyclerView.
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
        //Passing the context to the cartViewModel so ot can used to create an instance of the database.
        mViewModel.getItems(context).observe(getViewLifecycleOwner(), this::updateItemsList);
    }
    //Setting up the recyclerView by using the RecyclerView that was set above and the adapter class.
    private void setUpRecyclerView()
    {
        //Setting the adapter object created above, to the correct Adapter that corresponds with this fragment. Also an ArrayList of the corresponding model us passed as a
        //parameter.
        adapter = new OrderHistoryAdapter(orderItems);
        //Setting the recyclerview adapter to the adapter that has been declared above with an arrayList passed a parameter.
        orderView.setAdapter(adapter);
        orderView.setLayoutManager(new LinearLayoutManager(orderView.getContext()));
    }
    //Update the RecyclerView dynamically.
    @SuppressLint("NotifyDataSetChanged")
    private void updateItemsList(List<OrderHistoryItemModel> newItems)
    {
        //Clear the contents of the recyclerview when another fragment is chosen. This will allow the recyclerview to maintain unique items and not create duplicates everytime it is
        //created.
        orderItems.clear();
        //Adding the contents that were received from the database through the fetchItems() method present here and the getItems() method present in the viewModel to the list created above.
        //This list is later passed on to the adapter.
        orderItems.addAll(newItems);
        adapter.notifyDataSetChanged();
    }

    //Since the button present on this page is not in the fragment, this method can be left empty.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

}