package com.example.consumirapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class create_user extends AppCompatActivity {
    EditText tnames,tuser,tpass,trol;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        tnames=findViewById(R.id.exNombres);
        tuser=findViewById(R.id.txUsername);
        tpass=findViewById(R.id.txPassword);
        trol=findViewById(R.id.txRol);
        enviar=findViewById(R.id.btnregistar);

        enviar.setOnClickListener(view -> {
            callRetrofit();
        });

    }
    private void callRetrofit() {
        String names= tnames.getText().toString();
        String username= tuser.getText().toString();
        String password= tpass.getText().toString();
        String rol=trol.getText().toString();
        Bundle datos=getIntent().getExtras();
        String url1=datos.getString("url","lola");


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postRquest postRquest=retrofit.create(com.example.consumirapi.postRquest.class);

        pos postmodel=new pos(names,username,password,rol);

        Call<pos> call=postRquest.postDataintoServer(postmodel);

        call.enqueue(new Callback<pos>() {
            @Override
            public void onResponse(Call<pos> call, Response<pos> response) {
                System.out.println("shdfkdhsbhbhhhhhh"+response.code());
                if(response.code()==200){
                    Toast.makeText(create_user.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(create_user.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(create_user.this, "Usuario invalido, no fue Creado", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(create_user.this,MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<pos> call, Throwable t) {
                Toast.makeText(create_user.this, "No se creo usuario", Toast.LENGTH_SHORT).show();
            }
        });


    }
}