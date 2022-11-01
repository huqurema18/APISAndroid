package com.example.consumirapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface postRquest {
    @Headers({
            "Content-Type:application/json",
            "Content-Length:<calculated when request is sent>",
            "Postman-Token:<calculated when request is sent>",
            "code-app:2022*01"
    })
    //https://084f-186-144-129-49.ngrok.io
@POST("/api/users")
Call<pos> postDataintoServer(@Body pos postmodel);

}
