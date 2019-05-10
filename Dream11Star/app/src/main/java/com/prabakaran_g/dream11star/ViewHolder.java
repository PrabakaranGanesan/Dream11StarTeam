package com.prabakaran_g.dream11star;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);




        mView = itemView;


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });




    }

    public  void setDetails(Context ctx, String title, String description, String image)
    {
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageIv = mView.findViewById(R.id.rImageView);
     //  AdView adView3 = mView.findViewById(R.id.adView3);
      //  MobileAds.initialize(this,"ca-app-pub-9096560946069188~5111774388");

     //   AdRequest adRequest3=new AdRequest.Builder().build();
     //   adView3.loadAd(adRequest3);



        mTitleTv.setText(title);
        mDetailTv.setText(description);
        Picasso.get().load(image).into(mImageIv);



    }


    private ViewHolder.ClickListener mClickListener;


    public interface ClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);

    }

    public void setonClickListener(ViewHolder.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }



}
