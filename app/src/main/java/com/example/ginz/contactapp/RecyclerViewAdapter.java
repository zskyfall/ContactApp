package com.example.ginz.contactapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Contact> mListContact;
    private Callback callback;
    private static final int NUMB_ZERO = 0;

    public RecyclerViewAdapter(Context context, Callback callback, List<Contact> listContact){
        this.mContext = context;
        this.mListContact = listContact;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_contact, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.RecyclerViewHolder holder, final int position) {
        holder.textDisplayName.setText(mListContact.get(position).getDisplayName());
        holder.textPhoneNumber.setText(mListContact.get(position).getPhoneNumber());
        holder.imagePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListContact != null){
            return mListContact.size();
        }
        else{
            return NUMB_ZERO;
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView textDisplayName;
        TextView textPhoneNumber;
        ImageView imageStar;
        ImageView imagePhone;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textDisplayName = itemView.findViewById(R.id.text_display_name);
            textPhoneNumber = itemView.findViewById(R.id.text_phone_number);
            imagePhone = itemView.findViewById(R.id.image_phone);
            imageStar = itemView.findViewById(R.id.image_star);
        }
    }

    public interface Callback {
        void onClickListener(int position);
    }
}
