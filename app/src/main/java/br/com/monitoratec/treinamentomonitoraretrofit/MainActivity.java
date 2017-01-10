package br.com.monitoratec.treinamentomonitoraretrofit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubStatusApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtStatus;
    private ImageView imgGitHub;
    private TextInputLayout txtUsername;
    private TextInputLayout txtPassword;
    private GitHubStatusApi statusApiImpl;
    private AppCompatButton btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txt_status);
        imgGitHub = (ImageView) findViewById(R.id.ic_github);
        txtUsername = (TextInputLayout) findViewById(R.id.tilUsername);
        txtPassword= (TextInputLayout) findViewById(R.id.tilPassword);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFilledFields()){
                    makeRequest();
                }
            }
        });

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        statusApiImpl = retrofit.create(GitHubStatusApi.class);
    }

    private void makeRequest() {
    }

    private boolean checkFilledFields() {
        if(!txtUsername.getEditText().getText().toString().trim().equals("") &&
                !txtPassword.getEditText().getText().toString().trim().equals("")){
            return true;
        }else{
            txtUsername.setError("O campo não pode estar vazio");
            txtPassword.setError("O campo não pode estar vazio");
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGitHubStatus();
        }

    private void checkGitHubStatus() {

        //SINCRONO
        /*try {
            Status body = statusApiImpl.lastMessage().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //ASSINCRONO
        statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.isSuccessful()){
                    Status status = response.body();
                    setColors(status.getType().getColorRes() , status.getBody());
                }else{
                    try {
                        String errorBody = response.errorBody().string();
                        setColors(Status.Type.MAJOR.getColorRes(),errorBody);
                    } catch (IOException e) {
                        Log.e(TAG,e.toString(),e);
                    }

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                setColors(Status.Type.MAJOR.getColorRes(),call.toString());
            }
        });
    }

    private void setColors(int colorRes, String msg) {
        txtStatus.setText(msg);
        int color = ContextCompat.getColor(MainActivity.this,colorRes);
        txtStatus.setTextColor(color); //Setar cor
        DrawableCompat.setTint(imgGitHub.getDrawable(),color); //set tint compat!

    }
}
