package com.kerberos.travel.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.TimePicker;

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
import com.kerberos.travel.adapters.TransportAdapter;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.TransportModel;
import com.kerberos.travel.pages.PaymentActivity;
import com.kerberos.travel.pages.SelectCarSeatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class BusFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TransportAdapter.ItemClickListener itemClickListener = new TransportAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            onClick(view, position);
        }
    };
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialButton search;
    TextInputLayout date, location, time;
    ArrayList<TransportModel> transportList;
    TransportAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusFragment newInstance(String param1, String param2) {
        BusFragment fragment = new BusFragment();
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
        return inflater.inflate(R.layout.fragment_bus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        transportList = new ArrayList<>();
        ImageView back = view.findViewById(R.id.imageView30);
        search = view.findViewById(R.id.materialButton3);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        location = view.findViewById(R.id.location);
        TextInputLayout to = view.findViewById(R.id.to);

        //time.getEditText().setText("08:00");

        date.setStartIconOnClickListener(v->{
            showDatePicker(date);
        });
        time.setStartIconOnClickListener(v->{
            showTimePicker(time);
        });

        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new TransportFragment()).commit();
        });

        search.setOnClickListener(v->{
            if(adapter != null){
                adapter.filter(location.getEditText().getText().toString(),to.getEditText().getText().toString(),"","","");
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("transport");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TransportModel transportModel = childSnapshot.getValue(TransportModel.class);

                    if(transportModel.getType().equals("bus")){
                        transportList.add(transportModel);
                    }
                }
                adapter = new TransportAdapter(requireActivity(),transportList);
                adapter.setClickListener(itemClickListener);
                recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),2));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClick(View view, int position) {
        TransportModel transport = transportList.get(position);
        BookingsModel bookingsModel = new BookingsModel();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());
        if(TextUtils.isEmpty(Objects.requireNonNull(date.getEditText()).getText().toString())){
            bookingsModel.setDate(formattedDate);
        }else{
            bookingsModel.setDate(date.getEditText().getText().toString());
        }
        bookingsModel.setFrom(transport.getFrom());
        bookingsModel.setTo(transport.getTo());
        bookingsModel.setName(transport.getName());
        bookingsModel.setPax("");
        bookingsModel.setPrice(String.valueOf(transport.getPrice()));
        bookingsModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        bookingsModel.setTicketType("bus");
        bookingsModel.setTime("");

        Intent intent = new Intent(requireActivity(), SelectCarSeatActivity.class);
        intent.putExtra("from",bookingsModel.getFrom());
        intent.putExtra("to",bookingsModel.getTo());
        intent.putExtra("name",bookingsModel.getName());
        intent.putExtra("price",Integer.parseInt(bookingsModel.getPrice()));
        intent.putExtra("userId",bookingsModel.getUserId());
        intent.putExtra("type",bookingsModel.getTicketType());

        showBottomSheetDialog(bookingsModel , intent, transport.getPrice());
    }
    private void showBottomSheetDialog(BookingsModel bookingsModel, Intent intent, int price) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.get_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        MaterialButton bottomSheetButton = bottomSheetView.findViewById(R.id.done);
        TextInputLayout pax = bottomSheetView.findViewById(R.id.textInputLayout4);
        TextInputLayout dateInput = bottomSheetView.findViewById(R.id.textInputLayout6);
        TextView totals = bottomSheetView.findViewById(R.id.textInputLayout7);
        final int[] total = {price};

        dateInput.setEndIconOnClickListener(view->{
            showDatePicker(dateInput);
        });
        dateInput.getEditText().setText(date.getEditText().getText().toString());


        pax.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString())){
                    total[0] = Integer.parseInt(s.toString()) * price;
                    totals.setText("Total : RM " + total[0]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(pax.getEditText().getText().toString())){
                    pax.setError("Enter no of travellers");
                } else if (TextUtils.isEmpty(dateInput.getEditText().getText().toString())) {
                    dateInput.setError("Enter date");
                }else if (TextUtils.isEmpty(location.getEditText().getText().toString())) {
                    location.setError("Enter address");
                }else{
                    intent.putExtra("date",dateInput.getEditText().getText().toString());
                    intent.putExtra("time",time.getEditText().getText().toString());
                    intent.putExtra("pax",Integer.parseInt(pax.getEditText().getText().toString()));
                    intent.putExtra("price",total[0]);
                    startActivity(intent);
                }
            }
        });

        bottomSheetDialog.show();
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
    private void showTimePicker(TextInputLayout textInputLayout) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle time selection here
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        Objects.requireNonNull(textInputLayout.getEditText()).setText(selectedTime);
                    }
                },
                hourOfDay, minute, true); // true for 24-hour format

        timePickerDialog.show();
    }
}