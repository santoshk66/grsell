package com.groupsale.Lootlo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.groupsale.Lootlo.models.currentCustomer;
import com.groupsale.Lootlo.models.customer;
import com.scwang.wave.MultiWaveHeader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;

    MultiWaveHeader waveFooter;
    MultiWaveHeader waveHeader;

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            addtoFirebase();

                            Intent intent = new Intent(OTP.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void addtoFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("customerDB");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String phonenumber = getIntent().getStringExtra("phonenumber");
        String name = getIntent().getStringExtra("username");
        String pincode = getIntent().getStringExtra("pincode");

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, pincode, Toast.LENGTH_SHORT).show();


        String customerID = user.getUid();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        HashMap<String, Object> customerMap = new HashMap<>();
        customerMap. put("pinCode", pincode);
        customerMap. put("name", name);
        customerMap. put("phoneNumber",phonenumber);
        customerMap. put("customerID", customerID);
        customerMap. put("createdAt", formatter.format(date));
        ref.child(customerID).updateChildren(customerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                    updateCurrentUser(customerID);

                }

                else {
                    //TODO take him back to Register page
                    Toast.makeText(getApplicationContext(), "Network Error try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
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

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });
    }
}