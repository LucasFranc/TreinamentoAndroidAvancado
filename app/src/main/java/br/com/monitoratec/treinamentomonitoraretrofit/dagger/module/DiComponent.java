package br.com.monitoratec.treinamentomonitoraretrofit.dagger.module;

import javax.inject.Singleton;

import br.com.monitoratec.treinamentomonitoraretrofit.MainActivity;
import dagger.Component;

/**
 * Created by lucasfranco on 12/01/17.
 */


    @Singleton
    @Component(modules = {
            ApplicationModule.class,
            PreferenceModule.class,
            NetworkModule.class,
            ServiceModule.class
    })
    public interface DiComponent {
        void inject(MainActivity activity);
    }

