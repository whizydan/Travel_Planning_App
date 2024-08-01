package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.travel.MainActivity;
import com.kerberos.travel.R;
import com.kerberos.travel.database.Database;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton signIn = findViewById(R.id.materialButton);
        TextInputLayout password = findViewById(R.id.password);
        TextInputLayout email = findViewById(R.id.email);
        TextView reset = findViewById(R.id.reset);
        TextView signUp = findViewById(R.id.sign_up);
        TextView terms = findViewById(R.id.terms);

        terms.setOnClickListener(view ->{
            SpannableString conditions = new SpannableString(getResources().getText(R.string.terms));
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Terms & Conditions")
                    .setMessage(conditions)
                    .show();
        });

        signUp.setOnClickListener(view->{
            startActivity(new Intent(this,RegisterActivity.class));
        });
        reset.setOnClickListener(v->{
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        signIn.setOnClickListener(view->{
            Database db = new Database(this);
            if(TextUtils.isEmpty(email.getEditText().getText().toString())){
                email.setError("Enter email");
            }else if(TextUtils.isEmpty(password.getEditText().getText().toString())){
                password.setError("Enter password");
            }else{
                db.login(email.getEditText().getText().toString(),password.getEditText().getText().toString());

            }
        });
    }
}