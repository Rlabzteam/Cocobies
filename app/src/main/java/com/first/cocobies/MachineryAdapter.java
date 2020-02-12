package com.first.cocobies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MachineryAdapter extends RecyclerView.Adapter<MachineryAdapter.ViewHolder> {
    List<shopbycategory> data1;
    Context context;
    public MachineryAdapter(List<shopbycategory> data){
        this.data1=data;
    }

    public MachineryAdapter(List<shopbycategory> data, Context context) {
        this.data1=data;
        this.context=context;
    }

    @NonNull
    @Override
    public MachineryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.machinery, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MachineryAdapter.ViewHolder holder, int position) {
        holder.t1.setText(data1.get(position).txt);
        holder.m1.setImageResource(data1.get(position).img);
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        CardView c1;
        ImageView m1,img_bookmark,img_addbookmark;
        public ViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.txttitlemac);
            m1=(ImageView)itemView.findViewById(R.id.imgmac);
            c1=(CardView)itemView.findViewById(R.id.c1);
//            Animation animation1 =AnimationUtils.loadAnimation(getc, R.anim.slidein);
//            c1.startAnimation(animation1);
            img_bookmark=(ImageView)itemView.findViewById(R.id.bookmark_machinery);
            img_addbookmark=(ImageView)itemView.findViewById(R.id.bookmarkadd_machinery);
            String s1="0";
            if (s1.equals("1")) {
                img_bookmark.setVisibility(View.GONE);
                img_addbookmark.setVisibility(View.VISIBLE);
            }
            else {
                img_addbookmark.setVisibility(View.GONE);
                img_bookmark.setVisibility(View.VISIBLE);
            }
            img_addbookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    img_addbookmark.setVisibility(View.GONE);
                    img_bookmark.setVisibility(View.VISIBLE);

                }
            });
            img_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    img_bookmark.setVisibility(View.GONE);
                    img_addbookmark.setVisibility(View.VISIBLE);

                }
            });
            t2=(TextView)itemView.findViewById(R.id.txt_all_process_id);
//            Animation animation1 =
//                    AnimationUtils.loadAnimation(g, R.anim.updown);
//            c1.startAnimation(animation1);
        }
    }
}

