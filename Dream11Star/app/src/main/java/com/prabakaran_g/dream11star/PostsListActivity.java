package com.prabakaran_g.dream11star;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class PostsListActivity extends AppCompatActivity {

    private Toolbar matchToolbar;


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
        setContentView(R.layout.activity_posts_list);


        AdView adView3 = findViewById(R.id.adView3);
        MobileAds.initialize(this,"ca-app-pub-9096560946069188~5111774388");

        AdRequest adRequest3=new AdRequest.Builder().build();
        adView3.loadAd(adRequest3);





        matchToolbar = (Toolbar)findViewById(R.id.matches_page_toolbar);
        setSupportActionBar(matchToolbar);
        getSupportActionBar().setTitle("Dream11Star Teams");
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




        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);




        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Data");

        showData();


    }




    private void showData()
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



    private void firebaseSearch(String searchText)
    {

        String query = searchText.toLowerCase();


        Query firebaseSearchQuery = mRef.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery, Model.class).build();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                firebaseSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                firebaseSearch(s);
                return false;
            }



        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        if (id == R.id.action_sort)
        {
            showSortDialog();


            return true;
        }

        if (id == R.id.action_add)
        {
            Intent intent = new Intent(PostsListActivity.this,AdminLoginActivity.class);
            startActivity(intent);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }




    private void showSortDialog()
    {
        String[] sortOptions = {"Newest", "Updated"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if(i==0)
                        {
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "newest");
                            editor.apply();
                            recreate();

                        }
                        else if(i==1)
                        {
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "oldest");
                            editor.apply();
                            recreate();


                        }


                    }
                });

        builder.show();


    }




}


