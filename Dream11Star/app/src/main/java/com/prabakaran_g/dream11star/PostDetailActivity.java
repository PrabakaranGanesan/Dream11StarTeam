package com.prabakaran_g.dream11star;





import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {

    private Toolbar matchToolbar;


    TextView mTitleTv, mDetailTv;
    ImageView mImageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        AdView adView2 = findViewById(R.id.adView2);
        MobileAds.initialize(this,"ca-app-pub-9096560946069188~5111774388");

        AdRequest adRequest2=new AdRequest.Builder().build();
        adView2.loadAd(adRequest2);


        matchToolbar = (Toolbar)findViewById(R.id.postdetail_page_toolbar);
        setSupportActionBar(matchToolbar);
        getSupportActionBar().setTitle("Dream11Star Teams");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);






        mTitleTv = findViewById(R.id.titleTv);
        mDetailTv = findViewById(R.id.descriptionTv);
        mImageIv = findViewById(R.id.imageView);


        String image = getIntent().getStringExtra("image");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");


        mTitleTv.setText(title);
        mDetailTv.setText(desc);
        Picasso .get().load(image).into(mImageIv);



    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }



}


