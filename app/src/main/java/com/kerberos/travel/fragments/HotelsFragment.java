package com.kerberos.travel.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.AttractionsAdapter;
import com.kerberos.travel.adapters.HotelsAdapter;
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.HotelsModel;
import com.kerberos.travel.pages.AttractionDetailActivity;
import com.kerberos.travel.pages.DestinationActivity;
import com.kerberos.travel.pages.PaymentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextInputLayout to;
    String checkInValue, checkOutValue, roomValue;
    final int[] totalPrice = {0};
    int hotelPrice = 0;

    private  HotelsAdapter.ItemClickListener itemClickListener = new HotelsAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            HotelsModel hotelsModel = hotels.get(position);
            hotelPrice = (int) hotelsModel.getPrice();
            Intent intent = new Intent(requireActivity(), PaymentActivity.class);
            //show a bottom sheet dialog to select number of rooms etc then proceed to checkout
            intent.putExtra("from", hotelsModel.getName());
            intent.putExtra("to", hotelsModel.getLocation());
            intent.putExtra("name", hotelsModel.getName());
            intent.putExtra("userId", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            intent.putExtra("type","hotel");
            intent.putExtra("time","");

            showBottomSheetDialog(hotelsModel ,intent);
        }
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<HotelsModel> hotels;
    HotelsAdapter hotelsAdapter;
    TextInputLayout date;

    public HotelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelsFragment newInstance(String param1, String param2) {
        HotelsFragment fragment = new HotelsFragment();
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
        return inflater.inflate(R.layout.fragment_hotels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView back = view.findViewById(R.id.imageView29);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MaterialButton search = view.findViewById(R.id.materialButton3);
        to = view.findViewById(R.id.to);
        date = view.findViewById(R.id.in);
        TextInputLayout out = view.findViewById(R.id.out);
        TextInputLayout rooms = view.findViewById(R.id.rooms);
        hotels = new ArrayList<>();

        date.setStartIconOnClickListener(v->{
            showDatePicker(date);
        });
        out.setStartIconOnClickListener(v->{
            showDatePicker(out);
        });

        search.setOnClickListener(v ->{
            if(TextUtils.isEmpty(to.getEditText().getText().toString())){
                to.setError("Enter location");
            } else if (TextUtils.isEmpty(date.getEditText().getText().toString())) {
                date.setError("Check in date required");
            }else if(TextUtils.isEmpty(out.getEditText().getText().toString())){
                out.setError("Check out date required");
            }else if(TextUtils.isEmpty(rooms.getEditText().getText().toString())){
                rooms.setError("Enter number of rooms required");
            }
            else{
                //perform search on the list
                if(hotelsAdapter != null){
                    hotelsAdapter.filter(to.getEditText().getText().toString(),date.getEditText().getText().toString(),rooms.getEditText().getText().toString());
                }
            }
        });
        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new HomeFragment()).commit();
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("hotels");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println(dataSnapshot.toString());
                    HotelsModel hotelsModel = dataSnapshot.getValue(HotelsModel.class);
                    hotels.add(hotelsModel);
                }
                hotelsAdapter = new HotelsAdapter(requireActivity(),hotels);
                hotelsAdapter.setClickListener(itemClickListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(hotelsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(),error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showDatePicker(TextInputLayout textInputLayout) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date selection here
                        String selectedDate = dayOfMonth + "-" + monthOfYear + "-" + year;
                        Objects.requireNonNull(textInputLayout.getEditText()).setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
    private void showBottomSheetDialog(HotelsModel hotel, Intent intent) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.sheet_enter_pax, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        totalPrice[0] = Integer.parseInt(String.valueOf(hotel.getPrice()));

        MaterialButton bottomSheetButton = bottomSheetView.findViewById(R.id.done);
        TextInputLayout pax = bottomSheetView.findViewById(R.id.textInputLayout3);
        TextInputLayout from = bottomSheetView.findViewById(R.id.textInputLayout4);
        TextInputLayout to = bottomSheetView.findViewById(R.id.textInputLayout6);
        TextView total = bottomSheetView.findViewById(R.id.textInputLayout7);
        TextInputLayout children = bottomSheetView.findViewById(R.id.textInputLayout5);

        from.setEndIconOnClickListener(v->{
            showDatePicker(from);
        });
        to.setEndIconOnClickListener(v->{
            showDatePicker(to);
        });

        children.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUi(children,pax,from,to,total);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        from.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUi(children,pax,from,to,total);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pax.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUi(children,pax,from,to,total);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        to.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUi(children,pax,from,to,total);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(from.getEditText().getText().toString())){
                    from.setError("Select date");
                } else if (TextUtils.isEmpty(to.getEditText().getText().toString())) {
                    to.setError("Date ir required");
                } else if (TextUtils.isEmpty(pax.getEditText().getText())) {
                    pax.setError("No. of travellers required");
                }else{
                    intent.putExtra("date",from.getEditText().getText().toString() + " to " + to.getEditText().getText().toString());
                    intent.putExtra("price",totalPrice[0]);
                    intent.putExtra("pax",Integer.parseInt(pax.getEditText().getText().toString()) + Integer.parseInt(children.getEditText().getText().toString()));
                    intent.putExtra("extra","Adults: " + pax.getEditText().getText().toString() + " , Children: " + children.getEditText().getText().toString());
                    startActivity(intent);
                }

            }
        });

        bottomSheetDialog.show();
    }

    private void updateUi(TextInputLayout children, TextInputLayout adults,
                          TextInputLayout checkIn, TextInputLayout checkOut, TextView total) {
        // Parse date strings into Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate;
        Date endDate;
        try {
            // Get date strings from TextInputLayouts
            String checkInDate = checkIn.getEditText().getText().toString();
            String checkOutDate = checkOut.getEditText().getText().toString();

            startDate = dateFormat.parse(checkInDate);
            endDate = dateFormat.parse(checkOutDate);

            // Calculate the difference in days between start and end dates
            long differenceMillis = endDate.getTime() - startDate.getTime();
            int daysBetween = (int) (differenceMillis / (1000 * 60 * 60 * 24));

            // Get values for number of traveler
            if(!TextUtils.isEmpty(children.getEditText().getText()) && !TextUtils.isEmpty(adults.getEditText().getText())){
                int childrenValue = Integer.parseInt(children.getEditText().getText().toString());
                int adultCount = Integer.parseInt(adults.getEditText().getText().toString());

                // Calculate total price based on number of travelers, hotel price, and days between
                totalPrice[0] = (childrenValue + adultCount) * hotelPrice * daysBetween;

                // Set the total price text in the TextView
                total.setText("Total price : RM " + totalPrice[0]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}