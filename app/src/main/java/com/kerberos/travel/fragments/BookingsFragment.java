package com.kerberos.travel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.BusAdapter;
import com.kerberos.travel.adapters.CarAdapter;
import com.kerberos.travel.adapters.FerryAdapter;
import com.kerberos.travel.adapters.FlightAdapter;
import com.kerberos.travel.adapters.HotelAdapter;
import com.kerberos.travel.adapters.TrainAdapter;
import com.kerberos.travel.models.BookingsModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    ArrayList<BookingsModel> hotels;
    ArrayList<BookingsModel> ferries;
    private String mParam2;
    RecyclerView recyclerView;
    BusAdapter busAdapter;
    CarAdapter adapter;
    FerryAdapter ferryAdapter;
    HotelAdapter hotelAdapter;
    FlightAdapter flightAdapter;
    TrainAdapter trainAdapter;
    ArrayList<BookingsModel> trains;
    ArrayList<BookingsModel> flights;
    ArrayList<BookingsModel> cars;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BookingsModel> buses;

    public BookingsFragment() {
    }
    // TODO: Rename and change types and number of parameters
    public static BookingsFragment newInstance(String param1, String param2) {
        BookingsFragment fragment = new BookingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        ImageView back = view.findViewById(R.id.imageView26);
        Spinner spinner = view.findViewById(R.id.spinner);
        layoutManager = new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false);
        buses = new ArrayList<>();
        trains = new ArrayList<>();
        flights = new ArrayList<>();
        cars = new ArrayList<>();
        ferries = new ArrayList<>();
        hotels = new ArrayList<>();

        String[] items = new String[]{"Attraction","hotel"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, items);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);


        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new ProfileFragment()).commit();
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("bookings");
        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BookingsModel> bookingsModels = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    BookingsModel ticket = childSnapshot.getValue(BookingsModel.class);
                    //if(isDateWithin7Days(ticket.getDate())){
                    //parse the date and only show the up to 7 days forward
                    if(ticket.getTicketType().equals("bus")){
                        buses.add(ticket);
                    }else if(ticket.getTicketType().equals("car")){
                        cars.add(ticket);
                    }else if(ticket.getTicketType().equals("ferry")){
                        ferries.add(ticket);
                    }else if(ticket.getTicketType().equals("Attraction")){
                        trains.add(ticket);
                    }else if(ticket.getTicketType().equals("hotel")){
                        hotels.add(ticket);
                    }else if(ticket.getTicketType().equals("flight")){
                        flights.add(ticket);
                    }
                    // }
                }
                busAdapter = new BusAdapter(requireActivity(),buses);
                flightAdapter = new FlightAdapter(requireActivity(), flights);
                adapter = new CarAdapter(requireActivity(), cars);
                ferryAdapter = new FerryAdapter(requireActivity(), ferries);
                trainAdapter = new TrainAdapter(requireActivity(), trains);
                hotelAdapter = new HotelAdapter(requireActivity(), hotels);

                setTrainView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here
                String selectedItem = (String) parent.getItemAtPosition(position);
                switch (selectedItem){
                    case "bus":
                        setBusView();
                        break;
                    case "flight":
                        setFlightView();
                        break;
                    case "car":
                        setCarView();
                        break;
                    case "ferry":
                        setFerryView();
                        break;
                    case "Attraction":
                        setTrainView();
                        break;
                    case "hotel":
                        setHotelView();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no item selected
            }
        });

    }
    public static boolean isDateWithin7Days(String inputDate) {
        // Define date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Parse the input date
            Date providedDate = dateFormat.parse(inputDate);

            // Get today's date
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(new Date());

            // Add 7 days to today's date
            currentDate.add(Calendar.DAY_OF_MONTH, 7);

            // Check if the provided date is before 7 days from today
            return providedDate.before(currentDate.getTime());
        } catch (ParseException e) {
            // Handle parsing errors or invalid input
            e.printStackTrace();
            return true;
        }
    }
    void setBusView(){
        busAdapter = new BusAdapter(requireActivity(),buses);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(busAdapter);
    }
    void setTrainView(){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TrainAdapter(requireActivity(),trains));
    }
    void setCarView(){
        adapter = new CarAdapter(requireActivity(),cars);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    void setFerryView(){
        ferryAdapter = new FerryAdapter(requireActivity(),ferries);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ferryAdapter);
    }
    void setHotelView(){
        hotelAdapter = new HotelAdapter(requireActivity(),hotels);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(hotelAdapter);
    }
    void  setFlightView(){
        flightAdapter = new FlightAdapter(requireActivity(),flights);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(flightAdapter);
    }
}