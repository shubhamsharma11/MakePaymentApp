package com.example.makepayment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.makepayment.models.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SendActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private DatabaseReference dbReference;
    private String phoneNumber;

    private EditText etPhone;
    private Button btnSend;
    private ProgressBar progressBar;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mAuth = FirebaseAuth.getInstance();

        etPhone = findViewById(R.id.etPhone);
        btnSend = findViewById(R.id.btnSend);
        progressBar = findViewById(R.id.progressBar);
        phoneNumber = "+91" + etPhone.getText().toString().trim();

        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        users = new ArrayList<>();

        dbReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendActivity.this, "Fail to get data from database.", Toast.LENGTH_SHORT).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                boolean isItemExist = false;

                if(etPhone.getText().toString().trim().isEmpty() || etPhone.getText().toString().trim().length() != 10)
                {
                    Toast.makeText(SendActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }
                else {
                    phoneNumber = "+91" + etPhone.getText().toString().trim();
                    long count = users.stream().filter(x -> x.phoneNumber.trim().equals(phoneNumber)).count();
                    if(count != 0)
                    {
                        sendOTP();
                    }
                    else
                    {
                        Toast.makeText(SendActivity.this, "Mobile number is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendOTP(){
        progressBar.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.INVISIBLE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.INVISIBLE);
                btnSend.setVisibility(View.VISIBLE);
                Toast.makeText(SendActivity.this, "Verification failed, " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(SendActivity.this, "Verification code is sent successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SendActivity.this, VerifyActivity.class);
                intent.putExtra("verificationId", s);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        };

        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallbacks)
                .setActivity(SendActivity.this)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }


}