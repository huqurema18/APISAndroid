package com.example.consumirapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context mContext;
    private List<dataclass> mData;

    public ContactAdapter(Context mContext, List<dataclass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater =LayoutInflater.from(mContext);
        v= inflater.inflate(R.layout.contactsmodels,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(mData.get(position).getId());
        holder.names.setText(mData.get(position).getNames());
        holder.username.setText(mData.get(position).getUsername());
        holder.rol.setText(mData.get(position).getRol());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id,names,username,rol;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.txid);
            names=itemView.findViewById(R.id.txNames);
            username=itemView.findViewById(R.id.txusername);
            rol=itemView.findViewById(R.id.txRol);

        }
    }
}
