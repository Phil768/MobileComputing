package com.example.coffebean.ui.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffebean.DataBaseOpenHelper;
import com.example.coffebean.R;
import com.example.coffebean.checkout;
import com.example.coffebean.databinding.FragmentCartBinding;
import com.example.coffebean.databinding.FragmentMenuBinding;
import com.example.coffebean.ui.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment
{
    //Creating a Button object.
    Button checkoutBtn;
    //Creating the required variables needed by the recyclerview.
    private CartViewModel cartViewModel;
    private CartAdapter adapter;
    private FragmentCartBinding binding;
    private RecyclerView CartView;
    private List<CartItemModel> cartItems = new ArrayList<>();

    //The onCreateView executes as soon as the corresponding view is loaded.
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Setting the cartViewModel item, to the correct instance of its ViewModel class.
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        //Binding the fragment.
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Setting the recyclerview object created above, to the recyclerview that is present in the fragment.
        CartView = root.findViewById(R.id.cart_list);
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
        cartViewModel.getItems(context).observe(getViewLifecycleOwner(), this::updateItemsList);
    }
    //Setting up the recyclerView by using the RecyclerView that was set above and the adapter class.
    private void setUpRecyclerView()
    {
        //Setting the adapter object created above, to the correct Adapter that corresponds with this fragment. Also an ArrayList of the corresponding model us passed as a
        //parameter.
        adapter = new CartAdapter(cartItems);
        //Setting the recyclerview adapter to the adapter that has been declared above with an arrayList passed a parameter.
        CartView.setAdapter(adapter);
        CartView.setLayoutManager(new LinearLayoutManager(CartView.getContext()));
    }
    //Update the RecyclerView dynamically.
    @SuppressLint("NotifyDataSetChanged")
    private void updateItemsList(List<CartItemModel> newItems)
    {
        //Clear the contents of the recyclerview when another fragment is chosen. This will allow the recyclerview to maintain unique items and not create duplicates everytime it is
        //created.
        cartItems.clear();
        //Adding the contents that were received from the database through the fetchItems() method present here and the getItems() method present in the viewModel to the list created above.
        //This list is later passed on to the adapter.
        cartItems.addAll(newItems);
        adapter.notifyDataSetChanged();
    }
    //This method executes after the view has been created. It executes after the OnCreateView.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Creating a new instance of the database using the current class' context.
        DataBaseOpenHelper db = new DataBaseOpenHelper(getContext());
        int size = db.getCartItems().size();

        //Creating an instance of the 'checkout' button that is present on the screen.
        checkoutBtn = (Button) view.findViewById(R.id.checkoutBtn);
        //Creating an OnClickListener which handles what happens when the button is clicked.
        checkoutBtn.setOnClickListener(new View.OnClickListener()
        {
            //Validation to check if the cart is empty or not.
            @Override
            public void onClick(View view)
            {
                //If the Cart table is empty.
                if(size == 0)
                {
                    //Inform the user that the cart is empty through a Toast, and restrict them from proceeding to the checkout.
                    Toast.makeText(getContext(), "Cart is empty !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Creating a new intent instance.
                    Intent intent = new Intent(view.getContext(), checkout.class);
                    //Switching to a new activity using the above created intent.
                    startActivity(intent);
                }
            }
        });

    }
}