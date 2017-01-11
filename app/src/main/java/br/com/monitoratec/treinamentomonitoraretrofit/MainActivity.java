package br.com.monitoratec.treinamentomonitoraretrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.monitoratec.treinamentomonitoraretrofit.entity.AccessToken;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubOauthApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubStatusApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.Status;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.txt_status)                  TextView txtStatus;
    @BindView(R.id.ic_github)                   ImageView imgGitHub;
    @BindView(R.id.tilUsername)                 TextInputLayout txtUsername;
    @BindView(R.id.tilPassword)                 TextInputLayout txtPassword;
    @BindView(R.id.btn_loginOauth)              AppCompatButton btnOauth;



    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubStatusApi statusApiImpl;
    private GitHubApi ApiImpl;
    private SharedPreferences sharedPreferences;
    private GitHubOauthApi gitHubOauthApi;


    @OnClick(R.id.btn_login)
    public void onAuthclick(View v){
        if(AppUtils.checkFilledFields(MainActivity.this,txtUsername,txtPassword)){
            makeRequest(v);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.call:{
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "+55169090993870941"));
                startActivity(intent);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnOauth.setOnClickListener(v -> {
            final String baseUrl = GitHubOauthApi.BASE_URL + "authorize";
            final String clientId = getString(R.string.oauth_client_id);
            final String redirectUri = getOAuthRedirectUri();
            final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        statusApiImpl = GitHubStatusApi.RETROFIT.create(GitHubStatusApi.class);
        ApiImpl = GitHubApi.RETROFIT.create(GitHubApi.class);
        gitHubOauthApi = GitHubOauthApi.RETROFIT.create(GitHubOauthApi.class);
        sharedPreferences = getSharedPreferences(getString(R.string.sp_file), MODE_PRIVATE);

    }

    private void processOAuthRedirectUri() {
        // Os intent-filter's permitem a interação com o ACTION_VIEW
        final Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
            String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);
                gitHubOauthApi.accessToken(clientId,clientSecret,code).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if(response.isSuccessful()){
                            AccessToken accessToken = response.body();
                            String credentialKey = getString(R.string.sp_credential);
                            sharedPreferences.edit().putString(credentialKey,accessToken.getAuthCredential()).apply();
                            Snackbar.make(btnOauth,accessToken.access_token,Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {

                    }
                });

            } else if (uri.getQueryParameter("error") != null) {
                //TODO Tratar erro
            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }


    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void makeRequest(final View view) {

        String username = txtUsername.getEditText().getText().toString();
        String password = txtPassword.getEditText().getText().toString();
        final String credential = Credentials.basic(username,password);
        ApiImpl.basicAuth(credential).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    sharedPreferences.edit().putString(getString(R.string.sp_credential),credential).apply();
                    sharedPreferences.getString(getString(R.string.sp_credential),"");
                    Snackbar.make(view,response.body().getLogin(),Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG,"erro");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGitHubStatus();
        processOAuthRedirectUri();
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
