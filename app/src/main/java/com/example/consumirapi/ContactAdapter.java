package com.example.consumirapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    Context mContext;
    static List<dataclass> mData;

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
 //---------------------------------------------------
            itemView.setOnClickListener(view -> {
                Context context =view.getContext();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mview = layoutInflaterAndroid.inflate(R.layout.activity_edita_user, null);
                final EditText EditNames =(EditText) mview.findViewById(R.id.edNamesPUT);
                final EditText EditUser =(EditText) mview.findViewById(R.id.edUsernamePUT);
                final EditText EditPassword =(EditText) mview.findViewById(R.id.edPasswordPUT);
                final EditText EditRol =(EditText) mview.findViewById(R.id.edRolPUT);
                final TextView textid =(TextView) mview.findViewById(R.id.textidextract);
                final Button EditInflatDelete=(Button) mview.findViewById(R.id.btnDeleteEditUser);
                final Button EditInflatEdit=(Button) mview.findViewById(R.id.btnEditUser);
                textid.setText(mData.get(getAdapterPosition()).getId()+"");
                EditNames.setText(mData.get(getAdapterPosition()).getNames()+"");
                EditUser.setText(mData.get(getAdapterPosition()).getUsername()+"");
                EditRol.setText(mData.get(getAdapterPosition()).getRol()+"");


                EditInflatEdit.setOnClickListener(view1 -> {
                    MainActivity main=new MainActivity();
                    main.callRetrofitPUT(mData.get(getAdapterPosition()).getId(),context,EditNames.getText().toString(),
                            EditUser.getText().toString(),EditPassword.getText().toString(),EditRol.getText().toString());
                    Intent intent =new Intent(view1.getContext(),MainActivity.class);
                    context.startActivity(intent);
                });
                EditInflatDelete.setOnClickListener(view1 -> {
                    MainActivity main=new MainActivity();
                    main.eliminarWs(mData.get(getAdapterPosition()).getId(),context);
                    Intent intent =new Intent(view1.getContext(),MainActivity.class);
                    context.startActivity(intent);
                });


                builder.setView(mview);
                AlertDialog dialog=builder.create();
                dialog.show();





            });

        }
    }
}
