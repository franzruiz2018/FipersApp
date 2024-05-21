package com.franzruiz.fipere1.API;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class API {
    public static  final String BASE_URL ="http://www.fipers.somee.com/";
    //Desarrollo
     //public static  final String BASE_URL ="http://10.0.2.2:44316/";
    private static Retrofit retrofit=null;


    public static Retrofit getAppi(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        if(retrofit==null){


                Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                   .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}
