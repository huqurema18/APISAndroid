package com.example.consumirapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    Button btnEnviar;
    List<dataclass> contactList;
    RecyclerView recyclerView;
    public String url1 = "https://647f-186-144-129-49.ngrok.io";

    //prueba11


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnviar = findViewById(R.id.btnEnviar);
        contactList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        LeerWs();
        btnEnviar.setOnClickListener(view -> {
            Intent intent=new Intent(this,create_user.class);
            intent.putExtra("url",url1);
            startActivity(intent);
        });
    }

    public void callRetrofitPUT(String id, Context context, String names, String user, String pass, String ro) {

        String nombre = names;
        String userName = user;
        String password = pass;
        String rol = ro;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postRquest apiUser = retrofit.create(postRquest.class);
            //Pasar datos modificados
            pos data = new pos(nombre,userName,password,rol);
            Call<pos> call = apiUser.updateDatos(id,data);
            call.enqueue(new Callback<pos>() {
                @Override
                public void onResponse(Call<pos> call, retrofit2.Response<pos> response) {
                    System.out.println("Codigo Update: " + response.code());
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Usuario modificado", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(ModificarActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<pos> call, Throwable t) {
                    Toast.makeText(context, "ERROR DE MODIFICACION", Toast.LENGTH_SHORT).show();
                    System.out.println("ERROR: " + t.getMessage());
                }
            });
    }



    private void consumirServicio() {
        // ahora ejecutaremos el hilo creado
        String names= "nuevo";
        String username= "nuevin66";
        String password= "23455";
        String rol="user";


        ServicioTask servicioTask= new ServicioTask(this,url1,names,username,password,rol);
        servicioTask.execute();


    }

    private void LeerWs() {

        //String url = "https://jsonplaceholder.typicode.com/posts/1";
        //String url="http://192.168.4.1:8080/api/users";
        String url =url1+"/api/users";
        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 =jsonArray.getJSONObject(i);

                        dataclass model = new dataclass();
                        model.setNames(jsonObject1.getString("names"));
                        model.setUsername(jsonObject1.getString("username"));
                        model.setRol(jsonObject1.getString("rol"));
                        model.setId(jsonObject1.getString("id"));

                        contactList.add(model);
                    }
                    putDataIntoRecyclerView(contactList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtUser.setText(jsonObject.getString("data"));
                    String title = jsonObject.getString("date");
                    txtTitle.setText(title);
                    txtBody.setText(jsonObject.getString("rol"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void putDataIntoRecyclerView(List<dataclass> contactList) {
        ContactAdapter adapery=new ContactAdapter(this, contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapery);
    }



    public void eliminarWs(String id, Context context) {

        String url = url1+"/api/users/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context, "RESULTADO = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(context).add(postResquest);
    }
}