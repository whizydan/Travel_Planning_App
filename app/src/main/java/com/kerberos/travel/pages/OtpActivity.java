package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kerberos.travel.MainActivity;
import com.kerberos.travel.R;

import org.json.JSONArray;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String code;

    private final String URL = "https://sms.360.my/gw/bulk360/v3_0/send.php?user=4openX5b7A&pass=JxHr4ROAe68IqN4ASnPrn7AEjLYNz9itCthxkYcn&to=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();

        TextInputLayout phoneNumberInput = findViewById(R.id.phone);
        Button sendCodeButton = findViewById(R.id.submit);
        TextInputLayout verificationCodeInput = findViewById(R.id.otp);
        Button verifyCodeButton = findViewById(R.id.verify);

        sendCodeButton.setOnClickListener(view -> {
            String phoneNumber = Objects.requireNonNull(phoneNumberInput.getEditText()).getText().toString().trim();

            if (TextUtils.isEmpty(phoneNumber)) {
                phoneNumberInput.setError("Enter phone number");
            } else {
                sendVerificationCode(phoneNumber);
            }
        });

        verifyCodeButton.setOnClickListener(view -> {
            String verificationCode = Objects.requireNonNull(verificationCodeInput.getEditText()).getText().toString().trim();

            if (TextUtils.isEmpty(verificationCode)) {
                verificationCodeInput.setError("Enter verification code");
            } else {
                if(verificationCode.equals(code)){
                    startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    finish();
                }else{
                    verificationCodeInput.setError("Invalid code provided");
                }
            }
        });
    }

    private void generateCode(){
        Random random = new Random();
        code = String.valueOf(random.nextInt(9000) + 1000);
    }

    private void sendVerificationCode(String phone){
        generateCode();
        String queryText = URL + phone + "&from=AEONSales&text=Your+Verification+Code+is+" + code;
        AndroidNetworking.get(queryText)
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        showMessage("Otp sent", "An Otp has been sent to the number provided");
                    }

                    @Override
                    public void onError(ANError anError) {
                        showMessage("Error sending verification code", anError.getErrorDetail());
                    }
                });
    }

    private void showMessage(String title, String message){
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
