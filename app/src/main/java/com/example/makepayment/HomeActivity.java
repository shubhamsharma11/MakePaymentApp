package com.example.makepayment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makepayment.models.History;
import com.example.makepayment.models.Merchant;
import com.example.makepayment.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dbHistory, dbSetting, dbUser;

    private User user = null;
    private Merchant merchant = null;
    private History history = null;
    private ArrayList<History> transactHistory = null;

    String phoneNumber;

    ProgressBar pbLoadHistory;
    TextView tvAmount, tvAddress, tvDate, tvGreeting;
    CardView infoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialization();

        dbHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    if(snap.getKey().trim().equals(phoneNumber.trim()))
                    {
                        for (DataSnapshot sp : snap.getChildren()) {
                            history = sp.getValue(History.class);
                            if(history != null && history.status)
                            {
                                infoCard.setVisibility(View.VISIBLE);
                            }
                            else if(history != null)
                            {
                                transactHistory.add(history);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Fail to get data from database.", Toast.LENGTH_SHORT).show();
            }
        });

        dbUser.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    if(snap.getValue(User.class).phoneNumber.trim().equals(phoneNumber.trim()))
                    {
                        user = snap.getValue(User.class);
                        tvGreeting.setText(new StringBuilder().append("Hi ").append(user.userName).toString());
                        tvAddress.setText(user.address);
                        tvAmount.setText(new StringBuilder().append("\u20B9 ").append(user.amount).toString());
                        tvDate.setText(user.date);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Fail to get data from database.", Toast.LENGTH_SHORT).show();
            }
        });

        dbSetting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                merchant = snapshot.getValue(Merchant.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initialization(){
        pbLoadHistory = findViewById(R.id.loadHistory);
        tvAmount = findViewById(R.id.tvHomeAmt);
        tvAddress = findViewById(R.id.tvHomeAddress);
        tvDate = findViewById(R.id.tvHomeDate);
        tvGreeting = findViewById(R.id.tvHomeGreeting);
        infoCard = findViewById(R.id.infoCard);
        infoCard.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbHistory = database.getReference("History");
        dbSetting = database.getReference("Merchant");
        dbUser = database.getReference("Users");
        transactHistory = new ArrayList<>();
    }

    public void onHistoryClick(View view) {
        Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
        intent.putExtra("transactHistory", transactHistory);
        startActivity(intent);
    }

    public void onPayUpiClick(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("HistoryData", history);
        intent.putExtra("UserData", user);
        intent.putExtra("MerchantData", merchant);
        startActivity(intent);
    }
}