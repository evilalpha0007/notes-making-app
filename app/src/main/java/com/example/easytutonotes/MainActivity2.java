package com.example.easytutonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity2 extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lott;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        appname=findViewById(R.id.appnames);
        lott=findViewById(R.id.lottie);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
            }
        },3000);

    }
}