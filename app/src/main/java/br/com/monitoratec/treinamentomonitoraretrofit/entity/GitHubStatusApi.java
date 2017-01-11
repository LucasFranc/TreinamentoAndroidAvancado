package br.com.monitoratec.treinamentomonitoraretrofit.entity;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by lucasfranco on 09/01/17.
 */

public interface GitHubStatusApi {

    String BASE_URL = "https://status.github.com/api/";

    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();


    @GET("last-message.json")
    Call<Status> lastMessage();
}
