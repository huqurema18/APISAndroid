package com.example.consumirapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import retrofit2.http.Headers;

public class ServicioTask extends AsyncTask<Void, Void, String> {
        //variables del hilo
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi="";
        public String linkrequestAPI="";//link  para consumir el servicio rest
        public String names="";
        public String username="";
        public String password="";
        public String rol="";

        //constructor del hilo (Asynctask)
        public ServicioTask(Context ctx, String linkAPI, String names, String  username, String password, String rol ){
            this.httpContext=ctx;
            this.linkrequestAPI=linkAPI;
            this.names=names;
            this.username=username;
            this.password=password;
            this.rol=rol;

        }
        @Headers({
                "Content-Type:application/json",
                "Content-Length:<calculated when request is sent>",
                "Postman-Token:<calculated when request is sent>",
                "code-app:2022*01"
        })
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(httpContext, "Procesando Solicitud", "por favor, espere");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result= null;

            String wsURL = linkrequestAPI;



            URL url = null;
            try {
                // se crea la conexion al api: http://localhost:15009/WEBAPIREST/api/persona
                url = new URL(wsURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //crear el objeto json para enviar por POST
                JSONObject parametrosPost= new JSONObject();
                parametrosPost.put("names",names);
                parametrosPost.put("username",username);
                parametrosPost.put("password",password);
                parametrosPost.put("rol",rol);
                System.out.println("gggggggggggg "+parametrosPost);

                //DEFINIR PARAMETROS DE CONEXION
                urlConnection.setReadTimeout(15000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");// se puede cambiar por delete ,put ,etc
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);


                //OBTENER EL RESULTADO DEL REQUEST
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(parametrosPost));
                writer.flush();
                writer.close();
                os.close();

                int responseCode=urlConnection.getResponseCode();// conexion OK?
                if(responseCode== HttpURLConnection.HTTP_OK){
                    BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuffer sb= new StringBuffer("");
                    String linea="";
                    while ((linea=in.readLine())!= null){
                        sb.append(linea);
                        break;

                    }
                    in.close();
                    result= sb.toString();
                }
                else{
                    result= new String("Error: "+ responseCode);


                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return  result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            resultadoapi=s;
            Toast.makeText(httpContext,resultadoapi, Toast.LENGTH_LONG).show();//mostrara una notificacion con el resultado del request

        }

        //FUNCIONES----------------------------------------------------------------------
        //Transformar JSON Obejct a String *******************************************
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;
            Iterator<String> itr = params.keys();
            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
            return result.toString();
        }

    }

