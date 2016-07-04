package com.arghami.www.clean.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arghami.www.clean.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Soroush on 6/12/2016.
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Photo> data;
    Context c ;
    public PhotoAdapter(@NonNull  List<Photo> data, Context context){
        this.data = data;
        this.c=context;
    }

    public void addData(List<Photo> photo){
        this.data.addAll(photo);
        notifyDataSetChanged();
    }
    public void addData(Photo photo){
        this.data.add(photo);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(c).load(data.get(position).farm.replace("https","http"))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHolder.thumb);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v){
            super(v);
            thumb = (ImageView) v.findViewById(R.id.thumb);
        }
        public ImageView thumb;

    }
}
