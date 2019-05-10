package com.prabakaran_g.dream11star;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private AdView adView;

    Button screenshot,matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-9096560946069188~5111774388");

        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);





        screenshot = (Button)findViewById(R.id.WINNINGSCREENSHOTS);
        matches = (Button)findViewById(R.id.GOBUTTON);




        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent screenshots = new Intent(MainActivity.this,Screeshots.class);
                startActivity(screenshots);

            }
        });


        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchesh = new Intent(MainActivity.this,PostsListActivity.class);
                startActivity(matchesh);
            }
        });



    }
}
