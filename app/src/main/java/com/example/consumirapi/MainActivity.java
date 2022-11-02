package com.example.consumirapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtTitle, txtBody;
    Button btnEnviar;
    List<dataclass> contactList;
    public String url1 = "https://647f-186-144-129-49.ngrok.io";

    //prueba11


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btnEnviar = findViewById(R.id.btnEnviar);
        contactList=new ArrayList<>();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LeerWs();
                //enviarWs("nuevito","nuevito69","456","novato");
                //enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //eliminarWs();
                //consumirServicio();
                //callRetrofit();
                callRetrofitPUT();
            }
        });
    }

    private void callRetrofitPUT() {
            /*String nombre = editNombre.getText().toString();
            String userName = editUserName.getText().toString();
            String password = editPassword.getText().toString();
            String rol = editRol.getText().toString();*/
        String nombre = "PUTfunciona2";
        String userName = "PUTfunciona2";
        String password = "PUTfunciona2";
        String rol = "rolfuncionaput2";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postRquest apiUser = retrofit.create(postRquest.class);
            //Pasar datos modificados
            pos data = new pos(nombre,userName,password,rol);
            Call<pos> call = apiUser.updateDatos("8",data);
            call.enqueue(new Callback<pos>() {
                @Override
                public void onResponse(Call<pos> call, retrofit2.Response<pos> response) {
                    System.out.println("Codigo Update: " + response.code());
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Usuario modificado", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(ModificarActivity.this, MainActivity.class);
                        //startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<pos> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "ERROR DE MODIFICACION", Toast.LENGTH_SHORT).show();
                    System.out.println("ERROR: " + t.getMessage());
                }
            });
    }

    private void callRetrofit() {
        String names= "nuevo";
        String username= "nuevin66";
        String password= "23455";
        String rol="user";


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postRquest postRquest=retrofit.create(com.example.consumirapi.postRquest.class);

        pos postmodel=new pos(names,username,password,rol);

        Call<pos> call=postRquest.postDataintoServer(postmodel);

        call.enqueue(new Callback<pos>() {
            @Override
            public void onResponse(Call<pos> call, retrofit2.Response<pos> response) {
                 txtBody.setText(response.body().username);
                Toast.makeText(MainActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<pos> call, Throwable t) {

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

                    txtUser.setText(contactList.get(2).getNames());
                    //String title = jsonObject.getString("date");
                    txtTitle.setText(contactList.get(2).getUsername());
                    txtBody.setText(contactList.get(2).getId());
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
    @Headers({
            "Content-Type:application/json",
            "Content-Length:<calculated when request is sent>",
            "Postman-Token:<calculated when request is sent>",
            "code-app:2022*01"
    })

    private void enviarWs(final String names, final String username, final String password,final String rol) {




        StringRequest postResquest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "RESULTADO POST = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Error", error.getMessage());
                }catch (Exception e){

                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("names", names);
                params.put("username", username);
                params.put("password", password);
                params.put("rol", rol);

                System.out.println("fffffffffff"+params);
                return params;
                /*"names": "TerceroHugo",
    "username": "Hugo05",
    "password": "12345",
    "rol": "user"*/
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void actualizarWs(final String title, final String body, final String userId) {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postResquest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1");
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void eliminarWs() {

        String url = url1+"/api/users/7";

        StringRequest postResquest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}