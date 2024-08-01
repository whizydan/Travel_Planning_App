package com.kerberos.travel.pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kerberos.travel.R;
import com.kerberos.travel.models.CardModel;

import java.util.Calendar;

public class EditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        TextInputLayout name = findViewById(R.id.textInputLayout5);
        TextInputLayout number = findViewById(R.id.textInputLayout3);
        TextInputLayout month = findViewById(R.id.textInputLayout4);
        TextInputLayout year = findViewById(R.id.textInputLayout6);
        TextInputLayout cvc = findViewById(R.id.textInputLayout7);
        MaterialButton save = findViewById(R.id.done);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ImageView back = findViewById(R.id.imageView27);

        String id = getIntent().getStringExtra("id");
        String cardNo = getIntent().getStringExtra("no");
        String monthValue = getIntent().getStringExtra("month");
        String yearValue = getIntent().getStringExtra("year");
        String cvcValue = getIntent().getStringExtra("cvc");
        String nameValue = getIntent().getStringExtra("name");

        name.getEditText().setText(nameValue);
        number.getEditText().setText(cardNo);
        month.getEditText().setText(monthValue);
        year.getEditText().setText(yearValue);
        cvc.getEditText().setText(cvcValue);

        back.setOnClickListener(view ->{
            super.onBackPressed();
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cards").child(uid);

        save.setOnClickListener(v->{
            if(TextUtils.isEmpty(name.getEditText().getText())){
                name.setError("Enter name");
            }else if(TextUtils.isEmpty(number.getEditText().getText())){
                number.setError("Enter number");
            }else if(TextUtils.isEmpty(month.getEditText().getText())){
                month.setError("Enter month");
            }else if(TextUtils.isEmpty(year.getEditText().getText())){
                year.setError("Enter year");
            }else if(TextUtils.isEmpty(cvc.getEditText().getText())){
                cvc.setError("Enter cvc");
            }else{
                name.setError("");
                number.setError("");
                month.setError("");
                year.setError("");
                cvc.setError("");

                CardModel card = new CardModel();
                card.setName(name.getEditText().getText().toString());
                card.setCardNumber(number.getEditText().getText().toString());
                card.setCvc(cvc.getEditText().getText().toString());
                card.setMonth(month.getEditText().getText().toString());
                card.setYear(year.getEditText().getText().toString());
                card.setUserId(uid);
                card.setId(id);

                reference.child(card.getId()).setValue(card).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditCardActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditCardActivity.this,"Card Updated",Toast.LENGTH_LONG).show();
                        EditCardActivity.super.onBackPressed();
                        finish();
                    }
                });
            }
        });

    }
}