package com.example.makepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyActivity extends AppCompatActivity {

    private String verificationId, phoneNumber, verificationCode;

    private EditText etNum1, etNum2, etNum3, etNum4, etNum5, etNum6;
    private Button btnVerify;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        Intent intent = getIntent();
        verificationId = intent.getStringExtra("verificationId");
        phoneNumber = intent.getStringExtra("phoneNumber");

        Log.i("verificationId", verificationId);

        ((TextView)findViewById(R.id.tvPhone)).setText(phoneNumber);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        etNum3 = findViewById(R.id.etNum3);
        etNum4 = findViewById(R.id.etNum4);
        etNum5 = findViewById(R.id.etNum5);
        etNum6 = findViewById(R.id.etNum6);

        btnVerify = findViewById(R.id.btnVerify);
        progressBar = findViewById(R.id.progressBar2);
        editTextInput();
    }

    private void editTextInput() {
        etNum1.requestFocus();
        etNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNum2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNum3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNum4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNum5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNum6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnVerify.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void verifyOTP(View view) {
        progressBar.setVisibility(View.VISIBLE);
        btnVerify.setVisibility(View.INVISIBLE);
        if (etNum1.getText().toString().trim().isEmpty() ||
                etNum2.getText().toString().trim().isEmpty() ||
                etNum3.getText().toString().trim().isEmpty() ||
                etNum4.getText().toString().trim().isEmpty() ||
                etNum5.getText().toString().trim().isEmpty() ||
                etNum6.getText().toString().trim().isEmpty())        
        {
            Toast.makeText(VerifyActivity.this, "Invalid verification code.", Toast.LENGTH_SHORT).show();
        }
        else if(verificationId != null) {
            String code = etNum1.getText().toString().trim() +
                    etNum2.getText().toString().trim() +
                    etNum3.getText().toString().trim() +
                    etNum4.getText().toString().trim() +
                    etNum5.getText().toString().trim() +
                    etNum6.getText().toString().trim();
            Log.i("verificationCode", code);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            FirebaseAuth.getInstance()
                    .signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        btnVerify.setVisibility(View.VISIBLE);
                        Toast.makeText(VerifyActivity.this, "Verification completed successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("phoneNumber", phoneNumber);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnVerify.setVisibility(View.VISIBLE);
                        Toast.makeText(VerifyActivity.this, "Invalid verification code.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void resendOTP(View view){
        Toast.makeText(VerifyActivity.this, "OTP sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
}