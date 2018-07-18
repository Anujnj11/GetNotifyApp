package com.getnotify;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ConsumeAPI {

    private static final String Key = "AIzaSyAkL7MteqiUjMsqjdLIkMWEMEKlmmzXpfQ";
    private static final String url = "https://notifyapi.herokuapp.com/";
    private static final String Socketurl = "https://socketnotifymeapi.herokuapp.com/";

    public static IRetrofit objIRetrofit = null;
    public static IRetrofit objSocketIRetrofit = null;

    public static IRetrofit postLog() {
        if (objIRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Socketurl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            objIRetrofit = retrofit.create(IRetrofit.class);
        }
        return objIRetrofit;
    }

    public static IRetrofit postSocketLog() {
        if (objSocketIRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Socketurl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            objSocketIRetrofit = retrofit.create(IRetrofit.class);
        }
        return objSocketIRetrofit;
    }


    public interface IRetrofit {


//        @Headers({"Content-Type:application/json"})
//        @POST("/api/postLogDetails")
//        public Call<LogDetailResponse> postLogDetails(@Body UserDetails ObjUserDetails);

        @Headers({"Content-Type:application/json"})
        @POST("/api/postAESLogDetails")
        public Call<LogDetailResponse> postAESLogDetails(@Body UserDetails ObjUserDetails);

        @Headers({"Content-Type:application/json"})
        @POST("/postAESLogDetails")
        public Call<LogDetailResponse> postSockectAESLogDetails(@Body UserDetails ObjUserDetails);

        @Headers({"Content-Type:application/json"})
        @POST("/api/getencrptoken")
        public Call<GetEncrptokenRes> GetAESToken(@Body UserAESTokenReq ObjUserAESTokenReq);

        @Headers({"Content-Type:application/json"})
        @POST("/api/postExceptionDetails")
        public Call PostException(@Body String Exception);
    }
}

