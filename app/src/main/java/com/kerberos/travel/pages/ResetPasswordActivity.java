package com.kerberos.travel.pages;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.travel.R;
import com.kerberos.travel.database.Database;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        MaterialButton reset = findViewById(R.id.materialButton);
        TextInputLayout email = findViewById(R.id.email);
        MaterialButton cancel =  findViewById(R.id.cancel);

        reset.setOnClickListener(v->{
            if(TextUtils.isEmpty(email.getEditText().getText().toString())){
                email.setError("Enter email");
            }else{
                Database db = new Database(ResetPasswordActivity.this);
                db.resetPassword(email.getEditText().getText().toString());
            }
        });

        cancel.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
    }
}