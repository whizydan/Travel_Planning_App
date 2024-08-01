package com.kerberos.travel.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.kerberos.travel.pages.SelectFlightSeatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlightsFragment extends Fragment{
    ArrayList<TransportModel> transportList;
    TextInputLayout date;

    String fromValue, toValue, dateValue, personsValue;

    public FlightsFragment() {
        // Required empty public constructor
    }
    public static FlightsFragment newInstance(String param1, String param2) {
        return new FlightsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flights, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        TextInputLayout from = view.findViewById(R.id.from);
        RadioButton multiCity = view.findViewById(R.id.multiCity);
        TextInputLayout to = view.findViewById(R.id.to);
        date = view.findViewById(R.id.date);
        TextInputLayout persons = view.findViewById(R.id.persons);
        MaterialButton search = view.findViewById(R.id.materialButton3);
        transportList = new ArrayList<>();
        ImageView back = view.findViewById(R.id.imageView28);
        final TransportAdapter[] adapter = new TransportAdapter[1];
        AtomicBoolean selected = new AtomicBoolean(false);
        personsValue = "1";

        radioGroup.setOnClickListener(v->{
            if(multiCity.isSelected()){
                selected.set(true);
            }
        });

        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new HomeFragment()).commit();
        });
        date.setEndIconOnClickListener(v->{
            showDatePicker();
        });
        search.setOnClickListener(v ->{
            String fromQuery = from.getEditText().getText().toString();
            String toQuery = to.getEditText().getText().toString();
            String dateQuery = date.getEditText().getText().toString();
            String personsQuery = persons.getEditText().getText().toString();
            String multiCityQuery = String.valueOf(selected.get());

            if(TextUtils.isEmpty(fromQuery)){
                from.setError("Enter starting point");
            }else if(TextUtils.isEmpty(toQuery)){
                to.setError("Enter destination");
            }else if(TextUtils.isEmpty(dateQuery)){
                date.setError("Enter Date");
            }else if(TextUtils.isEmpty(personsQuery)){
                persons.setError("Enter persons");
            }else{
                from.setError("");to.setError("");date.setError("");persons.setError("");
                fromValue = fromQuery;toValue = toQuery;dateValue = dateQuery;personsValue = personsQuery;

                adapter[0].filter(fromQuery, toQuery, dateQuery, personsQuery, multiCityQuery);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("transport");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TransportModel transportModel = childSnapshot.getValue(TransportModel.class);

                    if(transportModel.getType().equals("flight")){
                        transportList.add(transportModel);
                    }
                }
                adapter[0] = new TransportAdapter(requireActivity(),transportList);
                recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),1));
                recyclerView.setAdapter(adapter[0]);

                adapter[0].setClickListener(new TransportAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        onClick(position);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void onClick(int position) {
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
        bookingsModel.setPax(personsValue);
        bookingsModel.setPrice(String.valueOf(transport.getPrice()));
        bookingsModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        bookingsModel.setTicketType("flight");
        bookingsModel.setTime("");

        Intent intent = new Intent(requireActivity(), SelectFlightSeatActivity.class);
        intent.putExtra("date",bookingsModel.getDate());
        intent.putExtra("from",bookingsModel.getFrom());
        intent.putExtra("to",bookingsModel.getTo());
        intent.putExtra("name",bookingsModel.getName());
        intent.putExtra("price",Integer.parseInt(bookingsModel.getPrice()));
        intent.putExtra("userId",bookingsModel.getUserId());
        intent.putExtra("type",bookingsModel.getTicketType());
        intent.putExtra("time",bookingsModel.getTime());
        intent.putExtra("pax",bookingsModel.getPax());

        showBottomSheetDialog(bookingsModel , intent);
    }
    private void showBottomSheetDialog(BookingsModel bookingsModel, Intent intent) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.flight_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        MaterialButton bottomSheetButton = bottomSheetView.findViewById(R.id.button);
        TextView date = bottomSheetView.findViewById(R.id.date);
        TextView from = bottomSheetView.findViewById(R.id.from);
        TextView to = bottomSheetView.findViewById(R.id.to);
        TextView price = bottomSheetView.findViewById(R.id.price);

        date.setText("Departing " + bookingsModel.getDate());
        from.setText(bookingsModel.getFrom());
        to.setText(bookingsModel.getTo());
        price.setText("RM " + bookingsModel.getPrice());

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        bottomSheetDialog.show();
    }
    private void showDatePicker() {
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
                        String selectedDate = dayOfMonth + " " + getMonthName(monthOfYear) + " " + year;
                        Objects.requireNonNull(date.getEditText()).setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month];
    }
}