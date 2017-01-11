package br.com.monitoratec.treinamentomonitoraretrofit.entity;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by lucasfranco on 11/01/17.
 */

public interface GitHubApi {

    String BASE_URL = "https://api.github.com/";


    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    @GET("user")
    Call <User> basicAuth(@Header("Authorization")String credential);


}
