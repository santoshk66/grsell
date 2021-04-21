package com.groupsale.Lootlo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.groupsale.Lootlo.comon.CountryData;
import com.groupsale.Lootlo.models.currentCustomer;
import com.groupsale.Lootlo.models.customer;
import com.scwang.wave.MultiWaveHeader;

public class Register extends AppCompatActivity {
    private Spinner spinner;
    private EditText Username,PhNumber,pincode;

    MultiWaveHeader waveFooter;
    MultiWaveHeader waveHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        waveHeader = findViewById(R.id.wave_header);
        waveFooter = findViewById(R.id.wave_footer);

        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.CYAN);
        waveHeader.setCloseColor(Color.BLUE);

        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.RED);
        waveFooter.setCloseColor(Color.YELLOW);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        Username = findViewById(R.id.usname);  // Username
        PhNumber = findViewById(R.id.editTextPhone); //Phone number
        pincode = findViewById(R.id.Deliverypincode); // delivery Pincodes

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = PhNumber.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    PhNumber.setError("Valid number is required");
                    PhNumber.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(Username.getText()) ) {
                    Username.setError("Name is required");
                    Username.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(pincode.getText()) ) {
                    pincode.setError("Name is required");
                    pincode.requestFocus();
                    return;
                }

                String phonenumber = "+" + code + number;

                Intent intent = new Intent(Register.this, OTP.class);
                intent.putExtra("phonenumber", phonenumber);
                intent.putExtra("username", Username.getText().toString());
                intent.putExtra("pincode", pincode.getText().toString());
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String customerID = FirebaseAuth.getInstance().getCurrentUser().getUid() ;

            updateCurrentUser(customerID);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    private void updateCurrentUser(String customerID) {
        currentCustomer currentCustomer = null;
        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("customerDB");
        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(customerID).exists()) {

                    currentCustomer.currentUser = dataSnapshot.child(customerID).getValue(customer.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Error: " +databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}