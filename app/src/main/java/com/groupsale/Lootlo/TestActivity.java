package com.groupsale.Lootlo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.groupsale.Lootlo.models.currentCustomer;
import com.groupsale.Lootlo.models.items;
import com.groupsale.Lootlo.models.products;
import com.groupsale.Lootlo.retrofit.ApiInterface;
import com.groupsale.Lootlo.retrofit.RetrofitClientInstance;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final String key = "Bearer uaakmqwdf0lv08ry66zbwkirgp2jrx9w";

        final Context mContext = TestActivity.this;

        Toast.makeText(mContext, "first", Toast.LENGTH_SHORT).show();

        text = findViewById(R.id.textView23);


        Retrofit retrofit2 = RetrofitClientInstance.getRetrofitInstance();
        Toast.makeText(mContext, "second", Toast.LENGTH_SHORT).show();

        ApiInterface jsonPlaceHolderApi = retrofit2.create(ApiInterface.class);

        jsonPlaceHolderApi.getskuitem(key,"Lootllo803").enqueue(new Callback<items>() {

            @Override
            public void onResponse(Call<items> call, Response<items> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    text.setText(response.message());
                    return;
                }

                Toast.makeText(mContext, "inside", Toast.LENGTH_SHORT).show();

                List<products> products = response.body().getProducts();

                AddtoFirebase(products);





                Toast.makeText(mContext, "fetch" + products.get(0).getPrice() , Toast.LENGTH_SHORT).show();
                text.setText(products.get(0).getAttributes().get(5).getValue().toString());

                for (int i = 0; i < 1; i++)
                {
                    Toast.makeText(mContext, products.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<items> call, Throwable t)
            {
                text.setText("On failure" + t.getMessage());


            }

        });

    }

    private void AddtoFirebase(List<products> products) {



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("dealsDB");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String customerID = user.getUid();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String dealID = formatter.format(date) + customerID;
        dealID = dealID.replaceAll("\\s", "");
        dealID = dealID.replaceAll("/", ":");


        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("pinCode", "201007");
        userMap. put("teamSize", products.get(0).getAttributes().get(5).getValue().toString());
        userMap. put("dealPrice",products.get(0).getAttributes().get(16).getValue().toString());
        userMap. put("dealID", dealID);
        userMap. put("productID,",products.get(0).getSku());
        userMap. put("textMessage", "Hi this is dummy message to add " +products.get(0).getName());
        userMap. put("description", products.get(0).getAttributes().get(12).getValue().toString());
        userMap. put("name", products.get(0).getName());
        userMap. put("creatorID", customerID);
        userMap.put("ImageUrl",products.get(0).getImages());
        ref.child(dealID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Deal added successfully", Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}