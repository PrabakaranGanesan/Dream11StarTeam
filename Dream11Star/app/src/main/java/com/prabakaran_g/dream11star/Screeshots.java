package com.prabakaran_g.dream11star;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Screeshots extends AppCompatActivity {

    private Toolbar screenToolbar;


    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;

    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screeshots);

        AdView adView4 = findViewById(R.id.adView4);
        MobileAds.initialize(this,"ca-app-pub-9096560946069188~5111774388");

        AdRequest adRequest4=new AdRequest.Builder().build();
        adView4.loadAd(adRequest4);



        screenToolbar = (Toolbar)findViewById(R.id.screenshot_page_toolbar);
        setSupportActionBar(screenToolbar);
        getSupportActionBar().setTitle("Our Users Winning Screenshots");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort","newest");


        if(mSorting.equals("newest"))
        {
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);


        }
        else if (mSorting.equals("oldest"))
        {
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);


        }



        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);




        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Screenshots");

        showscreenshots();

        }


    private void showscreenshots()
    {

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mRef, Model.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model)
            {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

                ViewHolder viewHolder = new ViewHolder(itemView);



                viewHolder.setonClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {


                        String mTitle = getItem(position).getTitle();
                        String mDesc = getItem(position).getDescription();

                        String mImage = getItem(position).getImage();



                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        intent.putExtra("title",mTitle);
                        intent.putExtra("description",mDesc);

                        intent.putExtra("image",mImage);
                        startActivity(intent);



                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return viewHolder;

            }
        };


        mRecyclerView.setLayoutManager(mLayoutManager);
        firebaseRecyclerAdapter.startListening();

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);






    }



    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseRecyclerAdapter != null)
        {
            firebaseRecyclerAdapter.startListening();
        }



    }







}
