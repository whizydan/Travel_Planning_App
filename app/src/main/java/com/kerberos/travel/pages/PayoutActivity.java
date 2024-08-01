package com.kerberos.travel.pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.MainActivity;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.CardsAdapter;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.CardModel;
import com.kerberos.travel.models.NotificationModel;
import com.kerberos.travel.models.TicketsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PayoutActivity extends AppCompatActivity{

    String date;
    String from;
    String to;
    String name;
    int price;
    String userId;
    String type;
    String time;
    int pax;

    ArrayList<CardModel> cardModelArrayList;
    private  CardsAdapter.ItemClickListener itemClickListener = new CardsAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            handleClick(view, position);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);
        LinearLayout hide = findViewById(R.id.hide);
        RecyclerView cards = findViewById(R.id.myCards);
        cardModelArrayList =  new ArrayList<>();
        final CardsAdapter[] adapter = new CardsAdapter[1];
        MaterialButton addCard = findViewById(R.id.addCard);
        ImageView back = findViewById(R.id.imageView24);
        MaterialButton bankTransfer = findViewById(R.id.bank);
        MaterialButton wallet = findViewById(R.id.wallet);

        date = getIntent().getStringExtra("date");
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        name = getIntent().getStringExtra("name");
        price = getIntent().getIntExtra("price",0);
        userId = getIntent().getStringExtra("userId");
        type = getIntent().getStringExtra("type");
        time = getIntent().getStringExtra("time");
        pax = getIntent().getIntExtra("pax",1);

        bankTransfer.setOnClickListener(v->{
            bankPayment();
        });

        wallet.setOnClickListener(v->{
            walletPayment();
        });

        back.setOnClickListener(view->{
            super.onBackPressed();
        });

        addCard.setOnClickListener(view ->{
            showSheet();
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cards");
        reference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cardModelArrayList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CardModel card = childSnapshot.getValue(CardModel.class);
                    cardModelArrayList.add(card);
                }
                if(!cardModelArrayList.isEmpty()){
                    hide.setVisibility(View.GONE);
                    cards.setVisibility(View.VISIBLE);
                }

                adapter[0] = new CardsAdapter(PayoutActivity.this,cardModelArrayList);
                cards.setLayoutManager(new LinearLayoutManager(PayoutActivity.this,LinearLayoutManager.VERTICAL,false));
                cards.setAdapter(adapter[0]);
                adapter[0].setClickListener(itemClickListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PayoutActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleClick(View view, int position) {
        view.setBackgroundColor(Color.CYAN);
        //CardModel card = cardModelArrayList.get(position);

        BookingsModel bookingsModel = new BookingsModel();
        bookingsModel.setDate(date);
        bookingsModel.setFrom(from);
        bookingsModel.setTo(to);
        bookingsModel.setName(name);
        bookingsModel.setPax(String.valueOf(pax));
        bookingsModel.setPrice(String.valueOf(price));
        bookingsModel.setUserId(userId);
        bookingsModel.setTicketType(type);
        bookingsModel.setTime(time);

        FirebaseDatabase.getInstance().getReference().child("bookings")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child(String.valueOf(System.currentTimeMillis())).setValue(bookingsModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        saveNotification();
                        //Toast.makeText(PayoutActivity.this,"Booking has been made",Toast.LENGTH_LONG).show();
                        if(bookingsModel.getTicketType().equals("car")){
                            startActivity(new Intent(PayoutActivity.this, ConfrimCarActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(PayoutActivity.this, ProcessedActivity.class));
                            finish();
                        }
                    }
                });
    }
    private void saveNotification(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        NotificationModel notificationModel = new NotificationModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        Date currentDate = new Date();
        notificationModel.setDate(dateFormat.format(currentDate));
        notificationModel.setId(String.valueOf(System.currentTimeMillis()));
        notificationModel.setType("payment");
        notificationModel.setTitle("Payment made");
        notificationModel.setMessage("A new payment has been completed successfully");

        reference.child(notificationModel.getId()).setValue(notificationModel);
    }
    private void showSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PayoutActivity.this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.activity_add_card, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        MaterialButton done = bottomSheetView.findViewById(R.id.done);
        TextInputLayout name = bottomSheetView.findViewById(R.id.textInputLayout5);
        TextInputLayout number = bottomSheetView.findViewById(R.id.textInputLayout3);
        TextInputLayout month = bottomSheetView.findViewById(R.id.textInputLayout4);
        TextInputLayout year = bottomSheetView.findViewById(R.id.textInputLayout6);
        TextInputLayout cvc = bottomSheetView.findViewById(R.id.textInputLayout7);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getEditText().getText())){
                    name.setError("Enter name");
                }else if(TextUtils.isEmpty(number.getEditText().getText())){
                    number.setError("Enter number");
                }else if(TextUtils.isEmpty(month.getEditText().getText())){
                    month.setError("Enter month");
                }else if(TextUtils.isEmpty(year.getEditText().getText())){
                    year.setError("Enter year");
                }else if(TextUtils.isEmpty(cvc.getEditText().getText())) {
                    cvc.setError("Enter cvc");
                }else{
                    Calendar cal = Calendar.getInstance();
                    CardModel card = new CardModel();
                    card.setName(name.getEditText().getText().toString());
                    card.setCardNumber(number.getEditText().getText().toString());
                    card.setCvc(cvc.getEditText().getText().toString());
                    card.setMonth(month.getEditText().getText().toString());
                    card.setYear(year.getEditText().getText().toString());
                    card.setId(String.valueOf(cal.getTime().getSeconds()));

                    addCard(bottomSheetDialog, card);
                }
            }
        });

        bottomSheetDialog.show();
    }
    private void addCard(BottomSheetDialog bottomSheetDialog, CardModel card){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cards").child(uid);
        card.setUserId(uid);

        reference.child(card.getId()).setValue(card).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PayoutActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(PayoutActivity.this,"Card added",Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void walletPayment(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PayoutActivity.this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.wallet_pay, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextInputLayout provider = bottomSheetView.findViewById(R.id.provider);
        TextInputLayout address = bottomSheetView.findViewById(R.id.walletAddress);
        TextInputLayout username = bottomSheetView.findViewById(R.id.username);
        TextInputLayout sso = bottomSheetView.findViewById(R.id.sso);
        MaterialButton pay = bottomSheetView.findViewById(R.id.pay);

        pay.setOnClickListener(v ->{
            if(TextUtils.isEmpty(provider.getEditText().getText())){
                provider.setError("Enter provider");
            }else if(TextUtils.isEmpty(address.getEditText().getText())){
                address.setError("Enter address");
            }else if(TextUtils.isEmpty(username.getEditText().getText())){
                username.setError("enter your username");
            }else if(TextUtils.isEmpty(sso.getEditText().getText())){
                sso.setError("Enter sso code");
            }else{
                handleClick(bottomSheetView, 0);
            }
        });

        bottomSheetDialog.show();
    }

    private void bankPayment(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PayoutActivity.this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.online_banking, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextInputLayout accountName = bottomSheetView.findViewById(R.id.account_name);
        TextInputLayout accountNumber = bottomSheetView.findViewById(R.id.account_number);
        TextInputLayout priceInput = bottomSheetView.findViewById(R.id.price);
        MaterialButton pay = bottomSheetView.findViewById(R.id.pay);
        Objects.requireNonNull(priceInput.getEditText()).setText(String.valueOf(price));
        priceInput.setEnabled(false);

        pay.setOnClickListener(v ->{
            if(TextUtils.isEmpty(accountName.getEditText().getText())){
                accountName.setError("Enter account name");
            }else if(TextUtils.isEmpty(accountNumber.getEditText().getText())){
                accountNumber.setError("Enter account number");
            }else if(TextUtils.isEmpty(priceInput.getEditText().getText())){
                priceInput.setError("Enter price");
            }else{
                handleClick(bottomSheetView, 0);
            }
        });

        bottomSheetDialog.show();
    }
}