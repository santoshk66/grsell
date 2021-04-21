package com.groupsale.Lootlo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.groupsale.Lootlo.models.items;
import com.groupsale.Lootlo.models.products;
import com.groupsale.Lootlo.retrofit.ApiInterface;
import com.groupsale.Lootlo.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Splash extends AppCompatActivity {

    ImageView ivTop,ivShop,ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay =200;
    Handler handler = new Handler();
    SharedPreferences introScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivTop = findViewById(R.id.iv_top);
        ivBottom = findViewById(R.id.iv_bottom);
        ivShop = findViewById(R.id.iv_Shop);
        textView = findViewById(R.id.text_view);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.top_wave);

        ivTop.setAnimation(animation1);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivShop,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)
        );
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        animatText("Grousale");


        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.bottom_wave);

        ivBottom.setAnimation(animation2);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                introScreen = getSharedPreferences("introScreen",MODE_PRIVATE);
                boolean isFirstTime = introScreen.getBoolean("firstTime",true);



               if(isFirstTime) {

                   SharedPreferences.Editor editor = introScreen.edit();
                   editor.putBoolean("firstTime",false);
                   editor.commit();

    startActivity(new Intent(Splash.this, introActivity.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    finish();
             }
               else
               {
                   startActivity(new Intent(Splash.this, MainActivity.class)
                           .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
               }

            }
        },4000);

    }
Runnable runnable = new Runnable() {
    @Override
    public void run() {
        textView.setText(charSequence.subSequence(0,index++));


        if(index <= charSequence.length())
        {
            handler.postDelayed(runnable,delay);

        }
    }
};
    public void animatText(CharSequence cs)
    {
        charSequence =cs;
       index = 0;
       textView.setText("");
       handler.removeCallbacks(runnable);
       handler.postDelayed(runnable,delay);

    }

    }

