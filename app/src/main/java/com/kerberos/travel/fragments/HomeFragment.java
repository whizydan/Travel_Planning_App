package com.kerberos.travel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.TrendingAdapter;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.TrendingModel;
import com.kerberos.travel.pages.AttractionsActivity;
import com.kerberos.travel.pages.DestinationActivity;
import com.kerberos.travel.pages.MapsActivity;
import com.kerberos.travel.pages.NotificationActivity;
import com.kerberos.travel.pages.RestaurantActivity;
import com.kerberos.travel.pages.TranslateActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView notification = view.findViewById(R.id.notify);
        ImageView attractions = view.findViewById(R.id.imageView8);
        ImageView currency = view.findViewById(R.id.imageView81);
        ImageView map = view.findViewById(R.id.imageView7);
        ImageView restaurant = view.findViewById(R.id.imageView6);
        ImageView translate = view.findViewById(R.id.translate);
        SearchBar searchBar =  view.findViewById(R.id.searchBar);
        SearchView searchView = view.findViewById(R.id.searchView);
        ImageView flights = view.findViewById(R.id.imageView3);
        ImageView hotels = view.findViewById(R.id.imageView4);
        ImageView transport = view.findViewById(R.id.car);
        ArrayList<TrendingModel> trending = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView);
        ImageView imageView = view.findViewById(R.id.imageView10);

        imageView.setOnClickListener(v ->{
            showBottomSheetDialog("30 % Off Sale", "");
        });

        trending.add(new TrendingModel("0","","Pulau Redang, Malaysia",R.drawable.pulau_thumbnail));
        trending.add(new TrendingModel("1","","Phuket, Thailand",R.drawable.phuket_thumbnail));
        trending.add(new TrendingModel("2","","Bali, Indonesia",R.drawable.bali_humbnail));

        searchView.setupWithSearchBar(searchBar);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),2));
        recyclerView.setAdapter(new TrendingAdapter(requireActivity(),trending));

        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));
        TrendingAdapter trendingAdapter = new TrendingAdapter(requireActivity(),trending);
        trendingRecyclerView.setAdapter(trendingAdapter);


        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                trendingAdapter.filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(searchView.isShowing()){
                    searchView.hide();
                }
            }
        });

        Intent intent = new Intent(requireActivity(), DestinationActivity.class);

        transport.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new TransportFragment()).commit();
        });
        hotels.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new HotelsFragment()).commit();
        });
        flights.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new FlightsFragment()).commit();
        });
        map.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), MapsActivity.class));
        });
        currency.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new CurrencyFragment()).commit();
        });
        attractions.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), AttractionsActivity.class));
        });
        restaurant.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new RestaurantFragment()).commit();
        });
        translate.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), TranslateActivity.class));
        });
        notification.setOnClickListener(v->{
            Intent intent2 = new Intent(requireActivity(), NotificationActivity.class);
            startActivity(intent2);
        });
    }
    private void showBottomSheetDialog(String title, String description) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.discount_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView titleText = bottomSheetView.findViewById(R.id.title);
        TextView descriptionText = bottomSheetView.findViewById(R.id.description);

        titleText.setText(title);
        //descriptionText.setText(description);

        bottomSheetDialog.show();
    }
}