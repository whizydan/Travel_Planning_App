package com.kerberos.travel.pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.TicketsAdapter;
import com.kerberos.travel.database.TinyDB;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.CardModel;
import com.kerberos.travel.models.TicketsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TicketsActivity extends AppCompatActivity{
    ArrayList<TicketsModel> ticketsModelArrayList;
    TicketsAdapter.ItemClickListener itemClickListener = new TicketsAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            handleClick(view, position);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        ticketsModelArrayList = new ArrayList<>();
        final TicketsAdapter[] adapter = new TicketsAdapter[1];
        RecyclerView tickets = findViewById(R.id.tickets);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ticket").child("bali");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TicketsModel ticket = childSnapshot.getValue(TicketsModel.class);
                    ticketsModelArrayList.add(ticket);
                }
                adapter[0] = new TicketsAdapter(TicketsActivity.this,ticketsModelArrayList);
                adapter[0].setClickListener(itemClickListener);
                tickets.setLayoutManager(new LinearLayoutManager(TicketsActivity.this,LinearLayoutManager.VERTICAL,false));
                tickets.setAdapter(adapter[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void handleClick(View view, int position) {
        TicketsModel ticketsModel = ticketsModelArrayList.get(position);
        BookingsModel bookingsModel = new BookingsModel();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());
        bookingsModel.setDate(formattedDate);
        bookingsModel.setFrom(ticketsModel.getFrom());
        bookingsModel.setTo(ticketsModel.getTo());
        bookingsModel.setName(ticketsModel.getAirline());
        bookingsModel.setPax("");
        bookingsModel.setPrice(String.valueOf(ticketsModel.getPrice()));
        bookingsModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        bookingsModel.setTicketType("flight");
        bookingsModel.setTime(ticketsModel.getDeparture());

        Intent intent = new Intent(this, SelectFlightSeatActivity.class);
        intent.putExtra("date",bookingsModel.getDate());
        intent.putExtra("from",bookingsModel.getFrom());
        intent.putExtra("to",bookingsModel.getTo());
        intent.putExtra("name",bookingsModel.getName());
        intent.putExtra("price",Integer.parseInt(bookingsModel.getPrice()));
        intent.putExtra("userId",bookingsModel.getUserId());
        intent.putExtra("type",bookingsModel.getTicketType());
        intent.putExtra("time",bookingsModel.getTime());

        showBottomSheetDialog(bookingsModel , intent);
    }

    private void showBottomSheetDialog(BookingsModel bookingsModel, Intent intent) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
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
        price.setText(bookingsModel.getPrice() + "RM");

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        bottomSheetDialog.show();
    }
}