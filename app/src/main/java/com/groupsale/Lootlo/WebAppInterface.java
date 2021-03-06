package com.groupsale.Lootlo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.groupsale.Lootlo.models.items;
import com.groupsale.Lootlo.models.products;
import com.groupsale.Lootlo.retrofit.ApiInterface;
import com.groupsale.Lootlo.retrofit.RetrofitClientInstance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WebAppInterface extends Fragment {
    Context mContext;
    String link;
    String key = "Bearer uaakmqwdf0lv08ry66zbwkirgp2jrx9w";


    
    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String sku) {

        Retrofit retrofit2 = RetrofitClientInstance.getRetrofitInstance();


        ApiInterface jsonPlaceHolderApi = retrofit2.create(ApiInterface.class);

        jsonPlaceHolderApi.getskuitem(key,sku).enqueue(new Callback<items>() {

            @Override
            public void onResponse(Call<items> call, Response<items> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<products> products = response.body().getProducts();

                AddtoFirebase(products);


            }

            @Override
            public void onFailure(Call<items> call, Throwable t)
            {
                Toast.makeText(mContext, "Failed: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        Toast.makeText(mContext, sku, Toast.LENGTH_SHORT).show();

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
        userMap. put("dateTime", formatter.format(date));
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

                    Toast.makeText(mContext, "Deal added successfully", Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public WebAppInterface(Deal deal) {
    }

    public void sharelink(String link1) {
        link = link1;
        Toast.makeText(mContext, link1, Toast.LENGTH_SHORT).show();


    }

    public void datapass(int a, int b, int c) {

        Toast.makeText(mContext, String.valueOf(a), Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext, String.valueOf(b), Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext, String.valueOf(c), Toast.LENGTH_SHORT).show();


    }

}



