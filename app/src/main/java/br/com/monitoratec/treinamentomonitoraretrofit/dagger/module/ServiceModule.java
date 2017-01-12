package br.com.monitoratec.treinamentomonitoraretrofit.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubOauthApi;
import br.com.monitoratec.treinamentomonitoraretrofit.entity.GitHubStatusApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.NetworkModule.RETROFIT_GITHUB;
import static br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.NetworkModule.RETROFIT_GITHUB_OAUTH;
import static br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.NetworkModule.RETROFIT_GITHUB_STATUS;

/**
 * Created by lucasfranco on 12/01/17.
 */

@Module
public class ServiceModule {

    @Singleton
    @Provides
    GitHubApi providesGitHub(
            @Named(RETROFIT_GITHUB) Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @Provides
    GitHubStatusApi providesGitHubStatus(
            @Named(RETROFIT_GITHUB_STATUS) Retrofit retrofit) {
        return retrofit.create(GitHubStatusApi.class);
    }

    @Singleton
    @Provides
    GitHubOauthApi providesGitHubOAuth(
            @Named(RETROFIT_GITHUB_OAUTH) Retrofit retrofit) {
        return retrofit.create(GitHubOauthApi.class);
    }

}
