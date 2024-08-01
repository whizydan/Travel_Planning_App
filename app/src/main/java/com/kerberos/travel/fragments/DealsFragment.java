package com.kerberos.travel.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.DealsAdapter;
import com.kerberos.travel.database.Database;
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.models.CouponsModel;
import com.kerberos.travel.pages.AttractionsActivity;

import java.sql.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DealsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<CouponsModel> coupons;

    DealsAdapter.ItemClickListener itemClickListener = new DealsAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            showBottomSheetDialog(coupons.get(position).getDiscount() + "% Off", coupons.get(position).getDescription());
        }
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DealsFragment newInstance(String param1, String param2) {
        DealsFragment fragment = new DealsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView deals = view.findViewById(R.id.deals);
        TextInputLayout searchBar = view.findViewById(R.id.searchDeal);
        coupons = new ArrayList<>();
        final DealsAdapter[] adapter = new DealsAdapter[1];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("coupons");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CouponsModel coupon = childSnapshot.getValue(CouponsModel.class);
                    coupons.add(coupon);
                }
                adapter[0] = new DealsAdapter(requireActivity(),coupons);
                adapter[0].setClickListener(itemClickListener);
                deals.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));
                deals.setAdapter(adapter[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(),error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

        searchBar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter[0].filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //adapter[0].filter(s.toString());
            }
        });
    }
    private void showBottomSheetDialog(String title, String description) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.discount_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView titleText = bottomSheetView.findViewById(R.id.title);
        TextView descriptionText = bottomSheetView.findViewById(R.id.description);

        titleText.setText(title);
        descriptionText.setText(description);

        bottomSheetDialog.show();
    }
}